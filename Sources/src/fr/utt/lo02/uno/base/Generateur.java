package fr.utt.lo02.uno.base;

import fr.utt.lo02.uno.io.Fichiers;

import java.util.List;
import java.util.Random;

/**
 * Singleton permettant d'acceder a une liste de noms, ainsi qu'au nom du joueur principal
 */
public class Generateur {
	private static Generateur instance;
	private final List<String> noms;
	private final Random random;
	private String nom;
	
	
	/**
	 * Design-pattern singleton. Cree une instance de cette classe si elle n'existe pas encore
	 * @return l'unique instance de cette classe
	 */
	public static Generateur getInstance() {
		synchronized(Generateur.class) {
			if(instance == null)
				instance = new Generateur();
			return instance;
		}
	}
	
	/**
	 * Constructeur prive, selon le design-pattern singleton
	 */
	private Generateur() {
		noms = Fichiers.getInstance().lireLignesFichier("noms.txt");
		random = new Random();
	}
	
	/**
	 * @return un nom aleatoire parmi la liste
	 */
	public String getNom() {
		if(noms.isEmpty())
			return "Pas de nom trouve";
		return noms.get(random.nextInt(noms.size()));
	}

	/**
	 * @return le nom du joueur principal
	 */
	public String getNomPrincipal() {
		if(nom == null)
			nom = getNom();
		return nom;
	}
	
	/**
	 * Assigne le nom du joueur principal
	 * @param nom le nom du joueur
	 * @return le nom principal
	 */
	public String setNomPrincipal(String nom) {
		this.nom = nom;
		return nom;
	}
	
}
