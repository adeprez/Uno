package fr.utt.lo02.uno.jeu.action.generateur;

import javax.swing.JOptionPane;

import fr.utt.lo02.uno.jeu.Controleur;
import fr.utt.lo02.uno.jeu.exception.ActionInvalideException;
import fr.utt.lo02.uno.jeu.joueur.Joueur;


/**
 * {@link Controleur} pour une partie se jouant hors ligne
 */
public class ControleurPartieHorsLigne extends Controleur {


	/**
	 * Cree un nouveau {@link Controleur} pour une partie se jouant hors ligne
	 * @param joueur le joueur devant etre controle
	 */
	public ControleurPartieHorsLigne(Joueur joueur) {
		super(joueur);
	}

	@Override
	public void faireAction(int idAction) {
		try {
			getJoueur().getTour().faireAction(idAction);
		} catch(ActionInvalideException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

}
