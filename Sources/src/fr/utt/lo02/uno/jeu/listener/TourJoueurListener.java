package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Interface definissant les ecouteurs du deroulement d'un tour
 */
public interface TourJoueurListener extends EventListener {
	
	/**
	 * Signifie qu'un joueur debute son tour
	 * @param tour le tour debutant
	 */
	public void debutTour(TourJoueur tour);
	
	/**
	 * Signifie qu'un joueur termine son tour
	 * @param tour le tour se terminant
	 */
	public void finTour(TourJoueur tour);
	
	/**
	 * Signifie qu'un joueur peut rejouer
	 * @param tour le tour pour lequel le joueur peut rejouer
	 */
	public void peutRejouer(TourJoueur tour);
	
}
