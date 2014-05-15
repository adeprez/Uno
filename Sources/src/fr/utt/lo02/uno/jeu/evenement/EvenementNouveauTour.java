package fr.utt.lo02.uno.jeu.evenement;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;

/**
 * Evenement engendrant le debut d'un tour de jeu
 */
public class EvenementNouveauTour implements Evenementiel {
	private final Partie partie;

	
	/**
	 * Cree un evenement engendrant le debut d'un tour de jeu
	 * @param partie la partie sur laquelle s'applique cet evenement
	 */
	public EvenementNouveauTour(Partie partie) {
		this.partie = partie;
	}

	@Override
	public void evenement(Periodique p) {
		partie.nouveauTour();
	}
	
	@Override
	public String toString() {
		return partie.getJoueurs().getJoueurSuivant() + " va jouer";
	}

}
