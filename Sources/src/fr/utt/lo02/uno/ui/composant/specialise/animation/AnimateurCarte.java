package fr.utt.lo02.uno.ui.composant.specialise.animation;

import java.awt.Rectangle;

import fr.utt.lo02.uno.jeu.carte.Carte;


public interface AnimateurCarte {
	public Rectangle getPositionSurEcran(Carte carte);
	public boolean estVisible(Carte carte);
	public void commencerSource(Carte carte);
	public void commencerCible(Carte carte);
	public void terminerSource(Carte carte);
	public void terminerCible(Carte carte);
}
