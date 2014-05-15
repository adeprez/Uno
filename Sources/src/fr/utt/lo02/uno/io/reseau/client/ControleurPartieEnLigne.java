package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.jeu.Controleur;
import fr.utt.lo02.uno.jeu.joueur.Joueur;

/**
 * {@link Controleur} pour une partie en ligne. Celui-ci transmet l'action du joueur au serveur.
 */
public class ControleurPartieEnLigne extends Controleur {
	private final Client client;

	
	/**
	 * Cree un nouveau {@link Controleur} pour une partie en ligne. Celui-ci transmet l'action du joueur au serveur
	 * @param client le client issu de la connexion au serveur
	 * @param joueur le joueur dont les actions sont controlees
	 */
	public ControleurPartieEnLigne(Client client, Joueur joueur) {
		super(joueur);
		this.client = client;
	}

	@Override
	public void faireAction(int idAction) {
		client.write(new Paquet(TypePaquet.ACTION, idAction));
	}

}
