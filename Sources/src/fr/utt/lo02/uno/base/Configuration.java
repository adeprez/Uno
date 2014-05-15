package fr.utt.lo02.uno.base;

import java.awt.Font;
import java.io.IOException;
import java.util.Properties;

import fr.utt.lo02.uno.io.Fichiers;
import fr.utt.lo02.uno.langue.Langue;

/**
 * Classe definissant les constantes de la partie. L'unite par defaut est la seconde
 */
public class Configuration {

	/**
	 * Police utilisee pour l'interface graphique
	 */
	public static final Font POLICE = new Font(Font.DIALOG, Font.BOLD, 16);

	/**
	 * Langue utilisee au sein de l'application
	 */
	public static Langue LANGUE = Langue.get();


	/**
	 * Temps entre chaque tour de joueur
	 */
	public static int TEMPS_INTERTOUR = 1;

	/**
	 * Temps maximal durant lequel un joueur peut realiser des actions. Après quoi, son tour doit se terminer
	 */
	public static int TEMPS_TOUR = 20;

	/**
	 * Nombre maximum de joueurs pouvant participer a une partie de Uno
	 */
	public static int MAX_JOUEUR = 10;

	/**
	 * Temps durant lequel la partie reste rejoignable apres la creation d'une salle reseau
	 */
	public static int TEMPS_PARTIE_REJOIGNABLE = 15;

	/**
	 * Temps avant de commencer une partie lorsque la salle reseau est pleine
	 */
	public static int TEMPS_COMMENCER_PARTIE_SALLE_PLEINE = 5;

	/**
	 * Nombre de cartes distribuees a chaque joueur, en debut de partie
	 */
	public static int NOMBRE_CARTES = 7;

	/**
	 * Temps durant lequel les resultats sont affiches en fin de partie
	 */
	public static int TEMPS_AFFICHAGE_RESULTATS = 15;

	/**
	 * Nombre de points d'un joueur a partir duquel la partie se termine 
	 */
	public static int SCORE_DEFAITE = 500;

	/**
	 * Messages d'informations
	 */
	public static String[] MESSAGES = new String[] {};


	/**
	 * Defini les constantes de partie selon le fichier "proprietes.txt"
	 * @throws IOException si le fichier n'est pas trouve
	 */
	public static void charger() throws IOException {
		Properties p = new Properties();
		p.load(Fichiers.getInstance().getFluxFichier("proprietes.txt"));
		TEMPS_INTERTOUR = Integer.valueOf((String) p.get("temps_intertour"));
		TEMPS_TOUR = Integer.valueOf((String) p.get("temps_tour"));
		MAX_JOUEUR = Integer.valueOf((String) p.get("max_joueur"));
		TEMPS_PARTIE_REJOIGNABLE = Integer.valueOf((String) p.get("temps_partie_rejoignable"));
		TEMPS_COMMENCER_PARTIE_SALLE_PLEINE = Integer.valueOf((String) p.get("temps_commencer_partie_salle_pleine"));
		NOMBRE_CARTES = Integer.valueOf((String) p.get("nombre_cartes"));
		TEMPS_AFFICHAGE_RESULTATS = Integer.valueOf((String) p.get("temps_affichage_resultats"));
		SCORE_DEFAITE = Integer.valueOf((String) p.get("score_defaite"));
		MESSAGES = ((String) p.get("messages")).split("&");
		try {
			LANGUE = Langue.valueOf(((String) p.get("langue")));
		} catch(Exception err) {}
	}

}
