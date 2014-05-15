package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPasseTour;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Carte ayant pour effet de faire passer le tour du joueur suivant
 */
public class CartePasseTour extends CarteSimple {
	
	
	/**
	 * Cree une nouvelle carte ayant pour effet de faire passer le tour du joueur suivant
	 * @param couleur la couleur de cette carte
	 */
	public CartePasseTour(Couleur couleur) {
		super(couleur);
	}

	/**
	 * Cree une nouvelle carte ayant pour effet de faire passer le tour du joueur suivant
	 * @param io le flux a partir duquel determiner la couleurde cette carte
	 */
	public CartePasseTour(IO io) {
		super(io);
	}

	@Override
	public String toString() {
		return Texte.get("Passe-tour") + " " + super.toString();
	}
	
	@Override
	public int getValeurTri() {
		return super.getValeurTri() + 12;
	}

	@Override
	public int getValeurPoints() {
		return 20;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		partie.getJoueurs().getJoueurSuivant().ajoutEffet(new EffetPasseTour());
	}

	@Override
	public CartePasseTour dupliquer() {
		return new CartePasseTour(couleur);
	}

	@Override
	public TypeCarte getType() {
		return TypeCarte.PASSE_TOUR;
	}

}
