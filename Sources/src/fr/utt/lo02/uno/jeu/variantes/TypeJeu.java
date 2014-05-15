package fr.utt.lo02.uno.jeu.variantes;

import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Enumeration des differents modes de jeu possibles
 */
public enum TypeJeu {
	CLASSIQUE, DUEL, EQUIPES, CHALLENGE;
	
	
	/**
	 * @return le nom de ce mode de jeu
	 */
	public String getNom() {
		return toString().charAt(0) + toString().substring(1).replace("_", " ").toLowerCase();
	}

	/**
	 * Cree un jeu a partir du mode de jeu
	 * @param nombreJoueursAdmissibles le nombre de joueurs admissibles pour ce jeu
	 * @return le jeu cree
	 */
	public Jeu creerJeu(int nombreJoueursAdmissibles) {
		switch(this) {
		case DUEL: return new Jeu(2);
		case CLASSIQUE: return new Jeu(nombreJoueursAdmissibles);
		case CHALLENGE: return new JeuChallenge(nombreJoueursAdmissibles);
		case EQUIPES: return new JeuEquipes(nombreJoueursAdmissibles);
		default: throw new IllegalAccessError("Ce type de jeu n'a pas ete implemente");
		}
	}

	/**
	 * @return le chemin vers la documentation de ce type de jeu
	 */
	public String getCheminDoc() {
		return getNom();
	}
	
	/**
	 * @return un talbeau contenant les noms des differents modes de jeu
	 */
	public static String[] getNoms() {
		String[] noms = new String[values().length];
		for(int i=0 ; i<noms.length ; i++)
			noms[i] = Texte.get(values()[i].getNom());
		return noms;
	}
	
}
