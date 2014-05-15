package fr.utt.lo02.uno.io.reseau.listeners;


import fr.utt.lo02.uno.io.reseau.AbstractClient;

import java.util.EventListener;

/**
 * Interface definissant les ecouteurs de connexion des clients
 */
public interface ConnexionListener extends EventListener {
	
	/**
	 * Signale la connexion d'un nouveau client
	 * @param client le nouveau client s'etant connecte
	 */
	public void connexion(AbstractClient client);
	
}
