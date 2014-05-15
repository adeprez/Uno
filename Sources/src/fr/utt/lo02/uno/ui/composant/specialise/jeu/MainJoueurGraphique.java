package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.MainListener;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.composant.specialise.animation.AnimateurCarte;
import fr.utt.lo02.uno.ui.layout.LayoutMainJoueur;


public class MainJoueurGraphique extends PanelImage implements MainListener, AnimateurCarte {
	private static final long serialVersionUID = 1L;
	protected final Map<Carte, CarteGraphique> cartes;
	private final Map<Carte, Component> combles; 
	private final boolean visible;


	public MainJoueurGraphique(MainJoueur main) {
		this(main, false);
	}

	public MainJoueurGraphique(MainJoueur main, boolean visible) {
		this.visible = visible;
		combles = new HashMap<Carte, Component>();
		setLayout(new LayoutMainJoueur());
		cartes = new HashMap<Carte, CarteGraphique>();
		main.addMainListener(this);
		init(main);
	}

	public void init(MainJoueur main) {
		init(main.getCartes());
	}

	public void init(List<Carte> cartes) {
		for(int i=0 ; i<cartes.size() ; i++)
			ajoutCarte(cartes.get(i), i);
	}

	public void activePossibilites(TourJoueur tour) {
		for(final Carte c : tour.getActions().getCartesPosables())
			cartes.get(c).setActif(true);
		change();
	}

	public CarteGraphique ajout(Carte carte, CarteGraphique c) {
		cartes.put(carte, c);
		return c;
	}

	public CarteGraphique retire(Carte carte) {
		CarteGraphique c = cartes.remove(carte);
		return c;
	}

	public void change() {
		validate();
		repaint();
		if(getParent() != null) {
			getParent().validate();
			getParent().repaint();
		}
	}

	public Rectangle getEmplacementCarte(Carte carte) {
		if(combles.containsKey(carte))
			return combles.get(carte).getBounds();
		return cartes.get(carte).getBounds();
	}

	@Override
	public void ajoutCarte(Carte carte, int rang) {
		add(ajout(carte, new CarteGraphique(carte, visible)), LayoutMainJoueur.SENS ? (getComponentCount() - rang) : rang);
		change();
	}

	@Override
	public void retireCarte(Carte carte, int rang) {
		remove(retire(carte));
		Component c = Box.createGlue();
		combles.put(carte, c);
		add(c, LayoutMainJoueur.SENS ? (getComponentCount() - rang) : rang);
		change();
	}

	@Override
	public void commencerSource(Carte carte) {
		commencerCible(carte);
	}

	@Override
	public void commencerCible(Carte carte) {
		cartes.get(carte).setVisible(false);
	}

	@Override
	public void terminerSource(Carte carte) {
		try {
			remove(combles.remove(carte));
		} catch(Exception err) {
			err.printStackTrace();
		}
		doLayout();
	}

	@Override
	public void terminerCible(Carte carte) {
		if(cartes.containsKey(carte))
			cartes.get(carte).setVisible(true);
		doLayout();
	}

	@Override
	public Rectangle getPositionSurEcran(Carte carte) {
		Rectangle r = getEmplacementCarte(carte);
		r.x += getLocation().x + getParent().getLocation().x;
		r.y += getLocation().y + getParent().getLocation().y;
		return r;
	}

	@Override
	public boolean estVisible(Carte carte) {
		return visible;
	}

}
