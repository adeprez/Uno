package fr.utt.lo02.uno.io.exception;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;

/**
 * Exception generee lors d'une action invalide
 * @see ActionJoueur
 */
public class InvalideException extends Exception {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception d'action invalide
	 */
	public InvalideException() {}

	/**
	 * Cree une nouvelle exception d'action invalide
	 * @param message la cause de l'invalidite de l'{@link ActionJoueur}
	 */
	public InvalideException(String message) {
		super(message);
	}
	
}
