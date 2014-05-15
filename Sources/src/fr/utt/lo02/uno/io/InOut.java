package fr.utt.lo02.uno.io;

import fr.utt.lo02.uno.io.exception.HorsLigneException;
import fr.utt.lo02.uno.io.exception.InvalideException;
import fr.utt.lo02.uno.io.interfaces.Lancable;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Classe abstraite representant un flux d'entree et de sortie
 * @see Out
 */
public abstract class InOut extends Out implements Runnable, Lancable {
	private InputStream input;
	private boolean run;


	/**
	 * Cree un nouvel objet de flux, de flux d'entree et de sortie indefinis
	 */
	public InOut() {}

	/**
	 * Cree un nouvel objet de flux, a partir des flux precises
	 * @param reader le flux d'entree
	 * @param writer le flux de sortie
	 */
	public InOut(InputStream reader, OutputStream writer) {
		super(writer);
		setOut(writer);
	}

	/**
	 * Methode abstraite permettant de lire le contenu du flux d'entree, selon les propres regles des classes filles
	 * @return les octets lus dans le flux d'entree
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws InvalideException si la lecture est invalide
	 * @throws HorsLigneException si le flux n'est pas ouvert
	 */
	public abstract byte[] lire() throws IOException, InvalideException, HorsLigneException;

	/**
	 * Assigne le nouveau flux d'entree
	 * @param input le nouveau flux d'entree
	 */
	public void setIn(InputStream input) {
		this.input = input;
	}
	
	/**
	 * @return le flux d'entree
	 */
	public InputStream getIn() {
		return input;
	}

	/**
	 * Ajoute un ecouteur des paquets lus dans le flux d'entree
	 * @param l le nouvel ecouteur
	 */
	public void addReceiveListener(ReceiveListener l) {
		addListener(ReceiveListener.class, l);
	}

	/**
	 * Retire un ecouteur des paquets lus dans le flux d'entree
	 * @param l l'ancien ecouteur
	 */
	public void removeReceiveListener(ReceiveListener l) {
		removeListener(ReceiveListener.class, l);
	}

	/**
	 * Lit un octet depuis le flux d'entree
	 * @return l'octet lu
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws HorsLigneException si le flux est ferme
	 */
	public byte lireByte() throws IOException, HorsLigneException {
		int i = input.read();
		if(i == -1)
			throw new HorsLigneException();
		return (byte) i;
	}

	/**
	 * Lit un entier court depuis le flux d'entree (compris entre 0 et 16777216)
	 * @return l'entier court ainsi lu
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws HorsLigneException si le flux est ferme
	 * @see IO#LIMITE_3_BYTES_POSITIF
	 */
	public int lireShortInt() throws IOException, HorsLigneException {
		return IO.getInt(lireByte(), lireByte(), lireByte());
	}

	/**
	 * Lit le nombre d'octets specifies
	 * @param nbr le nombre d'octets a lire, correspondant a la taille du talbeau retourne
	 * @return le tableau des octets lus
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws HorsLigneException si le flux est ferme
	 */
	public byte[] lire(int nbr) throws IOException, HorsLigneException {
		byte[] b = new byte[nbr];
		for(int i=0 ; i<b.length ; i++) 
			b[i] = lireByte();
		return b;
	}

	/**
	 * Notifie les ecouteurs de paquets qu'un nouveau paquet a ete decouvert sur le flux d'entree
	 * @param type le type du paquet
	 * @param in les informations contenues dans le paquet apres lecture
	 */
	protected void notifyReceiveListener(TypePaquet type, IO in) {
		for(final ReceiveListener l : getListeners(ReceiveListener.class)) {
			in.setIndex(1);
			try {
				l.recu(type, in);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @return vrai si ce flux lit sur sur flux d'entree
	 */
	public boolean isRunning() {
		return run;
	}

	@Override
	public void run() {
		while(isRunning()) try {
			IO io = new IO(lire());
			notifyReceiveListener(TypePaquet.get(io.nextPositif()), io);
		} catch(SocketTimeoutException e) {
			fermer();
		} catch(SocketException e) {
			fermer();
		} catch(HorsLigneException e) {
			fermer();
		} catch(InvalideException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean lancer() {
		run = true;
		new Thread(this).start();
		return run;
	}

	@Override
	public boolean fermer() {
		if(super.fermer()) try {
			input.close();
			run = false;
		} catch(IOException e) {
			e.printStackTrace();
		}
		return !run;
	}


}
