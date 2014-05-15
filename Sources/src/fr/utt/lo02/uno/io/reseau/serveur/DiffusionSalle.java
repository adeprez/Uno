package fr.utt.lo02.uno.io.reseau.serveur;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.SocketUDP;
import fr.utt.lo02.uno.io.reseau.TypePaquet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe permettant de diffuser les informations d'une salle de jeu sur un reseau
 */
public class DiffusionSalle extends SocketUDP {
	private final InfoSalle infos;

	
	/**
	 * Cree un nouveau diffuseur pour la salle de jeu sur un reseau
	 * @param infos les informations a transmettre aux clients
	 * @throws IOException
	 */
	public DiffusionSalle(InfoSalle infos) throws IOException {
		this.infos = infos;
	}

	/**
	 * Envoie les informations relatives a cette salle
	 */
	public void envoyerInfos() {
		try {
			envoyer(new Paquet(TypePaquet.INFO_SALLE, infos).addShort(InetAddress.getLocalHost().getHostAddress()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void lancer() throws UnknownHostException, IOException {
		super.lancer();
		envoyerInfos();
	}

	@Override
	public void recu(TypePaquet type, IO io) {
		if(type == TypePaquet.DEMANDE_INFO_SALLE)
			envoyerInfos();
		super.recu(type, io);
	}
	
	
}
