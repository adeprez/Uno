package fr.utt.lo02.uno.ui.composant.chat;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.jeu.joueur.Joueur;


public class PanelMessages extends JEditorPane {
	private static final long serialVersionUID = 1L;
	private final List<String> messages;
	private final Joueur joueur;

	
	public PanelMessages(Joueur joueur) {
		super("text/html", "");
		this.joueur = joueur;
		messages = new ArrayList<String>();
		setEditable(false);
		setFont(Configuration.POLICE);
	}
	
	public void change() {
		String s = "<html>";
		for(final String msg : messages)
			s += msg + "<br />";
		setText(s + "</html>");
	}
	
	public void nouveauMessage(Joueur joueur, String msg) {
		String s = "<b><u><font color=\"" + (this.joueur == joueur ? "0000ff" : "#ff0000") + "\">" + joueur + " :</font></u></b> " + msg;
		messages.add(s);
		change();
	}

	public List<String> getMessages() {
		return messages;
	}
	
}
