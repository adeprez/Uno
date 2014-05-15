package fr.utt.lo02.uno.temps;

import java.util.EventListener;

/**
 * Interface definissant les ecouteur d'un horloge
 */
public interface HorlogeListener extends EventListener {
	
	/**
	 * Signifie qu'une nouvelle seconde s'est ecoulee
	 * @param horloge l'horloge ayant genere cet evenement
	 */
	public void action(Horloge horloge);
	
}
