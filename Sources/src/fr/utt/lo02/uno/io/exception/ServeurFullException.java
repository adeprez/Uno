package fr.utt.lo02.uno.io.exception;

/**
 * Exception generee lorsque le serveur est plein
 */
public class ServeurFullException extends Throwable {
	private static final long serialVersionUID = 1L;

	
	/**
	 * Cree une nouvelle exception de serveur plein
	 */
	public ServeurFullException() {
		super("Le serveur est plein");
	}
	
}
