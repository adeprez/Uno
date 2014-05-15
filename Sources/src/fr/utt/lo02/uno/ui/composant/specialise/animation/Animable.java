package fr.utt.lo02.uno.ui.composant.specialise.animation;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Animable {
	public void dessiner(Graphics g, Rectangle bounds);
	public boolean bouge();
}
