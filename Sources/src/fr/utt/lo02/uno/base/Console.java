package fr.utt.lo02.uno.base;

import java.util.Scanner;

/**
 * Singleton gerant les entrees et sorties, lorque l'application est utilisee en mode console.
 * @see #active()
 */
public class Console extends Thread {
	private static Console instance;
	private final Scanner scan;
	private final AdminJeu admin;
	private boolean affiche;

	
	/**
	 * Design-pattern singleton. Cree cet objet s'il n'existe pas.
	 * @return l'unique instance de cette classe
	 */
	public static Console getInstance() {
		synchronized(Console.class) {
			if(instance == null)
				instance = new Console();
			return instance;
		}
	}
	
	/**
	 * Constructeur prive afin d'empecher sa creation, selon le design-pattern sigleton
	 */
	private Console() {
		scan = new Scanner(System.in);
		admin = new AdminJeu();
	}
	
	/**
	 * Affiche un message sur la sortie standard
	 * @param message le message a afficher
	 */
	public void affiche(String message) {
		if(affiche)
			System.out.println(message);
	}
	
	/**
	 * Affiche un message sur la sortie d'erreur
	 * @param message le message a afficher
	 */
	public void erreur(String message) {
		if(affiche)
			System.err.println(message);
	}
	
	/**
	 * Passe une ligne sur la sortie standard
	 */
	public void retourLigne() {
		affiche("");
	}
	
	/**
	 * @return vrai si l'affichage est actif pour cette console
	 */
	public boolean estAffiche() {
		return affiche;
	}

	/**
	 * Active cette console, sans quoi les messages ne sont pas affiches. 
	 * Cela demarre egalement l'ecoute des commandes entrees sur la console
	 * @see AdminJeu
	 */
	public void active() {
		affiche = true;
		start();
		afficheOptions();
	}

	/**
	 * Affiche les commandes realisables pour cette console
	 */
	public void afficheOptions() {
		affiche("-----------------------");
		affiche("COMMANDES DISPONIBLES : ");
		affiche(AdminJeu.getCommandes());
	}

	/**
	 * Realise une action via l'administrateur de jeu
	 * @param action l'action a realiser
	 * @see AdminJeu#action(String)
	 */
	public void action(String action) {
		admin.action(action);
	}

	/**
	 * Affiche la representation textuelle d'un objet sur la sortie standard
	 * @param o l'objet a afficher
	 * @see Object#toString()
	 */
	public void affiche(Object o) {
		affiche("" + o);
	}
	
	@Override
	public void run() {
		while(true) try {
			action(scan.nextLine());
		} catch(Exception err) {
			err.printStackTrace();
		}
	}
	
}
