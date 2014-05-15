package fr.utt.lo02.uno.ui.composant.specialise.animation;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import fr.utt.lo02.uno.io.Images;

public class Animation implements Animable {
	private final BufferedImage image, fond;
	private int angle, tour, transparence;


	public Animation(String fond, String image) {
		this(fond == null ? null : Images.getInstance().getImage(fond), image == null ? null : Images.getInstance().getImage(image));
	}

	public Animation(BufferedImage fond, BufferedImage image) {
		this.fond = fond;
		this.image = image;
	}
	
	public Animation(String image) {
		this("fond logo uno.png", image);
	}

	@Override
	public void dessiner(Graphics g, Rectangle bounds) {
		double d = Math.min(.5, tour/20.0);
		Composite tmp = ((Graphics2D) g).getComposite();
		if(angle >= 360) {
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, (100 - transparence)/100f)));
			transparence += 2;
		}
		if(fond != null)
			g.drawImage(fond, (int) (bounds.width - fond.getWidth() * d)/2, (int) (bounds.height - fond.getHeight() * d)/2, 
					(int) (fond.getWidth() * d), (int) (fond.getHeight() * d), null);
		if(image != null) {
			AffineTransform transform = new AffineTransform();
			transform.translate(bounds.width/2 - ((image.getWidth()) * d)/2, bounds.height/2 - ((image.getHeight()) * d)/2);
			transform.scale(d, d);
			if(angle < 360) {
				transform.rotate(Math.toRadians(angle), image.getWidth()/2, image.getHeight()/2);
				angle += 1 + Math.sqrt(500/(tour/3.3 + 1));
			}
			((Graphics2D) g).drawImage(image, transform, null);
		}
		((Graphics2D) g).setComposite(tmp);
	}

	@Override
	public boolean bouge() {
		tour++;
		return transparence < 100;
	}

}
