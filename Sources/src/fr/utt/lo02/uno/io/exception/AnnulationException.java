package fr.utt.lo02.uno.io.exception;

/**
 * Exception d'annulation d'une action
 */
public class AnnulationException extends Exception {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception d'annulation d'une action
	 */
	public AnnulationException() {
		super();
	}

	/**
	 * Cree une nouvelle exception d'annulation d'une action
	 * @param message le message de l'erreur
	 */
	public AnnulationException(String message) {
		super(message);
	}

}
