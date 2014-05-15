package fr.utt.lo02.uno.jeu.action.generateur.ia;

import java.util.List;
import java.util.Random;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.generateur.Strategie;
import fr.utt.lo02.uno.jeu.action.generateur.VueJeu;


/**
 * Classe representant une strategie pour le comportement d'un ordinateur, reflechissant selon une methode d'attribution par points
 * @see ComportementOrdinateur
 */
public class StrategiePoints implements Strategie {
	private final AttributeurPoints analyseurActions;
	
	
	/**
	 * Cree une nouvelle strategie pour le comportement d'un ordinateur, reflechissant selon une methode d'attribution par points
	 * @param analyseurActions l'objet en charge de valuer les actions
	 */
	public StrategiePoints(AttributeurPoints analyseurActions) {
		this.analyseurActions = analyseurActions;
	}

	
	/**
	 * Cree une nouvelle strategie pour le comportement d'un ordinateur, reflechissant selon une methode d'attribution par points
	 * @see AnalyseurAction
	 */
	public StrategiePoints() {
		this(new AnalyseurAction(true));
	}


	@Override
	public int getIDAction(List<ActionJoueur> actions, VueJeu vue) {
		int id = 0, i = 0, max = -1;
		analyseurActions.nouvelleAction(vue);
		Random r = new Random();
		for(final ActionJoueur a : actions) {
			int poidsAction = analyseurActions.getPoints(a, vue);
			if(poidsAction > max || (poidsAction == max && r.nextBoolean())) {
				max = poidsAction;
				id = i;
			}
			i++;
		}
		return id;
	}

}
