package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import fr.utt.lo02.uno.io.ImageCartes;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.listener.PanelActionJoueurListener;


public class PanelActionJoueur extends PanelImage implements TourJoueurListener, ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int LARGEUR = 150, HAUTEUR = 50;
	private final AbstractButton uno, contreUno, passe, bluff, pioche;


	public PanelActionJoueur() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setImage(Images.getInstance().getImage("fond transparent.png"));
		setMax(true);
		setVisible(false);

		uno = new Bouton("UNO !", new ImageIcon(Images.getInstance().getImage("icone_32.png")));
		contreUno = new Bouton(Texte.get("Contre") + " UNO !", new ImageIcon(Images.getInstance().getImage("pioche.png")
				.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING)));
		passe = new Bouton(Texte.get("Fin tour"), new ImageIcon(Images.getInstance().getImage("passe tour.png")
				.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING)));
		bluff = new Bouton(Texte.get("Bluffer"), new ImageIcon(Images.getInstance().getImage("chat.png")
				.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING)));
		pioche = new Bouton(Texte.get("Piocher"), new ImageIcon(ImageCartes.getInstance().getImage(null, 0)
				.getScaledInstance(20, 30, Image.SCALE_AREA_AVERAGING)));

		AbstractButton[] boutons = new AbstractButton[] {uno, contreUno, passe, bluff, pioche};

		for(final AbstractButton b : boutons) {
			b.addActionListener(this);
			b.setPreferredSize(new Dimension(LARGEUR, HAUTEUR));
		}
	}

	public void addPanelActionJoueurListener(PanelActionJoueurListener l) {
		listenerList.add(PanelActionJoueurListener.class, l);
	}

	public void removePanelActionJoueurListener(PanelActionJoueurListener l) {
		listenerList.remove(PanelActionJoueurListener.class, l);
	}

	public void testePossibilites(TourJoueur tour) {
		if(tour.getActions().peutUno())
			add(uno);
		if(tour.getActions().peutContreUno())
			add(contreUno);
		if(tour.getActions().peutTerminerTour())
			add(passe);
		if(tour.getActions().peutBluffer()) {
			add(bluff);
			add(pioche);
		}
		setVisible(getComponentCount() > 0);
		validate();
		repaint();
	}

	public int getHauteur() {
		return (getComponentCount() * (HAUTEUR + 10));
	}

	@Override
	public void debutTour(TourJoueur tour) {
		testePossibilites(tour);
	}

	@Override
	public void finTour(TourJoueur tour) {
		removeAll();
		setVisible(false);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		finTour(tour);
		debutTour(tour);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PanelActionJoueurListener[] ll = listenerList.getListeners(PanelActionJoueurListener.class);
		if(e.getSource() == passe)
			for(final PanelActionJoueurListener l : ll)
				l.passeTour();
		else if(e.getSource() == uno)
			for(final PanelActionJoueurListener l : ll)
				l.uno();
		else if(e.getSource() == contreUno)
			for(final PanelActionJoueurListener l : ll)
				l.contreUno();
		else for(final PanelActionJoueurListener l : ll)
			l.contreBluff(e.getSource() == bluff);
	}

}
