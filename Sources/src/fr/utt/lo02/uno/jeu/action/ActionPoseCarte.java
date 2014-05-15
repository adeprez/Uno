package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.Joueur;

/**
 * Action permettant de poser une carte pour un joueur
 */
public class ActionPoseCarte implements ActionJoueur {
	private final Carte carte;

	
	/**
	 * Cree une nouvelle action de pose de carte
	 * @param carte
	 */
	public ActionPoseCarte(Carte carte) {
		this.carte = carte;
	}

	/**
	 * @return la carte devant etre posee
	 */
	public Carte getCarte() {
		return carte;
	}
	
	@Override
	public String toString() {
		return "pose " + carte;
	}

	@Override
	public Action getType() {
		return Action.POSE;
	}

	@Override
	public void faireEffet(Partie partie) {
		Joueur j = partie.getJoueurs().getJoueur();
		Console.getInstance().affiche(j + " pose " + carte);
		partie.getPlateau().getTalon().poser(j.getMain().poseCarte(carte)).faireEffet(partie, false);
		partie.notifyDeplacementCarte(j.getMain(), partie.getPlateau().getTalon(), carte);
	}

}
