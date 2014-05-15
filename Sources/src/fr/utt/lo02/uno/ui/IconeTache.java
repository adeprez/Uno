package fr.utt.lo02.uno.ui;

import java.awt.AWTException;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.langue.Langue;
import fr.utt.lo02.uno.langue.LangueListener;
import fr.utt.lo02.uno.langue.Texte;

public class IconeTache implements ActionListener, LangueListener {
	private static IconeTache instance;
	private final TrayIcon icone;
	private final PopupMenu menu;
	private String[] options;


	public static IconeTache getInstance() {
		synchronized(IconeTache.class) {
			if(instance == null)
				instance = new IconeTache();
			return instance;
		}
	}

	private IconeTache() {
		menu = new PopupMenu();
		menu.addActionListener(this);
		icone = new TrayIcon(Images.getInstance().getImage("icone_16.png"), "Uno UTT Edition");
		icone.setPopupMenu(menu);
		if(SystemTray.isSupported()) try {
			SystemTray.getSystemTray().add(icone);
		} catch(AWTException e) {
			e.printStackTrace();
		}
		Texte.getInstance().addLangueListener(this);
		changeLangue(Texte.getInstance().getLangue());
	}
	
	public void message(String message) {
		message(null, message, MessageType.NONE);
	}

	public void message(String legende, String message, MessageType type) {
		icone.displayMessage(legende, message, type);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i=0 ; i<options.length ; i++)
			if(options[i].equals(e.getActionCommand())) {
				action(i);
				break;
			}
	}

	@Override
	public void changeLangue(Langue langue) {
		menu.removeAll();
		options = new String[] {Texte.get("Informations"), Texte.get("Langue") + " (" + langue.getNom() + ")", Texte.get("Quitter")};
		boolean premier = true;
		for(final String s : options) {
			if(premier)
				premier = false;
			else menu.addSeparator();
			menu.add(s);
		}
	}

	public static void action(int action) {
		switch(action) {
		case 0:
			String msg = "";
			for(final String s : Configuration.MESSAGES)
				msg += s + "\n";
			JOptionPane.showMessageDialog(null, msg, Texte.get("Informations"), 
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Images.getInstance().getImage("icone_64.png")));
			break;

		case 1:
			Texte.getInstance().proposeChangeLangue();
			break;

		case 2:
			if(JOptionPane.showConfirmDialog(null, Texte.get("Voulez-vous vraiment quitter ?")) == JOptionPane.OK_OPTION)
				System.exit(0);
			break;
		}
	}

}
