package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.interfaces.Sauvegardable;
import fr.utt.lo02.uno.jeu.Partie;

/**
 * Classe abstraite representant les cartes d'un jeu de uno
 */
public abstract class Carte implements Sauvegardable {
	protected Couleur couleur;
	
	
	/**
	 * Cree une nouvelle carte, sans couleur
	 */
	public Carte() {}
	
	/**
	 * Cree une nouvelle carte, de la couleur specifiee
	 * @param couleur la couleur de cette carte
	 */
	public Carte(Couleur couleur) {
		this.couleur = couleur;
	}
	
	/**
	 * Cree une nouvelle carte, depuis un flux externe
	 * @param io
	 */
	public Carte(IO io) {
		this(Couleur.get(io.nextPositif()));
	}

	/**
	 * @return la valeur en points de cette carte
	 */
	public abstract int getValeurPoints();
	
	/**
	 * Action a realiser lorsque cette carte est recouverte
	 */
	public abstract void recouvrir();
	
	/**
	 * Cree une copie cet objet
	 * @return une instance nouvelle de cet objet, comportant les memes champs
	 */
	public abstract Carte dupliquer();
	
	/**
	 * @return le type de cette carte
	 */
	public abstract TypeCarte getType();
	
	/**
	 * Effectue l'effet lie a cette carte
	 * @param partie la partie sur laquelle cette carte doit faire un effet
	 * @param debutPartie vrai si cette action s'effectue en debut de partie
	 */
	public abstract void faireEffet(Partie partie, boolean debutPartie);
	
	/**
	 * @return la couleur de cette carte
	 */
	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	 * Teste si le symbole de la carte a poser est compatible avec cette carte
	 * @param carte la carte a comparer
	 * @return vrai si la carte peut recouvrir celle-ci
	 */
	public boolean estSymboleCompatible(Carte carte) {
		return carte.getType() == getType();
	}
	
	/**
	 * @param carte la carte a comparer
	 * @return vrai si les deux cartes peuvent etre posees l'une sur l'autre
	 */
	public boolean compatible(Carte carte) {
		return couleur == null || carte.couleur == null || carte.couleur == couleur || estSymboleCompatible(carte);
	}

	/**
	 * @return le poids de la carte, pour trier la main des joueurs
	 */
	public int getValeurTri() {
		return couleur.ordinal() * 13;
	}
	
	@Override
	public String toString() {
		return couleur.getNom();
	}
	
	@Override
	public IO sauvegarder(IO io) {
		return io.addBytePositif(getType().ordinal()).addBytePositif(couleur == null ? IO.LIMITE_BYTE_POSITIF : couleur.ordinal());
	}
	
	/**
	 * Convertit un flux externe en une carte
	 * @param io le flux externe
	 * @return la carte correspondante
	 */
	public static Carte getCarte(IO io) {
		switch(TypeCarte.values()[io.nextPositif()]) {
		case INVERSION: return new CarteInversion(io);
		case JOKER: return new CarteJoker(io);
		case NUMERO: return new CarteNumero(io);
		case PASSE_TOUR: return new CartePasseTour(io);
		case PLUS_DEUX: return new CartePlusDeux(io);
		case PLUS_QUATRE: return new CartePlusQuatre(io);
		default: throw new IllegalArgumentException("Aucune carte ne peut etre lue");
		}
	}
	
}
