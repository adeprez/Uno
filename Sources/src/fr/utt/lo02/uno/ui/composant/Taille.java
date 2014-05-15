package fr.utt.lo02.uno.ui.composant;

import java.awt.Dimension;


public class Taille {
	private final Dimension dimension;
	private int max = 2147483647, min = 1;
	private double rapport;


	public Taille() {
		dimension = new Dimension(1, 1);
		setRapport(1);
	}

	public Taille(int largeur) {
		this();
		setLargeur(largeur);
	}

	public Taille(int largeur, int hauteur) {
		this(largeur, (double) (hauteur) / largeur);
	}

	public Taille(int largeur, double rapport) {
		dimension = new Dimension();
		setRapport(rapport).setLargeur(largeur);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public double getRapport() {
		return rapport;
	}

	public int getLargeur() {
		return dimension.width;
	}

	public int getHauteur() {
		return dimension.height;
	}

	public int getLargeurMax() {
		return max;
	}

	public int getHauteurMax() {
		return (int) (max * rapport);
	}
	
	public int getlargeurMin() {
		return min;
	}
	
	public int getHauteurMin() {
		return (int) (min * rapport);
	}

	public Taille agrandir(int incr) {
		return setLargeur(dimension.width + incr);
	}

	public Taille setRapport(double rapport) {
		this.rapport = rapport;
		return this;
	}
	
	public Taille forceLargeur(int largeur) {
		dimension.width = largeur;
		return this;
	}
	
	public Taille forceHauteur(int hauteur) {
		dimension.height = hauteur;
		return this;
	}
	
	public void setMax() {
		setLargeur(getLargeurMax());
	}
	
	public void setMin() {
		setLargeur(getlargeurMin());
	}
	
	public void changeBorne() {
		if(getLargeur() > getLargeurMax()/2)
			setMin();
		else setMax();
	}

	public Taille setLargeur(int largeur) {
		int h = (int) (largeur * rapport);
		if(largeur <= getLargeurMax() && h <= getHauteurMax() && largeur >= getlargeurMin() && h >= getHauteurMin()) {
			dimension.width = largeur;
			dimension.height = h;
		}
		return this;
	}

	public Taille setHauteur(int hauteur) {
		int l = (int) (hauteur/rapport);
		if(hauteur <= getHauteurMax() && l <= getLargeurMax() && hauteur >= getHauteurMin() && l >= getlargeurMin()) {
			dimension.height = hauteur;
			dimension.width = l;
		}
		return this;
	}

	public Taille setLargeurMax(int max) {
		this.max = max;
		return this;
	}
	
	public Taille setLargeurMin(int min) {
		this.min = min;
		return this;
	}

	@Override
	public String toString() {
		return dimension.width + "/" + getLargeurMax() + ", " + getHauteur() + "/" + getHauteurMax() 
				+ " (" + (dimension.width * 100)/max + "%)";
	}

}
