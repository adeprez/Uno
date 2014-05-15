package fr.utt.lo02.uno.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.ListeJoueurs;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;
import fr.utt.lo02.uno.jeu.listener.PartieListener;
import fr.utt.lo02.uno.jeu.variantes.TypeJeu;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.temps.Evenement;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Periodique;


/**
 * Objet representant un jeu de UNO
 */
public class Jeu extends Listenable implements PartieListener, Evenementiel {
	private final List<ResultatPartie> historiqueParties;
	private final TypeJeu type;
	private final ListeJoueurs joueurs;
	private Partie partie;

	
	/**
	 * Cree un nouveau jeu de UNO
	 * @param type le type de jeu
	 * @param joueurs les joueurs participant a ce jeu
	 */
	public Jeu(TypeJeu type, ListeJoueurs joueurs) {
		this.joueurs = joueurs;
		this.type = type;
		historiqueParties = new ArrayList<ResultatPartie>();
	}
	
	/**
	 * Cree un nouveau jeu de UNO, sans joueurs
	 * @param type le type de jeu
	 * @param nombre le nombre de joueurs pouvant participer
	 */
	protected Jeu(TypeJeu type, int nombre) {
		this(type, new ListeJoueurs(nombre));
	}

	/**
	 * Cree un nouveau jeu de UNO classique, sans joueurs
	 * @param nombre le nombre de joueurs pouvant participer
	 * @see TypeJeu#CLASSIQUE
	 */
	public Jeu(int nombre) {
		this(TypeJeu.CLASSIQUE, nombre);
	}
	
	/**
	 * @return la liste des joueurs participant au UNO
	 */
	public ListeJoueurs getListeJoueurs() {
		return joueurs;
	}
	
	/**
	 * Cree une nouvelle partie
	 * @return la partie cree
	 */
	public Partie creerPartie() {
		return new Partie(joueurs);
	}
	
	/**
	 * Cree et lance une nouvelle partie
	 */
	public void nouvellePartie() {
		setPartie(creerPartie());
		partie.lancer();
	}

	/**
	 * Assigne une nouvelle partie
	 * @param nouvellePartie la partie a jouer
	 */
	public void setPartie(Partie nouvellePartie) {
		if(partie != null) {
			partie.removePartieListener(this);
			partie.getHorloge().terminer();
			for(final Joueur j : joueurs.getListeJoueurs())
				j.getMain().vider();
		}
		partie = nouvellePartie;
		partie.addPartieListener(this);
		for(final JeuListener l : getListeners(JeuListener.class))
			l.nouvellePartie(nouvellePartie);
	}
	
	/**
	 * @return la partie se jouant
	 */
	public Partie getPartie() {
		return partie;
	}
	
	/**
	 * @return les resultats des parties precedentes
	 */
	public List<ResultatPartie> getResultats() {
		return historiqueParties;
	}
	
	/**
	 * @return le type de ce jeu
	 */
	public TypeJeu getType() {
		return type;
	}
	
	/**
	 * @return vrai si le jeu est fini (lorsqu'un joueur depasse le score limite)
	 */
	public boolean estJeuFini() {
		for(final Integer i : ResultatPartie.getPoints(historiqueParties).values())
			if(i >= Configuration.SCORE_DEFAITE)
				return true;
		return false;
	}
	
	/**
	 * Ajoute un ecouteur pour les evenements notifiables de ce jeu
	 * @param l le listener a ajouter
	 */
	public void addJeuListener(JeuListener l) {
		addListener(JeuListener.class, l);
	}

	/**
	 * Retire un ecouteur pour les evenements notifiables de ce jeu
	 * @param l le listener a retirer
	 */
	public void removeJeuListener(JeuListener l) {
		removeListener(JeuListener.class, l);
	}
	
	/**
	 * Determine le gagnant du jeu
	 * @return le gagnant du jeu, selon son implementation
	 */
	public String getGagnant() {
		Entry<Joueur, Integer> e = ResultatPartie.getGagnant(historiqueParties);
		return e.getKey() + " " + Texte.get("gagne le jeu avec") +" " + e.getValue() + " " + Texte.get("points");
	}
	
	@Override
	public void evenement(Periodique p) {
		nouvellePartie();
	}

	@Override
	public void finPartie(Partie partie) {
		ResultatPartie r = new ResultatPartie(partie.getJoueurs().getListeJoueurs());
		historiqueParties.add(r);
		for(final JeuListener l : getListeners(JeuListener.class))
			l.finPartie(partie, r);
		if(estJeuFini())
			for(final JeuListener l : getListeners(JeuListener.class))
				l.finJeu(historiqueParties, this);
		else partie.ajoutEvenement(new Evenement(Configuration.TEMPS_AFFICHAGE_RESULTATS, this));
	}

	@Override
	public void debutPartie(Partie partie) {}

	@Override
	public void debutTour(int id, TourJoueur tour) {}

	@Override
	public void passeTour(int id) {}


	
}
