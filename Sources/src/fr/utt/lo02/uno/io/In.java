package fr.utt.lo02.uno.io;

import fr.utt.lo02.uno.io.exception.HorsLigneException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Classe representant un flux d'entree, pouvant egalement servir de flux de sortie
 */
public class In extends InOut {

	/**
	 * Cree un nouveau flux d'entree
	 * @param in l'inputstream a partir duquel creer ce flux
	 */
	public In(InputStream in) {
		setIn(in);
	}

	@Override
	public byte[] lire() throws IOException, HorsLigneException {
		return lire(lireShortInt());
	}

}
