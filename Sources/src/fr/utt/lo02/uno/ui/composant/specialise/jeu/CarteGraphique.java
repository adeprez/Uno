package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import fr.utt.lo02.uno.io.ImageCartes;
import fr.utt.lo02.uno.io.Sons;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.ui.listener.CliqueCarteListener;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class CarteGraphique extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private final Carte carte;
	private boolean survol, actif, visible;


	public CarteGraphique(Carte carte, boolean visible) {
		this.carte = carte;
		this.visible = visible;
		actif = true;
		setOpaque(false);
		addMouseListener(this);
	}
	
	public void setAffiche(boolean visible) {
		this.visible = visible;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
		repaint();
	}

	public void setSurvol(boolean survol) {
		this.survol = survol;
		repaint();
	}

	public void dessine(Graphics2D g, Color couleur) {
		Composite tmp = g.getComposite();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .4f));
		g.setColor(couleur);
		g.fillRoundRect(0, 0, getWidth() - getWidth()/40, getHeight() - getHeight()/40, getWidth()/7, getHeight()/7);
		g.setComposite(tmp);
	}

	public void addCliqueCarteListener(CliqueCarteListener l) {
		listenerList.add(CliqueCarteListener.class, l);
	}

	public void removeCliqueCarteListener(CliqueCarteListener l) {
		listenerList.remove(CliqueCarteListener.class, l);
	}

	public Carte getCarte() {
		return carte;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageCartes.getInstance().dessiner(g, getWidth(), getHeight(), carte, visible);
		if(!actif)
			dessine((Graphics2D) g, Color.BLACK);
		else if(survol)
			dessine((Graphics2D) g, Color.WHITE);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(actif) {
			Sons.getInstance().jouer("tchic.wav");
			for(final CliqueCarteListener l : listenerList.getListeners(CliqueCarteListener.class))
				l.clique(this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setSurvol(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setSurvol(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}


}
