package fr.utt.lo02.uno.jeu.effet;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Effet representant le fait de passer son tour
 */
public class EffetPasseTour implements Effet {

	
	@Override
	public boolean faireEffet(Partie partie, MainJoueur main, boolean finPartie) {
		if(!finPartie)
			partie.finTour();
		return true;
	}
	
	@Override
	public String toString() {
		return Texte.get("passer son tour");
	}

	@Override
	public int getPriorite() {
		return 1;
	}

	@Override
	public String getCheminImage() {
		return "passe tour.png";
	}

}
