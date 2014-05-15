package fr.utt.lo02.uno.jeu;

import fr.utt.lo02.uno.base.Generateur;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.jeu.action.generateur.ControleurPartieHorsLigne;
import fr.utt.lo02.uno.jeu.action.generateur.Strategie;
import fr.utt.lo02.uno.jeu.action.generateur.ia.ComportementOrdinateur;
import fr.utt.lo02.uno.jeu.exception.JeuException;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.jeu.listener.TourJoueurListener;
import fr.utt.lo02.uno.jeu.variantes.TypeJeu;
import fr.utt.lo02.uno.temps.Horloge;
import fr.utt.lo02.uno.temps.Periodique;
import fr.utt.lo02.uno.ui.Fenetre;
import fr.utt.lo02.uno.ui.composant.ecran.EcranPartie;

/**
 * Objet representant une salle de jeu
 */
public class Salle {
	private String nom;
	private Jeu jeu;


	/**
	 * Cree une nouvelle salle de jeu classique sans nom avec deux joueurs admissibles
	 */
	public Salle() {
		this(TypeJeu.CLASSIQUE, 2, "Sans nom");
	}

	/**
	 * Cree une nouvelle salle de jeu
	 * @param type le type du jeu
	 * @param nombreJoueursAdmissibles le nombre de joueurs pour cette salle
	 * @param nom le nom de cette salle
	 */
	public Salle(TypeJeu type, int nombreJoueursAdmissibles, String nom) {
		setInformations(type, nombreJoueursAdmissibles, nom);
	}

	/**
	 * Assigne les informations de cette salle
	 * @param type le type du jeu
	 * @param nombreJoueursAdmissibles le nombre de joueurs admissibles
	 * @param nom le nom de cette salle
	 */
	public void setInformations(TypeJeu type, int nombreJoueursAdmissibles, String nom) {
		this.nom = nom;
		jeu = type.creerJeu(nombreJoueursAdmissibles);
	}

	/**
	 * @return un objet {@link InfoSalle} representant les informations de cette salle
	 */
	public InfoSalle getInfos() {
		return new InfoSalle(this);
	}

	/**
	 * @return le nom de cette salle
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return le jeu associe a cette salle
	 */
	public Jeu getJeu() {
		return jeu;
	}

	/**
	 * Cree une nouvelle salle de jeu et lance une partie hors ligne
	 * @param principale la strategie pour les actions affichees a l'ecran
	 * @param autres les strategies pour les actions des joueurs
	 */
	public static void partieRapide(Strategie principale, Strategie... autres) {
		TourJoueurListener[] t = new TourJoueurListener[autres.length];
		Periodique p = new Horloge();
		for(int i=0 ; i<t.length ; i++)
			t[i] = new ComportementOrdinateur(p, autres[i]);
		partieRapide(new ComportementOrdinateur(p, principale), t);
		p.lancer();
	}

	/**
	 * Cree une nouvelle salle de jeu et lance une partie hors ligne
	 * @param principal le gestionnaire dont les actions sont affiches a l'ecran
	 * @param autres les gestionnaires des actions des joueurs
	 */
	public static void partieRapide(TourJoueurListener principal, TourJoueurListener... autres) {
		Salle salle = new Salle(TypeJeu.CLASSIQUE, 1 + autres.length, "Salle rapide");
		Joueur pj = new Joueur(TypeJoueur.HUMAIN, Generateur.getInstance().getNomPrincipal());
		pj.addTourJoueurListener(principal);
		try {
			salle.getJeu().getListeJoueurs().ajoutJoueur(pj);
		} catch(JeuException e1) {
			e1.printStackTrace();
		}
		for(final TourJoueurListener l : autres) try {
			Joueur j = new Joueur(TypeJoueur.HUMAIN, Generateur.getInstance().getNom());
			j.addTourJoueurListener(l);
			salle.getJeu().getListeJoueurs().ajoutJoueur(j);
		} catch(JeuException e) {
			e.printStackTrace();
		}
		salle.getJeu().nouvellePartie();
		new Fenetre(new EcranPartie(new ControleurPartieHorsLigne(pj), pj, salle));
	}


}
