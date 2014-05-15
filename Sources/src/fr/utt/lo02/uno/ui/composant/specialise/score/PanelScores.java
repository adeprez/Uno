package fr.utt.lo02.uno.ui.composant.specialise.score;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.variantes.JeuEquipes;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.layout.LayoutLignes;


public class PanelScores extends PanelImage {
	private static final long serialVersionUID = 1L;


	public PanelScores(Joueur joueur, Jeu jeu) {
		setImage(Images.getInstance().getImage("tapis.jpg"));
		setMax(true);
		setLayout(new GridLayout());
		switch(jeu.getType()) {
		case CHALLENGE:
		case DUEL:
		case CLASSIQUE:
			for(final Entry<Joueur, Integer> e : ResultatPartie.getPoints(jeu.getResultats()).entrySet()) {
				PanelImage p = new PanelImage(new LayoutLignes());
				Joueur j = e.getKey();
				if(j == joueur)
					p.setImage(Images.getInstance().getImage("survol.png"));
				JLabel l1 = new JLabel(j.getNom(), new ImageIcon(Images.getInstance().getImage(j.getType().getLienImage())), SwingConstants.CENTER);
				l1.setFont(Configuration.POLICE);
				JLabel l2 = new JLabel(e.getValue() + " " + Texte.get("points"), SwingConstants.CENTER);
				l2.setFont(Configuration.POLICE.deriveFont(25f));
				l2.setForeground(Color.YELLOW);
				p.add(l1);
				p.add(l2);
				add(p);
			}
			break;
		case EQUIPES:
			JeuEquipes j = (JeuEquipes) jeu;
			for(int i=0 ; i<JeuEquipes.NOMBRE_EQUIPES ; i++) {
				PanelImage p = new PanelImage(new LayoutLignes());
				if(i == j.getEquipe(joueur))
					p.setImage(Images.getInstance().getImage("survol.png"));
				JLabel l1 = new JLabel(Texte.get("Equipe") + " " + (i + 1), new ImageIcon(
						Images.getInstance().getImage("hors ligne.png")), SwingConstants.CENTER);
				l1.setFont(Configuration.POLICE);
				JLabel l2 = new JLabel(j.getPoints(i) + " " + Texte.get("points"), SwingConstants.CENTER);
				l2.setFont(Configuration.POLICE.deriveFont(25f));
				l2.setForeground(Color.YELLOW);
				p.add(l1);
				p.add(l2);
				add(p);
			}
			break;
		}
	}

}
