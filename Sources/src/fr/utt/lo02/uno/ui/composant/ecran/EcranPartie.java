package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.Controleur;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.DeplacementCarteListener;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.IconeTache;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.specialise.animation.AnimateurCarte;
import fr.utt.lo02.uno.ui.composant.specialise.animation.AnimationCarte;
import fr.utt.lo02.uno.ui.composant.specialise.animation.PanelAnimations;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.BarreTourJoueurs;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.MainJoueurGraphiqueVisible;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.PanelActionJoueur;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.PanelChoixCouleur;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.PanelJoueur;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.PlateauGraphique;
import fr.utt.lo02.uno.ui.layout.LayoutCentre;


public class EcranPartie extends Ecran implements LayoutManager, JeuListener, DeplacementCarteListener, TourJoueurListener, ActionListener {
	private static final Dimension TAILLE_PLATEAU = new Dimension(280, 190);
	private static final long serialVersionUID = 1L;
	private final MainJoueurGraphiqueVisible mainJoueur;
	private final PanelAnimations animations;
	private final PanelActionJoueur actions;
	private final PanelChoixCouleur couleur;
	private final List<PanelJoueur> joueurs;
	private final PlateauGraphique plateau;
	private final BarreTourJoueurs barre;
	private final BufferedImage contours;
	private final Controleur controleur;
	private final AbstractButton info;
	private final Partie partie;
	private final Joueur joueur;
	private final Salle salle;
	private JInternalFrame fenetre;


	public EcranPartie(Controleur controleur, Joueur joueur, Salle salle) {
		this.joueur = joueur;
		this.salle = salle;
		this.controleur = controleur;
		
		partie = salle.getJeu().getPartie();

		contours = Images.getInstance().getImage("contours.png");
		setImage(Images.getInstance().getImage("tapis 2.jpg"));
		setMax(true);
		setName(joueur.getNom());

		joueurs = new ArrayList<PanelJoueur>();
		animations = new PanelAnimations(salle);
		plateau = new PlateauGraphique(partie.getPlateau());
		mainJoueur = new MainJoueurGraphiqueVisible(controleur, joueur.getMain());
		barre = new BarreTourJoueurs(joueurs, partie.getJoueurs());
		couleur = new PanelChoixCouleur();
		actions = new PanelActionJoueur();
		info = new Bouton(new ImageIcon(Images.getInstance().getImage("info.png")));
		
		for(final Joueur j : partie.getJoueurs().getJoueurs())
			if(j != null)
				ajoutJoueur(j);

		plateau.getPioche().addPanelTasCarteListener(controleur);
		couleur.addChoixCouleurListener(controleur);
		actions.addPanelActionJoueurListener(controleur);
		info.addActionListener(this);
		joueur.addTourJoueurListener(mainJoueur);
		joueur.addTourJoueurListener(plateau.getPioche());
		joueur.addTourJoueurListener(couleur);
		joueur.addTourJoueurListener(actions);
		joueur.addTourJoueurListener(this);
		partie.getHorloge().addHorlogeListener(barre);
		partie.getPlateau().getTalon().addTasCarteListener(barre);
		partie.addDeplacementCarteListener(this);
		salle.getJeu().addJeuListener(this);

		mainJoueur.setSize(0, 175);
		setLayout(this);
		
		add(actions);
		add(couleur);
		add(animations);
		add(mainJoueur);
		add(plateau);
		add(barre);
		add(info);
		barre.lancer();
	}
	
	public void ajoutJoueur(Joueur j) {
		j.addTourJoueurListener(barre);
		j.addTourJoueurListener(animations);
		PanelJoueur pj = new PanelJoueur(j);
		if(j == joueur) {
			pj.add(new JLabel(), BorderLayout.CENTER);
			pj.setImage(null);
		}
		joueurs.add(pj);
		add(pj);
	}
	
	public AnimateurCarte getAnimateur(Object o) {
		if(o == mainJoueur.getMain())
			return mainJoueur;
		if(o == plateau.getPioche().getTas())
			return plateau.getPioche();
		if(o == plateau.getTalon().getTas())
			return plateau.getTalon();
		for(final PanelJoueur pj : joueurs)
			if(o == pj.getJoueur().getMain())
				return pj.getMain();
		throw new IllegalArgumentException("Cet element originaire du modele n'est pas affiche par cette vue");
	}

	@Override
	public void deplacement(Object source, Object cible, Carte c) {
		if(isShowing())
			animations.nouvelleAnimation(new AnimationCarte(c, getAnimateur(source), getAnimateur(cible)));
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {
		changeEcran(salle.getJeu().estJeuFini() ? new EcranResultatJeu(joueur, salle.getJeu()) : new EcranScores(joueur, controleur, salle));
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {}
	
	@Override
	public boolean fermer() {
		barre.fermer();
		animations.fermer();
		salle.getJeu().removeJeuListener(this);
		partie.getHorloge().removeHorlogeListener(barre);
		for(final Joueur j : partie.getJoueurs().getListeJoueurs()) {
			j.removeTourJoueurListener(barre);
			j.removeTourJoueurListener(animations);
		}
		joueur.removeTourJoueurListener(this);
		joueur.removeTourJoueurListener(mainJoueur);
		joueur.removeTourJoueurListener(plateau.getPioche());
		joueur.removeTourJoueurListener(couleur);
		joueur.removeTourJoueurListener(actions);
		return true;
	}

	@Override
	public void layoutContainer(Container parent) {
		plateau.setBounds((getWidth() - TAILLE_PLATEAU.width)/2, (getHeight() - TAILLE_PLATEAU.height)/2, TAILLE_PLATEAU.width, TAILLE_PLATEAU.height);
		Dimension d = mainJoueur.getPreferredSize();
		if(d.width > getWidth()/1.5)
			d.width = (int) (getWidth()/1.5);
		mainJoueur.setBounds((getWidth() - d.width)/2, getHeight() - d.height, d.width, d.height);
		animations.setBounds(getBounds());
		int w = getWidth()/(joueurs.size() + 1), h = getHeight()/5;
		int dx = (getWidth() - (joueurs.size() * w))/(Math.max(1, joueurs.size() - 1));
		for(int i=0 ; i<joueurs.size() ; i++)
			joueurs.get(i).setBounds(i * (w + dx), BarreTourJoueurs.TAILLE, w, h);
		barre.setBounds(0, 0, getWidth(), BarreTourJoueurs.TAILLE);
		LayoutCentre.setTaille(this, couleur, couleur.getPreferredSize().width, couleur.getPreferredSize().height);
		actions.setBounds(5, (getHeight() - actions.getHauteur())/2, PanelActionJoueur.LARGEUR, actions.getHauteur());
		info.setBounds(5, getHeight() - 55, 50, 50);
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
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(contours, (getWidth() - contours.getWidth())/2, (getHeight() - contours.getHeight())/2, null);
	}

	@Override
	public void debutTour(TourJoueur tour) {
		if(!estAffiche())
			IconeTache.getInstance().message(joueur.getNom(), Texte.get("C'est votre tour de jouer"), MessageType.NONE);
		demandeFocus();
		Toolkit.getDefaultToolkit().beep();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(fenetre == null || !fenetre.isVisible())	try {
			JEditorPane p = new JEditorPane(getClass().getResource("/" + salle.getJeu().getType().getCheminDoc() + ".html"));
			fenetre = new JInternalFrame(salle.getJeu().getType().getNom(), true, true, true);
			fenetre.setContentPane(new JScrollPane(p));
			fenetre.setBounds(5, getHeight()/2 - 60, 250, getHeight()/2);
			fenetre.setVisible(true);
			add(fenetre, 0);
		} catch(IOException err) {
			err.printStackTrace();
		} else {
			fenetre.setVisible(false);
			remove(fenetre);
			fenetre = null;
		}
	}

	@Override
	public void finTour(TourJoueur tour) {
		if(!estAffiche())
			IconeTache.getInstance().message(joueur.getNom(), Texte.get("Votre tour est fini"), MessageType.NONE);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		if(!estAffiche())
			IconeTache.getInstance().message(joueur.getNom(), Texte.get("Vous pouvez rejouer"), MessageType.NONE);
	}

	@Override
	public void removeLayoutComponent(Component comp) {}

	@Override
	public void addLayoutComponent(String name, Component comp) {}

	@Override
	public void nouvellePartie(Partie partie) {}

}
