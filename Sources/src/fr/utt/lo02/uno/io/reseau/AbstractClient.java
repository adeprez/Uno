package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.client.Client;
import fr.utt.lo02.uno.io.reseau.listeners.DeconnexionListener;
import fr.utt.lo02.uno.io.reseau.serveur.ClientServeur;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe abstraite representant un client avec un flux d'entree et de sortie via un socket TCP, pouvant se trouver cote client ou cote serveur.
 * Il contient un identifiant permettant de le reconnaitre des deux parties, selon le design-pattern Client/Serveur.
 * @see ClientServeur
 * @see Client
 */
public abstract class AbstractClient extends InOutReseau {
	private int ping;
	private int id;


	/**
	 * @param socket le Socket associe au client
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public AbstractClient(Socket socket) throws IOException {
		super(socket);
		id = -1;
	}

	/**
	 * Traite un paquet de donnes recu sur le flux d'entree, et de type reconnu
	 * @param type le type du paquet
	 * @param in les informations du paquet
	 */
	protected abstract void traite(TypePaquet type, IO in);
	
	/**
	 * @return l'indentifiant de ce client
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id le nouvel identifiant de ce client
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return le temps de latence cote client ou serveur, selon la specification de ce client
	 */
	public int getPing() {
		return ping;
	}
	
	/**
	 * @param ping le nouveau temps de latence cote client ou serveur, selon la specification de ce client
	 */
	public void setPing(int ping) {
		this.ping = ping;
	}

	/**
	 * @param t le temps auquel un message de ping a ete envoye au travers d'un flux externe vers ce client.
	 */
	public void setPing(long t) {
		setPing((int) (System.currentTimeMillis() - t));
	}

	/**
	 * Ajoute un ecouteur de la deconnexion de ce client
	 * @param l un nouvel ecouteur de la deconnexion de ce client
	 */
	public void addDeconnexionListener(DeconnexionListener l) {
		addListener(DeconnexionListener.class, l);
	}

	/**
	 * Supprime un ecouteur de la deconnexion de ce client
	 * @param l l'ancien ecouteur de la deconnexion de ce client
	 */
	public void removeDeconnexionListener(DeconnexionListener l) {
		removeListener(DeconnexionListener.class, l);
	}

	/**
	 * Signifie aux ecouteur que ce client est deconnecte
	 */
	private void notifyDeconnexionListener() {
		for(final DeconnexionListener l : getListeners(DeconnexionListener.class))
			l.deconnexion(this);
	}

	@Override
	public boolean fermer() {
		if(super.fermer()) {
			notifyDeconnexionListener();
			return true;
		}
		return false;
	}

	@Override
	protected void notifyReceiveListener(TypePaquet type, IO in) {
		int rang = in.getIndex();
		traite(type, in);
		in.setIndex(rang);
		super.notifyReceiveListener(type, in);
	}


}
