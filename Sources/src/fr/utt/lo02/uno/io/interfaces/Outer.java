package fr.utt.lo02.uno.io.interfaces;

import fr.utt.lo02.uno.io.Out;

/**
 * Interface definissant les classes pouvant ecrire un objet {@link IOable}
 * @see Out
 */
public interface Outer {
	
	/**
	 * Ecrit un objet dans un flux de sortie
	 * @param out l'objet a ecrire
	 * @return vrai si l'objet a bien ete ecrit
	 */
	public boolean write(IOable out);
	
}
