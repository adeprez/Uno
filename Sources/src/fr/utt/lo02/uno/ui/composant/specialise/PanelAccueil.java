package fr.utt.lo02.uno.ui.composant.specialise;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.ecran.EcranCreerSalle;
import fr.utt.lo02.uno.ui.composant.ecran.EcranRegles;
import fr.utt.lo02.uno.ui.composant.ecran.EcranRejoindreSalle;
import fr.utt.lo02.uno.ui.interfaces.EcranChangeable;
import fr.utt.lo02.uno.ui.layout.LayoutLignes;


public class PanelAccueil extends JPanel implements Runnable, ActionListener {
	private static final long serialVersionUID = 1L;
	private final AbstractButton jouer, horsLigne, enLigne, retour, regles;
	private final AbstractButton[] boutons;
	private final EcranChangeable ecran;
	private final BufferedImage fond;
	private final LogoUno logo;
	private float taille;


	public PanelAccueil(EcranChangeable ecran) {
		this.ecran = ecran;
		
		boutons = new AbstractButton[] {jouer = new Bouton(Texte.get("Jouer")),
				horsLigne = new Bouton(Texte.get("Partie hors ligne"), new ImageIcon(Images.getInstance().getImage("hors ligne.png"))),
				enLigne = new Bouton(Texte.get("Partie en ligne"), new ImageIcon(Images.getInstance().getImage("online.png"))),
				regles = new Bouton(Texte.get("Règles du jeu")),
				retour = new Bouton(Texte.get("Retour"), new ImageIcon(Images.getInstance().getImage("retour.png")))
		};

		Font police = new Font(Font.DIALOG, Font.BOLD, 18);
		Dimension d = new Dimension(225, 65);

		for(final AbstractButton b : boutons) {
			b.addActionListener(this);
			b.setFont(police);
			b.setPreferredSize(d);
		}

		setLayout(new LayoutLignes(false));
		setOpaque(false);

		fond = Images.getInstance().getImage("menu uno.png");
		logo = new LogoUno();
		taille = 1;

		setAccueil();
	}

	public void setComposants(Component... composants) {
		new Thread(this).start();
		removeAll();
		add(logo);
		add(Box.createRigidArea(new Dimension(0, 5)));
		validate();
		repaint();
		for(final Component c : composants) {
			add(c);
			add(Box.createRigidArea(new Dimension(0, 15)));
		}
		add(Box.createRigidArea(new Dimension(0, 10)));
	}

	public PanelAccueil lancer() {
		logo.lancer();
		return this;
	}

	public void terminer() {
		taille = 1;
		logo.terminer();
	}

	public void setAccueil() {
		setComposants(jouer, regles);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = logo.getWidth()/2;
		int y = logo.getY() + logo.getHeight()/2;
		g.drawImage(fond, (getWidth() - w)/2, y, w, (int) ((getHeight() - y) * taille) - 10, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jouer)
			setComposants(retour, enLigne, horsLigne);
		else if(e.getSource() == horsLigne)
			ecran.changeEcran(new EcranCreerSalle(false));
		else if(e.getSource() == regles)
			ecran.changeEcran(new EcranRegles());
		else if(e.getSource() == enLigne)
			ecran.changeEcran(new EcranRejoindreSalle());
		else if(e.getSource() == retour)
			setAccueil();
	}

	@Override
	public void run() {
		float ajout = .1f;
		taille = 0;
		for(final AbstractButton b : boutons)
			b.setVisible(false);
		Outil.attendre(200);
		while(taille < 1) {
			taille += ajout;
			if(ajout > .001)
				ajout -= .005f;
			repaint();
			Outil.attendre(42);
		}
		for(final AbstractButton b : boutons)
			b.setVisible(true);
	}

}
