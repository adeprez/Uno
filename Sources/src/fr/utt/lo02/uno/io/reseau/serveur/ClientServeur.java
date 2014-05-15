package fr.utt.lo02.uno.io.reseau.serveur;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.jeu.exception.JeuException;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;

import java.io.IOException;
import java.net.Socket;


/**
 * Classe heritant de {@link AbstractClient} et representant un client du cote serveur 
 */
public class ClientServeur extends AbstractClient {
	private final Serveur serveur;
	private Joueur joueur;


	/**
	 * Cree une nouvelle representation un client cote serveur
	 * @param serveur le serveur responsable de ce client
	 * @param socket le socket utilise pour la connexion du client
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public ClientServeur(Serveur serveur, Socket socket) throws IOException {
		super(socket);
		this.serveur = serveur;
	}

	@Override
	protected void traite(TypePaquet type, IO io) {
		switch(type) {
		case ID:
			try {
				if(serveur.rejoindre(this)) {
					joueur = new Joueur(TypeJoueur.JOUEUR_RESEAU, io.nextShortString());
					serveur.getSalle().getJeu().getListeJoueurs().ajoutJoueur(joueur);
					write(new Paquet(TypePaquet.ID, getID()));
				}
			} catch(JeuException e) {
				e.printStackTrace();
			}
			break;
		case AJOUT_JOUEUR:
			int id = 0;
			for(final Joueur j : serveur.getSalle().getJeu().getListeJoueurs().getListeJoueurs()) {
				write(new Paquet(TypePaquet.AJOUT_JOUEUR).addBytePositif(id).addBytePositif(
						(j == joueur ? TypeJoueur.HUMAIN : j.getType()).ordinal()).addShort(j.getNom()));
				id++;
			}
			break;
		case TEMPS:
			write(new Paquet(TypePaquet.TEMPS, serveur.getTemps()));
			break;
		case ACTION:
			try {
				TourJoueur t = serveur.getSalle().getJeu().getPartie().getJoueurs().getJoueur().getTour();
				if(t != null)
					t.faireAction(io.nextPositif());
			} catch(Exception e) {
				e.printStackTrace();
			} 
			break;
		case MESSAGE:
			serveur.envoyerTous(io);
			break;
		default:
			break;
		}
	}

}
