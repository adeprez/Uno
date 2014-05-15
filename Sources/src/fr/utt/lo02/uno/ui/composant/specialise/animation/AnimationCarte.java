package fr.utt.lo02.uno.ui.composant.specialise.animation;

import java.awt.Graphics;
import java.awt.Rectangle;

import fr.utt.lo02.uno.io.ImageCartes;
import fr.utt.lo02.uno.jeu.carte.Carte;


public class AnimationCarte implements Animable {
	private static final int ITERATIONS = 15;
	private final AnimateurCarte source, cible;
	private final Carte carte;
	private int avancement;


	public AnimationCarte(Carte carte, AnimateurCarte source, AnimateurCarte cible) {
		this.carte = carte;
		this.source = source;
		this.cible = cible;
		cible.commencerCible(carte);
		cible.commencerSource(carte);
	}

	public boolean estFini() {
		return avancement > ITERATIONS;
	}

	@Override
	public void dessiner(Graphics g, Rectangle bounds) {
		Rectangle s = source.getPositionSurEcran(carte);
		Rectangle p = cible.getPositionSurEcran(carte);

		Rectangle r = new Rectangle(
				s.x + ((p.x - s.x) * avancement)/ITERATIONS,
				s.y + ((p.y - s.y) * avancement)/ITERATIONS,
				s.width + ((p.width - s.width) * avancement)/ITERATIONS,
				s.height + ((p.height - s.height) * avancement)/ITERATIONS
				);

		boolean visible = source.estVisible(carte);

		if(!visible && cible.estVisible(carte)) {
			visible = avancement >= ITERATIONS/2;
			if(visible) {
				int d = r.width - (r.width * avancement)/ITERATIONS;
				r.width -= d;
				r.x += d/2;
			} else {
				int d = (r.width * avancement)/(ITERATIONS/2);
				r.width -= d;
				r.x += d/2;
			}
		}

		ImageCartes.getInstance().dessiner(g, r.x, r.y, r.width, r.height, carte, visible);
	}

	@Override
	public boolean bouge() {
		avancement ++;
		if(estFini()) {
			source.terminerSource(carte);
			cible.terminerCible(carte);
			return false;
		}
		return true;
	}

}
