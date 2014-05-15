package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Generateur;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TypeJoueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.interfaces.ChangeTypeListener;


public class EmplacementJoueur extends PanelImage implements ActionListener, ChangeTypeListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton type;
	private final String recherche;
	private final JTextField nom;
	private Joueur joueur;
	private int etat;


	public EmplacementJoueur(boolean editable) {
		setLayout(new BorderLayout(10, 10));
		setMax(true);
		setImage(Images.getInstance().getImage("fond transparent.png"));

		recherche = Texte.get("Recherche d'un joueur");
		nom = new JTextField();
		nom.setOpaque(false);
		nom.setEditable(editable);
		nom.setFont(Configuration.POLICE);

		type = new Bouton();
		if(editable) {
			nom.addActionListener(this);
			type.addActionListener(this);
			nom.setToolTipText(Texte.get("Entree pour changer le nom du joueur"));
			type.setToolTipText(Texte.get("Cliquez pour changer"));
		}

		add(type, BorderLayout.WEST);
		add(nom, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.NORTH);
		add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.EAST);
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
		nom.setText(joueur.toString());
		changeType(joueur.getType());
		joueur.addChangeTypeListener(this);
	}

	public void setVide() {
		joueur = null;
		nom.setText(null);
		type.setIcon(null);
	}

	public void evenementAnimation() {
		if(joueur == null) {
			String s = recherche;
			for(int i=0 ; i<=(etat/5) % 5 ; i++)
				s += ".";
			nom.setText(s);
			type.setIcon(new ImageIcon(Images.getInstance().getImage("chargement/" + (etat % 8) + ".png")));
			etat++;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == type)
			joueur.setType(joueur.getType() == TypeJoueur.HUMAIN ? TypeJoueur.ORDINATEUR : TypeJoueur.HUMAIN);
		else if(e.getSource() == nom) {
			if(nom.getText() == null || nom.getText().trim().isEmpty() || nom.getText().equals(joueur.getNom()))
				nom.setText(Generateur.getInstance().getNom());
			joueur.setNom(nom.getText());
		}
	}

	@Override
	public void changeType(TypeJoueur typeJoueur) {
		type.setIcon(new ImageIcon(Images.getInstance().getImage(typeJoueur.getLienImage())));
		type.setText(typeJoueur.getNom());
	}

}
