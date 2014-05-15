package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class LayoutTriplet implements LayoutManager {


	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void layoutContainer(Container parent) {
		Component c;
		int w1 = 0, w2 = 0, h;
		switch(parent.getComponentCount()) {
		case 3:
			c = parent.getComponent(2);
			w2 = Math.max(c.getMinimumSize().width, c.getPreferredSize().width);
			h = Math.max(c.getMinimumSize().height, c.getPreferredSize().height);
			c.setBounds(parent.getWidth() - w2, 0, w2, h);
		case 1:
			c = parent.getComponent(0);
			w1 = Math.max(c.getMinimumSize().width, c.getPreferredSize().width);
			h = Math.max(c.getMinimumSize().height, c.getPreferredSize().height);
			c.setBounds(0, 0, w1, h);
			break;
		default: throw new IllegalAccessError("Trop de composants");
		}
		if(parent.getComponentCount() > 2) {
			c = parent.getComponent(1);
			c.setBounds(w1, 0, parent.getWidth() - w2 - w1, Math.max(c.getMinimumSize().width, h));
		}
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension d = null;
		for(final Component c : parent.getComponents()) {
			if(d == null)
				d = c.getMinimumSize();
			if(c.getMinimumSize().height > d.height)
				d.height = c.getMinimumSize().height;
			d.width += c.getMinimumSize().width;
		}
		return d == null ? new Dimension() : d;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = null;
		for(final Component c : parent.getComponents()) {
			if(d == null)
				d = c.getPreferredSize();
			if(c.getPreferredSize().height > d.height)
				d.height = c.getPreferredSize().height;
		}
		return d == null ? new Dimension(150, 30 * parent.getComponentCount() + 30) : d;
	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}



}
