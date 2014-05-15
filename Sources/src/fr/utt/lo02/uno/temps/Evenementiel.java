package fr.utt.lo02.uno.temps;

/**
 * Interface definissant un evenement
 */
public interface Evenementiel {
	
	/**
	 * Signifie que cet evenement est arrive a terme
	 * @param p le periodique ayant genere cet evenement
	 */
	public void evenement(Periodique p);
	
}
