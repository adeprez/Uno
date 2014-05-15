package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPasseTour;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;

/**
 * Carte ayant pour effet de faire piocher deux cartes au joueur suivant
 */
public class CartePlusDeux extends CarteSimple {
	
	
	/**
	 * Cree une nouvelle carte ayant pour effet de faire piocher deux cartes au joueur suivant
	 * @param couleur la couleur de cette carte
	 */
	public CartePlusDeux(Couleur couleur) {
		super(couleur);
	}

	/**
	 * Cree une nouvelle carte ayant pour effet de faire piocher deux cartes au joueur suivant
	 * @param io le flux de donnees contenant la couleur de cette carte plus deux
	 */
	public CartePlusDeux(IO io) {
		super(io);
	}

	@Override
	public String toString() {
		return "+2 " + super.toString();
	}
	
	@Override
	public int getValeurTri() {
		return super.getValeurTri() + 11;
	}

	@Override
	public int getValeurPoints() {
		return 20;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		partie.getJoueurs().getJoueurSuivant().ajoutEffet(new EffetPioche(2));
		partie.getJoueurs().getJoueurSuivant().ajoutEffet(new EffetPasseTour());
	}

	@Override
	public CartePlusDeux dupliquer() {
		return new CartePlusDeux(couleur);
	}

	@Override
	public TypeCarte getType() {
		return TypeCarte.PLUS_DEUX;
	}

}
