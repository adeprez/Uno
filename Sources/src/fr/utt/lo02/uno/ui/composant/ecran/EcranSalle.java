package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.jeu.listener.ListeJoueurListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.Fenetre;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.EmplacementJoueur;
import fr.utt.lo02.uno.ui.layout.LayoutLignes;


public abstract class EcranSalle extends Ecran implements ListeJoueurListener, JeuListener {
	private static final long serialVersionUID = 1L;
	private final EmplacementJoueur[] emplacements;
	private final Salle salle;


	public EcranSalle(Salle salle, boolean editable) {
		this.salle = salle;
		salle.getJeu().addJeuListener(this);

		setLayout(new BorderLayout());
		setName(salle.getNom());
		setImage(Images.getInstance().getImage("fond.jpg"));
		setMax(true);

		JPanel centre = new JPanel(new LayoutLignes());
		centre.setOpaque(false);

		JLabel l = new JLabel(Texte.get("Joueurs de") + " " + salle.getNom(), SwingConstants.CENTER);
		l.setFont(Configuration.POLICE.deriveFont(20f));
		centre.add(l);

		emplacements = new EmplacementJoueur[salle.getJeu().getListeJoueurs().getMaxJoueurs()];
		for(int i=0 ; i<emplacements.length; i++) {
			emplacements[i] = new EmplacementJoueur(editable);
			centre.add(Box.createRigidArea(new Dimension(1, 10)));
			centre.add(emplacements[i]);
		}
		centre.add(Box.createRigidArea(new Dimension(1, 10)));
		JScrollPane jsp = new JScrollPane(centre);
		jsp.setBorder(null);
		jsp.getViewport().setOpaque(false);
		add(jsp, BorderLayout.CENTER);
		salle.getJeu().getListeJoueurs().addListeJoueursListener(this);
		for(int i=0 ; i<emplacements.length ; i++) 
			if(salle.getJeu().getListeJoueurs().joueurExiste(i))
				emplacements[i].setJoueur(salle.getJeu().getListeJoueurs().getJoueur(i));
	}
	
	public abstract Ecran getEcranJeu(Joueur joueur, Salle salle);

	public EmplacementJoueur[] getEmplacements() {
		return emplacements;
	}

	public Salle getSalle() {
		return salle;
	}
	
	public void ouvreEcran(Joueur j) {
		new Fenetre(getEcranJeu(j, salle));
	}

	@Override
	public boolean fermer() {
		salle.getJeu().getListeJoueurs().removeListeJoueursListener(this);
		return super.fermer();
	}

	@Override
	public void ajout(int id, Joueur joueur) {
		emplacements[id].setJoueur(joueur);
	}

	@Override
	public void retire(int id, Joueur joueur) {
		emplacements[id].setVide();
	}

	@Override
	public void nouvellePartie(Partie partie) {
		for(final Joueur j : partie.getJoueurs().getJoueurs())
			if(j != null && j.getType() == TypeJoueur.HUMAIN) try {
				ouvreEcran(j);
			} catch(Exception err) {
				Outil.attendre(500);
				ouvreEcran(j);
			}
		changeEcran(null);
		salle.getJeu().removeJeuListener(this);
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {}

}
