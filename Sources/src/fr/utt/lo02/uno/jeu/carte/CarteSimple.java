package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;

/**
 * Carte abstraite representant une carte devant etre cree avec une couleur
 */
public abstract class CarteSimple extends Carte {

	
	/**
	 * Cree une nouvelle carte dont la couleur ne peut pas varier
	 * @param couleur la couleur de cette carte
	 */
	public CarteSimple(Couleur couleur) {
		super(couleur);
	}
	
	/**
	 * Cree une nouvelle carte dont la couleur ne peut pas varier
	 * @param io le flux de donnees contenant la couleur de cette carte
	 */
	public CarteSimple(IO io) {
		super(io);
	}

	@Override
	public void recouvrir() {
		
	}
	
}
