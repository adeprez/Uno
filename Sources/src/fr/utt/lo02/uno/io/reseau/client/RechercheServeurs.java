package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.exception.AnnulationException;
import fr.utt.lo02.uno.io.interfaces.Lancable;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.listeners.DeconnexionListener;
import fr.utt.lo02.uno.io.reseau.listeners.RechercheSalleListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe permettant de recherches les salles de jeu en ligne, a partir d'une liste d'adresses
 * @see RechercheServeur
 */
public class RechercheServeurs extends Listenable implements DeconnexionListener, Runnable, Lancable {
	private final List<RechercheServeur> serveurs;
	private final List<String> adresses;
	private boolean fin;


	/**
	 * Cree un nouvel objet permettant de recherches les salles de jeu en ligne, a partir d'une liste d'adresses
	 * @param adresses la liste des adresses
	 */
	public RechercheServeurs(List<String> adresses) {
		this.adresses = adresses;
		serveurs = new ArrayList<RechercheServeur>();
	}

	/**
	 * Ajoute une adresse pour la recherche des salles en ligne
	 * @param adresse l'adresse a ajouter a la liste de recherche
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public void ajout(InetAddress adresse) throws IOException {
		RechercheServeur rs = new RechercheServeur(adresse);
		rs.addDeconnexionListener(this);
		serveurs.add(rs);
	}

	/**
	 * Termine la connexion des {@link RechercheServeur}
	 */
	public void terminer() {
		fin = true;
		synchronized(serveurs) {
			for(final RechercheServeur rs : serveurs)
				rs.fermer();
		}
	}

	/**
	 * Ajoute un ecouteur pour la detection de salles de jeu du Uno en ligne
	 * @param l un nouvel ecouteur de detection de salles de jeu du Uno en ligne
	 */
	public void addRechercheSalleListener(RechercheSalleListener l) {
		addListener(RechercheSalleListener.class, l);
	}

	/**
	 * Retire un ecouteur pour la detection de salles de jeu du Uno en ligne
	 * @param l l'ancien ecouteur de detection de salles de jeu du Uno en ligne
	 */
	public void removeRechercheSalleListener(RechercheSalleListener l) {
		removeListener(RechercheSalleListener.class, l);
	}

	/**
	 * Signale aux ecouteurs de la detection de salles de jeu du Uno en ligne qu'une nouvelle salle a ete decouverte
	 * @param infos les informations relatives a cette salle
	 * @param adresse l'adresse de cette salle
	 */
	public void notifyDecouverte(InfoSalle infos, InetAddress adresse) {
		for(final RechercheSalleListener l : getListeners(RechercheSalleListener.class))
			l.nouvelleSalle(adresse, infos);
	}

	@Override
	public boolean lancer() {
		new Thread(this).start();
		return true;
	}

	@Override
	public void deconnexion(AbstractClient client) {
		try {
			serveurs.remove(client);
			notifyDecouverte(((RechercheServeur) client).getInfoSalle(), client.getAdresse());
		} catch(AnnulationException e) {}
		client.removeDeconnexionListener(this);
	}

	@Override
	public void run() {
		for(int i=0 ; i<adresses.size() && !fin ; i++) try {
			ajout(InetAddress.getByName(adresses.get(i)));
		} catch(Exception e) {}
		for(final RechercheServeur rs : serveurs)
			rs.lancer();
	}

}
