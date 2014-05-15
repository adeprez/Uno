package fr.utt.lo02.uno.temps;

import java.util.EventListener;

/**
 * Interface definissant les ecouteurs d'un objet periodique
 */
public interface PeriodiqueListener extends EventListener {
	
	/**
	 * Realise l'action a effectuer a chaque iteration
	 * @param p l'element periodique ayant genere cet evenement
	 */
	public void action(Periodique p);
	
}
