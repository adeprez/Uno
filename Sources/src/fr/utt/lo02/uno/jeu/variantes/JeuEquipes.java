package fr.utt.lo02.uno.jeu.variantes;

import java.util.Map.Entry;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;


/**
 * Classe heritant de {@link Jeu} et redefinissant certaines de ses methodes pour correspondre a l'implementation d'un jeu en equipes
 * @see TypeJeu#EQUIPES
 */
public class JeuEquipes extends Jeu {
	/**
	 * Nombre d'equipes lors d'un jeu en equipe
	 */
	public static final int NOMBRE_EQUIPES = 2;

	
	/**
	 * Cree un nouveau jeu en equipes
	 * @param nombre le nombre de joueurs pour ce jeu
	 */
	public JeuEquipes(int nombre) {
		super(TypeJeu.EQUIPES, nombre);
	}
	
	/**
	 * Determine a quelle equipe appartient ce joueur, allant de 0 a {@link #NOMBRE_EQUIPES}
	 * @param joueur le joueur dont on souhaite obtenir l'equipe
	 * @return l'equipe de ce joueur
	 */
	public int getEquipe(Joueur joueur) {
		return getListeJoueurs().getID(joueur) % NOMBRE_EQUIPES;
	}
	
	/**
	 * Calcule le nombre de points pour l'equipe donnee
	 * @param equipe le numero de l'equipe pour laquelle calculer le nombre de points
	 * @return le nombre de points pour les joueurs de cette equipe
	 */
	public int getPoints(int equipe) {
		int pts = 0;
		for(final Entry<Joueur, Integer> e : ResultatPartie.getPoints(getResultats()).entrySet())
			if(getEquipe(e.getKey()) == equipe)
				pts += e.getValue();
		return pts;
	}
	
	/**
	 * Calcule le nombre de points pour chaque equipe
	 * @return un tableau contenant le nombre de points pour chaque equipe
	 */
	public int[] getPoints() {
		int[] points = new int[NOMBRE_EQUIPES];
		for(int i=0 ; i<points.length ; i++)
			points[i] = getPoints(i);
		return points;
	}

	@Override
	public String getGagnant() {
		int[] points = getPoints();
		int gagnant = -1;
		int pts = Configuration.SCORE_DEFAITE;
		for(int equipe=0 ; equipe<points.length ; equipe++)
			if(gagnant == -1 || points[equipe] < pts) {
				gagnant = equipe;
				pts = points[equipe];
			}
		return Texte.get("L'equipe") + " " + (gagnant + 1) + " " + Texte.get("gagne le jeu avec") + " " + pts + " " + Texte.get("points") + " !";
	}

	@Override
	public boolean estJeuFini() {
		for(final int i : getPoints())
			if(i >= Configuration.SCORE_DEFAITE)
				return true;
		return false;
	}

	
	
	
}
