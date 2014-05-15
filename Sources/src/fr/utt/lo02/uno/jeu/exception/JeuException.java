package fr.utt.lo02.uno.jeu.exception;

/**
 * Objet caracterisant les exceptions pouvant etre soulevees par le jeu
 */
public class JeuException extends Exception {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception
	 */
	public JeuException() {
		super();
	}

	/**
	 * Cree une nouvelle exception avec une cause specifiee
	 * @param message la cause de cette exception
	 */
	public JeuException(String message) {
		super(message);
	}
	
}
