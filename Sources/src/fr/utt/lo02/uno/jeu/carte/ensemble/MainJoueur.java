package fr.utt.lo02.uno.jeu.carte.ensemble;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.listener.Listenable;
import fr.utt.lo02.uno.jeu.listener.MainListener;


/**
 * Objet contenant l'ensemble des cartes d'un joueur
 */
public class MainJoueur extends Listenable implements Comparator<Carte> {
	private final List<Carte> cartes;


	/**
	 * Cree une nouvelle main vide
	 */
	public MainJoueur() {
		this(new ArrayList<Carte>());
	}

	/**
	 * Cree une nouvelle main contenant l'ensemble des cartes passees en parametre
	 * @param cartes
	 */
	public MainJoueur(List<Carte> cartes) {
		this.cartes = cartes;
	}

	/**
	 * @return la liste des cartes de cette main
	 */
	public List<Carte> getCartes() {
		return cartes;
	}

	/**
	 * Ajoute une carte a cette main, et la trie par ordre de poids
	 * @param carte la carte a ajouter
	 */
	public void ajoutCarte(Carte carte) {
		if(cartes.add(carte)) {
			Collections.sort(cartes, this);
			for(final MainListener l : getListeners(MainListener.class)) try {
				l.ajoutCarte(carte, cartes.indexOf(carte));
			} catch(Exception err) {
				err.printStackTrace();
			}
		}
	}

	/**
	 * Retire la carte de cette main
	 * @param carte la carte devant etre retiree
	 * @return la carte retiree
	 * @throws IllegalArgumentException si cette main ne contient pas la carte specifiee
	 */
	public Carte poseCarte(Carte carte) {
		int index = cartes.indexOf(carte);
		if(cartes.remove(carte)) {
			for(final MainListener l : getListeners(MainListener.class))
				l.retireCarte(carte, index);
			return carte;
		}
		throw new IllegalArgumentException(this + " ne possede pas la carte " + carte);
	}

	/**
	 * Retire toutes les cartes de cette main
	 */
	public void vider() {
		while(!cartes.isEmpty())
			poseCarte(cartes.get(0));
	}

	/**
	 * @return vrai si cette main est vide
	 */
	public boolean estVide() {
		return cartes.isEmpty();
	}

	/**
	 * Ajoute un ecouteur des modifications de cette main
	 * @param l le nouvel ecouteur
	 */
	public void addMainListener(MainListener l) {
		addListener(MainListener.class, l);
	}

	/**
	 * Retire un ecouteur des modifications de cette main
	 * @param l l'ancien ecouteur
	 */
	public void removeMainListener(MainListener l) {
		removeListener(MainListener.class, l);
	}

	@Override
	public String toString() {
		if(cartes.isEmpty())
			return "(vide)";
		String s = "";
		for(final Carte c : cartes)
			s += ", " + c;
		return s.substring(2);
	}

	@Override
	public int compare(Carte o1, Carte o2) {
		return new Integer(o1.getValeurTri()).compareTo(o2.getValeurTri());
	}

}
