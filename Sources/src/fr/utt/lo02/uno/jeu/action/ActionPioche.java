package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;

/**
 * Action de piocher une carte
 */
public class ActionPioche extends EffetPioche implements ActionJoueur {

	
	/**
	 * Cree une nouvelle action de pioche
	 */
	public ActionPioche() {
		super(1);
	}
	
	@Override
	public Action getType() {
		return Action.PIOCHE;
	}

	@Override
	public void faireEffet(Partie partie) {
		Console.getInstance().affiche(partie.getJoueurs().getJoueur() + " " + this);
		faireEffet(partie, partie.getJoueurs().getJoueur().getMain());
	}
	
}
