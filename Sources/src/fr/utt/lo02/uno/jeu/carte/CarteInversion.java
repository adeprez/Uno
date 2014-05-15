package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPasseTour;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Carte ayant pour effet d'inverser le sens de jeu
 */
public class CarteInversion extends CarteSimple {
	
	
	/**
	 * Cree une nouvelle carte d'inversion
	 * @param couleur la couleur de cette carte
	 */
	public CarteInversion(Couleur couleur) {
		super(couleur);
	}

	/**
	 * Cree une nouvelle carte d'inversion
	 * @param io le flux a partir duquel determiner la couleur de cette carte
	 */
	public CarteInversion(IO io) {
		super(io);
	}

	@Override
	public String toString() {
		return Texte.get("Inversion") + " " + super.toString();
	}

	@Override
	public int getValeurTri() {
		return super.getValeurTri() + 10;
	}

	@Override
	public int getValeurPoints() {
		return 20;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		if(partie.getJoueurs().getMaxJoueurs() == 2)
			partie.getJoueurs().getJoueurSuivant().ajoutEffet(new EffetPasseTour());
		partie.getJoueurs().inverseSens();
	}

	@Override
	public boolean estSymboleCompatible(Carte carte) {
		return carte instanceof CarteInversion;
	}

	@Override
	public CarteInversion dupliquer() {
		return new CarteInversion(couleur);
	}
	
	@Override
	public TypeCarte getType() {
		return TypeCarte.INVERSION;
	}

}
