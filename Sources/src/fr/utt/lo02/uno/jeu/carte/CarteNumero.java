package fr.utt.lo02.uno.jeu.carte;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.jeu.Partie;

/**
 * Objet representant une carte numerotee
 */
public class CarteNumero extends CarteSimple {
	public static final int MAXIMUM = 9;
	private final int numero;
	

	/**
	 * Cree une nouvelle carte numerotee
	 * @param couleur la couleur de cette carte
	 * @param numero le numero de cette carte
	 */
	public CarteNumero(Couleur couleur, int numero) {
		super(couleur);
		this.numero = numero;
	}

	/**
	 * Cree une nouvelle carte numerotee
	 * @param io le flux a partir duquel determiner la couleur et le numero de cette carte
	 */
	public CarteNumero(IO io) {
		super(io);
		numero = io.nextPositif();
	}

	/**
	 * @return le numero de cette carte
	 */
	public int getNumero() {
		return numero;
	}
	
	@Override
	public String toString() {
		return numero + " " + super.toString();
	}
	
	@Override
	public int getValeurTri() {
		return super.getValeurTri() + numero;
	}

	@Override
	public int getValeurPoints() {
		return numero;
	}

	@Override
	public void faireEffet(Partie partie, boolean debutPartie) {
		
	}

	@Override
	public boolean estSymboleCompatible(Carte carte) {
		return super.estSymboleCompatible(carte) && ((CarteNumero) carte).numero == numero;
	}

	@Override
	public CarteNumero dupliquer() {
		return new CarteNumero(couleur, numero);
	}
	
	@Override
	public IO sauvegarder(IO io) {
		return super.sauvegarder(io).addBytePositif(numero);
	}

	@Override
	public TypeCarte getType() {
		return TypeCarte.NUMERO;
	}

}
