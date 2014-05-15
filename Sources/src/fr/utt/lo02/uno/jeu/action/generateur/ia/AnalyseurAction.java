package fr.utt.lo02.uno.jeu.action.generateur.ia;

import java.util.Random;

import fr.utt.lo02.uno.jeu.action.ActionChoixCouleur;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.ActionPoseCarte;
import fr.utt.lo02.uno.jeu.action.generateur.VueJeu;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.carte.TypeCarte;


/**
 * Classe representant un analyseur d'actions, attribuant un poids aux actions proposees
 */
public class AnalyseurAction implements AttributeurPoints {
	private static final int ACTION_FAIBLE = -1, ACTION_FORTE = 100;
	private final boolean fort;
	private final Random r;
	private int agressivite;

	
	/**
	 * Cree un nouvel analyseur d'action
	 * @param fort si ce comportement est fort
	 */
	public AnalyseurAction(boolean fort) {
		this.fort = fort;
		r = new Random();
	}
	
	@Override
	public int getPoints(ActionJoueur action, VueJeu vue) {
		switch(action.getType()) {
		case BLUFF:
			Carte dessous = vue.getAncienneCarteTalon();
			if(fort && dessous.getCouleur() == vue.getCarteTalon().getCouleur())
				return ACTION_FORTE/2;
			return (agressivite/3) * vue.getNombreCarteJoueurPrecedent();
			
		case NON_BLUFF:
			return ACTION_FORTE/2 - r.nextInt(agressivite);
			
		case CHOIX_COULEUR:
			Couleur couleur = ((ActionChoixCouleur) action).getCouleur();
			return vue.getNombreCarteCouleur(couleur) == 0 ? ACTION_FAIBLE : vue.getPointsCarteCouleur(couleur);
			
		case CONTRE_UNO:
			return fort ? ACTION_FORTE : r.nextInt(ACTION_FORTE/2);
			
		case UNO:
			return fort ? ACTION_FORTE : r.nextInt(ACTION_FORTE);
			
		case FIN_TOUR:
			return fort ? ACTION_FAIBLE : r.nextInt(ACTION_FORTE);
			
		case PIOCHE:
			return ACTION_FAIBLE;
			
		case POSE:
			Carte c = ((ActionPoseCarte) action).getCarte();
			if(c.getType() == TypeCarte.PLUS_QUATRE)
				return agressivite/(r.nextInt(10) + 2);
			if(c.getType() == TypeCarte.JOKER)
				return 0;
			return c.getValeurPoints() * vue.getNombreCarteCouleur(c.getCouleur()) + 1;
		}
		return 0;
	}

	@Override
	public void nouvelleAction(VueJeu vue) {
		agressivite = ACTION_FORTE/Math.max(1, vue.getNombreCarteJoueurSuivant());
	}

}
