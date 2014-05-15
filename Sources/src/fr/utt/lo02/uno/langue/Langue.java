package fr.utt.lo02.uno.langue;

import java.util.Locale;

/**
 * Enumeration des langues disponibles pour l'application
 */
public enum Langue {
	FRANCAIS, ANGLAIS, CHINOIS, ESPAGNOL, PORTUGAIS;

	
	/**
	 * Determine la langue de l'utilisateur
	 * @return la langue de l'utilisateur
	 * @see Locale#getDefault()
	 */
	public static Langue get() {
		Locale l = Locale.getDefault();
		if(Locale.ENGLISH.equals(l))
			return ANGLAIS;
		if(Locale.CHINESE.equals(l))
			return CHINOIS;
		return FRANCAIS;
	}

	/**
	 * @return une representation textuelle de ce nom
	 */
	public String getNom() {
		return Texte.get(toString().charAt(0) + toString().substring(1).toLowerCase());
	}

	/**
	 * @return le chemin vers l'image du drapeau correspondant a la langue
	 */
	public String getCheminImage() {
		return toString().toLowerCase() + ".jpg";
	}

	/**
	 * @return l'ensemble des noms des langages
	 */
	public static String[] getNoms() {
		Langue[] langues = values();
		String[] noms = new String[langues.length];
		for(int i=0 ; i<noms.length ; i++)
			noms[i] = langues[i].getNom();
		return noms;
	}
}
