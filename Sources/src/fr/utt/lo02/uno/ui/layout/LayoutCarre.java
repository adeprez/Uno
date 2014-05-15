package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutCarre implements LayoutManager {
	
	
	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void layoutContainer(Container parent) {
		int taille = Math.min(parent.getSize().width, parent.getSize().height);
		for(final Component c : parent.getComponents()) {
			c.setBounds((parent.getWidth() - taille)/2, (parent.getHeight() - taille)/2, taille, taille);
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int taille = Math.min(parent.getMinimumSize().width, parent.getMinimumSize().height);
		return new Dimension(taille, taille);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int taille = Math.min(parent.getPreferredSize().width, parent.getPreferredSize().height);
		return new Dimension(taille, taille);
	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}



}
