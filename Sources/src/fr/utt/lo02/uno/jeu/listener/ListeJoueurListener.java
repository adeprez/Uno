package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.joueur.Joueur;


/**
 * Interface definissant les ecouteurs de la liste de joueurs
 */
public interface ListeJoueurListener extends EventListener {
	
	/**
	 * Action a realiser lorsqu'un joueur est ajoute
	 * @param id l'indentifiant du joueur
	 * @param joueur le nouveau joueur
	 */
	public void ajout(int id, Joueur joueur);
	
	/**
	 * Action a realiser lorsqu'un joueur est retire
	 * @param id l'indentifiant du joueur
	 * @param joueur l'ancien joueur
	 */
	public void retire(int id, Joueur joueur);
	
}
