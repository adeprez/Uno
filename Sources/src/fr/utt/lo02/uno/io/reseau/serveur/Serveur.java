package fr.utt.lo02.uno.io.reseau.serveur;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.exception.ServeurFullException;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.serveur.filtreEnvoi.FiltreEnvoiExclusionID;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.action.ActionJoueur;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.ActionTourListener;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.jeu.listener.ListeJoueurListener;
import fr.utt.lo02.uno.jeu.listener.PartieListener;
import fr.utt.lo02.uno.temps.Evenement;
import fr.utt.lo02.uno.temps.Evenementiel;
import fr.utt.lo02.uno.temps.Horloge;
import fr.utt.lo02.uno.temps.Periodique;

import java.io.IOException;
import java.net.Socket;
import java.util.List;


/**
 * Classe representant un serveur permettant de gerer une salle de jeu du Uno en ligne.
 */
public class Serveur extends AbstractServeur implements ListeJoueurListener, Evenementiel, PartieListener, JeuListener, ActionTourListener {
	/**
	 * Numero de port utilisee par defaut pour les connexions
	 */
	public static final int DEFAULT_PORT = 9876;
	private final DiffusionSalle diffusion;
	private final Horloge horloge;
	private final Salle salle;
	private Evenement evenement;
	private TourJoueur tour;


	/**
	 * Cree un nouveau serveur sur le port par defaut permettant de gerer une salle de jeu du Uno en ligne
	 * @param salle la salle de jeu a gerer
	 * @throws IOException si une erreur d'entree/sortie survient
	 * @see #DEFAULT_PORT
	 */
	public Serveur(Salle salle) throws IOException {
		this(DEFAULT_PORT, salle);
	}

	/**
	 * Cree un nouveau serveur permettant de gerer une salle de jeu du Uno en ligne
	 * @param port le port pour ce serveur
	 * @param salle la salle de jeu a gerer
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public Serveur(int port, Salle salle) throws IOException {
		super(port);
		this.salle = salle;
		diffusion = new DiffusionSalle(salle.getInfos());
		horloge = new Horloge();
		salle.getJeu().getListeJoueurs().addListeJoueursListener(this);
		salle.getJeu().addJeuListener(this);
	}

	/**
	 * @return l'horloge utilisee pour ce serveur
	 */
	public Horloge getHorloge() {
		return horloge;
	}

	/**
	 * @return la salle gerant le jeu du Uno
	 */
	public Salle getSalle() {
		return salle;
	}

	/**
	 * Envoie a tous les clients le temps restant avant le demarrage d'une partie
	 * @see #getTemps()
	 */
	public void envoyerTemps() {
		envoyerTous(new Paquet(TypePaquet.TEMPS, getTemps()));
	}

	/**
	 * @return le temps avant le demarrage d'une partie
	 */
	public int getTemps() {
		return Math.max(0, evenement.getTemps() - horloge.getTemps());
	}

	@Override
	public void deconnexion(AbstractClient client) {
		super.deconnexion(client);
		salle.getJeu().getListeJoueurs().retireJoueur(client.getID());
	}

	@Override
	public ClientServeur getClient(int id) {
		return super.getClient(id);
	}

	@Override
	public boolean fermer() {
		diffusion.fermer();
		return super.fermer() && horloge.terminer();
	}

	@Override
	public boolean lancer() {
		try {
			diffusion.lancer();
		} catch(Exception e) {
			e.printStackTrace();
		}
		horloge.addEvenementFutur(evenement = new Evenement(Configuration.TEMPS_PARTIE_REJOIGNABLE, this));
		return super.lancer() && horloge.lancer();
	}

	@Override
	public Paquet getPaquetConnexion() {
		return new Paquet(new InfoSalle(salle));
	}

	@Override
	public int getMaxClients() {
		return salle.getJeu().getListeJoueurs().getMaxJoueurs();
	}

	@Override
	public ClientServeur creerClient(Socket socket) throws IOException, ServeurFullException {
		if(salle.getJeu().getListeJoueurs().estPleine())
			throw new ServeurFullException();
		return new ClientServeur(this, socket);
	}

	@Override
	public void ajout(int id, Joueur joueur) {
		envoyerFiltre(new Paquet(TypePaquet.AJOUT_JOUEUR).addBytePositif(id).addBytePositif(joueur.getType().ordinal()).addShort(joueur.getNom()), 
				new FiltreEnvoiExclusionID(id));
		if(salle.getJeu().getListeJoueurs().estPleine())
			evenement.setTemps(0);
	}

	@Override
	public void retire(int id, Joueur joueur) {
		envoyerTous(new Paquet(TypePaquet.RETIRE_JOUEUR, id));
	}

	@Override
	public void evenement(Periodique p) {
		if(salle.getJeu().getListeJoueurs().aHumain()) {
			if(!salle.getJeu().getListeJoueurs().estPleine())
				salle.getJeu().getListeJoueurs().combler();
			diffusion.fermer();
			horloge.addEvenementFutur(evenement = new Evenement(Configuration.TEMPS_COMMENCER_PARTIE_SALLE_PLEINE,
					new EvenementNouvellePartie(salle.getJeu())));
			envoyerTemps();
		}
		else horloge.addEvenementFutur(evenement = new Evenement(Configuration.TEMPS_PARTIE_REJOIGNABLE, this));
	}

	@Override
	public void finPartie(Partie partie) {
		partie.removePartieListener(this);
	}

	@Override
	public void debutPartie(Partie partie) {
		envoyerTous(new Paquet(TypePaquet.COMMENCER, salle.getJeu().getPartie().getPlateau()));
	}

	@Override
	public void nouvellePartie(Partie partie) {
		partie.addPartieListener(this);
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {
		partie.removePartieListener(this);
	}

	@Override
	public void debutTour(int id, TourJoueur tour) {
		envoyerTous(new Paquet(TypePaquet.TOUR).addBytePositif(id));
		if(this.tour != null)
			this.tour.removeActionTourListener(this);
		this.tour = tour;
		tour.addActionTourListener(this);
	}

	@Override
	public void action(int idAction, ActionJoueur action) {
		envoyerTous(new Paquet(TypePaquet.ACTION, idAction));
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {
		fermer();
	}

	@Override
	public void passeTour(int id) {
		envoyerTous(new Paquet(TypePaquet.TOUR).addBytePositif(id));
	}

}
