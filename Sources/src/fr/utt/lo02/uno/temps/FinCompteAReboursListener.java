package fr.utt.lo02.uno.temps;

import java.util.EventListener;


/**
 * Interface definissant les ecouteurs de la fin d'un compte a rebours
 */
public interface FinCompteAReboursListener extends EventListener {
	
	/**
	 * Signifie qu'un compte a rebours est arrive a terme
	 * @param temps le temps du compte a rebours
	 * @param source le compte a rebours ayant produit cet evenement
	 */
	public void finCompteARebours(int temps, CompteARebours source);
	
}
