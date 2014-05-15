package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutMainJoueur implements LayoutManager {
	public static final boolean SENS = true;
	private static final double COEF = 1.5;
	
	
	@Override
	public void layoutContainer(Container parent) {
		int h = parent.getHeight();
		int w = (int) (h/COEF);
		if(parent.getComponentCount() == 1) {
			parent.getComponent(0).setBounds((parent.getWidth() - w)/2, 0, w, h);
		} else {
			int x = 0;
			int dx = (parent.getWidth() - (parent.getComponentCount() * w))/(parent.getComponentCount() - 1);
			if(SENS)
				for(int i = parent.getComponentCount() - 1 ; i>=0 ; i--) {
					parent.getComponent(i).setBounds(x, 0, w, h);
					x += dx + w;
				}
			else for(final Component c : parent.getComponents()) {
				c.setBounds(x, 0, w, h);
				x += dx + w;
			}
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = new Dimension((int) (parent.getSize().height/COEF), parent.getSize().height);
		if(parent.getComponentCount() == 1)
			return d;
		return new Dimension(d.width * parent.getComponentCount(), d.height);
	}

	@Override
	public void removeLayoutComponent(Component comp) {}
	@Override
	public void addLayoutComponent(String name, Component comp) {}
}
