package fr.utt.lo02.uno.jeu.evenement;

import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;

/**
 * Evenement engendrant la fin d'un tour de jeu
 */
public class EvenementFinTour implements Evenementiel {
	private final TourJoueur tour;


	/**
	 * Cree un nouvel evenement engendrant la fin d'un tour de jeu
	 */
	public EvenementFinTour(TourJoueur tour) {
		this.tour = tour;
	}

	@Override
	public void evenement(Periodique p) {
		tour.termineTour();
	}



}
