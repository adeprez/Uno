package fr.utt.lo02.uno.jeu.action;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;
import fr.utt.lo02.uno.jeu.joueur.Joueur;

/**
 * {@link ActionJoueur} de contre-Uno. Si un joueur a pose son avant-derniere carte et n'a pas dit Uno, alors cette action peut etre appelee.
 * Elle a pour effet de faire piochre deux cartes au joueur precedent
 */
public class ActionContreUno implements ActionJoueur {

	
	@Override
	public Action getType() {
		return Action.CONTRE_UNO;
	}

	@Override
	public void faireEffet(Partie partie) {
		Joueur j = partie.getJoueurs().getJoueurPrecedent();
		j.ajoutEffet(new EffetPioche(2));
		j.activeEffets(partie, false);
	}
	
	@Override
	public String toString() {
		return "Contre uno !";
	}

}
