package fr.utt.lo02.uno.ui.composant.specialise.score;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.CarteGraphique;


public class PanelResultatJoueur extends PanelImage {
	private static final long serialVersionUID = 1L;
	private final boolean principal;

	
	public PanelResultatJoueur(boolean principal, Joueur joueur, List<Carte> cartes) {
		this.principal = principal;
		setLayout(new BorderLayout());
		setImage(Images.getInstance().getImage("fond transparent.png"));
		setMax(true);
		
		JLabel l = new JLabel(joueur.getNom(), new ImageIcon(Images.getInstance().getImage(joueur.getType().getLienImage())), SwingConstants.CENTER);
		l.setFont(Configuration.POLICE);
		
		JPanel centre = new JPanel();
		centre.setOpaque(false);
		
		int pts = 0;
		for(final Carte c : cartes) {
			CarteGraphique cg = new CarteGraphique(c, true);
			cg.setToolTipText(c.toString());
			cg.setActif(true);
			cg.setPreferredSize(new Dimension(40, 60));
			centre.add(cg);
			pts += c.getValeurPoints();
		}
		
		JLabel points = new JLabel("(" + pts + ")    ", SwingConstants.CENTER);
		points.setForeground(Color.YELLOW);
		points.setFont(Configuration.POLICE);
		
		JLabel gagnant = new JLabel(Texte.get("gagnant"), SwingConstants.CENTER);
		gagnant.setForeground(Color.BLACK);
		gagnant.setFont(Configuration.POLICE);
		
		add(l, BorderLayout.WEST);
		add(cartes.isEmpty() ? gagnant : centre, BorderLayout.CENTER);
		add(points, BorderLayout.EAST);
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(principal)
			g.drawImage(Images.getInstance().getImage("survol.png"), 0, 0, getWidth(), getHeight(), null);
	}
	
	
	
}
