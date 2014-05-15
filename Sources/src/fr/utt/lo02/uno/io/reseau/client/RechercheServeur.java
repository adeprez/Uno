package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.exception.AnnulationException;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.serveur.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Objet permettant d'obtenir les informations a propos d'une salle de jeu du Uno
 */
public class RechercheServeur extends AbstractClient {
	/**
	 * Temps maximum d'attente pour la reception des informations de salle
	 */
	public static final int TEMPS_LIMITE_RECEPTION = 500;
	private InfoSalle infos;


	/**
	 * Cree un nouvel objet permettant d'obtenir les informations a propos d'une salle de jeu du Uno
	 * @param adresse l'adresse sur laquelle recherche les informations de jeu du Uno
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public RechercheServeur(InetAddress adresse) throws IOException {
		super(creerSocket(adresse));
	}

	/**
	 * @return les informations relative a la salle
	 * @throws AnnulationException si le serveur n'a pas repondu a la demande d'informations dans le temps imparti
	 */
	public InfoSalle getInfoSalle() throws AnnulationException {
		if(infos == null)
			throw new AnnulationException("Ce serveur n'a pas transmis ses informations");
		return infos;
	}
	
	@Override
	protected void traite(TypePaquet type, IO in) {
		if(type == TypePaquet.INFO_SALLE) {
			infos = new InfoSalle(in);
			fermer();
		}
	}
	
	/**
	 * Cree un socket a partir de l'adresse, avec un temps de reception {@link #TEMPS_LIMITE_RECEPTION}
	 * @param adresse l'adresse du serveur
	 * @return le socket ainsi cree
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	private static Socket creerSocket(InetAddress adresse) throws IOException {
		Socket socket = new Socket();
		socket.setSoTimeout(TEMPS_LIMITE_RECEPTION);
		socket.connect(new InetSocketAddress(adresse, Serveur.DEFAULT_PORT), TEMPS_LIMITE_RECEPTION);
		return socket;
	}
	
}
