package fr.utt.lo02.uno.jeu.action.generateur.ia;

import fr.utt.lo02.uno.jeu.action.generateur.Strategie;
import fr.utt.lo02.uno.jeu.action.generateur.VueJeu;
import fr.utt.lo02.uno.jeu.exception.ActionInvalideException;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.temps.Evenement;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;

/**
 * Objet representant le comportement d'un joueur virtuel lors d'une partie de UNO, effectuant une action lorsque le tour d'un joueur debute
 */
public class ComportementOrdinateur implements TourJoueurListener, Evenementiel {
	private final Periodique periodique;
	private final Strategie strategie;
	private TourJoueur tour;


	/**
	 * Cree un nouveau comportement, selon la strategie qu'il adopte
	 * @param periodique un element periodique pour gerer la temporalite des tours
	 * @param strategie la strategie adoptee par ce comportement, lui permettant de repondre aux sollicitations
	 */
	public ComportementOrdinateur(Periodique periodique, Strategie strategie) {
		this.periodique = periodique;
		this.strategie = strategie;
	}

	/**
	 * Cree un nouveau comportement de strategie par defaut
	 * @param periodique un element periodique pour gerer la temporalite des tours
	 * @see #ComportementOrdinateur(Periodique, Strategie)
	 * @see StrategieAleatoire
	 * @see StrategiePoints
	 */
	public ComportementOrdinateur(Periodique periodique) {
		this(periodique, new StrategiePoints());
	}
	
	/**
	 * Demande a ce comportement de jouer. Il realisera une action dans la seconde suivante
	 * @param tour le tour du joueur auquel appartient ce comportement
	 */
	public void joue(TourJoueur tour) {
		this.tour = tour;
		periodique.addEvenementFutur(new Evenement(1, this));
	}

	@Override
	public void debutTour(TourJoueur tour) {
		joue(tour);
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		joue(tour);
	}

	@Override
	public void finTour(TourJoueur tour) {
		tour = null;
	}

	@Override
	public void evenement(Periodique p) {
		if(tour != null) try {
			tour.faireAction(strategie.getIDAction(tour.getActions().getActionsPossibles(), 
					new VueJeu(tour.getActions().getPartie().getJoueurs().getJoueur(), tour.getActions().getPartie())));
		} catch(ActionInvalideException e) {
			e.printStackTrace();
		}
	}

}
