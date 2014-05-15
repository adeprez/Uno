package fr.utt.lo02.uno.io.interfaces;

import fr.utt.lo02.uno.io.IO;

/**
 * Interface definissant les classes pouvant exporter leur contenu dans un flux de sortie
 */
public interface Sauvegardable {
	
	/**
	 * Exporte le contenu de cet objet dans le flux de sortie
	 * @param io le flux de sortie dans lequel exporter les donnees
	 * @return le meme objet {@link IO}
	 */
	public IO sauvegarder(IO io);
	
}
