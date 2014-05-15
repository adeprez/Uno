package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.SocketUDP;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.listeners.RechercheSalleListener;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Classe reprentant une communication locale en multicast selon le protocole UDP
 */
public class RechercheLocale extends SocketUDP {


	/**
	 * Cree un nouvel objet gerant la communication locale en multicast selon le protocole UDP
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	public RechercheLocale() throws IOException {
		super();
	}

	/**
	 * Ajoute un ecouteur pour la detection de salles de jeu du Uno en ligne
	 * @param l un nouvel ecouteur de detection de salles de jeu du Uno en ligne
	 */
	public void addRechercheSalleListener(RechercheSalleListener l) {
		addListener(RechercheSalleListener.class, l);
	}

	/**
	 * Retire un ecouteur pour la detection de salles de jeu du Uno en ligne
	 * @param l l'ancien ecouteur de detection de salles de jeu du Uno en ligne
	 */
	public void removeRechercheSalleListener(RechercheSalleListener l) {
		removeListener(RechercheSalleListener.class, l);
	}

	/**
	 * Signale aux ecouteurs de la detection de salles de jeu du Uno en ligne qu'une nouvelle salle a ete decouverte
	 * @param infos les informations relatives a cette salle
	 * @param adresse l'adresse de cette salle
	 */
	public void notifyDecouverte(InfoSalle infos, InetAddress adresse) {
		for(final RechercheSalleListener l : getListeners(RechercheSalleListener.class))
			l.nouvelleSalle(adresse, infos);
	}
	
	/**
	 * Envoye une demande d'informations pour les salles presentes sur le reseau local
	 */
	public void demandeInfos() {
		try {
			envoyer(new Paquet(TypePaquet.DEMANDE_INFO_SALLE));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void recu(TypePaquet type, IO io) {
		if(type == TypePaquet.INFO_SALLE) try {
			InfoSalle infos = new InfoSalle(io);
			InetAddress adresse = InetAddress.getByName(io.nextShortString());
			for(final RechercheSalleListener l : getListeners(RechercheSalleListener.class))
				l.nouvelleSalle(adresse, infos);
		} catch(Exception e) {
			e.printStackTrace();
		}
		super.recu(type, io);
	}


}
