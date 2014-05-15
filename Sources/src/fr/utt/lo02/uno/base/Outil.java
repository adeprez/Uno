package fr.utt.lo02.uno.base;

/**
 * Classe non heritable mettant a disposition des methodes pour toute l'application
 */
public final class Outil {

	
	/**
	 * Classe non instanciable
	 */
	private Outil() {}
	
	/**
	 * Met en pause l'execution du code pour un temps donne
	 * @param temps le temps (en millisecondes) durant lequel l'execution doit patienter
	 */
	public static void attendre(int temps) {
		try {
			Thread.sleep(temps);
		} catch(InterruptedException e) {}
	}
	
}
