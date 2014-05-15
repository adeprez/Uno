package fr.utt.lo02.uno.io;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Singleton permettant de jouer des sons via le repertoire "sons", devant etre place au meme endroit que le jar executable
 */
public class Sons {
	private static final String PATH = "/sons/";
	private static Sons instance;
	private final Map<String, Clip> sons;
	private boolean desactive;


	/**
	 * Design-pattern singleton. Cree l'unique instance de cet objet s'il n'existe pas encore
	 * @return l'unique instance de cet classe
	 */
	public static Sons getInstance() {
		synchronized(Images.class) {
			if(instance == null)
				instance = new Sons();
			return instance;
		}
	}

	/**
	 * Constructeur prive, selon le design-pattern singleton
	 */
	private Sons() {
		sons = new HashMap<String, Clip>();
	}
	
	/**
	 * Charge le son selon son nom, depuis le repertoire "sons", devant etre place au meme endroit que le jar executable
	 * @param nom le nom du son a jouer
	 * @throws UnsupportedAudioFileException si le format de fichiers n'est pas supporte
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws LineUnavailableException si une erreur survient a la lecture de ce fichier
	 */
	public void charger(String nom) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(PATH + nom)));
		sons.put(nom, clip);
	}
	
	/**
	 * Si le son n'a pas ete charge, memorise le son via la methode {@link #charger(String)}
	 * @param nom le nom du son a jouer
	 * @return le son
	 * @throws UnsupportedAudioFileException si le format de fichiers n'est pas supporte
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @throws LineUnavailableException si une erreur survient a la lecture de ce fichier
	 */
	public Clip getSon(String nom) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(!sons.containsKey(nom))
			charger(nom);
		return sons.get(nom);
	}
	
	/**
	 * Joue le son. Si le son n'a pas ete charge, memorise le son via la methode {@link #charger(String)}
	 * @param son le nom du son a jouer
	 */
	public void jouer(String son) {
		if(!desactive) try {
			Clip c = getSon(son);
			c.setFramePosition(0);
			c.start();
		} catch(Exception e) {
			System.err.println("Impossible de jouer le son " + son + ". En consequence, les sons ont ete desactives");
			desactive = true;
		}
	}

}
