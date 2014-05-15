package fr.utt.lo02.uno.jeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.joueur.Joueur;


/**
 * Classe representant les resultats d'une partie
 */
public class ResultatPartie {
	private final Map<Joueur, List<Carte>> resultat;


	/**
	 * Cree un nouveau resultat de partie
	 * @param joueurs la collection des joueurs ayant participe a la partie
	 */
	public ResultatPartie(Iterable<Joueur> joueurs) {
		resultat = new HashMap<Joueur, List<Carte>>();
		for(final Joueur j : joueurs) {
			List<Carte> cartes = new ArrayList<Carte>();
			for(final Carte c : j.getMain().getCartes())
				cartes.add(c.dupliquer());
			resultat.put(j, cartes);
		}
	}
	
	/**
	 * @return l'ensemble des joueurs ayant participe a la partie
	 */
	public Set<Joueur> getJoueurs() {
		return resultat.keySet();
	}
	
	/**
	 * @param joueur le joueur a analyser
	 * @return la liste des cartes que le joueur possedait a la fin de la partie
	 */
	public List<Carte> getCartes(Joueur joueur) {
		return resultat.get(joueur);
	}
	
	/**
	 * @param joueur le joueur dont on souhaite calculer les points
	 * @return les points de ce joueur
	 */
	public int getPoints(Joueur joueur) {
		int pts = 0;
		for(final Carte c : getCartes(joueur))
			pts += c.getValeurPoints();
		return pts;
	}
	
	/**
	 * Cree une map des joueurs avec leurs resultats individuels. Le gagnant aura 0 points
	 * @return les resultats
	 */
	public Map<Joueur, Integer> getPoints() {
		Map<Joueur, Integer> result = new HashMap<Joueur, Integer>();
		for(final Joueur joueur : getJoueurs())
			result.put(joueur, getPoints(joueur));
		return result;
	}
	
	/**
	 * Recherche le gagnant de cette partie
	 * @return le gagnant de cette partie, c'est a dire celui qui ne dispose de plus aucune carte
	 * @throws IllegalAccessError si aucun gagnant ne peut etre trouve
	 */
	public Joueur getGagnant() {
		for(final Entry<Joueur, List<Carte>> result : resultat.entrySet())
			if(result.getValue().isEmpty())
				return result.getKey();
		throw new IllegalAccessError("Impossible de determiner le gagnant");
	}
	
	/**
	 * @param resultats les resultats de chaque partie
	 * @return les resultats pour l'ensembles des parties
	 */
	public static Map<Joueur, Integer> getPoints(List<ResultatPartie> resultats) {
		Map<Joueur, Integer> result = new HashMap<Joueur, Integer>();
		for(final ResultatPartie rp : resultats) {
			Map<Joueur, Integer> r = rp.getPoints();
			for(final Entry<Joueur, Integer> e : r.entrySet()) {
				Joueur j = e.getKey();
				if(!result.containsKey(j))
					result.put(j, 0);
				result.put(j, e.getValue() + result.get(j));
			}
		}
		return result;
	}
	
	/**
	 * @param resultats les resultats des differentes parties
	 * @return le gagnant de ce jeu, ou null s'il ne peut etre trouve
	 */
	public static Entry<Joueur, Integer> getGagnant(List<ResultatPartie> resultats) {
		Entry<Joueur, Integer> gagnant = null;
		for(final Entry<Joueur, Integer> e : getPoints(resultats).entrySet())
			if(gagnant == null || e.getValue() < gagnant.getValue())
				gagnant = e;
		return gagnant;
	}
	
}
