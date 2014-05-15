package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * {@link LayoutManager} dont les composants se placent en prennant la taille la plus adaptee pour 
 * occuper tout l'espace en conservant des bordures
 */
public class LayoutHorizontalMax implements LayoutManager {
	private final int espaceX, ajoutY;
	private final float tailleY;

	
	/**
	 * Cree un {@link LayoutManager} dont les composants se placent en prennant la taille la plus adaptee pour 
	 * occuper tout l'espace en conservant des bordures
	 * @param espaceX l'espacement entre chacun des composants
	 * @param tailleY le pourcentage du la hauteur du container parent, correspondant a la taille de chacun des composants
	 */
	public LayoutHorizontalMax(int espaceX, float tailleY) {
		this(espaceX, 0, tailleY);
	}


	/**
	 * Cree un {@link LayoutManager} dont les composants se placent en prennant la taille la plus adaptee pour 
	 * occuper tout l'espace en conservant des bordures
	 * @param espaceX l'espacement entre chacun des composants
	 * @param ajoutY le decalage par rapport au haut du container parent (utile lorsque celui-ci possede un titre
	 * @param tailleY le pourcentage du la hauteur du container parent, correspondant a la taille de chacun des composants
	 */
	public LayoutHorizontalMax(int espaceX, int ajoutY, float tailleY) {
		this.espaceX = espaceX;
		this.ajoutY = ajoutY;
		this.tailleY = tailleY;
	}


	@Override
	public void layoutContainer(Container parent) {
		int n = parent.getComponentCount();
		if(n>0) {
			int largeur = (parent.getWidth()-espaceX*(n+1))/n, hauteur = (int) (parent.getHeight()*tailleY), x = espaceX;
			for(Component c : parent.getComponents()) {
				c.setBounds(x, (parent.getHeight()-hauteur)/2+ajoutY, largeur, hauteur);
				x += largeur + espaceX;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getMinimumSize();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(parent.getWidth(), parent.getHeight());
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

}
