package fr.utt.lo02.uno.io.reseau.listeners;


import fr.utt.lo02.uno.io.reseau.AbstractClient;

import java.util.EventListener;

/**
 * Interface definissant les ecouteurs de deconnexion des clients
 */
public interface DeconnexionListener extends EventListener {
	
	/**
	 * Signale la deconnexion d'un client
	 * @param client le client s'etant deconnecte
	 */
	public void deconnexion(AbstractClient client);
	
}
