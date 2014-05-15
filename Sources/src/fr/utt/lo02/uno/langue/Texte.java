package fr.utt.lo02.uno.langue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Fichiers;
import fr.utt.lo02.uno.jeu.listener.Listenable;

/**
 * Objet permettant de gerer des textes de plusieurs langues, a partir du fichier "textes.txt"
 * @see Langue
 * @see Locale
 */
public class Texte extends Listenable {
	private static final String PATH = "textes.csv", SEPARATEUR = ";";
	private static Texte instance;
	private final Map<String, String[]> textes;
	private Langue langue;


	/**
	 * Permet l'acces a l'unique instance de cette classe. Si elle n'existe pas encore, l'instance est cree
	 * @return l'unique instance de cette classe, selon le design-pattern singleton
	 */
	public static Texte getInstance() {
		synchronized(Texte.class) {
			if(instance == null)
				instance = new Texte();
			return instance;
		}
	}

	/**
	 * Constructeur prive, selon le design-pattern singleton. Lit le fichier "textes.txt" et en deduit les textes pour chaque langue
	 */
	private Texte() {
		textes = new HashMap<String, String[]>();
		for(final String s : Fichiers.getInstance().lireLignesFichier(PATH)) {
			if(!(s.startsWith("//") || s.isEmpty())) {
				String[] txt = s.split(SEPARATEUR);
				if(txt.length > 0)
					textes.put(txt[0], txt);
			}
		}
		langue = Configuration.LANGUE;
	}

	/**
	 * Assigne le language pour les textes
	 * @param langue la langue utilisee
	 */
	public void setLangue(Langue langue) {
		this.langue = langue;
		for(final LangueListener l : getListeners(LangueListener.class))
			l.changeLangue(langue);
	}

	/**
	 * @return la langue utilisee dans l'application
	 */
	public Langue getLangue() {
		return langue;
	}

	/**
	 * Recherche le tableau des textes pour le texte en francais precise
	 * @param texte le texte en francais a partir duquel determiner le texte en langue courante
	 * @return le texte dans toutes les langues supportees
	 */
	public String[] getTextes(String texte) {
		return textes.get(texte);
	}

	/**
	 * Recherche le texte dans une langue donnee
	 * @param texte le texte en francais a determiner
	 * @return le texte dans la langue actuelle
	 * @see #getLangue()
	 */
	public String getTexte(String texte) {
		String[] textes = getTextes(texte);
		int l = langue.ordinal();
		if(textes != null && l < textes.length)
			return textes[l];
		System.err.println(texte + " n'a pas de correspondance en " + langue);
		return texte;
	}

	/**
	 * Ajoute un ecouteur de langue
	 * @param l le nouvel ecouteur de langue
	 */
	public void addLangueListener(LangueListener l) {
		addListener(LangueListener.class, l);
	}

	/**
	 * Retire un ecouteur de langue
	 * @param l l'ancien ecouteur de langue
	 */
	public void removeLangueListener(LangueListener l) {
		removeListener(LangueListener.class, l);
	}

	/**
	 * Demande a l'utilisateur quelle langue il souhaite utiliser
	 * @return vrai si la langue a ete changee
	 */
	public boolean proposeChangeLangue() {
		int index = JOptionPane.showOptionDialog(null, null, Texte.get("Choisir une langue"), 
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, Langue.getNoms(), Texte.getInstance().getLangue().getNom());
		if(index == -1)
			return false;
		setLangue(Langue.values()[index]);
		return true;
	}

	/**
	 * Fait appel a l'unique instance de cette classe (singleton) pour determiner un texte dans la langue actuelle
	 * @param texte le texte en francais a determiner
	 * @return le texte dans la langue actuelle
	 */
	public static String get(String texte) {
		return getInstance().getTexte(texte);
	}


}
