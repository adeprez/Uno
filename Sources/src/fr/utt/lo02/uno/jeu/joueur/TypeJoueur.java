package fr.utt.lo02.uno.jeu.joueur;

import fr.utt.lo02.uno.langue.Texte;

/**
 * Enumeration des differents types de joueurs
 */
public enum TypeJoueur {
	HUMAIN, ORDINATEUR, JOUEUR_RESEAU;
	
	
	/**
	 * @return l'element sous forme textuelle lisible
	 */
	public String getNom() {
		return Texte.get(toString().charAt(0) + toString().substring(1).replace("_", " ").toLowerCase());
	}
	
	/**
	 * @return le lien vers l'image representant ce type de joueur
	 */
	public String getLienImage() {
		switch(this) {
		case HUMAIN: return "humain.png";
		case ORDINATEUR: return "bot.png";
		case JOUEUR_RESEAU: return "reseau.png";
		default: throw new IllegalAccessError();
		}
	}
	
}
