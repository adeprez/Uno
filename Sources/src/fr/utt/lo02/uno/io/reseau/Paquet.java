package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.interfaces.Paquetable;
import fr.utt.lo02.uno.io.interfaces.Sauvegardable;

/**
 * Objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
 */
public class Paquet extends IO implements Sauvegardable {
	private final TypePaquet typePaquet;


	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param paquetable l'objet a partir duquel construire ce paquet
	 */
	public Paquet(Paquetable paquetable) {
		this(paquetable.getType(), paquetable.getBytes());
	}

	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param typePaquet le type ce paquet
	 */
	public Paquet(TypePaquet typePaquet) {
		addBytePositif(typePaquet.getID());
		this.typePaquet = typePaquet;
	}

	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param typePaquet le type ce paquet
	 * @param donnees les donnees contenues dans ce paquet
	 */
	public Paquet(TypePaquet typePaquet, byte... donnees) {
		this(typePaquet);
		addBytes(donnees);
	}

	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param typePaquet le type ce paquet
	 * @param donnees les donnees contenues dans ce paquet
	 */
	public Paquet(TypePaquet typePaquet, int... donnees) {
		this(typePaquet);
		addBytesPositif(donnees);
	}

	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param typePaquet le type ce paquet
	 * @param donnees les donnees contenues dans ce paquet
	 */
	public Paquet(TypePaquet typePaquet, IO donnees) {
		this(typePaquet, donnees.getBytes());
	}

	/**
	 * Cree un nouvel objet pouvant etre envoye via un flux, defini par son {@link TypePaquet}
	 * @param typePaquet le type ce paquet
	 * @param donnees les donnees contenues dans ce paquet
	 */
	public Paquet(TypePaquet typePaquet, Sauvegardable donnees) {
		this(typePaquet, donnees.sauvegarder(new IO()));
	}

	/**
	 * @return le type de ce paquet
	 */
	public TypePaquet getType() {
		return typePaquet;
	}

	@Override
	public String toString() {
		return getType() + " : [" + super.toString() + "]";
	}

	@Override
	public IO sauvegarder(IO io) {
		return io.add(this);
	}

}
