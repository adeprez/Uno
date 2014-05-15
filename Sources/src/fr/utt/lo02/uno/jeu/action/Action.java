package fr.utt.lo02.uno.jeu.action;


/**
 * Enumeration des differentes actions possibles
 */
public enum Action {
	PIOCHE, POSE, CHOIX_COULEUR, FIN_TOUR, UNO, CONTRE_UNO, BLUFF, NON_BLUFF;

	/**
	 * @return une representation textuelle de cette action
	 */
	public String getNom() {
		return toString().toLowerCase().replace("_", " ");
	}
}
