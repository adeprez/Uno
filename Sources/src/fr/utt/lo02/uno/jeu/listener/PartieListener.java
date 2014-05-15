package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Interface definissant les ecouteurs de de partie
 */
public interface PartieListener extends EventListener {
	
	/**
	 * Informe que la partie est terminee
	 * @param partie la partie finie
	 */
	public void finPartie(Partie partie);
	
	/**
	 * Informe que la partie commence
	 * @param partie la partie demarrant
	 */
	public void debutPartie(Partie partie);
	
	/**
	 * Informe que le tour d'un joueur debute
	 * @param id l'identifiant de ce joueur
	 * @param tour le tour du joueur debutant son tour
	 */
	public void debutTour(int id, TourJoueur tour);

	/**
	 * Informe qu'un joueur a passe son tour
	 * @param id l'identifiant de ce joueur
	 */
	public void passeTour(int id);
	
}
