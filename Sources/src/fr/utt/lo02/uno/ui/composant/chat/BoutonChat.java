package fr.utt.lo02.uno.ui.composant.chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.Sons;
import fr.utt.lo02.uno.io.interfaces.Fermable;
import fr.utt.lo02.uno.io.interfaces.Lancable;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.IconeTache;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.listener.MessageChatListener;


public class BoutonChat extends Bouton implements MessageChatListener, Fermable, Lancable, Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	private int nombre, difference;
	private final Component actif;
	private boolean run;

	
	public BoutonChat(Component actif) {
		super(new ImageIcon(Images.getInstance().getImage("chat.png")));
		this.actif = actif;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		actif.setBounds(getParent().getWidth() - 250, getParent().getHeight()/2 - 100, 250, 200);
		actif.setVisible(!actif.isVisible());
		if(actif.isVisible())
			fermer();
	}

	@Override
	public void nouveauMessage(Joueur joueur, String message, List<String> messages) {
		if(!actif.isVisible()) {
			IconeTache.getInstance().message(joueur.getNom() + " " + Texte.get("dit") + " : ", message, MessageType.NONE);
			Sons.getInstance().jouer("kling.wav");
			difference = messages.size() - nombre;
			lancer();
			repaint();
		} else {
			nombre = messages.size();
		}
	}

	@Override
	public boolean fermer() {
		if(!run)
			return false;
		actif.setVisible(true);
		difference = 0;
		run = false;
		return true;
	}

	@Override
	public boolean lancer() {
		if(run)
			return false;
		run = true;
		new Thread(this).start();
		return true;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!actif.isVisible() && difference != 0) {
			String txt = difference + "";
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(Color.YELLOW);
			g.fillRoundRect(getWidth() - 20, 0, 20, 20, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString(txt, getWidth() - g.getFontMetrics().stringWidth(txt)/2 - 10, 15);
		}
	}

	@Override
	public void run() {
		while(run) {
			setSurvol(!estSurvol());
			Outil.attendre(500);
		}
	}

}
