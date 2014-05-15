package fr.utt.lo02.uno.jeu.effet;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Effet representant le resultat de la pose d'une carte +4
 */
public class EffetPlusQuatre implements Effet {

	
	@Override
	public boolean faireEffet(Partie partie, MainJoueur main, boolean finPartie) {
		if(finPartie) {
			new EffetPioche(4).faireEffet(partie, main);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "+4 : " + Texte.get("piocher et passer son tour, ou tenter un contre-bluff");
	}

	@Override
	public int getPriorite() {
		return 3;
	}

	@Override
	public String getCheminImage() {
		return "pioche 4.png";
	}

}
