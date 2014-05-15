package fr.utt.lo02.uno.jeu.listener;

import java.util.EventListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.event.EventListenerList;

/**
 * Classe representant un objet ecoutable. Cela correspond au design-pattern {@link Observer}/{@link Observable},
 * en permettant toutefois une conception plus avancee.
 */
public class Listenable {
	private EventListenerList listenerList;


	/**
	 * Si la liste n'existe pas encore, celle-ci est cree
	 * @return la liste des ecouteurs
	 */
	public EventListenerList getListenerList() {
		if(listenerList == null)
			listenerList = new EventListenerList();
		return listenerList;
	}

	/**
	 * Ajoute un ecouteur
	 * @param t le type des ecouteurs a ajouter
	 * @param ll l'ensemble des ecouteurs a ajouter
	 */
	public <T extends EventListener> void addListeners(Class<T> t, T... ll) {
		for(final T l : ll)
			getListenerList().add(t, l);
	}

	/**
	 * Ajoute un ecouteur
	 * @param t le type de l'ecouteur a ajouter
	 * @param l l'ecouteur a ajouter
	 */
	public <T extends EventListener> void addListener(Class<T> t, T l) {
		getListenerList().add(t, l);
	}

	/**
	 * Retire un ecouteur
	 * @param t le type de l'ecouteur a retirer
	 * @param l l'ecouteur a retirer
	 */
	public <T extends EventListener> void removeListener(Class<T> t, T l) {
		getListenerList().remove(t, l);
	}

	/**
	 * @param t le type des ecouteurs a obtenir
	 * @return un tableau contenant tous les ecouteurs correspondant au type
	 */
	public <T extends EventListener> T[] getListeners(Class<T> t) {
		return getListenerList().getListeners(t);
	}

	/**
	 * Reinitialise la liste des ecouteurs
	 */
	public void removeAllListeners() {
		listenerList = new EventListenerList();
	}


}
