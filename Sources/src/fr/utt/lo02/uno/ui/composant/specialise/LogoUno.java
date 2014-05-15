package fr.utt.lo02.uno.ui.composant.specialise;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.ui.composant.Taille;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;



public class LogoUno extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private static final float VITESSE = .004f;
	private final BufferedImage logo, fond;
	private float decalage;
	private boolean run;

	
	public LogoUno() {
		setOpaque(false);
		logo = Images.getInstance().getImage("logo uno.png");
		fond = Images.getInstance().getImage("fond logo uno.png");
		setPreferredSize(new Taille(logo.getWidth(), logo.getHeight()).setLargeur(600).getDimension());
		decalage = 1;
	}
	
	public void lancer() {
		run = true;
		new Thread(this).start();
	}
	
	public void terminer() {
		run = false;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(fond, 0, 0, getWidth(), getHeight(), null);
		int w = (int) (getWidth() * decalage);
		int h = (int) (getHeight() * decalage);
		g.drawImage(logo, (getWidth() - w)/2, (getHeight() - h)/2, w, h, null);
	}

	@Override
	public void run() {
		boolean up = false;
		while(run) {
			decalage += up ? VITESSE : - VITESSE;
			if(decalage >= 1 || decalage <= .9)
				up = !up;
			Outil.attendre(21);
			repaint();
		}
	}
	
}
