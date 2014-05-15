package fr.utt.lo02.uno.base;

import javax.swing.UIManager;

import fr.utt.lo02.uno.ui.Fenetre;
import fr.utt.lo02.uno.ui.IconeTache;
import fr.utt.lo02.uno.ui.composant.ecran.EcranAccueil;


/**
 * Point d'entree du programme.
 */
public class MainClass {
	
	/**
	 * @param args les arguments du lancement s'il y a des parametres, l'application se lance en mode console.
	 * Sinon, l'interface utilisateur s'affiche
	 */
	public static void main(String[] args) {
		try {
			Configuration.charger();
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			IconeTache.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(args != null && args.length > 0)
			Console.getInstance().active();
		else new Fenetre(new EcranAccueil());
	}
}
