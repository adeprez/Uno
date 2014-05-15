package fr.utt.lo02.uno.io.reseau.serveur;

import fr.utt.lo02.uno.io.exception.ServeurFullException;
import fr.utt.lo02.uno.io.interfaces.Fermable;
import fr.utt.lo02.uno.io.interfaces.FiltreEnvoi;
import fr.utt.lo02.uno.io.interfaces.IOable;
import fr.utt.lo02.uno.io.interfaces.Joignable;
import fr.utt.lo02.uno.io.interfaces.Lancable;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.listeners.ConnexionListener;
import fr.utt.lo02.uno.io.reseau.listeners.DeconnexionListener;
import fr.utt.lo02.uno.io.reseau.serveur.filtreEnvoi.DefaultFiltreEnvoi;
import fr.utt.lo02.uno.jeu.listener.Listenable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * Classe abstraite representant un serveur generique via l'utilisation de {@link ServerSocket}. Il gere la connexion et la deconnexion de clients,
 * ainsi que leurs communications.
 */
public abstract class AbstractServeur extends Listenable implements Runnable, Lancable, Fermable, DeconnexionListener, Joignable {
	private final HashMap<Integer, ClientServeur> clients;
	private final List<Exception> erreurs;
	private final ServerSocket socket;
	private final Thread thread;


	/**
	 * Cree un nouveeau serveur generique gerant la connexion et la deconnexion de client, ainsi que leurs communications.
	 * @param port le port d'ecoute de ce serveur
	 * @throws IOException si une erreur survient a la creation de ce serveur
	 */
	public AbstractServeur(int port) throws IOException {
		socket = new ServerSocket(port);
		clients = new HashMap<Integer, ClientServeur>();
		erreurs = new ArrayList<Exception>();
		thread = new Thread(this);
	}

	/**
	 * @return la paquet envoye a un client lors de sa connexion a ce serveur
	 */
	public abstract Paquet getPaquetConnexion();
	
	/**
	 * @return le nombre maximum de clients acceptes pour ce serveur
	 */
	public abstract int getMaxClients();
	
	/**
	 * Demande la creation d'un objet representant le client, a partir d'un socket
	 * @param socket le socket du client souhaitant se connecter
	 * @return le client cree
	 * @throws IOException si une erreur d'entree/sorti survient
	 * @throws ServeurFullException si le serveur n'accepte plus de clients supplementaires
	 */
	public abstract ClientServeur creerClient(Socket socket) throws IOException, ServeurFullException;

	/**
	 * @return le port sur lequel le serveur ecoute
	 */
	public int getPort() {
		return socket.getLocalPort();
	}

	/**
	 * @return l'adresse de ce serveur
	 */
	public InetAddress getAdresse() {
		return socket.getInetAddress();
	}

	/**
	 * @return l'ensemble des clients identifies sur ce serveur
	 */
	public Collection<ClientServeur> getClients() {
		return clients.values();
	}

	/**
	 * @param id l'identifiant du client a rechercher
	 * @return le client correspondant a cet identifiant, ou null s'il n'existe pas
	 */
	public ClientServeur getClient(int id) {
		return clients.get(id);
	}

	/**
	 * Deconnecte tous les clients de ce serveur
	 */
	public void deconnecterClients() {
		for(final AbstractClient c : getClients())
			c.fermer();
	}

	/**
	 * Ajoute un ecouteur de la connexion des clients
	 * @param l le nouvel ecouteur de la connexion des clients
	 */
	public void addConnexionListener(ConnexionListener l) {
		addListener(ConnexionListener.class, l);
	}

	/**
	 * Retire un ecouteur de la connexion des clients
	 * @param l l'ancien ecouteur de la connexion des clients
	 */
	public void removeConnexionListener(ConnexionListener l) {
		removeListener(ConnexionListener.class, l);
	}

	/**
	 * Ajoute un ecouteur de la deconnexion des clients
	 * @param l le nouvel ecouteur de la deconnexion des clients
	 */
	public void addDeconnexionListener(DeconnexionListener l) {
		addListener(DeconnexionListener.class, l);
	}

	/**
	 * Retire un ecouteur de la deconnexion des clients
	 * @param l l'ancien ecouteur de la deconnexion des clients
	 */
	public void removeDeconnexionListener(DeconnexionListener l) {
		removeListener(DeconnexionListener.class, l);
	}

	/**
	 * Notifie les ecouteurs qu'un nouveau client s'est connecte
	 * @param client le client s'etant connecte
	 */
	private void notifierConnexionListener(ClientServeur client) {
		for(final ConnexionListener l : getListeners(ConnexionListener.class))
			l.connexion(client);
	}

	/**
	 * Notifie les ecouteurs qu'un client s'est deconnecte
	 * @param client le client s'etant deconnecte
	 */
	private void notifyDeconnexionListener(ClientServeur client) {
		for(final DeconnexionListener l : getListeners(DeconnexionListener.class))
			l.deconnexion(client);
	}

	/**
	 * @return un identifiant libre pour un client
	 * @throws ServeurFullException si le serveur n'accepte pas de clients supplementaires
	 */
	private int getIDLibre() throws ServeurFullException {
		if(getClients().size() >= getMaxClients())
			throw new ServeurFullException();
		for(byte i=0 ; i<getMaxClients() ; i++) 
			if(getClient(i) == null)
				return i;
		throw new ServeurFullException();
	}
	
	/**
	 * Envoie des informations a un client donne
	 * @param id l'indentifiant du client
	 * @param io les informations a envoyer
	 */
	public void envoyer(int id, IOable io) {
		getClient(id).write(io);
	}
	
	/**
	 * Envoie des informations a tous les clients
	 * @param io les informations a envoyer
	 */
	public void envoyerTous(IOable io) {
		envoyerFiltre(io, new DefaultFiltreEnvoi());
	}
	
	/**
	 * Envoie des informations a tous les clients acceptes par le filtre
	 * @param io les informations a envoyer
	 * @param filtre le filtre d'envoi, acceptant ou non les clients
	 */
	public void envoyerFiltre(IOable io, FiltreEnvoi filtre) {
		for(final int id : clients.keySet())
			if(filtre.doitEnvoyer(id, io))
				envoyer(id, io);
	}

	@Override
	public void run() {
		while(!socket.isClosed()) {
			try {
				AbstractClient c = creerClient(socket.accept());
				c.lancer();
				c.write(getPaquetConnexion());
			} catch(SocketException e) {
			} catch(ServeurFullException e) {
			} catch(Exception e) {
				e.printStackTrace();
				erreurs.add(e);
			}
		}
		if(!erreurs.isEmpty())
			System.err.println(erreurs.size() + " erreurs : ");
	}

	@Override
	public boolean rejoindre(ClientServeur c) {
		try {
			c.setID(getIDLibre());
			clients.put(c.getID(), c);
			c.addDeconnexionListener(this);
			notifierConnexionListener(c);
			return true;
		} catch(ServeurFullException e) {
			c.fermer();
			return false;
		}
	}

	@Override
	public void deconnexion(AbstractClient client) {
		ClientServeur c = clients.remove(client.getID());
		if(c != null) {
			c.removeDeconnexionListener(this);
			notifyDeconnexionListener(c);
		}
	}

	@Override
	public boolean fermer() {
		try {
			deconnecterClients();
			socket.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean lancer() {
		if(!thread.isAlive()) {
			thread.start();
			return true;
		}
		return false;
	}

}
