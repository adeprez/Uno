package fr.utt.lo02.uno.io.interfaces;

/**
 * Interface definissant un objet pouvant etre stoppe
 * @see Lancable
 */
public interface Fermable {

	/**
	 * Termine l'action correspondante
	 * @return vrai si l'action a bien ete arretee
	 * @see Lancable#lancer()
	 */
	public boolean fermer();
	
}
