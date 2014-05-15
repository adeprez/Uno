package fr.utt.lo02.uno.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton permettant de charger des fichiers et d'acceder a leur contenu, a partir du dossier "fichiers" de l'archive jar 
 */
public class Fichiers {
	private static final String PATH = "/fichiers/";
	private static Fichiers instance;


	/**
	 * Design-pattern singleton. Cree l'unique instance de cette classe, si elle n'existe pas encore
	 * @return l'unique instance de cette classe
	 */
	public static Fichiers getInstance() {
		synchronized(Fichiers.class) {
			if(instance == null)
				instance = new Fichiers();
			return instance;
		}
	}
	
	/**
	 * Constructeur prive, selon le design-pattern singleton
	 */
	private Fichiers() {}
	
	/**
	 * Cree un flux vers le fichier demande
	 * @param nom le nom du fichier dans le repertoire "fichiers" de l'archive jar
	 * @return un flux d'entree permettant de lire le contenu du fichier
	 * @throws NullPointerException si la ressource ne peut pas etre trouvee
	 */
	public InputStream getFluxFichier(String nom) {
		return getClass().getResourceAsStream(PATH + nom);
	}
	
	/**
	 * Lit le contenu d'un fichier, vers un objet {@link IO}
	 * @param nom le nom du fichier dans le repertoire "fichiers" de l'archive jar
	 * @return l'objet IO permettant de lire differents types
	 */
	public IO lireFichier(String nom) {
		try {
			return new IO(new In(getFluxFichier(nom)).lire());
		} catch(Exception e) {
			e.printStackTrace();
			return new IO();
		}
	}
	
	/**
	 * Lit les lignes contenues dans un fichier
	 * @param nom le nom du fichier dans le repertoire "fichiers" de l'archive jar
	 * @return la liste des lignes contenues dans ce fichier
	 */
	public List<String> lireLignesFichier(String nom) {
		ArrayList<String> lignes = new ArrayList<String>();
		BufferedReader read = null;
		try {
			read = new BufferedReader(new InputStreamReader(getFluxFichier(nom), "UTF-8"));
			String ligne;
			while((ligne = read.readLine()) != null)
				lignes.add(ligne);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(read != null)
					read.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return lignes;
	}
	
}
