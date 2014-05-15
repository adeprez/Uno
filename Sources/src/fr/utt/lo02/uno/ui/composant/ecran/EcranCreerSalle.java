package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.specialise.PanelCreerSalle;
import fr.utt.lo02.uno.ui.layout.LayoutCentre;



public class EcranCreerSalle extends Ecran {
	private static final long serialVersionUID = 1L;
	private final PanelCreerSalle salle;
	private final BufferedImage image, logo;

	
	public EcranCreerSalle(boolean enLigne) {
		setLayout(new LayoutCentre());
		setMax(true);
		setImage(Images.getInstance().getImage("fond.jpg"));
		setName(Texte.get("Creer une salle"));
		image = Images.getInstance().getImage("fond logo uno.png");
		logo = Images.getInstance().getImage("logo uno.png");
		add(salle = new PanelCreerSalle(enLigne, this));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(logo, 0, 0, 500, 300, null);
		int w = (int) (salle.getWidth() * 1.68);
		int h = (int) (salle.getHeight() * 1.8);
		g.drawImage(image, (getWidth() - w)/2 + 18, (getHeight() - h)/2 - 25, w, h, null);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		g.drawString("- " + getName() + " -", (getWidth() - w)/2 + 265, (int) ((getHeight() - h/1.4)/2));
	}

}
