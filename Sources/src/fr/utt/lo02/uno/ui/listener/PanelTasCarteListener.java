package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.ui.composant.specialise.jeu.PanelTasCartes;


public interface PanelTasCarteListener extends EventListener {
	public void clique(PanelTasCartes tas);
}
