package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Interface definissant les ecouteurs de la main d'un {@link TourJoueur}
 */
public interface MainListener extends EventListener {
	
	/**
	 * Signifie qu'une carte est ajoutee a la main
	 * @param carte la carte ajoutee a la main
	 * @param rang le rang de cette carte dans la main
	 */
	public void ajoutCarte(Carte carte, int rang);
	
	/**
	 * Signifie qu'une carte est retiree de la main
	 * @param carte la carte retiree de la main
	 * @param rang le rang qu'occupait la carte dans la main
	 */
	public void retireCarte(Carte carte, int rang);
	
}
