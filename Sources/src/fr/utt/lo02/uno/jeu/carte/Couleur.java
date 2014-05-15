package fr.utt.lo02.uno.jeu.carte;

import java.awt.Color;

import fr.utt.lo02.uno.langue.Texte;


/**
 * Enumeration representant les differentes couleurs du jeu du UNO
 */
public enum Couleur {
	ROUGE, VERT, BLEU, JAUNE;
	
	
	/**
	 * @return une representation textuelle de la couleur
	 */
	public String getNom() {
		return Texte.get(toString().toLowerCase());
	}

	/**
	 * @return la couleur associee
	 */
	public Color getColor() {
		switch(this) {
		case BLEU: return Color.BLUE;
		case JAUNE: return Color.YELLOW;
		case ROUGE: return Color.RED;
		case VERT: return Color.GREEN;
		}
		return Color.BLACK;
	}
	
	/**
	 * @param id l'identifiant de la couleur
	 * @return la couleur correspondante, ou null si elle n'est pas assignee
	 */
	public static Couleur get(int id) {
		if(id < 0 || id >= values().length)
			return null;
		return values()[id];
	}
	
}
