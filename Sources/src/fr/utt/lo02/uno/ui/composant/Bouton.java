package fr.utt.lo02.uno.ui.composant;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JButton;

import fr.utt.lo02.uno.base.Configuration;
import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.io.Sons;


public class Bouton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;
	private final BufferedImage image, survol, enfonce;
	private boolean estSurvol, estEnfonce, aFond;


	public Bouton() {
		image = Images.getInstance().getImage("fond bouton.png");
		survol = Images.getInstance().getImage("survol.png");
		enfonce = Images.getInstance().getImage("enfonce.png");
		aFond = true;
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setFont(Configuration.POLICE);
		setForeground(Color.YELLOW);
		addMouseListener(this);
		setContentAreaFilled(false);
	}

	public Bouton(Icon icon) {
		this();
		setIcon(icon);
	}

	public Bouton(String text) {
		this();
		setText(text);
	}

	public Bouton(String text, Icon icon) {
		this(icon);
		setText(text);
	}
	
	public boolean aFond() {
		return aFond;
	}

	public void setFond(boolean aFond) {
		this.aFond = aFond;
		repaint();
	}

	public void setSurvol(boolean estSurvol) {
		this.estSurvol = estSurvol;
		repaint();
	}
	
	public boolean estSurvol() {
		return estSurvol;
	}

	@Override
	public void paintComponent(Graphics g) {
		if(aFond)
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		super.paintComponent(g);
		if(estSurvol)
			g.drawImage(survol, 0, 0, getWidth(), getHeight(), null);
		if(estEnfonce)
			g.drawImage(enfonce, 0, 0, getWidth(), getHeight(), null);
		if(!isEnabled())
			g.drawImage(enfonce, 0, 0, getWidth(), getHeight(), null);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(isEnabled()) {
			estEnfonce = true;
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isEnabled()) {
			estEnfonce = false;
			setSurvol(false);
			Sons.getInstance().jouer("pop.wav");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(isEnabled())
			setSurvol(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(isEnabled())
			setSurvol(false);
	}


}
