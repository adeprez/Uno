package fr.utt.lo02.uno.jeu.action.generateur;

import java.util.List;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;


/**
 * Interface definissant la strategie de jeu d'un joueur virtuel, selon le design-pattern Strategy
 */
public interface Strategie {
	
	/**
	 * Demande a l'objet definissant la strategie quelle action celui-ci souhaite apporter
	 * @param actions les differentes possibilites offertes au joueur
	 * @param vue l'apercu de l'etat actuel du jeu pour le joueur, sans possibilite de modification
	 * @return le rang de l'action a effectuer dans la liste des possibilites, compris entre 0 et la taille de la liste d'actions
	 */
	public int getIDAction(List<ActionJoueur> actions, VueJeu vue);
}
