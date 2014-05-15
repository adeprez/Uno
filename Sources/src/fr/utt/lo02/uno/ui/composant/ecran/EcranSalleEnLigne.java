package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.TrayIcon.MessageType;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.Paquet;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.client.SalleReseau;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;
import fr.utt.lo02.uno.jeu.Jeu;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.ResultatPartie;
import fr.utt.lo02.uno.jeu.Salle;
import fr.utt.lo02.uno.jeu.joueur.Joueur;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.temps.CompteARebours;
import fr.utt.lo02.uno.temps.Horloge;
import fr.utt.lo02.uno.temps.HorlogeListener;
import fr.utt.lo02.uno.temps.Periodique;
import fr.utt.lo02.uno.temps.PeriodiqueListener;
import fr.utt.lo02.uno.ui.IconeTache;
import fr.utt.lo02.uno.ui.composant.specialise.jeu.EmplacementJoueur;


public class EcranSalleEnLigne extends EcranSalle implements PeriodiqueListener, ReceiveListener, HorlogeListener {
	private static final long serialVersionUID = 1L;
	private final JProgressBar avancement;
	private final Periodique periodique;
	private final CompteARebours cpt;
	private final SalleReseau salle;


	public EcranSalleEnLigne(SalleReseau salle) {
		super(salle, false);
		this.salle = salle;
		cpt = new CompteARebours();
		avancement = new JProgressBar();
		periodique = new Periodique(75);
		if(salle.getJeu().getPartie() != null)
			nouvellePartie(salle.getJeu().getPartie());
		else {
			salle.getClient().addReceiveListener(this);
			cpt.addHorlogeListener(this);
			salle.getClient().write(new Paquet(TypePaquet.TEMPS));
			avancement.setStringPainted(true);
			avancement.setFont(Configuration.POLICE);
			add(avancement, BorderLayout.SOUTH);
			setRecherche();
			periodique.addPeriodiqueListener(this);
			periodique.lancer();
			avancement.setValue(100);
		}
		JLabel ip = new JLabel(Texte.get("Adresse pour rejoindre la partie") + " : " 
				+ salle.getClient().getAdresse().getHostAddress(), SwingConstants.CENTER);
		ip.setFont(Configuration.POLICE.deriveFont(20F));
		ip.setToolTipText(Texte.get("Donnez cette adresse a vos amis afin qu'ils puissent rejoindre vos parties"));
		add(ip, BorderLayout.NORTH);
	}

	public void setRecherche() {
		for(final EmplacementJoueur e : getEmplacements())
			e.evenementAnimation();
	}

	@Override
	public void ajout(int id, Joueur joueur) {
		super.ajout(id, joueur);
		if(!estAffiche())
			IconeTache.getInstance().message(getSalle().getNom(), joueur + " " + Texte.get("rejoint la salle"), MessageType.INFO);
	}

	@Override
	public void retire(int id, Joueur joueur) {
		super.retire(id, joueur);
		if(!estAffiche())
			IconeTache.getInstance().message(getSalle().getNom(), joueur + " " + Texte.get("quitte la salle"), MessageType.WARNING);
	}

	@Override
	public boolean fermer() {
		return super.fermer() && periodique.terminer() && cpt.terminer();
	}

	@Override
	public void action(Periodique p) {
		setRecherche();
	}

	@Override
	public void recu(TypePaquet type, IO io) {
		switch(type) {
		case TEMPS:
			int t = io.nextPositif();
			if(avancement != null)
				avancement.setMaximum(t);
			cpt.setTemps(t - 1);
			break;
		default:
			break;
		}
	}

	@Override
	public void action(Horloge horloge) {
		avancement.setValue(horloge.getTemps());
		avancement.setString(horloge.toString());
	}

	@Override
	public void nouvellePartie(Partie partie) {
		super.nouvellePartie(partie);
		salle.getClient().removeReceiveListener(this);
	}

	@Override
	public EcranPartieReseau getEcranJeu(Joueur joueur, Salle salle) {
		return new EcranPartieReseau(joueur, (SalleReseau) salle);
	}

	@Override
	public void finJeu(List<ResultatPartie> resultats, Jeu jeu) {}

}
