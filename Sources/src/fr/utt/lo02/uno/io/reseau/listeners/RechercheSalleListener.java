package fr.utt.lo02.uno.io.reseau.listeners;

import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;

import java.net.InetAddress;
import java.util.EventListener;

/**
 * Interface definissant les ecouteurs de la detection de salles de jeu en ligne
 * @see SalleReseau
 */
public interface RechercheSalleListener extends EventListener {
	
	/**
	 * Signifie qu'une nouvelle salle de jeu de Uno en ligne a ete trouvee
	 * @param adresse l'adresse de cette salle
	 * @param infos les informations relatives a cette salle de jeus
	 * @see SalleReseau
	 */
	public void nouvelleSalle(InetAddress adresse, InfoSalle infos);
	
}
