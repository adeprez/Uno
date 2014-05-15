package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JPanel;

import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.action.generateur.ControleurPartieHorsLigne;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.Fenetre;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.EcranTabParties;


public class EcranSalleHorsLigne extends EcranSalle implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton retour, lancer;
	private final EcranTabParties parties;
	private EcranPartie ecran;
	private int nombre;


	public EcranSalleHorsLigne(Salle salle) {
		super(salle, true);
		retour = new Bouton(Texte.get("Retour"));
		lancer = new Bouton(Texte.get("Lancer"));
		parties = new EcranTabParties(salle);
		retour.addActionListener(this);
		lancer.addActionListener(this);
		JPanel bas = new JPanel();
		bas.setOpaque(false);
		bas.add(retour);
		bas.add(lancer);
		add(bas, BorderLayout.SOUTH);
		salle.getJeu().getListeJoueurs().combler();
	}
	
	@Override
	public void ouvreEcran(Joueur j) {
		EcranPartie e = getEcranJeu(j, getSalle());
		parties.ajout(j, e);
		if(ecran == null)
			ecran = e;
		nombre ++;
	}
	
	@Override
	public void nouvellePartie(Partie partie) {
		super.nouvellePartie(partie);
		if(nombre > 1)
			new Fenetre(parties);
		else {
			new Fenetre(ecran);
			parties.fermer();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == retour)
			changeEcran(new EcranAccueil());
		else if(e.getSource() == lancer)
			getSalle().getJeu().nouvellePartie();
	}

	@Override
	public EcranPartie getEcranJeu(Joueur joueur, Salle salle) {
		return new EcranPartie(new ControleurPartieHorsLigne(joueur), joueur, salle);
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {}

}
