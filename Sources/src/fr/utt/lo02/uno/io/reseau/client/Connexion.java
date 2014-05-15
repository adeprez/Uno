package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.exception.AnnulationException;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;

import java.net.InetAddress;


/**
 * Classe representant une tentative de connexion TCP a une salle de jeu de Uno
 */
public class Connexion extends Thread {
	private static final int DELAI_ATTENTE = 500, PAS_ATTENTE = 10;
	private final InetAddress adresse;
	private final String nom;
	private Client client;
	private boolean fini;


	/**
	 * Cree une nouvelle tentative de connexion TCP a une salle de jeu de Uno
	 * @param adresse l'adresse de cette salle
	 * @param nom le nom de la salle
	 * @see #start()
	 */
	public Connexion(InetAddress adresse, String nom) {
		this.adresse = adresse;
		this.nom = nom;
	}

	/**
	 * Methode bloquante permettant d'obtenir le client resultant de cette connexion
	 * @return le client construit a partir de la connexion
	 * @throws AnnulationException si la connexion n'a pas pu etre etablie
	 */
	public Client getClient() throws AnnulationException {
		while(!fini)
			Outil.attendre(PAS_ATTENTE);
		if(client == null)
			throw new AnnulationException("Connexion au serveur impossible");
		return client;
	}

	@Override
	public void run() {
		try {
			client = new Client(adresse);
			client.lancer();
			client.write(new Paquet(TypePaquet.ID).addShort(nom));
			for(int i=0 ; i<DELAI_ATTENTE/PAS_ATTENTE && client.getID() == -1 ; i++)
				Outil.attendre(PAS_ATTENTE);
		} catch(Exception e) {}
		fini = true;
	}

}
