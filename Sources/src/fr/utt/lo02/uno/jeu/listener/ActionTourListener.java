package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.AutomateActions;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Interface definissant les ecouteurs des actions d'un {@link TourJoueur}
 */
public interface ActionTourListener extends EventListener {
	
	/**
	 * Signifie qu'une action a ete realisee au cours d'un {@link TourJoueur}
	 * @param idAction l'index de l'action effectuee dans la liste des actions possibles
	 * @param action l'action effectuee
	 * @see AutomateActions#getActionsPossibles()
	 */
	public void action(int idAction, ActionJoueur action);
	
}
