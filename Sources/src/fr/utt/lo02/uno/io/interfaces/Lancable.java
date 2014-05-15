package fr.utt.lo02.uno.io.interfaces;

/**
 * Interface definissant un objet pouvant etre demarre
 * @see Fermable
 */
public interface Lancable {
	
	/**
	 * Lance l'action correspondante
	 * @return vrai si l'action a bien ete lancee
	 * @see Fermable#fermer()
	 */
	public boolean lancer();
	
}
