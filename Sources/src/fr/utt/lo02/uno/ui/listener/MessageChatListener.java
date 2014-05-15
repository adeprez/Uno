package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;
import java.util.List;

import fr.utt.lo02.uno.jeu.joueur.Joueur;

public interface MessageChatListener extends EventListener {
	public void nouveauMessage(Joueur joueur, String message, List<String> messages);
}
