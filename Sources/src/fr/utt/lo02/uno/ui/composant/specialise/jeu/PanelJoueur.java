package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.ui.composant.PanelImage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class PanelJoueur extends PanelImage {
	private static final long serialVersionUID = 1L;
	private final MainJoueurGraphique main;
	private final Joueur joueur;
	
	
	public PanelJoueur(Joueur joueur) {
		this.joueur = joueur;
		setLayout(new BorderLayout(5, 5));
		setMax(true);
		setImage(Images.getInstance().getImage("survol.png"));
		
		PanelEffetsJoueur pej = new PanelEffetsJoueur();
		joueur.addEffetListener(pej);
		
		PanelImage p = new PanelImage(Images.getInstance().getImage("joueur.png"));
		p.setMax(true);
		p.add(new JLabel(new ImageIcon(Images.getInstance().getImage(joueur.getType().getLienImage()))));
		JLabel nom = new JLabel(joueur.getNom());
		nom.setFont(Configuration.POLICE);
		p.add(nom);
		
		add(p, BorderLayout.NORTH);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.EAST);
		add(main = new MainJoueurGraphique(joueur.getMain()), BorderLayout.CENTER);
		add(pej, BorderLayout.SOUTH);
	}
	
	public MainJoueurGraphique getMain() {
		return main;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	
}
