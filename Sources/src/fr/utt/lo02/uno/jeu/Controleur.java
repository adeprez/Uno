package fr.utt.lo02.uno.jeu;

import java.util.List;

import fr.utt.lo02.uno.jeu.action.Action;
import fr.utt.lo02.uno.jeu.action.ActionChoixCouleur;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.ActionPoseCarte;
import fr.utt.lo02.uno.jeu.action.AutomateActions;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.CarteGraphique;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.PanelTasCartes;
import fr.utt.lo02.uno.ui.listener.ChoixCouleurListener;
import fr.utt.lo02.uno.ui.listener.CliqueCarteListener;
import fr.utt.lo02.uno.ui.listener.PanelActionJoueurListener;
import fr.utt.lo02.uno.ui.listener.PanelTasCarteListener;


/**
 * Classe abtraite representant le controleur de l'application du jeu du Uno au cours d'une partie, selon le design-pattern MVC
 */
public abstract class Controleur implements CliqueCarteListener, PanelTasCarteListener, ChoixCouleurListener, PanelActionJoueurListener {
	private final Joueur joueur;
	
	
	/**
	 * Cree un nouveau controleur de l'application du jeu du Uno au cours d'une partie, selon le design-pattern MVC
	 * @param joueur le joueur a controler
	 */
	public Controleur(Joueur joueur) {
		this.joueur= joueur; 
	}

	/**
	 * @return le joueur controle
	 */
	public Joueur getJoueur() {
		return joueur;
	}
	
	/**
	 * Effectue l'action, selon son implementation dans la classe fille
	 * @param idAction l'identifiant de l'action a effectuer, correspondant a son rang dans la liste des actions possibles
	 * @see AutomateActions#getActionsPossibles()
	 */
	public abstract void faireAction(int idAction);

	/**
	 * @param carte la carte a poser
	 * @return l'index de l'action permettant de poser cette carte
	 */
	public int getIDActionPose(Carte carte) {
		List<ActionJoueur> actions = joueur.getTour().getActions().getActionsPossibles();
		for(int id = 0 ; id < actions.size() ; id++) {
			ActionJoueur a = actions.get(id);
			if(a.getType() == Action.POSE && ((ActionPoseCarte) a).getCarte() == carte)
				return id;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * @param couleur la couleur a choisir
	 * @return l'index de l'action permettant de choisir cette couleur
	 */
	public int getIDActionChoixCouleur(Couleur couleur) {
		List<ActionJoueur> actions = joueur.getTour().getActions().getActionsPossibles();
		for(int id = 0 ; id < actions.size() ; id++) {
			ActionJoueur a = actions.get(id);
			if(a.getType() == Action.CHOIX_COULEUR && ((ActionChoixCouleur) a).getCouleur() == couleur)
				return id;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * @param action l'action a rechercher
	 * @return l'index de l'action correspondante
	 */
	public int getID(Action action) {
		List<ActionJoueur> actions = joueur.getTour().getActions().getActionsPossibles();
		for(int id = 0 ; id < actions.size() ; id++) {
			ActionJoueur a = actions.get(id);
			if(a.getType() == action)
				return id;
		}
		throw new IllegalArgumentException("Cette action ne peut etre faite");
	}

	@Override
	public void clique(CarteGraphique carte) {
		faireAction(getIDActionPose(carte.getCarte()));
	}

	@Override
	public void clique(PanelTasCartes tas) {
		faireAction(getID(Action.PIOCHE));
	}

	@Override
	public void choixCouleur(Couleur couleur) {
		faireAction(getIDActionChoixCouleur(couleur));
	}

	@Override
	public void uno() {
		faireAction(getID(Action.UNO));
	}

	@Override
	public void passeTour() {
		faireAction(getID(Action.FIN_TOUR));
	}

	@Override
	public void contreUno() {
		faireAction(getID(Action.CONTRE_UNO));
	}

	@Override
	public void contreBluff(boolean contreBluffe) {
		faireAction(getID(contreBluffe ? Action.BLUFF : Action.NON_BLUFF));
	}
	
	
	
	
}
