package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.effet.Effet;
import fr.utt.lo02.uno.jeu.joueur.Joueur;


/**
 * Interface definissant les ecouteurs des {@link Effet}s d'un {@link Joueur}
 */
public interface EffetListener extends EventListener {
	
	/**
	 * Signifie qu'un nouvel effet a ete ajoute a ce joueur
	 * @param effet l'effet ajoute
	 */
	public void ajoutEffet(Effet effet);
	
	/**
	 * Signifie qu'un effet a ete retire d'un joueur
	 * @param effet l'effet retire
	 */
	public void retireEffet(Effet effet);
	
}
