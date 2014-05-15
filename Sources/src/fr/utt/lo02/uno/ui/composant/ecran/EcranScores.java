package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.jeu.Controleur;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.temps.Horloge;
import fr.utt.lo02.uno.temps.HorlogeListener;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.composant.specialise.score.PanelScorePartie;
import fr.utt.lo02.uno.ui.composant.specialise.score.PanelScores;
import fr.utt.lo02.uno.ui.layout.LayoutLignes;


public class EcranScores extends Ecran implements JeuListener, HorlogeListener {
	private static final long serialVersionUID = 1L;
	private final Controleur controleur;
	private final JProgressBar restant;
	private final Joueur joueur;
	private final JPanel centre;
	private final Salle salle;
	
	
	public EcranScores(Joueur joueur, Controleur controleur, Salle salle) {
		this.salle = salle;
		this.joueur = joueur;
		this.controleur = controleur;
		salle.getJeu().addJeuListener(this);
		setImage(Images.getInstance().getImage("fond.jpg"));
		setLayout(new BorderLayout());
		setName(Texte.get("Scores"));
		setMax(true);
		setLayout(new BorderLayout());
		
		restant = new JProgressBar(0, Configuration.TEMPS_AFFICHAGE_RESULTATS);
		centre = new PanelImage();
		centre.setLayout(new LayoutLignes());
		JScrollPane jsp = new JScrollPane(centre);
		
		restant.setStringPainted(true);
		restant.setFont(Configuration.POLICE);
		restant.setValue(Configuration.TEMPS_AFFICHAGE_RESULTATS);
		
		salle.getJeu().getPartie().getHorloge().addHorlogeListener(this);
		jsp.getViewport().setOpaque(false);
		jsp.setBorder(null);
		
		centre.add(new PanelScores(joueur, salle.getJeu()));
		centre.add(Box.createRigidArea(new Dimension(50, 50)));
		
		List<ResultatPartie> resultats = salle.getJeu().getResultats();
		for(int i=0 ; i<resultats.size() ; i++)
			ajoutResultat(resultats.get(i), i + 1);
		
		add(jsp, BorderLayout.CENTER);
		add(restant, BorderLayout.NORTH);
	}
	
	public void ajoutResultat(ResultatPartie resultat, int numero) {
		centre.add(new PanelScorePartie(joueur, resultat, numero));
		centre.add(Box.createRigidArea(new Dimension(25, 25)));
	}
	
	@Override
	public void nouvellePartie(Partie partie) {
		salle.getJeu().removeJeuListener(this);
		if(salle instanceof SalleReseau)
			changeEcran(new EcranPartieReseau(joueur, (SalleReseau) salle));
		else changeEcran(new EcranPartie(controleur, joueur, salle));
	}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {}

	@Override
	public void action(Horloge horloge) {
		restant.setValue(restant.getValue() - 1);
		String s = Texte.get("Une nouvelle partie commence dans") + " " + restant.getValue() + " " + Texte.get("secondes");
		if(restant.getValue() <= 0) {
			horloge.removeHorlogeListener(this);
			s = Texte.get("La partie va commencer") + "...";
		}
		restant.setString(s);
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {}
	
}
