package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.ui.composant.specialise.jeu.CarteGraphique;


public interface CliqueCarteListener extends EventListener {
	public void clique(CarteGraphique carte);
}
