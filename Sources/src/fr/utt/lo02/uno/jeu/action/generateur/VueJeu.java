package fr.utt.lo02.uno.jeu.action.generateur;

import java.util.HashMap;
import java.util.Map;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Objet offrant une vue sur le jeu, sans pouvoir le modifier
 */
public class VueJeu {
	private final Partie partie;
	private final Joueur joueur;
	
	
	/**
	 * Cree une nouvelle vue sur le jeu
	 * @param joueur le joueur souhaitant visualiser le jeu
	 * @param partie la partie a visualiser
	 */
	public VueJeu(Joueur joueur, Partie partie) {
		this.joueur = joueur;
		this.partie = partie;
	}
	
	/**
	 * Cree une nouvelle vue sur le jeu
	 * @param tour le tour de joueur sur lequel se baser
	 */
	public VueJeu(TourJoueur tour) {
		this(tour.getActions().getPartie().getJoueurs().getJoueur(), tour.getActions().getPartie());
	}
	
	/**
	 * @return l'ensemble de cartes iterable du joueur
	 */
	public Iterable<Carte> getCartes() {
		return joueur.getMain().getCartes();
	}
	
	/**
	 * @return le nombre de cartes de cette couleur qu'a le joueur
	 * @param couleur la couleur
	 */
	public int getNombreCarteCouleur(Couleur couleur) {
		int nbr = 0;
		for(final Carte c : getCartes())
			if(c.getCouleur() == couleur)
				nbr++;
		return nbr;
	}
	
	/**
	 * @return le nombre de cartes de cette couleur qu'a le joueur
	 * @param couleur la couleur
	 */
	public int getPointsCarteCouleur(Couleur couleur) {
		int pts = 0;
		for(final Carte c : getCartes())
			if(c.getCouleur() == couleur)
				pts += c.getValeurPoints();
		return pts;
	}

	/**
	 * @return la carte sur le dessus du talon
	 */
	public Carte getCarteTalon() {
		return partie.getPlateau().getTalon().getCarte();
	}
	
	/**
	 * @return la carte sous la carte sur le dessus du talon
	 */
	public Carte getAncienneCarteTalon() {
		return partie.getPlateau().getTalon().getCartePrecedente();
	}
	
	/**
	 * @return le nombre de cartes dans la main du joueur
	 */
	public int getNombreCartes() {
		return joueur.getMain().getCartes().size();
	}
	
	/**
	 * @return le nombre de cartes de chaque joueur, hormis le proprietaire de cette vue
	 */
	public Map<Joueur, Integer> getNombreCarteAutresJoueurs() {
		Map<Joueur, Integer> mains = new HashMap<Joueur, Integer>();
		for(final Joueur j : partie.getJoueurs().getJoueurs())
			if(j != joueur)
				mains.put(j, j.getMain().getCartes().size());
		return mains;
	}
	
	/**
	 * @return le nombre de cartes du joueur precedent
	 */
	public int getNombreCarteJoueurPrecedent() {
		return partie.getJoueurs().getJoueurPrecedent().getMain().getCartes().size();
	}

	/**
	 * @return le nombre de cartes du joueur suivant
	 */
	public int getNombreCarteJoueurSuivant() {
		return partie.getJoueurs().getJoueurSuivant().getMain().getCartes().size();
	}
	
}
