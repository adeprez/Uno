package fr.utt.lo02.uno.jeu.carte.ensemble;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.interfaces.Sauvegardable;
import fr.utt.lo02.uno.jeu.carte.Carte;
import fr.utt.lo02.uno.jeu.carte.CarteInversion;
import fr.utt.lo02.uno.jeu.carte.CarteJoker;
import fr.utt.lo02.uno.jeu.carte.CarteNumero;
import fr.utt.lo02.uno.jeu.carte.CartePasseTour;
import fr.utt.lo02.uno.jeu.carte.CartePlusDeux;
import fr.utt.lo02.uno.jeu.carte.CartePlusQuatre;
import fr.utt.lo02.uno.jeu.carte.Couleur;

import java.util.Stack;


/**
 * Objet representant le plateau de jeu du UNO. Il contient la pioche et le talon
 */
public class PlateauJeu implements Sauvegardable {
	private final TasDeCarte pioche, talon;


	/**
	 * Cree un nouveau plateau de jeu
	 * @param cartes les cartes contenues dans la pioche
	 */
	public PlateauJeu(Stack<Carte> cartes) {
		talon = new TasDeCarte();
		pioche = new TasDeCarte(cartes);
		pioche.melanger();
		talon.poser(pioche.piocher());
	}
	
	/**
	 * Cree un nouveau plateau de jeu a partir d'un flux externe
	 * @param io le flux externe a convertir
	 */
	public PlateauJeu(IO io) {
		talon = new TasDeCarte(io);
		pioche = new TasDeCarte(io);
	}

	/**
	 * Cree un nouveau plateau de jeu, selon les regles de base
	 */
	public PlateauJeu() {
		this(creerCartes());
	}

	/**
	 * Pioche une nouvelle carte dans la pioche
	 * @return la carte piochee
	 */
	public Carte piocher() {
		if(pioche.estVide())
			reconstruirePioche(talon.piocher());
		return pioche.piocher();
	}

	/**
	 * Transfert les cartes du talon vers la pioche
	 * @param carteTalon la carte superieure du talon
	 */
	public void reconstruirePioche(Carte carteTalon) {
		talon.transvaser(pioche);
		talon.poser(carteTalon);
	}

	/**
	 * @return la pioche
	 */
	public TasDeCarte getPioche() {
		return pioche;
	}

	/**
	 * @return le talon
	 */
	public TasDeCarte getTalon() {
		return talon;
	}

	@Override
	public String toString() {
		return "Talon : " + talon.getCarte() + " (" + pioche.getNombre() + " cartes dans la pioche)";
	}

	/**
	 * Cree un nouvel ensemble de cartes selon les regles de base
	 * @return l'ensemble de carte, dans l'ordre de leur ajout
	 * @see #creerCartes(int, int, int)
	 */
	private static final Stack<Carte> creerCartes() {
		return creerCartes(2, 4, 4);
	}

	/**
	 * Cree un nouvel ensemble de cartes
	 * @param nbrMalus le nombre de carte pour chaque type de malus : +2, inversion et passe-tour contenus dans la pile 
	 * @param nbrJoker le nombre de joker contenus dans la pile
	 * @param nbrPlus4 le nombre de +4 contenus dans la pile
	 * @return l'ensemble de carte, dans l'ordre de leur ajout
	 */
	private static final Stack<Carte> creerCartes(int nbrMalus, int nbrJoker, int nbrPlus4) {
		Stack<Carte> cartes = new Stack<Carte>();

		for(final Couleur c : Couleur.values()) {
			//ajout des cartes de 0 a 9
			for(int i=0 ; i <= CarteNumero.MAXIMUM ; i++) {
				cartes.add(new CarteNumero(c, i));
				if(i != 0)
					cartes.add(new CarteNumero(c, i));
			}

			//ajout des cartes +2, inversion et passe-tour
			for(int i=0 ; i < nbrMalus ; i++) {
				cartes.add(new CartePlusDeux(c));
				cartes.add(new CarteInversion(c));
				cartes.add(new CartePasseTour(c));
			}
		}

		//ajout des cartes joker
		for(int i=0 ; i < nbrJoker ; i++)
			cartes.add(new CarteJoker());

		//ajout des cartes +4
		for(int i=0 ; i < nbrPlus4 ; i++)
			cartes.add(new CartePlusQuatre());
		
		return cartes;
	}

	@Override
	public IO sauvegarder(IO io) {
		return pioche.sauvegarder(talon.sauvegarder(io));
	}

}
