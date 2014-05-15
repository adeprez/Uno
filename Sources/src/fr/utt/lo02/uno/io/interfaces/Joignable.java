package fr.utt.lo02.uno.io.interfaces;

import fr.utt.lo02.uno.io.reseau.serveur.ClientServeur;

/**
 * Interface definissant les classes representant un element rejoignable par un {@link ClientServeur}
 */
public interface Joignable {
	
	/**
	 * Fait rejoindre un {@link ClientServeur} dans cet element
	 * @param client le client souhaitant rejoindre cet element
	 * @return vrai si le client a rejoint cet element, faux sinon
	 */
	public boolean rejoindre(ClientServeur client);
	
}
