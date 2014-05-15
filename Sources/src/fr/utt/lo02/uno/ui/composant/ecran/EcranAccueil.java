package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.langue.Langue;
import fr.utt.lo02.uno.langue.LangueListener;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;
import fr.utt.lo02.uno.ui.composant.specialise.PanelAccueil;
import fr.utt.lo02.uno.ui.layout.LayoutCentre;

public class EcranAccueil extends Ecran implements Runnable, LangueListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private final BufferedImage logo;
	private final PanelAccueil accueil;
	private final Bouton langue;
	private boolean run, suivant;
	private String texte;
	private int x, index;


	public EcranAccueil() {
		setMax(true);
		setImage(Images.getInstance().getImage("fond.jpg"));
		setName(Texte.get("Accueil"));
		logo = Images.getInstance().getImage("utt.png");
		langue = new Bouton(new ImageIcon(Images.getInstance().getImage(Texte.getInstance().getLangue().getCheminImage())));
		accueil = new PanelAccueil(this);
		setLayout(new LayoutCentre() {
			@Override
			public void layoutContainer(Container parent) {
				super.layoutContainer(parent);
				langue.setBounds(5, 5, 100, 75);
			}
		});
		add(langue);
		add(accueil);
		accueil.lancer();
		langue.setFond(false);
		langue.setToolTipText(Texte.get("Changer de langue"));
		langue.addActionListener(this);
		run = true;
		if(Configuration.MESSAGES.length > 0)
			new Thread(this).start();
		Texte.getInstance().addLangueListener(this);
	}

	@Override
	public boolean fermer() {
		accueil.terminer();
		run = false;
		Texte.getInstance().removeLangueListener(this);
		return super.fermer();
	}

	@Override
	public void run() {
		texte = Configuration.MESSAGES[index];
		while(run) {
			Outil.attendre(50);
			x += 2;
			if(suivant) {
				suivant = false;
				index ++;
				if(index >= Configuration.MESSAGES.length)
					index = 0;
				texte = Configuration.MESSAGES[index];
				x = 0;
			}
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(texte != null) {
			g.setFont(Configuration.POLICE.deriveFont(Font.ITALIC).deriveFont(12F));
			g.setColor(Color.YELLOW);
			int nx = x - g.getFontMetrics().stringWidth(texte);
			g.drawString(texte, nx, getHeight() - 5);
			if(nx > getWidth())
				suivant = true;
		}
		g.drawImage(logo, getWidth() - logo.getWidth(), 0, null);
	}

	@Override
	public void changeLangue(Langue langue) {
		changeEcran(new EcranAccueil());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Texte.getInstance().proposeChangeLangue();
	}
	
	
}
