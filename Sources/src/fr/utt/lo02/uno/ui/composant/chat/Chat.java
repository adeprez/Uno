package fr.utt.lo02.uno.ui.composant.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.ecran.Ecran;
import fr.utt.lo02.uno.ui.listener.MessageChatListener;


public class Chat extends Ecran implements ReceiveListener, ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private final SalleReseau salle;
	private final PanelMessages msg;
	private final JSlider taille;
	private final JTextField txt;
	private final JLabel txttail;
	private final int id;


	public Chat(int id, Joueur joueur, SalleReseau salle) {
		this.id = id;
		this.salle = salle;
		setMax(true);
		setLayout(new BorderLayout());

		msg = new PanelMessages(joueur);
		txt = new JTextField();
		txttail = new JLabel();
		taille = new JSlider(SwingConstants.VERTICAL, 5, 30, 12);

		txt.addActionListener(this);
		taille.addChangeListener(this);
		txt.setFont(Configuration.POLICE);
		stateChanged(null);

		add(txttail, BorderLayout.NORTH);
		add(taille, BorderLayout.WEST);
		add(new JScrollPane(msg), BorderLayout.CENTER);
		add(txt, BorderLayout.SOUTH);

		salle.getClient().addReceiveListener(this);
	}

	public void addMessageChatListener(MessageChatListener l) {
		listenerList.add(MessageChatListener.class, l);
	}

	public void removeMessageChatListener(MessageChatListener l) {
		listenerList.remove(MessageChatListener.class, l);
	}

	@Override
	public boolean fermer() {
		salle.getClient().removeReceiveListener(this);
		return true;
	}

	@Override
	public void recu(TypePaquet type, IO io) {
		if(type == TypePaquet.MESSAGE) {
			Joueur j = salle.getJeu().getListeJoueurs().getJoueur(io.nextPositif());
			String message = io.nextString();
			msg.nouveauMessage(j, message);
			for(final MessageChatListener l : getListeners(MessageChatListener.class))
				l.nouveauMessage(j, message, msg.getMessages());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(txt.getText().length() >= IO.LIMITE_SHORT_MAX)
			JOptionPane.showMessageDialog(null, Texte.get("Vous ne pouvez pas ecrire plus que") 
					+ " " + IO.LIMITE_SHORT_MAX + " " + Texte.get("caracteres"));
		else {
			salle.getClient().write(new Paquet(TypePaquet.MESSAGE).addBytePositif(id).add(txt.getText()));
			txt.setText("");
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		msg.setFont(Configuration.POLICE.deriveFont((float) taille.getValue()));
		txttail.setText("" + taille.getValue());
	}

}
