package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutCases implements LayoutManager {
	private final Dimension taille;
	private int espace;
	
	
	public LayoutCases(Dimension taille) {
		this.taille = taille;
	}
	
	public LayoutCases(int taille) {
		this(new Dimension(taille, taille));
	}
	
	public LayoutCases(int taille, int espace) {
		this(taille);
		this.espace = espace;
	}
	
	public LayoutCases setEspace(int espace) {
		this.espace = espace;
		return this;
	}

	@Override public void addLayoutComponent(String name, Component comp) {}
	@Override public void removeLayoutComponent(Component comp) {}	

	@Override
	public void layoutContainer(Container parent) {
		int x = espace, y = espace;
		for(final Component c : parent.getComponents()) {
			c.setBounds(x, y, taille.width, taille.height);
			x += taille.width + espace;
			if(x + taille.width > parent.getWidth()) {
				y += taille.height + espace;
				x = espace;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(taille.width, taille.height);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = new Dimension(espace, espace);
		for(int i=0 ; i<parent.getComponentCount() ; i++) {
			d.width += taille.width + espace;
			if(d.width + taille.width > parent.getWidth()) {
				d.height += taille.height + espace;
			}
		}
		return d;
	}
}
