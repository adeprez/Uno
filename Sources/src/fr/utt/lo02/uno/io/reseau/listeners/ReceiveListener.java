package fr.utt.lo02.uno.io.reseau.listeners;


import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.In;
import fr.utt.lo02.uno.io.reseau.TypePaquet;

import java.util.EventListener;

/**
 * Interface definissant les ecouteurs de reception de paquets
 * @see TypePaquet
 * @see IO
 * @see In
 */
public interface ReceiveListener extends EventListener {
	
	/**
	 * Action a realiser a la reception d'un nouveau paquet sur un flux d'entree
	 * @param type le type du paquet recu
	 * @param io les donnees recues
	 */
	public void recu(TypePaquet type, IO io);
	
}
