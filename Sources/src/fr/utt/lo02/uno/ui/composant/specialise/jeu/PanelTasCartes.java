package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import fr.utt.lo02.uno.io.ImageCartes;
import fr.utt.lo02.uno.io.Sons;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.TasDeCarte;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TasCarteListener;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.ui.composant.specialise.animation.AnimateurCarte;
import fr.utt.lo02.uno.ui.listener.PanelTasCarteListener;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class PanelTasCartes extends JPanel implements MouseListener, TasCarteListener, TourJoueurListener, AnimateurCarte {
	private static final long serialVersionUID = 1L;
	private final TasDeCarte tas;
	private final boolean affiche;
	private boolean survol, actif, dessusVisible;


	public PanelTasCartes(boolean affiche, TasDeCarte tas) {
		this.affiche = affiche;
		this.tas = tas;
		dessusVisible = true;
		setOpaque(false);
		addMouseListener(this);
		tas.addTasCarteListener(this);
	}
	
	public TasDeCarte getTas() {
		return tas;
	}

	public int getDecalage() {
		return tas.getNombre()/5;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
		repaint();
	}

	public void setDessusVisible(boolean dessusVisible) {
		this.dessusVisible = dessusVisible;
		repaint();
	}
	
	public void addPanelTasCarteListener(PanelTasCarteListener l) {
		listenerList.add(PanelTasCarteListener.class, l);
	}
	
	public void removePanelTasCarteListener(PanelTasCarteListener l) {
		listenerList.remove(PanelTasCarteListener.class, l);
	}
	
	public Rectangle getZoneDessin() {
		return new Rectangle(0, 0, getWidth() - getDecalage(), getHeight() - getDecalage());
	}

	@Override
	public void debutTour(TourJoueur tour) {
		setActif(tour.getActions().peutPiocher());
	}

	@Override
	public void finTour(TourJoueur tour) {
		setActif(false);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		setActif(tour.getActions().peutPiocher());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Rectangle r = getZoneDessin();
		Graphics2D g2d = (Graphics2D) g;
		Composite tmp = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f));
		g.fillRoundRect(getWidth()/100, getDecalage(), getWidth() - getWidth()/100, r.height, getWidth()/5, getHeight()/5);
		g2d.setComposite(tmp);
		for(int y=getDecalage() ; y>=0 ; y-=2) {
			if(dessusVisible)
				ImageCartes.getInstance().dessiner(g, 0, y, r.width, r.height, tas.getCarte(), affiche);
			else if(tas.getNombre() > 1)
				ImageCartes.getInstance().dessiner(g, 0, y, r.width, r.height, tas.getCartePrecedente(), affiche);
		}
		if(survol && actif) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f));
			g.setColor(Color.WHITE);
			g.fillRoundRect(0, 0, r.width - 1, r.height - 2, getWidth()/5, getHeight()/5);
			g2d.setComposite(tmp);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(actif) {
			Sons.getInstance().jouer("tchic.wav");
			for(final PanelTasCarteListener l : listenerList.getListeners(PanelTasCarteListener.class))
				l.clique(this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		survol = true;
		getParent().repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		survol = false;
		getParent().repaint();
	}

	@Override
	public Rectangle getPositionSurEcran(Carte carte) {
		Rectangle r = getZoneDessin();
		r.x += getParent().getLocation().x + getLocation().x; 
		r.y += getParent().getLocation().y + getLocation().y;
		return r;
	}

	@Override
	public boolean estVisible(Carte carte) {
		return affiche;
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void changeDessus(Carte carte) {
		repaint();
	}

	@Override
	public void terminerSource(Carte carte) {
		setDessusVisible(true);
	}

	@Override
	public void terminerCible(Carte carte) {
		terminerSource(carte);
	}

	@Override
	public void commencerSource(Carte carte) {
		setDessusVisible(false);
	}

	@Override
	public void commencerCible(Carte carte) {
		commencerSource(carte);
	}


}
