package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.reseau.serveur.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *  Gestionnaire de flux d'entree et de sortie securise par un indentifiant, passant par un {@link Socket} reseau de protocole TCP
 * @see InOutCodeID
 */
public class InOutReseau extends InOutCodeID {
	private final Socket socket;


	/**
	 * Cree un nouveau gestionnaire de flux d'entree et de sortie securise par un indentifiant, 
	 * passant par un {@link Socket} reseau de protocole TCP en local
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public InOutReseau() throws IOException {
		this(InetAddress.getLocalHost());
	}

	/**
	 * Cree un nouveau gestionnaire de flux d'entree et de sortie securise par un indentifiant, 
	 * passant par un {@link Socket} reseau de protocole TCP, se connectant sur l'adresse serveur par defaut
	 * @param adresse l'adresse sur laquel se connecter, via l'instanciation d'un {@link Socket}
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @see Serveur#DEFAULT_PORT
	 */
	public InOutReseau(InetAddress adresse) throws IOException {
		this(adresse, Serveur.DEFAULT_PORT);
	}

	/**
	 * Cree un nouveau gestionnaire de flux d'entree et de sortie securise par un indentifiant, 
	 * passant par un {@link Socket} reseau de protocole TCP
	 * @param adresse l'adresse sur laquel se connecter, via l'instanciation d'un {@link Socket}
	 * @param port le numero de port sur lequel se connecter
	 * @throws UnknownHostException si l'hote ne peut pas etre trouve
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public InOutReseau(InetAddress adresse, int port) throws IOException {
		this(new Socket(adresse, port));
	}

	/**
	 * Cree un nouveau gestionnaire de flux d'entree et de sortie securise par un indentifiant, 
	 * passant par un {@link Socket} reseau de protocole TCP
	 * @param socket le socket reseau a partir duquel creer ce gestionnaire de flux
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public InOutReseau(Socket socket) throws IOException {
		this.socket = socket;
		setOut(socket.getOutputStream());
		setIn(socket.getInputStream());
	}

	/**
	 * @return l'adresse de ce gestionnaire de flux
	 */
	public InetAddress getAdresse() {
		return socket.getInetAddress();
	}
	
	/**
	 * @return le socket utilise par ce gestionnaire de flux
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * @return le port utlise par cette connexion
	 */
	public int getPort() {
		return socket.getPort();
	}

	@Override
	public boolean fermer() {
		if(super.fermer()) try {
			socket.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
