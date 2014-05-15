package fr.utt.lo02.uno.jeu.effet;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;

/**
 * Interface representant les objets pouvant appliquer un effet a un joueur, active au debut de son tour
 */
public interface Effet {
	
	/**
	 * Effectue l'effet lie a cet objet
	 * @param partie la partie sur laquelle cet effet doit s'appliquer
	 * @param main la main du joueur activant l'effet
	 * @param finPartie si cet effet s'applique a la fin de partie
	 * @return true si l'effet a ete effectue, false sinon
	 */
	public boolean faireEffet(Partie partie, MainJoueur main, boolean finPartie);
	
	
	/**
	 * Une valeur elevee caracterise les effets s'effectuant en premier
	 * @return la valeur de l'indice de priorite de cet effet
	 */
	public int getPriorite();
	
	/**
	 * @return le chemin vers l'image represenant cet effet
	 */
	public String getCheminImage();

}
