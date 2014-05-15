package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;
import java.util.List;

import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;


/**
 * Interface definissant les ecouteurs d'un jeu de UNO
 * @see Jeu
 * @see Partie
 * @see ResultatPartie
 */
public interface JeuListener extends EventListener {
	
	/**
	 * Signifie qu'une nouvelle partie a ete cree
	 * @param partie la partie cree
	 */
	public void nouvellePartie(Partie partie);
	
	/**
	 * Signifie qu'une partie se termine
	 * @param partie la partie se terminant
	 * @param resultats le resultat associe a cette partie
	 */
	public void finPartie(Partie partie, ResultatPartie resultats);
	
	/**
	 * Signifie qu'un jeu de UNO est termine
	 * @param resultats la liste des resultats pour chaque manche de jeu
	 * @param jeu le jeu se terminant
	 */
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu);
	
}
