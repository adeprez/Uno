package fr.utt.lo02.uno.ui.composant;

import fr.utt.lo02.uno.io.Images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Chrono {
	private final BufferedImage image;
	private int temps;

	
	public Chrono() {
		image = Images.getInstance().getImage("chrono.png");
	}
	
	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public void dessiner(Graphics g, int x, int y, int width, int height) {
		g.drawImage(image, x - width/2, y, width, height, null);
		g.setColor(Color.GREEN);
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, (int) (height/1.5)));
		String s = (temps < 0 ? "..." : "" + temps);
		g.drawString(s, x - g.getFontMetrics().stringWidth(s)/2, y + height/2 + g.getFontMetrics().getHeight()/4);
	}

}
