package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;

/**
 * Action sans effet representant le fait de terminer son tour
 */
public class ActionFinTour implements ActionJoueur {

	
	@Override
	public void faireEffet(Partie partie) {
		
	}

	@Override
	public Action getType() {
		return Action.FIN_TOUR;
	}

	@Override
	public String toString() {
		return "terminer son tour";
	}

}
