package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.Sons;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.ListeJoueurs;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TasCarteListener;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.temps.Horloge;
import fr.utt.lo02.uno.temps.HorlogeListener;
import fr.utt.lo02.uno.ui.composant.Chrono;
import fr.utt.lo02.uno.ui.composant.PanelImage;


public class BarreTourJoueurs extends PanelImage implements TourJoueurListener, Runnable, HorlogeListener, TasCarteListener {
	public static final int TAILLE = 30;
	private static final long serialVersionUID = 1L;
	private final List<PanelJoueur> joueurs;
	private final BufferedImage sens;
	private final ListeJoueurs liste;
	private final Chrono chrono;
	private Color couleur;
	private int decalage;
	private boolean run;


	public BarreTourJoueurs(List<PanelJoueur> joueurs, ListeJoueurs liste) {
		this.liste = liste;
		this.joueurs = joueurs;
		chrono = new Chrono();
		setImage(Images.getInstance().getImage("fond transparent.png"));
		sens = Images.getInstance().getImage("sens.png");
		setMax(true);
	}

	public void lancer() {
		run = true;
		new Thread(this).start();
	}

	public void fermer() {
		run = false;
	}

	public int getXHorloge() {
		Component c = getPanelJoueur();
		return c == null ? getWidth()/2 : c.getX() + c.getWidth()/2;
	}

	public PanelJoueur getPanelJoueur() {
		for(final PanelJoueur pj : joueurs)
			if(pj.getJoueur() == liste.getJoueur())
				return pj;
		throw null;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(couleur);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(int x = -TAILLE ; x < getWidth() + TAILLE ; x += TAILLE)
			g.drawImage(sens, x + (liste.sensTrigo() ? decalage : - decalage), 0, liste.sensTrigo() ? TAILLE : -TAILLE, TAILLE, null);
		super.paintComponent(g);
		try {
			chrono.dessiner(g, getXHorloge(), 0, TAILLE * 2, TAILLE);
		} catch(Exception e) {}
	}

	@Override
	public void debutTour(TourJoueur tour) {
		chrono.setTemps(Configuration.TEMPS_TOUR);
		repaint();
		Sons.getInstance().jouer("pchit.wav");
	}

	@Override
	public void finTour(TourJoueur tour) {
		chrono.setTemps(-1);
		repaint();
	}

	@Override
	public void peutRejouer(TourJoueur tour) {

	}

	@Override
	public void run() {
		while(run) {
			decalage ++;
			decalage = decalage % TAILLE;
			repaint();
			Outil.attendre(60);
		}
	}

	@Override
	public void action(Horloge horloge) {
		chrono.setTemps(chrono.getTemps() - 1);
	}

	@Override
	public void changeDessus(Carte carte) {
		couleur = carte == null || carte.getCouleur() == null ? Color.BLACK : carte.getCouleur().getColor();
	}

}
