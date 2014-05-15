package fr.utt.lo02.uno.jeu.evenement;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;

/**
 * Evenement engendrant la fin d'une partie
 */
public class EvenementFinPartie implements Evenementiel {
	private final Partie partie;

	
	/**
	 * Cree un evenement engendrant la fin d'une partie
	 * @param partie la partie sur laquelle s'applique cet evenement
	 */
	public EvenementFinPartie(Partie partie) {
		this.partie = partie;
	}

	@Override
	public void evenement(Periodique p) {
		partie.finPartie();
	}
	
	@Override
	public String toString() {
		return partie + " va se terminer";
	}

}
