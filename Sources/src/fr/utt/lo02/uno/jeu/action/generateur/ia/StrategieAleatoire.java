package fr.utt.lo02.uno.jeu.action.generateur.ia;

import java.util.List;
import java.util.Random;

import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.generateur.Strategie;
import fr.utt.lo02.uno.jeu.action.generateur.VueJeu;


/**
 * Classe representant une strategie aleatoire pour le comportement d'un ordinateur
 * @see ComportementOrdinateur
 */
public class StrategieAleatoire implements Strategie {
	private final Random r;

	
	/**
	 * Cree une nouvelle strategie aleatoire pour le comportement d'un ordinateur
	 * @see ComportementOrdinateur
	 */
	public StrategieAleatoire() {
		r = new Random();
	}

	@Override
	public int getIDAction(List<ActionJoueur> actions, VueJeu vue) {
		return r.nextInt(actions.size());
	}

}
