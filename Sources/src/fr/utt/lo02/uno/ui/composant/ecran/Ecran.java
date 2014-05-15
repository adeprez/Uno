package fr.utt.lo02.uno.ui.composant.ecran;

import fr.utt.lo02.uno.io.interfaces.Fermable;
import fr.utt.lo02.uno.ui.composant.PanelImage;
import fr.utt.lo02.uno.ui.interfaces.EcranChangeable;
import fr.utt.lo02.uno.ui.listener.ChangeEcranListener;

public class Ecran extends PanelImage implements EcranChangeable, Fermable {
	private static final long serialVersionUID = 1L;
	
	
	public void addChangeEcranListener(ChangeEcranListener l) {
		listenerList.add(ChangeEcranListener.class, l);
	}

	public void removeChangeEcranListener(ChangeEcranListener l) {
		listenerList.remove(ChangeEcranListener.class, l);
	}
	
	public void demandeFocus() {
		for(final ChangeEcranListener l : listenerList.getListeners(ChangeEcranListener.class))
			l.focus();
	}
	
	public boolean estAffiche() {
		boolean b = true;
		for(final ChangeEcranListener l : listenerList.getListeners(ChangeEcranListener.class))
			b = b && l.askShowed();
		return b && isShowing();
	}

	@Override
	public boolean fermer() {
		return true;
	}
	
	@Override
	public void changeEcran(Ecran nouveau) {
		for(final ChangeEcranListener l : listenerList.getListeners(ChangeEcranListener.class))
			l.changeEcran(nouveau);
	}
	
}
