package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.ui.listener.CliqueCarteListener;

public class MainJoueurGraphiqueVisible extends MainJoueurGraphique implements TourJoueurListener {
	private static final long serialVersionUID = 1L;
	private final CliqueCarteListener listener;
	private final MainJoueur main;

	
	public MainJoueurGraphiqueVisible(CliqueCarteListener listener, MainJoueur main) {
		super(main, true);
		this.main = main;
		this.listener = listener;
		init(main.getCartes());
	}
	
	public MainJoueur getMain() {
		return main;
	}
	
	public void desactive() {
		for(final CarteGraphique c : cartes.values())
			c.setActif(false);
	}

	@Override
	public void init(MainJoueur main) {}

	@Override
	public void debutTour(TourJoueur tour) {
		activePossibilites(tour);
	}

	@Override
	public void finTour(TourJoueur tour) {
		desactive();
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		desactive();
		activePossibilites(tour);
	}

	@Override
	public CarteGraphique ajout(Carte carte, CarteGraphique c) {
		c.addCliqueCarteListener(listener);
		c.setActif(false);
		c.setToolTipText(carte.toString());
		return super.ajout(carte, c);
	}
	
	@Override
	public CarteGraphique retire(Carte carte) {
		CarteGraphique c = super.retire(carte);
		c.removeCliqueCarteListener(listener);
		return c;
	}

}
