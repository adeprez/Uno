package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.jeu.carte.Couleur;


public interface ChoixCouleurListener extends EventListener {
	public void choixCouleur(Couleur couleur);
}
