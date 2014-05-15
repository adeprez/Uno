package fr.utt.lo02.uno.jeu.action;

import java.util.ArrayList;
import java.util.List;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.CarteChangeCouleur;
import fr.utt.lo02.uno.jeu.carte.Couleur;
import fr.utt.lo02.uno.jeu.effet.Effet;
import fr.utt.lo02.uno.jeu.effet.EffetPlusQuatre;
import fr.utt.lo02.uno.jeu.exception.ActionInvalideException;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;


/**
 * Automate permettant de gerer les actions possibles et de memorisees celles deja effectuees par un joueur lors d'un tour de jeu
 */
public class AutomateActions {
	private final List<ActionJoueur> actions;
	private final Partie partie;
	private final Joueur joueur;
	private List<ActionJoueur> actionsPossibles;
	private boolean passe;


	/**
	 * Cree un nouvel automate permettant de gerer les actions possibles et de memorisees celles deja effectuees par un joueur lors d'un tour de jeu
	 * @param joueur le joueur realisant son tour. Si celui-ci est different du joueur en cours dans la partie, il ne pourra pas jouer
	 * @param partie la partie sur laquelle effectuer les actions
	 */
	public AutomateActions(Joueur joueur, Partie partie) {
		this.partie = partie;
		this.joueur = joueur;
		actions = new ArrayList<ActionJoueur>();
		calculeActionsPossibles();
	}
	
	/**
	 * Passe ce tour
	 */
	public void passer() {
		passe = true;
	}

	/**
	 * @return la partie sur laquelle cet automate applique les {@link ActionJoueur}
	 */
	public Partie getPartie() {
		return partie;
	}

	/**
	 * @return le joueur effectuant les {@link ActionJoueur}
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * @return vrai s'il s'agit encore du tour de ce joueur, par comparaison avec celui de la partie
	 */
	public boolean estTour() {
		return partie.getJoueurs().getJoueur() == joueur;
	}

	/**
	 * @return vrai si le joueur ne peut pas effectuer d'{@link ActionJoueur} supplementaire
	 */
	public boolean estFini() {
		return getActionsPossibles().isEmpty();
	}

	/**
	 * @return la derniere action effectuee par cet automate, ou null s'il n'y en a pas encore eu
	 */
	public ActionJoueur getDerniereAction() {
		return actions.isEmpty() ? null : actions.get(actions.size() - 1);
	}

	/**
	 * @return la liste les actions realisables pour le {@link Joueur}
	 */
	public List<ActionJoueur> getActionsPossibles() {
		return actionsPossibles == null ? calculeActionsPossibles() : actionsPossibles;
	}

	/**
	 * Ajoute une action a la liste des actions realisee au cours d'un {@link TourJoueur}
	 * @param action l'action realisee
	 * @return la liste des nouvelles action realisables
	 * @see #getActionsPossibles()
	 */
	public List<ActionJoueur> ajouteAction(ActionJoueur action) {
		actions.add(action);
		return calculeActionsPossibles();
	}

	/**
	 * Calcule les actions realisables par le joueur courant
	 * @return la liste des actions realisables par le joueur courant
	 */
	public List<ActionJoueur> calculeActionsPossibles() {
		actionsPossibles = new ArrayList<ActionJoueur>();
		if(peutBluffer()) {
			EffetPlusQuatre e = getEffetPlusQuatre();
			actionsPossibles.add(new ActionBluff(e));
			actionsPossibles.add(new ActionNonBluff(e));
		} else {
			if(peutChoisirCouleur())
				for(final Couleur c : Couleur.values())
					actionsPossibles.add(new ActionChoixCouleur(c));
			if(peutPiocher())
				actionsPossibles.add(new ActionPioche());
			for(final Carte carte : getCartesPosables())
				actionsPossibles.add(new ActionPoseCarte(carte));
			if(peutUno())
				actionsPossibles.add(new ActionUno());
			if(peutContreUno())
				actionsPossibles.add(new ActionContreUno());
			if(peutTerminerTour())
				actionsPossibles.add(new ActionFinTour());
		}
		return actionsPossibles;
	}


	/**
	 * @return la liste des cartes jouables par le joueur courant
	 */
	public List<Carte> getCartesPosables() {
		List<Carte> cartes = new ArrayList<Carte>();
		for(final Carte c : joueur.getMain().getCartes())
			if(peutPoser(c))
				cartes.add(c);
		return cartes;
	}

	/**
	 * Teste si une action est realisable par le joueur courant
	 * @param action l'action a tester
	 * @throws ActionInvalideException si l'action precisee ne peut etre effectuee par le joueur courant
	 */
	public void testeAction(ActionJoueur action) throws ActionInvalideException {
		String message = getMessageErreur(action);
		if(message != null)
			throw new ActionInvalideException(message);
	}

	/**
	 * Teste si une action est realisable par le joueur
	 * @param action l'action a realiser
	 * @return le message d'erreur correspondant, ou null si l'action est valide
	 */
	public String getMessageErreur(ActionJoueur action) {
		switch(action.getType()) {
		case POSE:
			if(!peutPoser(((ActionPoseCarte) action).getCarte()))
				return "Vous ne pouvez pas poser cette carte";
			break;
		case PIOCHE:
			if(!peutPiocher())
				return "Vous ne pouvez pas piocher de carte";
			break;
		case CHOIX_COULEUR:
			if(!peutChoisirCouleur())
				return "Vous ne pouvez pas changer la couleur de cette carte";
			break;
		case FIN_TOUR:
			if(!peutTerminerTour())
				return "Vous ne pouvez pas terminer votre tour";
			break;
		case UNO:
			if(!peutUno())
				return "Il ne doit vous rester qu'une carte pour declarer Uno !";
			break;
		case CONTRE_UNO:
			if(!peutContreUno())
				return "Vous ne pouvez pas declarer contre uno";
			break;
		case BLUFF:
		case NON_BLUFF:
			if(!peutBluffer())
				return "Vous ne pouvez pas bluffer";
			break;
		}
		return null;
	}

	/**
	 * @return vrai si le joueur peut terminer son tour
	 */
	public boolean peutTerminerTour() {
		ActionJoueur a = getDerniereAction();
		return !aFini() && (peutUno() || (a != null && a.getType() == Action.PIOCHE && getCartesPosables().size() > 0));
	}

	/**
	 * @return vrai si le joueur peut changer la couleur de la carte sur le dessus du talon
	 */
	public boolean peutChoisirCouleur() {
		Carte c = partie.getPlateau().getTalon().getCarte();
		return c instanceof CarteChangeCouleur && c.getCouleur() == null;
	}

	/**
	 * @return vrai si le joueur courant est en mesure de piocher une carte
	 */
	public boolean peutPiocher() {
		if(passe || peutBluffer())
			return false;
		for(final ActionJoueur a : actions)
			if(a.getType() == Action.POSE || a.getType() == Action.PIOCHE)
				return false;
		return !aFini();
	}

	/**
	 * @return vrai si le joueur a deja demande a terminer son tour
	 */
	public boolean aFini() {
		return aDeja(Action.FIN_TOUR);
	}

	/**
	 * @return vrai si le joueur peut annoncer "UNO !"
	 */
	public boolean peutUno() {
		return !peutChoisirCouleur() && !joueur.aDitUno() && joueur.getMain().getCartes().size() == 1 && !aFini() && aDeja(Action.POSE);
	}

	/**
	 * @return vrai si le joueur peut declarer "Contre uno !"
	 */
	public boolean peutContreUno() {
		Joueur j = partie.getJoueurs().getJoueurPrecedent();
		return !peutBluffer() && actions.isEmpty() && j.estUno() && !j.aDitUno();
	}

	/**
	 * @return vrai si le joueur est en mesure de faire un contre-bluff
	 */
	public boolean peutBluffer() {
		if(!actions.isEmpty())
			return false;
		return getEffetPlusQuatre() != null;
	}

	/**
	 * @param carte la carte que le joueur courant souhaite poser
	 * @return vrai si le joueur courant est en mesure de poser la carte precisee
	 */
	public boolean peutPoser(Carte carte) {
		return !passe && !peutBluffer() && !aFini() && !aDeja(Action.POSE) && partie.getPlateau().getTalon().getCarte().compatible(carte);
	}

	/**
	 * Teste si une action est realisable par le joueur courant
	 * @param action l'action a tester
	 * @return vrai si cette action peut etre effectuee par le joueur courant
	 */
	public boolean peutFaire(ActionJoueur action) {
		try {
			testeAction(action);
			return true;
		} catch(ActionInvalideException e) {
			return false;
		}
	}

	/**
	 * @param action le type de l'action a tester
	 * @return vrai si ce type d'action a deja ete realise durant ce tour
	 */
	public boolean aDeja(Action action) {
		for(final ActionJoueur a : actions)
			if(a.getType() == action)
				return true;
		return false;
	}
	
	/**
	 * Recherche un effet plus quatre detennu par le joueur
	 * @return cet effet, ou null s'il n'en a pas
	 */
	public EffetPlusQuatre getEffetPlusQuatre() {
		for(final Effet e : joueur.getEffets())
			if(e instanceof EffetPlusQuatre)
				return (EffetPlusQuatre) e;
		return null;
	}

}
