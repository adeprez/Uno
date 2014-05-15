package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.CarteChangeCouleur;
import fr.utt.lo02.uno.jeu.carte.Couleur;

/**
 * Action representant le fait de choisir la couleur par un joueur
 */
public class ActionChoixCouleur implements ActionJoueur {
	private final Couleur couleur;

	
	/**
	 * Cree une nouvelle action de changement de couleur
	 * @param couleur la couleur a donner a la {@link CarteChangeCouleur} presente sur le dessus du talon
	 */
	public ActionChoixCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	/**
	 * @return la couleur choisie
	 */
	public Couleur getCouleur() {
		return couleur;
	}
	
	@Override
	public void faireEffet(Partie partie) {
		((CarteChangeCouleur) partie.getPlateau().getTalon().getCarte()).setCouleur(couleur);
	}

	@Override
	public Action getType() {
		return Action.CHOIX_COULEUR;
	}

	@Override
	public String toString() {
		return "choisir la couleur " + couleur.getNom();
	}

}
