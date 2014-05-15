package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.exception.InvalideException;
import fr.utt.lo02.uno.io.interfaces.Fermable;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;


/**
 * Classe reprentant une communication en multicast selon le protocole UDP
 */
public class SocketUDP extends Listenable implements Runnable, Fermable {
	private static final int TAILLE = 100;
	private static final String ADRESSE = "224.0.0.23";
	private static final int PORT = 6789;
	private final MulticastSocket socket;
	private InetAddress adresse;


	/**
	 * Cree un nouvel objet gerant la communication en multicast selon le protocole UDP
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public SocketUDP() throws IOException {
		socket = new MulticastSocket(PORT);
	}

	/**
	 * Rejoint le groupe et lance l'ecoute sur le flux d'entree de cette communication
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @see #fermer()
	 */
	public void lancer() throws IOException {
		adresse = InetAddress.getByName(ADRESSE);
		socket.joinGroup(adresse);
		new Thread(this).start();
	}

	/**
	 * Reception d'un message sur le flux d'entree de cette communication
	 * @param io les informations lues sur le flux d'entree de cette communication
	 * @throws InvalideException si le type du paquet n'est pas valide
	 */
	public void recu(IO io) throws InvalideException {
		recu(TypePaquet.get(io.nextPositif()), io);
	}

	/**
	 * Reception d'un message sur le flux d'entree de cette communication
	 * @param type le type du paquet recu
	 * @param io les informations lues sur le flux d'entree de cette communication
	 */
	public void recu(TypePaquet type, IO io) {
		for(final ReceiveListener l : getListeners(ReceiveListener.class)) {
			io.setIndex(1);
			l.recu(type, io);
		}
	}
	
	/**
	 * Envoie des informations sur le flux de sortie
	 * @param io les informations a envoyer
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public void envoyer(IO io) throws IOException {
		socket.send(new DatagramPacket(io.getBytes(), io.size(), adresse, PORT));
	}

	@Override
	public boolean fermer() {
		try {
			socket.close();
			return true;
		} catch(Exception err) {
			err.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		while(!socket.isClosed()) {
			try {
				DatagramPacket paquet = new DatagramPacket(new byte[TAILLE], TAILLE);
				socket.receive(paquet);
				recu(new IO(paquet.getData()));
			} catch(SocketException e) {
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
