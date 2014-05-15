package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.TasDeCarte;


/**
 * Interface definissant les ecouteurs des changements d'un {@link TasDeCarte}
 */
public interface TasCarteListener extends EventListener {
	
	/**
	 * Signifie que la carte sur le dessus du tas de carte a change
	 * @param carte la nouvelle carte sue le dessus du tas de carte
	 * @see TasDeCarte
	 */
	public void changeDessus(Carte carte);
	
}
