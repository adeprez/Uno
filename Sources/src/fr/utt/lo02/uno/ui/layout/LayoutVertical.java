package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * {@link LayoutManager} dont les composants se placents les uns en dessous des autres en conservant leur taille preferee en hauteur,
 * et en prennant la taille maximale en largeur, moins un espace lateral
 */
public class LayoutVertical implements LayoutManager {
	private final int espaceLateral;
	

	/**
	 * Cree un layout conservant la hauteur preferee du composant, et harmonisant toutes leurs largeurs a celle du containeur
	 * moins l'espace lateral
	 * @param espaceLateral l'espacement (en pixels) de chaque cote du composant
	 */
	public LayoutVertical(int espaceLateral) {
		this.espaceLateral = espaceLateral;
	}


	@Override
	public void layoutContainer(Container parent) {
		if(parent.getComponentCount()>0) {
			int taille = 0, y;
			for(Component c : parent.getComponents()) taille+=c.getHeight()/(parent.getComponentCount()*2);
			int espace = (parent.getHeight()-taille)/(parent.getComponentCount());
			if(parent.getComponentCount()==1) y=espace/2;
			else y = espace/(parent.getComponentCount()-1);
			for(Component c : parent.getComponents()) {
				c.setBounds(espaceLateral, y, parent.getWidth()-espaceLateral*2, c.getPreferredSize().height);
				y+=espace;
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
