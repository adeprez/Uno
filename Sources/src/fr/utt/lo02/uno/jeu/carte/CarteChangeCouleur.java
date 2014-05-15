package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;

/**
 * Objet abstrait representant une carte pouvant permettre un changement de couleur
 */
public abstract class CarteChangeCouleur extends Carte {

	
	/**
	 * Cree une nouvelle carte permettant de changer de couleur
	 */
	public CarteChangeCouleur() {}
	
	/**
	 * Cree une nouvelle carte permettant de changer de couleur
	 * @param io le flux a partir duquel construire cette carte
	 */
	public CarteChangeCouleur(IO io) {
		super(io);
	}
	
	/**
	 * Assigne la nouvelle couleur de cette carte
	 * @param couleur
	 */
	public void setCouleur(Couleur couleur) {
		super.couleur = couleur;
	}
	
	@Override
	public boolean estSymboleCompatible(Carte carte) {
		return carte instanceof CarteChangeCouleur;
	}

	@Override
	public int getValeurPoints() {
		return 50;
	}
	
	@Override
	public void recouvrir() {
		setCouleur(null);
	}

	@Override
	public String toString() {
		return couleur == null ? "" : super.toString();
	}
	
}
