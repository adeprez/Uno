package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutPrctCentre implements LayoutManager {
	private final float largeur, hauteur;


	public LayoutPrctCentre(float largeur, float hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	@SuppressWarnings("static-method")
	public void layout(Component parent, Component c, int w, int h) {
		c.setBounds((parent.getWidth() - w)/2, (parent.getHeight() - h)/2, w, h);
	}

	@Override
	public void layoutContainer(Container parent) {
		int w = (int) (largeur * parent.getWidth()), h = (int) (hauteur * parent.getHeight());
		for(Component c : parent.getComponents())
			layout(parent, c, w, h);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(32, 32);
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
