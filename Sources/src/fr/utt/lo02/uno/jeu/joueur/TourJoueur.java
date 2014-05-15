package fr.utt.lo02.uno.jeu.joueur;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.AutomateActions;
import fr.utt.lo02.uno.jeu.exception.ActionInvalideException;
import fr.utt.lo02.uno.jeu.listener.ActionTourListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;

/**
 * Objet representant le tour d'un joueur dans une partie de UNO
 */
public class TourJoueur extends Listenable {
	private final AutomateActions actions;


	/**
	 * Cree un nouveau tour pour le joueur courant
	 * @param joueur le joueur concerne
	 * @param partie la partie sur laquelle ce tour s'applique
	 */
	public TourJoueur(Joueur joueur, Partie partie) {
		actions = new AutomateActions(joueur, partie);
	}
	
	/**
	 * @return l'automate gerant les actions realisables pour ce tour de jeu
	 */
	public AutomateActions getActions() {
		return actions;
	}

	/**
	 * Effectue une action pour le joueur courant
	 * @param idAction l'indice de l'action a effectuer dans la liste des actions possibles
	 * @return vrai si le joueur peut rejouer, faux si son tour est termine
	 * @throws ActionInvalideException si l'action ne peut etre effectuee
	 */
	public boolean faireAction(int idAction) throws ActionInvalideException {
		ActionJoueur action = actions.getActionsPossibles().get(idAction);
		actions.testeAction(action);
		action.faireEffet(actions.getPartie());
		actions.ajouteAction(action);
		for(final ActionTourListener l : getListeners(ActionTourListener.class))
			l.action(idAction, action);
		if(actions.estFini()) {
			actions.getPartie().finTour();
			return false;
		}
		actions.getJoueur().peutRejouer();
		return true;
	}

	/**
	 * Termine le tour du joueur, en lui faisant faire l'action par defaut
	 */
	public void termineTour() {
		try {
			while(!actions.estFini())
				faireAction(0);
		} catch(ActionInvalideException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return vrai si ce tour est termine
	 */
	public boolean estTermine() {
		return actions.estFini() || !actions.estTour();
	}

	/**
	 * Ajoute un ecouteur pour les evenements notifiables de ce tour de jeu
	 * @param l le listener a ajouter
	 */
	public void addActionTourListener(ActionTourListener l) {
		addListener(ActionTourListener.class, l);
	}

	/**
	 * Retire un ecouteur pour les evenements notifiables de ce tour de jeu
	 * @param l le listener a retirer
	 */
	public void removeActionTourListener(ActionTourListener l) {
		removeListener(ActionTourListener.class, l);
	}

	@Override
	public String toString() {
		return "Tour de jeu de " + actions.getJoueur();
	}

}
