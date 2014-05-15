package fr.utt.lo02.uno.langue;

import java.util.EventListener;

/**
 * Interface definissant les ecouteurs de changement de langue
 */
public interface LangueListener extends EventListener {
	
	/**
	 * Signifie que la langue de l'application a change
	 * @param langue la nouvelle langue pour l'application
	 */
	public void changeLangue(Langue langue);
	
}
