package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.jeu.effet.Effet;

/**
 * {@link ActionJoueur} representant le fait d'annoncer Uno
 */
public class ActionUno implements ActionJoueur, Effet {

	
	@Override
	public Action getType() {
		return Action.UNO;
	}

	@Override
	public void faireEffet(Partie partie) {
		partie.getJoueurs().getJoueur().ajoutEffet(this);
	}

	@Override
	public boolean faireEffet(Partie partie, MainJoueur main, boolean finPartie) {
		return true;
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getCheminImage() {
		return "icone_32.png";
	}

	@Override
	public String toString() {
		return "Uno !";
	}

}
