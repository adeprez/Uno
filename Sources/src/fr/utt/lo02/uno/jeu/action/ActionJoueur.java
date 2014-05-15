package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;


/**
 * Interface definissant une action instantanee pour un joueur
 */
public interface ActionJoueur {

	/**
	 * @return le type de l'action
	 */
	public Action getType();

	/**
	 * Effectue l'effet lie a cette action
	 * @param partie la partie sur laquelle l'effet de cette action doit s'appliquer
	 */
	public void faireEffet(Partie partie);

}
