package fr.utt.lo02.uno.io.interfaces;

import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.TypePaquet;

/**
 * Interface definissant les objets pouvant etre envoyes dans un flux de donnees, via un {@link TypePaquet} et la generation d'un tableau d'octets
 * @see IOable
 */
public interface Paquetable extends IOable {
	
	/**
	 * @return le type du paquet a envoyer. Determine la facon dont les {@link AbstractClient} traiteront les informations
	 */
	public TypePaquet getType();
	
}
