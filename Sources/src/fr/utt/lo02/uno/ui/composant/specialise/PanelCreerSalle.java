package fr.utt.lo02.uno.ui.composant.specialise;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Generateur;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.exception.InvalideException;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.io.reseau.serveur.Serveur;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.jeu.variantes.TypeJeu;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.ecran.Ecran;
import fr.utt.lo02.uno.ui.composant.ecran.EcranAccueil;
import fr.utt.lo02.uno.ui.composant.ecran.EcranSalleEnLigne;
import fr.utt.lo02.uno.ui.composant.ecran.EcranSalleHorsLigne;
import fr.utt.lo02.uno.ui.interfaces.EcranChangeable;



public class PanelCreerSalle extends Ecran implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton valider, retour;
	private final JTextField nom, nomPartie;
	private final EcranChangeable ecran;
	private final boolean enLigne;
	private final JSlider nombre;
	private final JLabel lnombre;
	private final JComboBox type;


	public PanelCreerSalle(boolean enLigne, EcranChangeable ecran) {
		this.enLigne = enLigne;
		this.ecran = ecran;
		setMax(true);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints g = new GridBagConstraints();
		g.insets.bottom = 10;
		g.insets.right = 25;
		setLayout(layout);

		nom = new JTextField(Generateur.getInstance().getNomPrincipal());
		nomPartie = new JTextField("Nouvelle partie");
		valider = new Bouton(Texte.get("Creer la salle"), new ImageIcon(
				Images.getInstance().getImage("ok.png").getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
		retour = new Bouton(Texte.get("Accueil"), new ImageIcon(
				Images.getInstance().getImage("retour.png").getScaledInstance(16, 16, Image.SCALE_AREA_AVERAGING)));
		nombre = new JSlider(2, Configuration.MAX_JOUEUR, 4);
		type = new JComboBox(TypeJeu.getNoms());

		JLabel l1 = new JLabel(Texte.get("Votre nom") + " : "), l2 = new JLabel(Texte.get("Nom de la salle") + " : "), 
				l3 = new JLabel(Texte.get("Mode de jeu") + " : ");
		lnombre = new JLabel();
		Component box = Box.createRigidArea(new Dimension(1, 15));

		layout.setConstraints(retour, g);
		g.gridy += 2;
		layout.setConstraints(l1, g);
		layout.setConstraints(nom, g);
		g.gridy ++;
		layout.setConstraints(l2, g);
		layout.setConstraints(nomPartie, g);
		g.gridy++;
		layout.setConstraints(lnombre, g);
		layout.setConstraints(nombre, g);
		g.gridy ++;
		layout.setConstraints(l3, g);
		layout.setConstraints(type, g);
		g.gridy ++;
		layout.setConstraints(box, g);
		g.gridy ++;
		layout.setConstraints(valider, g);

		Dimension d = new Dimension(200, 30);
		nom.setPreferredSize(d);
		nomPartie.setPreferredSize(d);
		nombre.setPreferredSize(new Dimension(180, 30));

		nom.setFont(Configuration.POLICE);
		nomPartie.setFont(Configuration.POLICE);
		l1.setFont(Configuration.POLICE);
		l2.setFont(Configuration.POLICE);
		l3.setFont(Configuration.POLICE);
		type.setFont(Configuration.POLICE);
		lnombre.setFont(Configuration.POLICE);
		l1.setForeground(Color.YELLOW);
		l2.setForeground(Color.YELLOW);
		l3.setForeground(Color.YELLOW);
		lnombre.setForeground(Color.YELLOW);

		add(retour);
		add(l1);
		add(nom);
		add(l2);
		add(nomPartie);
		add(lnombre);
		add(nombre);
		add(l3);
		add(type);
		add(box);
		add(valider);

		nombre.addChangeListener(this);
		nom.addActionListener(this);
		nomPartie.addActionListener(this);
		valider.addActionListener(this);
		retour.addActionListener(this);
		type.addActionListener(this);

		setTexteNombre();
	}

	public void setTexteNombre() {
		lnombre.setText(Texte.get("Nombre de joueurs") + " (" + nombre.getValue() + ")");
	}

	public void verifieChamps() throws InvalideException {
		if(nom.getText() == null || nom.getText().isEmpty())
			throw new InvalideException(Texte.get("Vous devez entrer votre nom"));
		if(nomPartie.getText() == null || nomPartie.getText().isEmpty())
			throw new InvalideException(Texte.get("Vous devez entrer un nom pour cette salle"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Generateur.getInstance().setNomPrincipal(nom.getText());
		if(e.getSource() == retour)
			ecran.changeEcran(new EcranAccueil());
		else if(e.getSource() == type) {
			boolean duel = type.getSelectedIndex() == TypeJeu.DUEL.ordinal();
			if(duel)
				nombre.setValue(2);
			nombre.setEnabled(!duel);
				
		} else try {
			verifieChamps();
			Salle salle = new Salle(TypeJeu.values()[type.getSelectedIndex()], nombre.getValue(), nomPartie.getText());
			if(enLigne) {
				new Serveur(salle).lancer();
				ecran.changeEcran(new EcranSalleEnLigne(new SalleReseau(nom.getText())));
			}
			else {
				salle.getJeu().getListeJoueurs().ajoutJoueur(new Joueur(TypeJoueur.HUMAIN, nom.getText()));
				ecran.changeEcran(new EcranSalleHorsLigne(salle));
			}
		} catch(Exception err) {
			JOptionPane.showMessageDialog(this, Texte.get("Impossible de creer la salle") + " :\n" + err.getMessage());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		setTexteNombre();
	}


}
