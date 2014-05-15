package fr.utt.lo02.uno.ui.composant.specialise.animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.action.ActionBluff;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.action.ActionPoseCarte;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.ActionTourListener;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;


public class PanelAnimations extends JPanel implements Runnable, TourJoueurListener, ActionTourListener {
	private static final long serialVersionUID = 1L;
	private final List<Animable> animables;
	private final Salle salle;
	private boolean run;


	public PanelAnimations(Salle salle) {
		this.salle = salle;
		setOpaque(false);
		animables = new ArrayList<Animable>();
	}

	public void lancer() {
		if(!run) {
			run = true;
			new Thread(this).start();
		}
	}

	public void fermer() {
		run = false;
	}

	public void nouvelleAnimation(Animable a) {
		if(!run)
			lancer();
		animables.add(a);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=0 ; i<animables.size() ; i++) try {
			animables.get(i).dessiner(g, getBounds());
		} catch(Exception err) {}
	}

	@Override
	public void run() {
		while(run) {
			if(!animables.isEmpty()) {
				for(int i=0 ; i<animables.size() ; i++)
					if(!animables.get(i).bouge()) {
						animables.remove(i);
						i--;
					}
				repaint();
			}
			Outil.attendre(40);
		}
	}

	@Override
	public void debutTour(TourJoueur tour) {
		tour.addActionTourListener(this);
	}

	@Override
	public void finTour(TourJoueur tour) {
		tour.removeActionTourListener(this);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {}

	@Override
	public void action(int idAction, ActionJoueur action) {
		switch(action.getType()) {
		case UNO:
			nouvelleAnimation(new Animation("logo uno.png"));
			break;
		case POSE:
			switch(((ActionPoseCarte) action).getCarte().getType()) {
			case INVERSION:
				nouvelleAnimation(new Animation("inversion.png"));
				break;
			case PASSE_TOUR:
				nouvelleAnimation(new Animation("passer.png"));
				break;
			case PLUS_DEUX:
				nouvelleAnimation(new Animation("+2.png"));
				break;
			case PLUS_QUATRE:
				nouvelleAnimation(new Animation("+4.png"));
				break;
			default:
				break;
			}
			break;
		case BLUFF:
			if(ActionBluff.aRaison(salle.getJeu().getPartie()))
				nouvelleAnimation(new Animation("bluff.png"));
			break;
		case CONTRE_UNO:
			nouvelleAnimation(new Animation("contre uno.png"));
			break;
		default:
			break;
		}
	}



}
