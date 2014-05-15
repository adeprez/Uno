package fr.utt.lo02.uno.jeu.action.generateur.ia;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.generateur.VueJeu;

/**
 * Interface definissant une classe pouvant attribuer une valuation a l'action d'un joueur, en fonction de sa pertinence
 */
public interface AttributeurPoints {
	
	/**
	 * Informe qu'une nouvelle action va etre effectuee
	 * @param vue la vue sur le jeu pour ce joueur
	 */
	public void nouvelleAction(VueJeu vue);
	
	/**
	 * Demande l'attribution d'une valuation a l'action d'un joueur, en fonction de sa pertinence
	 * @param action l'action proposee
	 * @param vue la vue sur le jeu pour ce joueur
	 * @return le poids de cette action, en fonction de sa pertinence
	 */
	public int getPoints(ActionJoueur action, VueJeu vue);
	
}
