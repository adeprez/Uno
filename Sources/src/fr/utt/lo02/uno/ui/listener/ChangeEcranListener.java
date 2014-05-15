package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;

import fr.utt.lo02.uno.ui.composant.ecran.Ecran;


public interface ChangeEcranListener extends EventListener {
	public void changeEcran(Ecran nouveau);
	public void focus();
	public boolean askShowed();
}
