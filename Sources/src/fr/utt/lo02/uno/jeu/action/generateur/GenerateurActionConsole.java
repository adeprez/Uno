package fr.utt.lo02.uno.jeu.action.generateur;

import java.util.List;

import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.exception.ActionInvalideException;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;


/**
 * Classe representant un generateur d'action pour un joueur, via une interface en console
 */
public class GenerateurActionConsole implements TourJoueurListener {
	private TourJoueur tour;


	/**
	 * Realise l'action selon la commande donnee
	 * @param commande
	 */
	public void action(String commande) {
		if(tour == null) {
			Console.getInstance().erreur("Ce n'est pas a vous de jouer");
		} else {
			if(commande.isEmpty())
				afficheOptions();
			else if(tour.getActions().getActionsPossibles().isEmpty())
				Console.getInstance().erreur("Vous ne pouvez faire aucune action");
			else try {
				tour.faireAction(Integer.valueOf(commande) - 1);
			} catch(NumberFormatException err) {
			} catch(IndexOutOfBoundsException err) {
				Console.getInstance().erreur("Action invalide, entrez un chiffre compris entre 1 et " + tour.getActions().getActionsPossibles().size());
			} catch(ActionInvalideException err) {
				Console.getInstance().erreur(err.getMessage());
			}
		}
	}

	/**
	 * Affiche la liste des {@link ActionJoueur} realisables
	 */
	public void afficheOptions() {
		Console.getInstance().affiche("-----------------");
		Console.getInstance().affiche("Talon : " + tour.getActions().getPartie().getPlateau().getTalon().getCarte());
		Console.getInstance().affiche("Contenu de votre main : " + tour.getActions().getPartie().getJoueurs().getJoueur().getMain());
		Console.getInstance().affiche("Entrez le numero de l'action a effectuer : ");
		List<ActionJoueur> actions = tour.getActions().getActionsPossibles();
		for(int i = 0 ; i < actions.size() ; i++)
			Console.getInstance().affiche(i + 1 + ") " + actions.get(i));
	}

	@Override
	public void debutTour(TourJoueur tour) {
		this.tour = tour;
		afficheOptions();
	}

	@Override
	public void finTour(TourJoueur tour) {
		this.tour = null;
	}

	@Override
	public void peutRejouer(TourJoueur tour) {
		afficheOptions();
	}

}
