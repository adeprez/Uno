package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import java.awt.BorderLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.jeu.joueur.TourJoueur;
import fr.utt.lo02.uno.jeu.listener.JeuListener;
import fr.utt.lo02.uno.jeu.listener.PartieListener;
import fr.utt.lo02.uno.ui.composant.ecran.Ecran;
import fr.utt.lo02.uno.ui.composant.ecran.EcranPartie;

public class EcranTabParties extends Ecran implements PartieListener, JeuListener {
	private static final long serialVersionUID = 1L;
	private final List<Integer> ids;
	private final JTabbedPane tab;
	private final Salle salle;


	public EcranTabParties(Salle salle) {
		this.salle = salle;
		setLayout(new BorderLayout());
		ids = new ArrayList<Integer>();
		tab = new JTabbedPane(SwingConstants.BOTTOM);
		tab.setFont(Configuration.POLICE);
		add(tab, BorderLayout.CENTER);
		setName(salle.getNom());
		salle.getJeu().addJeuListener(this);
	}

	public void ajout(Joueur j, EcranPartie ecranJeu) {
		ids.add(salle.getJeu().getListeJoueurs().getID(j));
		tab.addTab(j.getNom(), new ImageIcon(Images.getInstance().getImage(
				j.getType().getLienImage()).getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)), ecranJeu);
	}

	@Override
	public boolean fermer() {
		salle.getJeu().getPartie().removePartieListener(this);
		salle.getJeu().removeJeuListener(this);
		return super.fermer();
	}

	@Override
	public void debutTour(int id, TourJoueur tour) {
		int index = ids.indexOf(id);
		if(index != -1)
			tab.setSelectedIndex(index);
	}

	@Override
	public void nouvellePartie(Partie partie) {
		partie.addPartieListener(this);
	}

	@Override
	public void debutPartie(Partie partie) {}

	@Override
	public void passeTour(int id) {}

	@Override
	public void finPartie(Partie partie) {}

	@Override
	public void finPartie(Partie partie, ResultatPartie resultats) {}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {}

}
