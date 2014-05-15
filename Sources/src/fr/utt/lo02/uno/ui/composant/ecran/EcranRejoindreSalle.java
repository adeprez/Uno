package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Generateur;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.reseau.InfoSalle;
import fr.utt.lo02.uno.io.reseau.client.RechercheLocale;
import fr.utt.lo02.uno.io.reseau.client.RechercheServeurs;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.io.reseau.listeners.RechercheSalleListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.IconeTache;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.composant.specialise.ModeleSalles;
import fr.utt.lo02.uno.ui.composant.specialise.PanelAdresses;
import fr.utt.lo02.uno.ui.layout.LayoutCentre;


public class EcranRejoindreSalle extends Ecran implements RechercheSalleListener, ActionListener, KeyListener, ListDataListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton rejoindre, actualise, creer, retour, changeNom;
	private final PanelAdresses adresses;
	private final ModeleSalles modele;
	private final JScrollPane centre;
	private final JTextField nom;
	private final PanelImage bas;
	private final JList salles;
	private RechercheServeurs recherche;
	private RechercheLocale local;


	public EcranRejoindreSalle() {
		setLayout(new LayoutCentre(500, 500));
		setName(Texte.get("Rejoindre une salle"));
		setImage(Images.getInstance().getImage("fond.jpg"));
		setMax(true);

		try {
			local = new RechercheLocale();
		} catch(IOException e) {
			e.printStackTrace();
		}

		PanelImage p = new PanelImage(new BorderLayout(5, 5));
		adresses = new PanelAdresses();
		nom = new JTextField(Generateur.getInstance().getNomPrincipal());
		nom.setFont(Configuration.POLICE);
		changeNom = new Bouton(new ImageIcon(Images.getInstance().getImage("random.png")));
		PanelImage haut = new PanelImage(new BorderLayout(5, 5));
		haut.setMax(true);
		haut.setImage(Images.getInstance().getImage("fond transparent.png"));
		JLabel txt = new JLabel("   " + Texte.get("Votre nom") + " : ", SwingConstants.CENTER);
		txt.setForeground(Color.WHITE);
		txt.setFont(Configuration.POLICE);
		haut.add(txt, BorderLayout.WEST);
		haut.add(changeNom, BorderLayout.EAST);
		haut.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.NORTH);
		haut.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.SOUTH);
		haut.add(nom, BorderLayout.CENTER);
		modele = new ModeleSalles();
		salles = new JList(modele);
		salles.setBackground(new Color(150, 175, 255));
		salles.addListSelectionListener(this);
		salles.setFont(Configuration.POLICE);
		centre = new JScrollPane(salles);
		centre.setBorder(null);
		centre.getViewport().setOpaque(false);

		bas = new PanelImage(new FlowLayout());
		bas.add(retour = new Bouton(Texte.get("Retour"), 
				new ImageIcon(Images.getInstance().getImage("retour.png").getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING))));
		bas.add(rejoindre = new Bouton(Texte.get("Rejoindre")),
				new ImageIcon(Images.getInstance().getImage("ok.png").getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
		bas.add(actualise = new Bouton(Texte.get("Rafraichir"),
				new ImageIcon(Images.getInstance().getImage("online.png").getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING))));
		bas.add(creer = new Bouton(Texte.get("Creer une salle")));

		retour.addActionListener(this);
		rejoindre.addActionListener(this);
		actualise.addActionListener(this);
		creer.addActionListener(this);
		changeNom.addActionListener(this);
		adresses.addListener(this);
		rejoindre.setEnabled(false);

		p.add(haut, BorderLayout.NORTH);
		p.add(centre, BorderLayout.CENTER);
		p.add(bas, BorderLayout.SOUTH);
		p.add(adresses, BorderLayout.WEST);
		add(p);

		nom.addKeyListener(this);

		salles.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() > 1)
					connecte();
			}
		});

		if(local != null) try {
			local.lancer();
			local.addRechercheSalleListener(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		recherche();
	}

	public void recherche() {
		rejoindre.setEnabled(false);
		if(recherche != null) {
			recherche.removeRechercheSalleListener(this);
			recherche.terminer();
		}
		modele.clear();
		recherche = new RechercheServeurs(adresses.getAdresses());
		recherche.addRechercheSalleListener(this);
		local.demandeInfos();
		recherche.lancer();
	}

	public void connecte() {
		try {
			changeEcran(new EcranSalleEnLigne(new SalleReseau(nom.getText(), ((InfoSalle) salles.getSelectedValue()).getAdresse())));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(this, Texte.get("Cette partie n'est plus disponible"));
			recherche();
		}
	}

	public void setAffiche(boolean affiche) {
		centre.setVisible(affiche);
		rejoindre.setVisible(affiche);
		actualise.setVisible(affiche);
		adresses.setVisible(affiche);
		validate();
		repaint();
	}

	public void changeNom() {
		setAffiche(nom.getText() != null && !nom.getText().isEmpty());
	}

	@Override
	public boolean fermer() {
		if(local != null) {
			local.removeRechercheSalleListener(this);
			local.fermer();
		}
		if(recherche != null) {
			recherche.removeRechercheSalleListener(this);
			recherche.terminer();
		}
		return super.fermer();
	}

	@Override
	public void nouvelleSalle(InetAddress adresse, InfoSalle infos) {
		infos.setAdresse(adresse);
		modele.add(infos);
		if(!estAffiche())
			IconeTache.getInstance().message(Texte.get("Salle de jeu trouvée"), 
					infos.getNom() + " (" + infos.getNombreJoueursMax() + " " + Texte.get("joueurs") + ") : " + adresse, MessageType.NONE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == retour)
			changeEcran(new EcranAccueil());
		else if(e.getSource() == actualise)
			recherche();
		else if(e.getSource() == rejoindre)
			connecte();
		else if(e.getSource() == creer)
			changeEcran(new EcranCreerSalle(true));
		else if(e.getSource() == changeNom) {
			changeNom();
			nom.setText(Generateur.getInstance().setNomPrincipal(Generateur.getInstance().getNom()));
		}
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		recherche();
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		Generateur.getInstance().setNomPrincipal(nom.getText());
		changeNom();
	}

	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void intervalAdded(ListDataEvent e) {}

	@Override
	public void intervalRemoved(ListDataEvent e) {}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		rejoindre.setEnabled(salles.getSelectedIndex() != -1);
	}

}
