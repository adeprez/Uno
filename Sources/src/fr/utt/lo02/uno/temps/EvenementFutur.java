package fr.utt.lo02.uno.temps;

/**
 * Interface definissant un evenement allant se produire apres un temps donne
 */
public interface EvenementFutur extends EvenementTempsDonne {
	
	/**
	 * Methode redefinissant le temps auquel cet evenement se produira
	 * @param temps le temps precis (en secondes) auquel cet evenement se produira
	 */
	public void setTemps(int temps);
	
}
