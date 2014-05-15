package fr.utt.lo02.uno.base;

import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.io.reseau.serveur.Serveur;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.action.generateur.GenerateurActionConsole;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.temps.Horloge;

import java.util.List;
import java.util.Map.Entry;


/**
 * Classe representant l'administrateur du jeu. Il permet d'interagir avec celui ci, notamment de creer une partie, ajouter et supprimer
 * des joueurs, afficher les joueurs presents et la lancer.
 */
public class AdminJeu implements JeuListener {
	private static final String[][] COMMANDES = new String[][] {
		new String[] {"prepare", "Cree une salle de jeu, pouvant accueillir le nombre de joueurs specifie", "[nombre]"},
		new String[] {"add", "Ajoute un joueur, avec un nom", "[nom] [controle par joueur]"},
		new String[] {"remove", "Supprime un joueur selon son rang", "[rang]"},
		new String[] {"affiche", "Affiche les joueurs et les places disponibles"},
		new String[] {"start", "Lance une nouvelle partie"},
		new String[] {"host", "Heberge une salle de jeu en ligne", "[nom]"},
		new String[] {"join", "Rejoint une partie en ligne, a l'adresse indiquee", "[nom joueur] [adresse]"}
	};
	private final Horloge horloge;
	private Jeu jeu;
	private GenerateurActionConsole console;


	/**
	 * Cree un nouvel administrateur de jeu. Elle permet d'interagir avec celui ci, notamment de creer une partie, ajouter et supprimer
	 * des joueurs, afficher les joueurs presents et la lancer.
	 */
	public AdminJeu() {
		horloge = new Horloge();
	}

	/**
	 * Effectue une action de gestion de partie
	 * @param action l'action a realiser, sous la forme d'une chaine de caracteres
	 * @return vrai si l'action a ete realisee
	 */
	public boolean action(String action) {
		for(int i=0 ; i<COMMANDES.length; i++) {
			String[] args = action.split(" ");
			if(args.length > 0 && getCommande(i).equalsIgnoreCase(args[0])) {
				action(i, args);
				return true;
			}
		}
		if(console != null)
			console.action(action);
		return false;
	}

	/**
	 * Effectue une action de gestion de partie
	 * @param id l'indentifiant de la commande a effectuer
	 * @param args les arguments associes a cette commande
	 */
	public void action(int id, String[] args) {
		try {
			if(id != 0 && id != 5 && id != 6 && jeu == null)
				Console.getInstance().affiche("Vous devez d'abord creer une salle : prepare [nombre]");
			else switch(id) {
			case 0://Prepare une nouvelle partie
				if(args.length > 1)
					jeu = new Jeu(Integer.valueOf(args[1]));
				break;

			case 1://Ajoute un joueur
				Joueur j;
				if(args.length < 3) {
					j = new Joueur(TypeJoueur.ORDINATEUR, args.length < 2 ? "Sans nom" : args[1]);
				} else {
					if(console == null)
						console = new GenerateurActionConsole();
					j = new Joueur(TypeJoueur.HUMAIN, args[1]);
					j.addTourJoueurListener(console);
				}
				jeu.getListeJoueurs().ajoutJoueur(j);
				break;

			case 2://Supprime un joueur
				jeu.getListeJoueurs().retireJoueur(args.length < 2 ? 0 : Integer.valueOf(args[1]));
				break;

			case 3://Affiche les joueurs et places libres
				for(int i=0 ; i<jeu.getListeJoueurs().getJoueurs().length ; i++) {
					Joueur joueur = jeu.getListeJoueurs().getJoueurs()[i];
					Console.getInstance().affiche(i + ") " + (joueur == null ? "(libre)" : joueur));
				}
				break;

			case 4://Lance une nouvelle partie
				jeu.nouvellePartie();
				horloge.lancer();
				break;

			case 5://Heberge une partie en ligne
				new Serveur(new Salle()).lancer();
				break;

			case 6://Rejoint une partie en ligne
				SalleReseau r = new SalleReseau(args.length >= 2 ? args[1] : "Anonyme", args.length >= 3 ? args[2] : "127.0.0.1");
				jeu = r.getJeu();
				jeu.addJeuListener(this);
				console = new GenerateurActionConsole();
				break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return une representation textuelle de la liste des commandes
	 */
	public static String getCommandes() {
		String s = "";
		for(int i=0 ; i<COMMANDES.length ; i++)
			s += getCommande(i) + " " + getArguments(i) + " : " + getDescription(i) + "\n";
		return s;
	}

	/**
	 * @param id l'identifiant de la commande
	 * @return le nom de la commande
	 */
	public static String getCommande(int id) {
		return COMMANDES[id][0];
	}

	/**
	 * @param id l'identifiant de la commande
	 * @return les arguments de cette commande
	 */
	public static String getArguments(int id) {
		return COMMANDES[id].length > 2 ? COMMANDES[id][2] : "";
	}

	/**
	 * @param id l'identifiant de la commande
	 * @return la description de cette commande
	 */
	public static String getDescription(int id) {
		return COMMANDES[id][1];
	}

	@Override
	public void nouvellePartie(Partie partie) {
		for(final Joueur j : partie.getJoueurs().getListeJoueurs())
			if(j.getType() == TypeJoueur.HUMAIN)
				j.addTourJoueurListener(console);
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {
		for(final Joueur j : partie.getJoueurs().getListeJoueurs())
			if(j.getType() == TypeJoueur.HUMAIN)
				j.removeTourJoueurListener(console);
		Console.getInstance().affiche(resultats.getGagnant() + " gagne la partie");
		for(final Entry<Joueur, Integer> e : resultats.getPoints().entrySet())
			Console.getInstance().affiche(e.getKey() + " : " + e.getValue() + " points");
		Console.getInstance().retourLigne();
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {
		Console.getInstance().affiche(ResultatPartie.getGagnant(resultats) + " gagne le jeu !");
	}

}
