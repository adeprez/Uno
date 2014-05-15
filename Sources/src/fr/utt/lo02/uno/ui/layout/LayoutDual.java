package fr.utt.lo02.uno.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * Cree un {@link LayoutManager} specialise pouvant contenir deux composants :
 * le premier se place au dessus du second (en gardant sa hauteur preferee) avec un espace entre eux de 10
 * tandis que le second se place au centre avec une largeur correspondant a 85% de celle du container parent,
 * et une hauteur relative ou absolue, precisee
 **/
public class LayoutDual implements LayoutManager {
	private static final int ESPACE = 10;
	private static final float COEFL = 0.85f;
	private float coefH;
	private int hauteur;
	private boolean absolu;


	/**
	 * Cree un {@link LayoutManager} specialise pouvant contenir deux composants :
	 * le premier se place au dessus du second (en gardant sa hauteur preferee) avec un espace entre eux de 10
	 * tandis que le second se place au centre avec une largeur correspondant a 85% de celle du container parent,
	 * et un pourcentage de hauteur precisee 
	 * @param coefH le pourcentage de hauteur de l'element ajoute en second, en fonction du container parent
	 */
	public LayoutDual(float coefH) {
		this.coefH = coefH;
	}
	
	
	/**
	 * Cree un {@link LayoutManager} specialise pouvant contenir deux composants :
	 * le premier se place au dessus du second (en gardant sa hauteur preferee) avec un espace entre eux de 10
	 * tandis que le second se place au centre avec une largeur correspondant a 85% de celle du container parent,
	 * et une hauteur precisee
	 * @param hauteur la hauteur de l'element ajoute en second
	 */
	public LayoutDual(int hauteur) {
		this.hauteur = hauteur;
		absolu = true;
	}


	@Override
	public void layoutContainer(Container parent) {
		if(parent.getComponentCount()==2) {
			int w = (int) (parent.getWidth()*COEFL);
			int h = absolu?hauteur:(int) (parent.getHeight()*coefH);
			int x = (parent.getWidth()-w)/2;
			int y = (parent.getHeight()-h)/2;
			parent.getComponent(1).setBounds(x, y, w, h);
			int hauteur = parent.getComponent(0).getPreferredSize().height;
			if(parent.getComponent(0).getPreferredSize().width>w) hauteur*=2;
			if(parent.getComponent(0).getPreferredSize().width/2>w) hauteur*=1.5;
			parent.getComponent(0).setBounds(x, y-hauteur-ESPACE, w, hauteur);
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
