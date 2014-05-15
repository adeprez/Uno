package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.AbstractClient;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.serveur.Serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Classe representant un client avec un flux d'entree et de sortie via un socket TCP, se trouvant cote client.
 * Il contient un identifiant permettant de le reconnaitre des deux parties, selon le design-pattern Client/Serveur. Son
 * identifiant a pour valeur -1 tant qu'il n'a pas obtenu d'identifiant.
 */
public class Client extends AbstractClient {
	private InfoSalle infos;


	/**
	 * Cree un nouveau client sur le port {@link Serveur#DEFAULT_PORT} avec un flux d'entree et de sortie via un socket TCP, 
	 * se trouvant cote client. Il contient un identifiant permettant de le reconnaitre des deux parties, 
	 * selon le design-pattern Client/Serveur. Son identifiant a pour valeur -1 tant qu'il n'a pas obtenu d'identifiant.
	 * @param adresse l'adresse a laquel se connecter, pour l'instanciation du socket
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public Client(InetAddress adresse) throws IOException {
		this(adresse, Serveur.DEFAULT_PORT);
	}

	/**
	 * Cree un nouveau client sur le port {@link Serveur#DEFAULT_PORT} avec un flux d'entree et de sortie via un socket TCP, 
	 * se trouvant cote client. Il contient un identifiant permettant de le reconnaitre des deux parties, 
	 * selon le design-pattern Client/Serveur. Son identifiant a pour valeur -1 tant qu'il n'a pas obtenu d'identifiant.
	 * @param adresse l'adresse a laquel se connecter, pour l'instanciation du socket
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public Client(String adresse) throws IOException {
		this(adresse, Serveur.DEFAULT_PORT);
	}

	/**
	 * Cree un nouveau client avec un flux d'entree et de sortie via un socket TCP, se trouvant cote client
	 * Il contient un identifiant permettant de le reconnaitre des deux parties, selon le design-pattern Client/Serveur. Son
	 * identifiant a pour valeur -1 tant qu'il n'a pas obtenu d'identifiant.
	 * @param adresse l'adresse a laquel se connecter, pour l'instanciation du socket
	 * @param port le numero du port sur lequel se connecter
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public Client(String adresse, int port) throws IOException {
		this(InetAddress.getByName(adresse), port);
	}

	/**
	 * Cree un nouveau client avec un flux d'entree et de sortie via un socket TCP, se trouvant cote client
	 * Il contient un identifiant permettant de le reconnaitre des deux parties, selon le design-pattern Client/Serveur. Son
	 * identifiant a pour valeur -1 tant qu'il n'a pas obtenu d'identifiant.
	 * @param adresse l'adresse a laquel se connecter, pour l'instanciation du socket
	 * @param port le numero du port sur lequel se connecter
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public Client(InetAddress adresse, int port) throws IOException {
		super(new Socket(adresse, port));
	}
	
	/**
	 * @return les informations sur la salle dans laquelle ce client est connecte, ou null s'il n'en a pas encore rejoint
	 */
	public InfoSalle getInfos() {
		return infos;
	}
	
	@Override
	protected void traite(TypePaquet type, IO io) {
		if(type == TypePaquet.INFO_SALLE)
			infos = new InfoSalle(io);
	}

}
