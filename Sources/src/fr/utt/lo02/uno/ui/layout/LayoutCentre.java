package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutCentre implements LayoutManager {
	private final int largeur, hauteur;


	public LayoutCentre(int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	public LayoutCentre() {
		this(0, 0);
	}

	public void layout(Component parent, Component c) {
		if(largeur == 0 || hauteur == 0) {
			Dimension d = c.getPreferredSize();
			setTaille(parent, c, d.width, d.height);
		}
		else setTaille(parent, c, largeur, hauteur);
	}

	@Override
	public void layoutContainer(Container parent) {
		for(Component c : parent.getComponents())
			layout(parent, c);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(parent.getWidth(), parent.getHeight());
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {}


	@Override
	public void removeLayoutComponent(Component comp) {}
	
	public static void setTaille(Component parent, Component c, int w, int h) {
		if(parent.getWidth() < w)
			w = parent.getWidth();
		if(parent.getHeight() < h)
			h = parent.getHeight();
		c.setBounds((parent.getWidth() - w)/2, (parent.getHeight() - h)/2, w, h);
	}

}
