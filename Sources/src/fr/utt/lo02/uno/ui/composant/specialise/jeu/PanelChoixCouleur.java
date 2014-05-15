package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.listener.ChoixCouleurListener;


public class PanelChoixCouleur extends PanelImage implements TourJoueurListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton[] boutons;
	
	
	public PanelChoixCouleur() {
		setLayout(new BorderLayout(25, 25));
		setImage(Images.getInstance().getImage("fond transparent.png"));
		setMax(true);
		
		JLabel titre = new JLabel(Texte.get("Choisir une couleur"), SwingConstants.CENTER);
		titre.setOpaque(true);
		titre.setBackground(Color.WHITE);
		titre.setFont(Configuration.POLICE);
		
		PanelImage centre = new PanelImage();
		boutons = new JButton[Couleur.values().length];
		for(int i = 0 ; i<boutons.length ; i++) {
			Couleur c = Couleur.values()[i];
			JButton b = new JButton(c.getNom());
			b.setFont(Configuration.POLICE);
			b.setPreferredSize(new Dimension(100, 50));
			b.addActionListener(this);
			b.setBackground(c.getColor());
			b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			centre.add(b);
			boutons[i] = b;
		}
		add(centre, BorderLayout.CENTER);
		add(titre, BorderLayout.NORTH);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.EAST);
		setVisible(false);
	}
	
	public void testeOuverture(TourJoueur tour) {
		setVisible(tour.getActions().peutChoisirCouleur());
	}
	
	public int getIDBouton(Object object) {
		for(int i = 0; i < boutons.length; i++)
			if(boutons[i] == object)
				return i;
		throw new IllegalArgumentException("Le composant ne correspond a aucun bouton");
	}
	
	public void addChoixCouleurListener(ChoixCouleurListener l) {
		listenerList.add(ChoixCouleurListener.class, l);
	}
	
	public void removeChoixCouleurListener(ChoixCouleurListener l) {
		listenerList.remove(ChoixCouleurListener.class, l);
	}

	@Override
	public void debutTour(TourJoueur tour) {
		testeOuverture(tour);
	}

	@Override
	public void finTour(TourJoueur tour) {
		setVisible(false);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		testeOuverture(tour);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Couleur couleur = Couleur.values()[getIDBouton(e.getSource())];
		for(final ChoixCouleurListener l : getListeners(ChoixCouleurListener.class))
			l.choixCouleur(couleur);
	}
	
}
