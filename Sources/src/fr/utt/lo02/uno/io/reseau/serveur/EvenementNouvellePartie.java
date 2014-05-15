package fr.utt.lo02.uno.io.reseau.serveur;

import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;

/**
 * Evenement permettant de lancer une nouvelle partie
 */
public class EvenementNouvellePartie implements Evenementiel {
	private final Jeu jeu;

	
	/**
	 * Cree un nouvel evenement permettant de lancer une nouvelle partie
	 * @param jeu le jeu pour lequel demarrer une nouvelle partie 
	 */
	public EvenementNouvellePartie(Jeu jeu) {
		this.jeu = jeu;
	}

	@Override
	public void evenement(Periodique p) {
		jeu.nouvellePartie();
	}

}
