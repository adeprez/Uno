package fr.utt.lo02.uno.ui.composant.specialise.score;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.layout.LayoutLignes;


public class PanelScorePartie extends PanelImage {
	private static final long serialVersionUID = 1L;

	
	public PanelScorePartie(Joueur joueur, ResultatPartie resultat, int numero) {
		setImage(Images.getInstance().getImage("tapis.jpg"));
		setMax(true);
		setLayout(new LayoutLignes());
		JLabel titre = new JLabel(Texte.get("Partie") + " " + numero, SwingConstants.CENTER);
		titre.setForeground(Color.WHITE);
		titre.setFont(Configuration.POLICE.deriveFont(25f));
		add(titre);
		for(final Joueur j : resultat.getJoueurs())
			add(new PanelResultatJoueur(joueur == j, j, resultat.getCartes(j)));
	}
	
}
