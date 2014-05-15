package fr.utt.lo02.uno.ui.interfaces;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;


/**
 * Interface definissant les ecouteurs de changement de type d'un joueur
 */
public interface ChangeTypeListener extends EventListener {
	
	/**
	 * Actin a realiser lorsqu'un joueur change de type
	 * @param type le nouveau type du joueur
	 */
	public void changeType(TypeJoueur type);
	
}
