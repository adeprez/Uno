package fr.utt.lo02.uno.jeu.effet;

import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.ensemble.MainJoueur;
import fr.utt.lo02.uno.langue.Texte;

/**
 * Effet faisant piocher une ou plusieurs cartes
 */
public class EffetPioche implements Effet {
	private int nombre;


	/**
	 * Cree un nouvel effet de pioche
	 * @param nombre le nombre de cartes a piocher
	 */
	public EffetPioche(int nombre) {
		setNombre(nombre);
	}

	/**
	 * Change le nombre de cartes a piocher de cet effet
	 * @param nombre le nouveau nombre de cartes a piocher
	 */
	public void setNombre(int nombre) {
		if(nombre < 0)
			throw new IllegalArgumentException("Le nombre de cartes a piocher doit etre positif");
		this.nombre = nombre;
	}
	
	/**
	 * Fait piocher une ou plusieurs cartes au joueur courant
	 * @param partie la partie contenant le plateau dans lequel piocher les cartes
	 * @param main la main du joueur devant piocher
	 */
	public void faireEffet(Partie partie, MainJoueur main) {
		for(int i=0 ; i < nombre ; i++) {
			Carte carte = partie.getPlateau().piocher();
			main.ajoutCarte(carte);
			partie.notifyDeplacementCarte(partie.getPlateau().getPioche(), main, carte);
		}
	}

	@Override
	public boolean faireEffet(Partie partie, MainJoueur main, boolean finPartie) {
		faireEffet(partie, main);
		return true;
	}
	
	@Override
	public String toString() {
		return Texte.get("pioche") + " " + nombre + " " + Texte.get("cartes");
	}

	@Override
	public int getPriorite() {
		return 2;
	}

	@Override
	public String getCheminImage() {
		return "pioche.png";
	}

}
