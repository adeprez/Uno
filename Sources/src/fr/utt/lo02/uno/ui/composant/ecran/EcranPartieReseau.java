package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.Container;

import javax.swing.JInternalFrame;

import fr.utt.lo02.uno.io.reseau.client.ControleurPartieEnLigne;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.chat.BoutonChat;
import fr.utt.lo02.uno.ui.composant.chat.Chat;


public class EcranPartieReseau extends EcranPartie {
	private static final long serialVersionUID = 1L;
	private final JInternalFrame fenetre;
	private final BoutonChat affiche;
	private final SalleReseau salle;
	private final Chat chat;

	
	public EcranPartieReseau(Joueur joueur, SalleReseau salle) {
		super(new ControleurPartieEnLigne(salle.getClient(), joueur), joueur, salle);
		this.salle = salle;
		chat = new Chat(salle.getJeu().getListeJoueurs().getID(joueur), joueur, salle);
		fenetre = new JInternalFrame(Texte.get("Chat"), true, true, true);
		affiche = new BoutonChat(fenetre);
		
		fenetre.setContentPane(chat);
		chat.addMessageChatListener(affiche);
		
		add(affiche, 1);
		add(fenetre, 0);
	}
	
	@Override
	public void layoutContainer(Container parent) {
		super.layoutContainer(parent);
		affiche.setBounds(getWidth() - 70, getHeight() - 70, 60, 60);
	}
	
	@Override
	public boolean fermer() {
		return chat.fermer();
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {
		super.finPartie(partie, resultats);
		if(salle.getJeu().estJeuFini())
			salle.getClient().fermer();
	}

}
