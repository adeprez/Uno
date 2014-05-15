package fr.utt.lo02.uno.jeu.variantes;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.exception.InvalideException;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.ListeJoueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Classe heritant de {@link Jeu} et redefinissant certaines de ses methodes 
 * pour correspondre a l'implementation du mode de jeu {@link TypeJeu#CHALLENGE}
 */
public class JeuChallenge extends Jeu {


	/**
	 * Cree un nouveau jeu en equipes
	 * @param nombre le nombre de joueurs pour ce jeu
	 */
	public JeuChallenge(int nombre) {
		super(TypeJeu.CHALLENGE, nombre);
	}

	/**
	 * @return les survivants de ce jeu, ayant moins de {@link Configuration#SCORE_DEFAITE} points
	 * @throws InvalideException s'il n'y a plus assez de joueurs pour continuer
	 */
	public ListeJoueurs getSurvivants() throws InvalideException {
		if(getResultats().isEmpty())
			return getListeJoueurs();
		Map<Joueur, Integer> r = ResultatPartie.getPoints(getResultats());
		List<Joueur> survivants = new ArrayList<Joueur>();
		for(final Entry<Joueur, Integer> e : r.entrySet())
			if(e.getValue() < Configuration.SCORE_DEFAITE)
				survivants.add(e.getKey());
		if(survivants.size() < 2)
			throw new InvalideException("Il ne reste plus qu'un survivant");
		return new ListeJoueurs(survivants.toArray(new Joueur[survivants.size()]));
	}

	@Override
	public Partie creerPartie() {
		try {
			return new Partie(getSurvivants());
		} catch(InvalideException e) {
			e.printStackTrace();
			return new Partie(getListeJoueurs());
		}
	}

	@Override
	public boolean estJeuFini() {
		try {
			getSurvivants();
			return false;
		} catch(InvalideException e) {
			return true;
		}
	}




}
