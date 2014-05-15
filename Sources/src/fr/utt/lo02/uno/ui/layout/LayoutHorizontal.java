package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * {@link LayoutManager} dont les composants se placents les uns en dessous des autres en conservant leur taille preferee en largeur,
 * et en prennant la taille maximale en hauteur, moins un espace
 */
public class LayoutHorizontal implements LayoutManager {
	private final int espaceLateral;
	private final float taille;
	

	/**
	 * Cree un layout conservant la largeur preferee du composant, et harmonisant toutes leurs hauteurs a celle du containeur
	 * moins l'espace lateral
	 * @param espaceLateral l'espacement (en pixels) entre chaque composant
	 * @param taille le pourcentage de taille en hauteur par rapport au container
	 */
	public LayoutHorizontal(int espaceLateral, float taille) {
		this.espaceLateral = espaceLateral;
		this.taille = taille;
	}

	
	@Override
	public void layoutContainer(Container parent) {
		if(parent.getComponentCount()>0) {
			int y=espaceLateral/2, hauteur = (int) (parent.getHeight()*taille);
			for(Component c : parent.getComponents()) {
				c.setBounds(y, (parent.getHeight()-hauteur)/2, c.getPreferredSize().width, hauteur);
				y+=c.getBounds().width+espaceLateral;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getMinimumSize();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(parent.getWidth()-parent.getInsets().bottom-parent.getInsets().top, 
				parent.getHeight()-parent.getInsets().left-parent.getInsets().right);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void removeLayoutComponent(Component comp) {}

}
