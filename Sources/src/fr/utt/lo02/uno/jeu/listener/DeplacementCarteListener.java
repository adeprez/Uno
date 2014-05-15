package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.carte.Carte;


/**
 * Interface definissant les ecouteurs des actions du deplacement des {@link Carte}s
 */
public interface DeplacementCarteListener extends EventListener {
	
	/**
	 * Signifie qu'un carte se deplace
	 * @param source l'objet dont provient la carte
	 * @param cible l'objet vers lequel la carte se dirige
	 * @param c la carte se deplacant
	 */
	public void deplacement(Object source, Object cible, Carte c);
	
}
