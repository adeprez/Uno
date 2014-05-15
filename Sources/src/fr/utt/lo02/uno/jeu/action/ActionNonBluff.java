package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;
import fr.utt.lo02.uno.jeu.effet.EffetPlusQuatre;
import fr.utt.lo02.uno.jeu.joueur.Joueur;

/**
 * Action de ne pas bluffer pour un joueur ayant subit l'effet d'une carte +4
 * @see EffetPlusQuatre
 */
public class ActionNonBluff extends EffetPioche implements ActionJoueur {
	private final EffetPlusQuatre effet;

	
	/**
	 * Cree une nouvelle action de ne pas bluffer pour un joueur ayant subit l'effet d'une carte +4
	 * @param effet l'effet +4 associe
	 */
	public ActionNonBluff(EffetPlusQuatre effet) {
		super(4);
		this.effet = effet;
	}

	@Override
	public Action getType() {
		return Action.NON_BLUFF;
	}

	@Override
	public void faireEffet(Partie partie) {
		Joueur j = partie.getJoueurs().getJoueur();
		j.supprimeEffet(effet);
		faireEffet(partie, j.getMain());
		j.getTour().getActions().passer();
	}
	
	@Override
	public String toString() {
		return "ne contre-bluffe pas";
	}

}
