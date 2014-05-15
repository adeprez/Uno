package fr.utt.lo02.uno.jeu.joueur;

import java.util.ArrayList;
import java.util.List;

import fr.utt.lo02.uno.base.Console;
import fr.utt.lo02.uno.base.Generateur;
import fr.utt.lo02.uno.jeu.exception.JeuException;
import fr.utt.lo02.uno.jeu.listener.ListeJoueurListener;
import fr.utt.lo02.uno.jeu.listener.Listenable;


/**
 * Objet representant une liste de joueurs
 */
public class ListeJoueurs extends Listenable {
	private final Joueur[] joueurs;
	private int indexJoueur;
	private boolean sens;


	/**
	 * Cree une nouvelle liste de joueurs de taille specifiee
	 * @param nombre le nombre de joueurs admissibles
	 * @throws IllegalArgumentException si le nombre de joueurs est inferieur a deux
	 */
	public ListeJoueurs(int nombre) {
		if(nombre < 2)
			throw new IllegalArgumentException("Cette liste doit comporter deux joueurs au minimum");
		joueurs = new Joueur[nombre];
		sens = true;
	}

	/**
	 * Cree une nouvelle liste de joueurs a partir de ceux passes en parametre
	 * @param joueurs les joueurs contenus dans cette liste
	 */
	public ListeJoueurs(Joueur... joueurs) {
		this(joueurs.length);
		for(final Joueur j : joueurs) try {
			ajoutJoueur(j);
		} catch(JeuException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute un joueur a cette liste
	 * @param joueur le joueur a ajouter
	 * @throws JeuException si le joueur ne peut pas etre ajoute
	 */
	public void ajoutJoueur(Joueur joueur) throws JeuException {
		ajoutJoueur(getIDLibre(), joueur);
	}

	/**
	 * Ajoute un joueur a cette liste
	 * @param id l'identifiant associe a ce joueur
	 * @param joueur le joueur a ajouter
	 */
	public void ajoutJoueur(int id, Joueur joueur) {
		if(joueurExiste(id))
			retireJoueur(id);
		joueurs[id] = joueur;
		notifyAjoutJoueur(id, joueur);
		Console.getInstance().affiche(joueur + " rejoint la partie");
	}

	/**
	 * @param id l'identifiant du joueur
	 * @return vrai si le joueur existe
	 */
	public boolean joueurExiste(int id) {
		return id >= 0 && id < joueurs.length && joueurs[id] != null;
	}

	/**
	 * Retire un joueur de cette liste
	 * @param id l'identifiant associe a ce joueur
	 * @throws IllegalArgumentException si le joueur correspondant a cet identifiant ne peut etre trouve
	 */
	public void retireJoueur(int id) {
		if(!joueurExiste(id))
			throw new IllegalArgumentException("Aucun joueur ne possede l'identifiant " + id);
		Joueur j = joueurs[id];
		joueurs[id] = null;
		notifyRetireJoueur(id, j);
		Console.getInstance().affiche(j + " quitte la partie");
	}

	/**
	 * Retire un joueur de cette liste
	 * @param joueur le joueur a retirer de cette liste
	 */
	public void retireJoueur(Joueur joueur) {
		retireJoueur(getID(joueur));
	}

	/**
	 * Recherche l'identifiant d'un joueur
	 * @param joueur le joueur dont on recherche l'identifiant
	 * @return l'identifiant du joueur
	 * @throws IllegalArgumentException si le joueur n'est pas present dans la liste
	 */
	public int getID(Joueur joueur) {
		for(int i=0 ; i<joueurs.length ; i++)
			if(joueurs[i] == joueur)
				return i;
		throw new IllegalArgumentException("Ce joueur n'est pas present dans la liste");
	}

	/**
	 * @return un identifiant libre pour un joueur
	 * @throws JeuException si plus aucun identifiant n'est libre
	 */
	public int getIDLibre() throws JeuException {
		for(int i=0 ; i<joueurs.length ; i++)
			if(!joueurExiste(i))
				return i;
		throw new JeuException("Cette liste est pleine");
	}

	/**
	 * Inverse le sens du jeu
	 */
	public void inverseSens() {
		sens = !sens;
		Console.getInstance().affiche("Changement du sens de jeu");
	}

	/**
	 * Fini le tour du joueur courant et demarre celui du nouveau joueur
	 * @return le nouveau joueur
	 */
	public Joueur suivant() {
		indexJoueur += getIncrementJoueurSuivant();
		Console.getInstance().affiche("C'est au tour de " + getJoueur() + " de jouer");
		return getJoueur();
	}
	
	/**
	 * Retourne vers le joueur precedent
	 * @return le nouveau joueur
	 */
	public Joueur precedent() {
		setIndexJoueur(getIndexJoueurPrecedent());
		return getJoueur();
	}

	/**
	 * @param id l'identifiant du joueur
	 * @return le joueur
	 */
	public Joueur getJoueur(int id) {
		if(joueurExiste(id))
			return joueurs[id];
		return null;
	}

	/**
	 * @return le joueur courant
	 */
	public Joueur getJoueur() {
		return joueurs[getIDJoueur()];
	}

	/**
	 * @return le joueur suivant
	 */
	public Joueur getJoueurSuivant() {
		return joueurs[getIndexJoueurSuivant()];
	}

	/**
	 * @return le joueur precedent
	 */
	public Joueur getJoueurPrecedent() {
		return joueurs[getIndexJoueurPrecedent()];
	}

	/**
	 * @return l'identifiant du joueur courant
	 */
	public int getIDJoueur() {
		while(indexJoueur < 0)
			indexJoueur += joueurs.length;
		return indexJoueur % joueurs.length;
	}

	/**
	 * @return l'index du joueur courant
	 */
	public int getIndexJoueur() {
		while(indexJoueur < 0)
			indexJoueur += joueurs.length;
		return indexJoueur % joueurs.length;
	}
	
	/**
	 * @param indexJoueur le nouvel index du joueur courant
	 */
	public void setIndexJoueur(int indexJoueur) {
		this.indexJoueur = indexJoueur % joueurs.length;
		while(indexJoueur < 0)
			indexJoueur += joueurs.length;
	}

	/**
	 * @return l'index du joueur suivant
	 */
	public int getIndexJoueurSuivant() {
		int i = indexJoueur + getIncrementJoueurSuivant();
		while(i < 0)
			i += joueurs.length;
		return i % joueurs.length;
	}

	/**
	 * @return l'index du joueur precedent
	 */
	public int getIndexJoueurPrecedent() {
		int i = indexJoueur - getIncrementJoueurSuivant();
		while(i < 0)
			i += joueurs.length;
		return i % joueurs.length;
	}

	/**
	 * @return le tableau des joueurs
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}

	/**
	 * @return la liste des joueurs
	 */
	public List<Joueur> getListeJoueurs() {
		List<Joueur> l = new ArrayList<Joueur>();
		for(final Joueur j : joueurs)
			if(j != null)
				l.add(j);
		return l;
	}

	/**
	 * @return le nombre de joueurs
	 */
	public int getNombre() {
		int n = 0;
		for(final Joueur j : joueurs)
			if(j != null)
				n++;
		return n;
	}

	/**
	 * @return le nombre de joueurs maximum que cette partie peut accueillir
	 */
	public int getMaxJoueurs() {
		return joueurs.length;
	}

	/**
	 * Ajoute des joueurs ordinateur pour combler les places dans cette partie
	 */
	public void combler() {
		for(int i=0 ; i<joueurs.length; i++)
			if(!joueurExiste(i))
				ajoutJoueur(i, new Joueur(TypeJoueur.ORDINATEUR, Generateur.getInstance().getNom()));
	}

	/**
	 * @param l le listener de la liste de joueurs a ajouter
	 */
	public void addListeJoueursListener(ListeJoueurListener l) {
		addListener(ListeJoueurListener.class, l);
	}

	/**
	 * @param l de la liste de joueurs a retirer
	 */
	public void removeListeJoueursListener(ListeJoueurListener l) {
		removeListener(ListeJoueurListener.class, l);
	}

	/**
	 * Signifie aux ecouteur de cette liste qu'un joueur a ete ajoute
	 * @param id l'identifiant du nouveau joueur
	 * @param joueur le nouveau joueur
	 */
	private void notifyAjoutJoueur(int id, Joueur joueur) {
		for(final ListeJoueurListener l : getListeners(ListeJoueurListener.class))
			l.ajout(id, joueur);
	}

	/**
	 * Signifie aux ecouteur de cette liste qu'un joueur a ete retire
	 * @param id l'identifiant de l'ancien joueur
	 * @param joueur l'ancien joueur
	 */
	private void notifyRetireJoueur(int id, Joueur joueur) {
		for(final ListeJoueurListener l : getListeners(ListeJoueurListener.class))
			l.retire(id, joueur);
	}

	/**
	 * @return 1 ou -1 selon le sens de rotation du jeu
	 */
	private int getIncrementJoueurSuivant() {
		return sens ? 1 : -1;
	}

	/**
	 * @return vrai si cette salle est pleine
	 */
	public boolean estPleine() {
		return getNombre() == getMaxJoueurs();
	}
	
	/**
	 * @return le sens de cette liste (true pour le sens trigonometrique, false pour le sens des aiguilles d'une montre)
	 */
	public boolean sensTrigo() {
		return sens;
	}

	/**
	 * @return vrai si au moins un joueur present dans cette partie est humain
	 */
	public boolean aHumain() {
		for(final Joueur j : joueurs)
			if(j != null && j.getType() != TypeJoueur.ORDINATEUR)
				return true;
		return false;
	}

}
