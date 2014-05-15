package fr.utt.lo02.uno.io.reseau;

import java.net.InetAddress;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.interfaces.Paquetable;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.variantes.TypeJeu;

/**
 * Objet pouvant etre envoye dans un flux de donnees, representant les informations sur une salle de jeu du Uno
 * @see Paquetable
 */
public class InfoSalle extends IO implements Paquetable {
	private final int nombreJoueursMax;
	private final TypeJeu type;
	private final String nom;
	private InetAddress adresse;
	

	/**
	 * Cree un nouvel objet pouvant etre envoye dans un flux de donnees, representant les informations sur une salle de jeu du Uno
	 * @param type le type de jeu
	 * @param nom le nom de la salle de jeu
	 * @param nombreJoueursMax le nombre maximum de joueurs
	 */
	public InfoSalle(TypeJeu type, String nom, int nombreJoueursMax) {
		this.type = type;
		this.nom = nom;
		this.nombreJoueursMax = nombreJoueursMax;
		addBytePositif(type.ordinal());
		addShort(nom);
		addBytePositif(nombreJoueursMax);
	}
	
	/**
	 * Cree un nouvel objet pouvant etre envoye dans un flux de donnees, representant les informations sur une salle de jeu du Uno
	 * @param salle la salle dont les informations doivent etre representees
	 */
	public InfoSalle(Salle salle) {
		this(salle.getJeu().getType(), salle.getNom(), salle.getJeu().getListeJoueurs().getMaxJoueurs());
	}
	
	/**
	 * Cree un nouvel objet representant les informations sur une salle de jeu du Uno, originaire d'un flux de donnees
	 * @param io le flux de donnees a partir duquel construire les informations de la salle
	 */
	public InfoSalle(IO io) {
		this(TypeJeu.values()[io.nextPositif()], io.nextShortString(), io.nextPositif());
	}
	
	/**
	 * @return le nombre de joueurs maximum que peut contenir la salle decrite par cet objet
	 */
	public int getNombreJoueursMax() {
		return nombreJoueursMax;
	}
	
	/**
	 * @return le type du jeu
	 */
	public TypeJeu getTypeJeu() {
		return type;
	}

	/**
	 * @return le nom de la salle decrite par cet objet
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @return l'adresse de la partie
	 */
	public InetAddress getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse l'adresse de la partie
	 */
	public void setAdresse(InetAddress adresse) {
		this.adresse = adresse;
	}

	@Override
	public TypePaquet getType() {
		return TypePaquet.INFO_SALLE;
	}
	
	@Override
	public String toString() {
		return nom + " (" + nombreJoueursMax + " joueurs) - " + type.getNom();
	}

}
