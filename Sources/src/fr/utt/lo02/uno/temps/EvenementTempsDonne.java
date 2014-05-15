package fr.utt.lo02.uno.temps;

/**
 * Interface definissant les evenements executes a un temps donne
 */
public interface EvenementTempsDonne extends Evenementiel {
	
	/**
	 * @return le temps (en secondes) ou cet evenement se produira
	 */
	public int getTemps();
	
}
