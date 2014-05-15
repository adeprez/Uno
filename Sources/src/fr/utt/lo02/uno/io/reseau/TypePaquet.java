package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.exception.InvalideException;

/**
 * Enumeration des differents types de paquets utilises pour les communications reseau du jeu du Uno
 */
public enum TypePaquet {
	ID, INFO_SALLE, AJOUT_JOUEUR, RETIRE_JOUEUR, COMMENCER, TEMPS, TOUR, ACTION, FIN_PARTIE, MESSAGE, DEMANDE_INFO_SALLE;
	
	
	/**
	 * @return l'identifiant de ce paquet
	 * @see #get(int)
	 */
	public int getID() {
		return ordinal();
	}
	
	/**
	 * Converti un indentifiant en un {@link TypePaquet} correspondant
	 * @param id l'indentifiant devant correspondre a un type de paquet
	 * @return le type du paquet
	 * @throws InvalideException si le type du paquet l'existe pas
	 * @see #getID()
	 */
	public static TypePaquet get(int id) throws InvalideException {
		TypePaquet t[] = values();
		if(id < 0 || id >= t.length)
			throw new InvalideException(id+" n'est pas un identifiant de paquet valide");
		return t[id];
	}
	
}
