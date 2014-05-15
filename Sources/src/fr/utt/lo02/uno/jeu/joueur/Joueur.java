package fr.utt.lo02.uno.jeu.joueur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.action.ActionUno;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.jeu.effet.Effet;
import fr.utt.lo02.uno.jeu.evenement.EvenementFinTour;
import fr.utt.lo02.uno.jeu.listener.EffetListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.temps.Evenement;
import fr.utt.lo02.uno.ui.interfaces.ChangeTypeListener;


/**
 * Objet representant un joueur pour une partie de UNO
 */
public class Joueur extends Listenable implements Comparator<Effet> {
	private final List<Effet> effets;
	private final MainJoueur main;
	private Evenement evenement;
	private TypeJoueur type;
	private TourJoueur tour;
	private boolean uno;
	private String nom;


	/**
	 * Cree un nouveau joueur
	 * @param type le type de ce joueur
	 * @param nom le nom de ce joueur
	 * @param main la main du joueur
	 */
	public Joueur(TypeJoueur type, String nom, MainJoueur main) {
		this.type = type;
		this.nom = nom;
		this.main = main;
		effets = new ArrayList<Effet>();
	}

	/**
	 * Cree un nouveau joueur avec une main vide
	 * @param type le type de ce joueur
	 * @param nom le nom de ce joueur
	 */
	public Joueur(TypeJoueur type, String nom) {
		this(type, nom, new MainJoueur());
	}

	/**
	 * @return le tour de ce joueur, ou null si ce n'est pas a lui de jouer
	 */
	public TourJoueur getTour() {
		return tour;
	}

	/**
	 * @return le type de ce joueur
	 */
	public TypeJoueur getType() {
		return type;
	}

	/**
	 * @return la main de ce joueur
	 */
	public MainJoueur getMain() {
		return main;
	}

	/**
	 * Change le type de ce joueur, et en informe les ecouteur
	 * @param type le nouveau type de ce joueur
	 */
	public void setType(TypeJoueur type) {
		this.type = type;
		for(final ChangeTypeListener l : getListeners(ChangeTypeListener.class))
			l.changeType(type);
	}
	
	/**
	 * Change le nom de ce joueur
	 * @param nom le nouveau nom du joueur
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Ajoute un effet qui sera active au debut du tour de ce joueur
	 * @param effet l'effet a ajouter a ce joueur
	 */
	public void ajoutEffet(Effet effet) {
		Console.getInstance().affiche(this + " devra " + effet);
		effets.add(effet);
		Collections.sort(effets, this);
		for(final EffetListener l : getListeners(EffetListener.class))
			l.ajoutEffet(effet);
	}

	/**
	 * Supprime un effet de ce joueur
	 * @param effet l'effet a retirer ce joueur
	 * @return true si l'effet a bien ete supprime
	 */
	public boolean supprimeEffet(Effet effet) {
		boolean suppr = effets.remove(effet);
		if(suppr) {
			Console.getInstance().affiche(this + " ne devra plus " + effet);
			for(final EffetListener l : getListeners(EffetListener.class))
				l.retireEffet(effet);
		}
		return suppr;
	}
	
	/**
	 * Commence le tour de ce joueur, en informe les ecouteurs et active les effets appliques sur ce joueur
	 * @param partie la partie dans laquelle ce joueur joue
	 * @param temps le temps dont ce joueur dispose pour jouer
	 * @return le tour du joueur, ou null s'il est dans l'incapacite de jouer
	 */
	public TourJoueur debutTour(Partie partie, int temps) {
		uno = false;
		Console.getInstance().retourLigne();
		Console.getInstance().affiche(this + " commence son tour");
		tour = new TourJoueur(this, partie);
		boolean uno = aDitUno();
		activeEffets(partie, false);
		if(tour != null && !tour.estTermine()) {
			evenement = new Evenement(temps, new EvenementFinTour(tour));
			partie.ajoutEvenement(evenement);
			for(final TourJoueurListener l : getListeners(TourJoueurListener.class))
				l.debutTour(tour);
		}
		else if(uno)
			ajoutEffet(new ActionUno());
		return tour;
	}

	/**
	 * Termine le tour de ce joueur, et en informe les ecouteurs
	 */
	public void finTour() {
		if(evenement != null) {
			evenement.desactiver();
			tour.getActions().getPartie().getHorloge().removeEvenement(evenement);
		}
		uno = main.getCartes().size() == 1;
		Console.getInstance().affiche(this + " termine son tour");
		for(final TourJoueurListener l : getListeners(TourJoueurListener.class))
			l.finTour(tour);
		tour = null;
	}

	/**
	 * Applique les effets de ce joueur
	 * @param partie la partie dans laquelle ce joueur joue
	 * @param finPartie vrai si la partie se termine
	 */
	public void activeEffets(Partie partie, boolean finPartie) {
		int skip = 0;
		while(effets.size() > skip) {
			Effet e = effets.get(skip);
			try {
				Console.getInstance().affiche("(Effet sur" + this + ") : " + e);
				if(e.faireEffet(partie, main, finPartie))
					supprimeEffet(e);
				else skip++;
			} catch(Exception err) {
				Console.getInstance().erreur(this + " ne peut pas " + e);
				err.printStackTrace();
			}
		}
	}

	/**
	 * Signifie aux ecouteur de ce joueur qu'il peut rejouer
	 */
	public void peutRejouer() {
		for(final TourJoueurListener l : getListeners(TourJoueurListener.class))
			l.peutRejouer(tour);
	}

	/**
	 * @return le nom de ce joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return vrai si le joueur a declare uno
	 */
	public boolean aDitUno() {
		for(final Effet e : effets)
			if(e instanceof ActionUno)
				return true;
		return false;
	}
	
	/**
	 * @return vrai si le joueur a pose son avant derniere carte lors de son dernier tour de jeu
	 */
	public boolean estUno() {
		return uno;
	}

	/**
	 * @return les effets appliques a ce joueur
	 */
	public List<Effet> getEffets() {
		return effets;
	}

	/**
	 * Ajoute un ecouteur pour le deroulement des tours des joueurs
	 * @param l le nouvel ecouteur de debut et de fin des tours de ce joueur
	 */
	public void addTourJoueurListener(TourJoueurListener l) {
		addListener(TourJoueurListener.class, l);
	}

	/**
	 * Retire un ecouteur pour le deroulement des tours des joueurs
	 * @param l l'ancien ecouteur de debut et de fin des tours de ce joueur
	 */
	public void removeTourJoueurListener(TourJoueurListener l) {
		removeListener(TourJoueurListener.class, l);
	}

	/**
	 * Ajoute un ecouteur pour le changement de type de ce joueur
	 * @param l le nouvel ecouteur de type pour ce joueur
	 */
	public void addChangeTypeListener(ChangeTypeListener l) {
		addListener(ChangeTypeListener.class, l);
	}

	/**
	 * Retire un ecouteur pour le changement de type de ce joueur
	 * @param l l'ancien ecouteur de type pour ce joueur
	 */
	public void removeChangeTypeListener(ChangeTypeListener l) {
		removeListener(ChangeTypeListener.class, l);
	}

	/**
	 * Ajoute un ecouteur pour les effets de ce joueur
	 * @param l le nouvel ecouteur de type pour ce joueur
	 */
	public void addEffetListener(EffetListener l) {
		addListener(EffetListener.class, l);
	}

	/**
	 * Retire un ecouteur pour les effets de ce joueur
	 * @param l l'ancien ecouteur de type pour ce joueur
	 */
	public void removeEffetListener(EffetListener l) {
		removeListener(EffetListener.class, l);
	}

	@Override
	public String toString() {
		return nom;
	}

	@Override
	public int compare(Effet e1, Effet e2) {
		return new Integer(e2.getPriorite()).compareTo(e1.getPriorite());
	}


}
