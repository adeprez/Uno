package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.effet.EffetPioche;
import fr.utt.lo02.uno.jeu.effet.EffetPlusQuatre;

/**
 * Carte ayant pour effet de faire piocher quatre cartes au joueur suivant, 
 * et de permettre au joueur l'ayant posee de choisir la nouvelle couleur de jeu
 */
public class CartePlusQuatre extends CarteChangeCouleur {

	
	/** 
	 * Cree une nouvelle carte +4, ayant pour effet de faire piocher quatre cartes au joueur suivant, 
	 * et de permettre au joueur l'ayant posee de choisir la nouvelle couleur de jeu
	 */
	public CartePlusQuatre() {}
	
	/**
	 * Cree une nouvelle +4, ayant pour effet de faire piocher quatre cartes au joueur suivant, 
	 * et de permettre au joueur l'ayant posee de choisir la nouvelle couleur de jeu
	 * @param io le flux a partir duquel determiner la couleur de cette carte
	 */
	public CartePlusQuatre(IO io) {
		super(io);
	}
	
	@Override
	public String toString() {
		return "+4 " + super.toString();
	}

	@Override
	public int getValeurTri() {
		return 1000;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		if(debutPartie)
			new EffetPioche(4).faireEffet(partie, partie.getJoueurs().getJoueurSuivant().getMain());
		else partie.getJoueurs().getJoueurSuivant().ajoutEffet(new EffetPlusQuatre());
	}
	
	@Override
	public CartePlusQuatre dupliquer() {
		return new CartePlusQuatre();
	}

	@Override
	public TypeCarte getType() {
		return TypeCarte.PLUS_QUATRE;
	}

}
