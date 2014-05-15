package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.exception.AnnulationException;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Classe representant une salle de jeu du Uno en reseau, cote Client
 */
public class SalleReseau extends Salle implements ReceiveListener {
	private final Client client;


	/**
	 * Cree une nouvelle salle de jeu en reseau
	 * @param nom le nom du joueur souhaitant se connecter
	 * @param adresse l'adresse de la salle
	 * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address. 
	 * @throws AnnulationException si le serveur ne fourni aucun identifiant valide
	 * @throws SecurityException if a security manager exists and its checkConnect method doesn't allow the operation
	 */
	public SalleReseau(String nom, String adresse) throws UnknownHostException, AnnulationException {
		this(nom, InetAddress.getByName(adresse));
	}

	/**
	 * Cree une nouvelle salle de jeu en reseau local
	 * @param nom le nom du joueur souhaitant se connecter
	 * @throws UnknownHostException if no IP address for the host could be found, or if a scope_id was specified for a global IPv6 address. 
	 * @throws AnnulationException si le serveur ne fourni aucun identifiant valide
	 * @throws SecurityException if a security manager exists and its checkConnect method doesn't allow the operation
	 */
	public SalleReseau(String nom) throws UnknownHostException, AnnulationException {
		this(nom, InetAddress.getLocalHost());
	}

	/**
	 * Cree une nouvelle salle de jeu en reseau
	 * @param nom le nom du joueur souhaitant se connecter
	 * @param adresse l'adresse de la salle
	 * @throws AnnulationException si le serveur ne fourni aucun identifiant valide
	 */
	public SalleReseau(String nom, InetAddress adresse) throws AnnulationException {
		Connexion connexion = new Connexion(adresse, nom);
		connexion.start();
		client = connexion.getClient();
		setInformations(client.getInfos().getTypeJeu(), client.getInfos().getNombreJoueursMax(), client.getInfos().getNom());
		client.addReceiveListener(this);
		client.write(new Paquet(TypePaquet.AJOUT_JOUEUR));
	}

	/**
	 * @return le client associe a cette salle
	 */
	public Client getClient() {
		return client;
	}
	
	@Override
	public void recu(TypePaquet type, IO io) {
		switch(type) {
		case AJOUT_JOUEUR:
			getJeu().getListeJoueurs().ajoutJoueur(io.nextPositif(), new Joueur(TypeJoueur.values()[io.nextPositif()], io.nextShortString()));
			break;
		case RETIRE_JOUEUR:
			getJeu().getListeJoueurs().retireJoueur(io.nextPositif());
			break;
		case COMMENCER:
			getJeu().setPartie(new PartieReseau(this, io));
			getJeu().getPartie().lancer();
			break;
		default:
			break;
		}
	}

}
