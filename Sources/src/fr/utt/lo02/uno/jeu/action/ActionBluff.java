package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.CartePlusQuatre;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;
import fr.utt.lo02.uno.jeu.effet.EffetPlusQuatre;

/**
 * Action de bluffer pour un joueur ayant subit l'effet d'une carte +4
 * @see EffetPlusQuatre
 */
public class ActionBluff implements ActionJoueur {
	private final EffetPlusQuatre effet;

	
	/**
	 * Cree une nouvelle action de bluff pour un joueur ayant subit l'effet d'une carte +4
	 * @param effet l'effet de plus quatre
	 */
	public ActionBluff(EffetPlusQuatre effet) {
		this.effet = effet;
	}
	
	@Override
	public Action getType() {
		return Action.BLUFF;
	}

	@Override
	public void faireEffet(Partie partie) {
		partie.getJoueurs().getJoueur().supprimeEffet(effet);
		int nombre = 6;
		MainJoueur main = partie.getJoueurs().getJoueur().getMain();
		if(aRaison(partie)) {
			nombre -= 2;
			main = partie.getJoueurs().getJoueurPrecedent().getMain();
		}
		else partie.getJoueurs().getJoueur().getTour().getActions().passer();
		new EffetPioche(nombre).faireEffet(partie, main);
	}
	
	@Override
	public String toString() {
		return "contre-bluffe";
	}

	/**
	 * Analyse la partie pour savoir si le joueur precedent a bluffe en posant son +4
	 * @param partie la partie a laquelle le joueur appartient
	 * @return vrai s'il a bluffe
	 */
	public static boolean aRaison(Partie partie) {
		Carte c1 = partie.getPlateau().getTalon().getCartePrecedente();
		for(final Carte c2 : partie.getJoueurs().getJoueurPrecedent().getMain().getCartes())
			if(c1.compatible(c2) && !(c2 instanceof CartePlusQuatre))
				return true;
		return false;
	}

}
