package fr.utt.lo02.uno.io.exception;

/**
 * Exception de flux deconnecte
 */
public class HorsLigneException extends Exception {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception de flux deconnecte
	 */
	public HorsLigneException() {}

	/**
	 * Cree une nouvelle exception de flux deconnecte
	 * @param message le message de l'exception
	 */
	public HorsLigneException(String message) {
		super(message);
	}

}
