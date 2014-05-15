package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import fr.utt.lo02.uno.jeu.carte.ensemble.PlateauJeu;
import fr.utt.lo02.uno.ui.composant.PanelImage;


public class PlateauGraphique extends PanelImage implements LayoutManager {
	private static final long serialVersionUID = 1L;
	private static final double PROPORTIONS = .8;
	private final PanelTasCartes pioche, talon;

	
	public PlateauGraphique(PlateauJeu plateau) {
		setLayout(this);
		add(talon = new PanelTasCartes(true, plateau.getTalon()));
		add(pioche = new PanelTasCartes(false, plateau.getPioche()));
	}
	
	public PanelTasCartes getPioche() {
		return pioche;
	}

	public PanelTasCartes getTalon() {
		return talon;
	}

	@Override
	public void layoutContainer(Container parent) {
		int w = (int) ((getWidth()/2) * PROPORTIONS), h = (int) (getHeight() * PROPORTIONS);
		int dx = (getWidth()/2 - w)/2;
		talon.setBounds(dx, (getHeight() - h)/2 - talon.getDecalage() + 5, w + talon.getDecalage(), h + talon.getDecalage());
		pioche.setBounds(getWidth() - dx - w, (getHeight() - h)/2 - pioche.getDecalage() + 5, w + pioche.getDecalage(), h + pioche.getDecalage());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension();
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getPreferredSize();
	}

	@Override
	public void removeLayoutComponent(Component comp) {}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {}
	
}
