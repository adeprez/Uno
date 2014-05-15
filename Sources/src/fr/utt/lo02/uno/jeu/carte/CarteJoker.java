package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Carte permettant de changer la couleur du jeu
 */
public class CarteJoker extends CarteChangeCouleur {
	
	
	/**
	 * Cree une nouvelle carte Joker
	 */
	public CarteJoker() {}
	
	/**
	 * Cree une nouvelle carte Joker a partir d'un flux
	 * @param io le flux a partir duquel construire cette carte
	 */
	public CarteJoker(IO io) {
		super(io);
	}

	@Override
	public String toString() {
		return Texte.get("Joker") + " " + super.toString();
	}

	@Override
	public int getValeurTri() {
		return 500;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		
	}

	@Override
	public CarteJoker dupliquer() {
		return new CarteJoker();
	}

	@Override
	public TypeCarte getType() {
		return TypeCarte.JOKER;
	}

}
