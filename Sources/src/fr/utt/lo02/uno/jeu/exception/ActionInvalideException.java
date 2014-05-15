package fr.utt.lo02.uno.jeu.exception;

/**
 * Objet caracterisant les exceptions de jeu lorsqu'une action invalide survient
 */
public class ActionInvalideException extends JeuException {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception d'action invalide
	 */
	public ActionInvalideException() {
		super();
	}

	/**
	 * Cree une nouvelle exception d'action invalide
	 * @param message la cause de l'invalidite de l'action concernee
	 */
	public ActionInvalideException(String message) {
		super(message);
	}

}
