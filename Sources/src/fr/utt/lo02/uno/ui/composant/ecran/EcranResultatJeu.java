package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.specialise.score.PanelScores;


public class EcranResultatJeu extends Ecran implements ActionListener {
	private static final long serialVersionUID = 1L;


	public EcranResultatJeu(Joueur joueur, Jeu jeu) {
		setMax(true);
		setName(Texte.get("Resultats"));
		setImage(Images.getInstance().getImage("fond.jpg"));
		setLayout(new BorderLayout());

		AbstractButton btn = new Bouton(Texte.get("Accueil"));
		btn.addActionListener(this);

		JLabel gagnant = new JLabel(jeu.getGagnant(), new ImageIcon(Images.getInstance().getImage("icone_64.png")), SwingConstants.CENTER);
		gagnant.setForeground(Color.YELLOW);
		gagnant.setFont(Configuration.POLICE.deriveFont(25f));

		add(gagnant, BorderLayout.CENTER);
		add(new PanelScores(joueur, jeu), BorderLayout.NORTH);
		add(btn, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		changeEcran(new EcranAccueil());
	}

}
