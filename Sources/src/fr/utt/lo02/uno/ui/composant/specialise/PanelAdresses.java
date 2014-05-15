package fr.utt.lo02.uno.ui.composant.specialise;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Fichiers;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.PanelImage;


public class PanelAdresses extends PanelImage implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final List<String> adresses;
	private final JTextField ajout;
	private final JList liste;


	public PanelAdresses() {
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(Texte.get("Adresses connues")));

		adresses = Fichiers.getInstance().lireLignesFichier("adresses.txt");

		String txtIp;
		try {
			txtIp = Texte.get("mon IP") + " : " + InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException e) {
			txtIp = "(" + Texte.get("Hors ligne") + ")";
		}
		JLabel ip = new JLabel(txtIp);
		ip.setFont(Configuration.POLICE);
		ip.setForeground(Color.ORANGE);
		ip.setToolTipText(Texte.get("Donnez cette adresse a vos amis afin qu'ils puissent rejoindre vos parties"));
		ajout = new JTextField();
		ajout.setToolTipText(Texte.get("Ajouter une adresse"));
		ajout.setFont(Configuration.POLICE);
		ajout.setPreferredSize(new Dimension(150, 40));
		ajout.addActionListener(this);
		ModeleAdresses mo = new ModeleAdresses();
		liste = new JList(mo);
		liste.setFont(Configuration.POLICE);
		liste.setForeground(new Color(50, 150, 0));
		liste.addKeyListener(mo);
		JScrollPane jsp = new JScrollPane(liste);
		jsp.setPreferredSize(new Dimension());

		add(ip, BorderLayout.SOUTH);
		add(jsp, BorderLayout.CENTER);
		add(ajout, BorderLayout.NORTH);
	}

	public List<String> getAdresses() {
		return adresses;
	}
	
	public void addListener(ListDataListener l) {
		listenerList.add(ListDataListener.class, l);
	}

	public void removeListener(ListDataListener l) {
		listenerList.remove(ListDataListener.class, l);
	}
	
	public void change() {
		ListDataEvent evt = new ListDataEvent(ajout, ListDataEvent.INTERVAL_ADDED, 0, adresses.size());
		for(final ListDataListener l : getListeners(ListDataListener.class))
			l.contentsChanged(evt);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String txt = ajout.getText();
		if(txt != null && !txt.trim().isEmpty()) {
			adresses.add(txt);
			ajout.setText("");
			change();
		}
	}


	private class ModeleAdresses extends KeyAdapter implements ListModel {

		
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DELETE && liste.getSelectedIndex() >= 0 && liste.getSelectedIndex() < getSize()) {
				adresses.remove(liste.getSelectedIndex());
				change();
			}
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			addListener(l);
		}

		@Override
		public String getElementAt(int index) {
			return adresses.get(index);
		}

		@Override
		public int getSize() {
			return adresses.size();
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			removeListener(l);
		}

	}

}
