package fr.utt.lo02.uno.jeu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.jeu.action.generateur.ia.ComportementOrdinateur;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.PlateauJeu;
import fr.utt.lo02.uno.jeu.evenement.EvenementFinPartie;
import fr.utt.lo02.uno.jeu.evenement.EvenementNouveauTour;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.ListeJoueurs;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.jeu.listener.DeplacementCarteListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;
import fr.utt.lo02.uno.jeu.listener.PartieListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.temps.Evenement;
import fr.utt.lo02.uno.temps.EvenementFutur;
import fr.utt.lo02.uno.temps.Horloge;


/**
 * Objet representant une partie de UNO, avec des joueurs et un plateau de jeu
 */
public class Partie extends Listenable {
	private final Map<Joueur, ComportementOrdinateur> ia;
	private final ListeJoueurs joueurs;
	private final Horloge horloge;
	private final PlateauJeu plateau;
	private boolean joue;


	/**
	 * Cree une nouvelle partie
	 * @param joueurs les joueurs participant a cette partie de UNO
	 * @param plateau le plateau de jeu
	 * en fonction du delai precise dans la {@link Configuration}.
	 */
	public Partie(ListeJoueurs joueurs, PlateauJeu plateau) {
		this.joueurs = joueurs;
		this.plateau = plateau;
		horloge = new Horloge();
		ia = new HashMap<Joueur, ComportementOrdinateur>();
	}

	/**
	 * Cree une nouvelle partie avec evenements actifs
	 * @param joueurs les joueurs participant a cette partie de UNO
	 */
	public Partie(ListeJoueurs joueurs) {
		this(joueurs, new PlateauJeu());
	}

	/**
	 * Ajoute un evenement futur
	 * @param e l'evenement futur a ajouter
	 * @return ce meme evenement
	 */
	public EvenementFutur ajoutEvenement(EvenementFutur e) {
		horloge.addEvenementFutur(e);
		return e;
	}

	/**
	 * Affecte l'ordre des joueurs, distribue les cartes et commence la partie de UNO
	 * @throws IllegalStateException si la partie ne comporte pas assez de joueurs pour etre jouee
	 */
	public void lancer() {
		if(joue)
			throw new IllegalStateException(Texte.get("Cette partie est d�j� lanc�e"));
		joue = true;
		joueurs.combler();
		affecteComportementOrdinateurs();
		for(final PartieListener l : getListeners(PartieListener.class))
			l.debutPartie(this);
		horloge.lancer();
		distribuer();
		choisirJoueurCommence();
		joueurs.precedent();
		plateau.getTalon().getCarte().faireEffet(this, true);
		joueurs.suivant();
		Console.getInstance().affiche(" --- La partie commence ! --- ");
		commence();
	}

	/**
	 * Assigne a tous les joueurs ordinateur un comportement
	 */
	public void affecteComportementOrdinateurs() {
		for(final Joueur j : joueurs.getJoueurs())
			if(j.getType() == TypeJoueur.ORDINATEUR) {
				ComportementOrdinateur c = new ComportementOrdinateur(horloge);
				ia.put(j, c);
				j.addTourJoueurListener(c);
			}
	}

	/**
	 * Chaque joueur pioche une carte ; celui ayant pioche la carte de plus grande valeur commence
	 */
	public void choisirJoueurCommence() {
		Carte[] cartes = new Carte[joueurs.getMaxJoueurs()];
		for(int i=0 ; i<cartes.length ; i++) {
			cartes[i] = plateau.piocher();
			plateau.getTalon().poser(cartes[i]);
		}
		joueurs.setIndexJoueur(getCarteMax(cartes));
	}

	/**
	 * Termine la partie
	 */
	public void finPartie() {
		joueurs.getJoueurSuivant().activeEffets(this, true);
		for(final Entry<Joueur, ComportementOrdinateur> e : ia.entrySet())
			e.getKey().removeTourJoueurListener(e.getValue());
		ia.clear();
		Console.getInstance().affiche(joueurs.getJoueur() + " gagne la partie !");
		for(final PartieListener l : getListeners(PartieListener.class))
			l.finPartie(this);
	}

	/**
	 * Action a realiser apres que la partie ait ete lancee
	 */
	public void commence() {
		commenceTour();
	}

	/**
	 * Disctribue {@link Configuration#NOMBRE_CARTES} cartes a chaque joueur
	 * @see #distribuer(int)
	 */
	public void distribuer() {
		distribuer(Configuration.NOMBRE_CARTES);
	}

	/**
	 * Distribue a chaque joueur un certain nombre de cartes
	 * @param nombre le nombre de cartes a disctribuer a chaque joueur
	 * @throws IllegalArgumentException si le nombre de cartes est negatif
	 */
	public void distribuer(int nombre) {
		if(nombre < 0)
			throw new IllegalArgumentException("Un minimum de 0 cartes doit etre distribue");
		Console.getInstance().affiche("Distribution des cartes (" + nombre + ")");
		for(int i = 0 ; i < nombre ; i++)
			for(final Joueur j : joueurs.getJoueurs())
				j.getMain().ajoutCarte(plateau.piocher());
	}

	/**
	 * @return la liste des joueurs participant a cette partie
	 */
	public ListeJoueurs getJoueurs() {
		return joueurs;
	}

	/**
	 * @return le plateau de jeu de cette partie
	 */
	public PlateauJeu getPlateau() {
		return plateau;
	}

	/**
	 * Passe au joueur suivant, et commence son tour
	 * @see #commenceTour()
	 */
	public void nouveauTour() {
		joueurs.suivant();
		commenceTour();
	}

	/**
	 * Commence le tour du joueur
	 */
	public void commenceTour() {
		int index = joueurs.getIDJoueur();
		TourJoueur tour = joueurs.getJoueur().debutTour(this, Configuration.TEMPS_TOUR);
		if(tour != null) {
			for(final PartieListener l : getListeners(PartieListener.class))
				l.debutTour(joueurs.getIDJoueur(), tour);
		} else {
			for(final PartieListener l : getListeners(PartieListener.class))
				l.passeTour(index);
		}
	}

	/**
	 * Termine le tour du joueur courant. S'il est victorieux, la partie se termine, sinon le joueur suivant commence son tour
	 */
	public void finTour() {
		joueurs.getJoueur().finTour();
		if(joueurs.getJoueur().getMain().estVide())
			horloge.addEvenementFutur(new Evenement(2, new EvenementFinPartie(this)));
		else ajoutEvenement(new Evenement(Configuration.TEMPS_INTERTOUR, new EvenementNouveauTour(this)));
	}

	/**
	 * @return l'horloge associee a cette partie
	 */
	public Horloge getHorloge() {
		return horloge;
	}

	/**
	 * @return vrai si la partie a ete lancee
	 */
	public boolean joue() {
		return joue;
	}

	/**
	 * Ajoute un ecouteur pour cette partie
	 * @param l le nouveau listener de cette partie
	 */
	public void addPartieListener(PartieListener l) {
		addListener(PartieListener.class, l);
	}

	/**
	 * Retire un ecouteur pour cette partie
	 * @param l l'ancien listener de cette partie
	 */
	public void removePartieListener(PartieListener l) {
		removeListener(PartieListener.class, l);
	}
	
	/**
	 * Signifie aux ecouteurs qu'une carte se deplace
	 * @param source l'objet duquel la carte est prelevee
	 * @param cible l'objet vers lequel la carte se deplace
	 * @param c la carte se deplacant
	 */
	public void notifyDeplacementCarte(Object source, Object cible, Carte c) {
		for(final DeplacementCarteListener l : getListeners(DeplacementCarteListener.class))
			l.deplacement(source, cible, c);
	}

	/**
	 * Ajoute un ecouteur pour les DeplacementCarteListener de cette partie
	 * @param l le nouveau listener des DeplacementCarteListener de cette partie
	 */
	public void addDeplacementCarteListener(DeplacementCarteListener l) {
		addListener(DeplacementCarteListener.class, l);
	}

	/**
	 * Supprime un ecouteur pour les DeplacementCarteListener de cette partie
	 * @param l l'ancien listener des DeplacementCarteListener de cette partie
	 */
	public void removeDeplacementCarteListener(DeplacementCarteListener l) {
		removeListener(DeplacementCarteListener.class, l);
	}

	/**
	 * Compare les cartes pour determiner laquelle est la plus grande
	 * @param cartes les cartes a comparer
	 * @return le rang de la carte la plus grande
	 */
	public static int getCarteMax(Carte... cartes) {
		int max = 0, id = 0;
		for(int i = 0 ; i < cartes.length ; i++) {
			if(cartes[i].getValeurPoints() > max)
				id = i;
		}
		return id;
	}

}
