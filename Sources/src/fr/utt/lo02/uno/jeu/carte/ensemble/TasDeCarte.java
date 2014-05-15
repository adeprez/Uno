package fr.utt.lo02.uno.jeu.carte.ensemble;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.interfaces.Sauvegardable;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.listener.Listenable;
import fr.utt.lo02.uno.jeu.listener.TasCarteListener;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;


/**
 * Classe representant un tas de {@link Carte}
 */
public class TasDeCarte extends Listenable implements Sauvegardable {
	private final Stack<Carte> cartes;
	

	/**
	 * Cree un nouveau tas de cartes vides;
	 */
	public TasDeCarte() {
		this(new Stack<Carte>());
	}
	
	/**
	 * Cree un nouveau tas de cartes a partir de la pile passee en parametre
	 * @param cartes
	 */
	public TasDeCarte(Stack<Carte> cartes) {
		this.cartes = cartes;
	}
	
	/**
	 * Cree un tas de cartes a partir d'un flux externe
	 * @param io le flux externe a convertir
	 */
	public TasDeCarte(IO io) {
		this();
		int nombre = io.nextPositif();
		for(int i=0 ; i<nombre ; i++)
			cartes.add(Carte.getCarte(io));
	}
	
	/**
	 * @return la carte sur le dessus de ce tas, sans la retirer de celui-ci
	 */
	public Carte getCarte() {
		if(cartes.isEmpty())
			return null;
		return cartes.peek();
	}
	
	/**
	 * Deplace toutes les carte contenues dans ce tas vers le tas cible
	 * @param cible le tas dans lequel placer les cartes
	 */
	public void transvaser(TasDeCarte cible) {
		for(final Carte c : cartes)
			cible.cartes.add(c);
		cartes.clear();
		cible.notifyTasCarteListener();
		notifyTasCarteListener();
	}
	
	/**
	 * Change aleatoirement l'ordre des cartes dans le tas, selon la methode {@link Collections#shuffle(java.util.List)}
	 */
	public void melanger() {
		Collections.shuffle(cartes);
	}
	
	/**
	 * Retire la carte situee sur le dessus de ce tas
	 * @return la carte retiree de ce tas
	 * @throws EmptyStackException si le tas est vide
	 */
	public Carte piocher() {
		Carte c = cartes.pop();
		notifyTasCarteListener();
		return c;
	}
	
	/**
	 * Ajoute une carte sur le dessus de ce tas
	 * @param carte la carte a ajouter sur le dessus de la pile
	 * @return la carte ajoutee
	 */
	public Carte poser(Carte carte) {
		if(!cartes.isEmpty())
			cartes.peek().recouvrir();
		cartes.push(carte);
		notifyTasCarteListener();
		return carte;
	}
	
	/**
	 * @return vrai si le tas est vide, faux sinon
	 */
	public boolean estVide() {
		return cartes.isEmpty();
	}

	/**
	 * @return le nombre de cartes conntenues dans ce tas
	 */
	public int getNombre() {
		return cartes.size();
	}

	/**
	 * @return la carte posee en dessous de celle sur le haut du paquet
	 */
	public Carte getCartePrecedente() {
		return cartes.size() < 2 ? null : cartes.get(cartes.size() - 2);
	}
	
	/**
	 * Ajoute un nouvel ecouteur des modifications de ce tas de cartes
	 * @param l le nouvel ecouteur des modifications de ce tas de cartes
	 */
	public void addTasCarteListener(TasCarteListener l) {
		addListener(TasCarteListener.class, l);
	}

	/**
	 * Retire un ecouteur des modifications de ce tas de cartes
	 * @param l l'ancien ecouteur des modifications de ce tas de cartes
	 */
	public void removeTasCarteListener(TasCarteListener l) {
		removeListener(TasCarteListener.class, l);
	}
	
	/**
	 * Signifie aux ecouteurs des modifications de ce tas du carte que la carte presente sur le dessus a change
	 */
	public void notifyTasCarteListener() {
		for(final TasCarteListener l : getListeners(TasCarteListener.class))
			l.changeDessus(getCarte());
	}
	
	@Override
	public String toString() {
		if(estVide())
			return "(vide)";
		String s = "";
		for(final Carte c : cartes)
			s += ", " + c;
		return s.substring(2);
	}

	@Override
	public IO sauvegarder(IO io) {
		io.addBytePositif(getNombre());
		for(final Carte c : cartes)
			c.sauvegarder(io);
		return io;
	}
	
}
