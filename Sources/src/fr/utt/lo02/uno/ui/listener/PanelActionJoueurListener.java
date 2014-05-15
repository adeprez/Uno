package fr.utt.lo02.uno.ui.listener;

import java.util.EventListener;

public interface PanelActionJoueurListener extends EventListener {
	public void contreUno();
	public void uno();
	public void passeTour();
	public void contreBluff(boolean contreBluffe);
}
