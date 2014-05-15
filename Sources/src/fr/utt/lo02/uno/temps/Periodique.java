package fr.utt.lo02.uno.temps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.utt.lo02.uno.base.Outil;
import fr.utt.lo02.uno.jeu.listener.Listenable;


/**
 * Classe permettant de gerer des evenements au cours du temps
 */
public class Periodique extends Listenable implements Runnable, Comparator<EvenementTempsDonne> {
	private final List<EvenementTempsDonne> evenements;
	private final int delai;
	private long lancement;
	private int iterations;
	private boolean run;


	/**
	 * Cree un nouveau gestionnaire des evenements au cours du temps
	 * @param delai le temps (en millisecondes) entre chaque changement d'etat
	 */
	public Periodique(int delai) {
		this.delai = delai;
		evenements = new ArrayList<EvenementTempsDonne>();
	}

	/**
	 * @return les evenements de ce element periodique
	 */
	public List<EvenementTempsDonne> getEvenements() {
		return evenements;
	}

	/**
	 * Ajoute un evenement futur
	 * @param e l'evenement periodique futur a ajouter
	 */
	public void addEvenementFutur(EvenementFutur e) {
		e.setTemps(e.getTemps() + getIterations());
		addEvenementTempsDonne(e);
	}

	/**
	 * Ajoute un evenement allant s'exectuer a un temps donne
	 * @param e l'evenement a ajouter
	 */
	public void addEvenementTempsDonne(EvenementTempsDonne e) {
		if(e.getTemps() > getIterations()) {
			evenements.add(e);
			Collections.sort(evenements, this);
		}
		else e.evenement(this);
	}

	/**
	 * Supprime l'evenement precise
	 * @param e l'evenement a supprimer
	 * @return vrai si l'element a bien ete supprime
	 */
	public boolean removeEvenement(EvenementTempsDonne e) {
		return evenements.remove(e);
	}

	/**
	 * @return le nombre de changements d'etat ayant ete subis par cet element periodique
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * @return vrai si cet element periodique esst en cours d'execution
	 */
	public boolean enExecution() {
		return run;
	}

	/**
	 * @return le decalage (en millisecondes) entre le temps theorique et le temps reel
	 */
	public int getDecalage() {
		return (int) (getMilisecondeEcoule() - getTempsTheorique());
	}

	/**
	 * @return le temps (en millisecondes) ecoule depuis le lancement de cet element periodique
	 */
	public long getMilisecondeEcoule() {
		return System.currentTimeMillis() - lancement;
	}

	/**
	 * @return le temps theorique depuis le lancement de cet element periodique
	 */
	public int getTempsTheorique() {
		return iterations * delai;
	}

	/**
	 * Initialise les champs de ce periodique
	 */
	public void setLancement() {
		lancement = System.currentTimeMillis();
		iterations = 0;
	}

	/**
	 * Consomme les evenements de ce periodique, si leur temps est arrive
	 */
	public void notifyEvenements() {
		EvenementTempsDonne e;
		synchronized(evenements) {
			while(!evenements.isEmpty() && (e = evenements.get(0)).getTemps() <= getIterations())
				if(evenements.remove(0) != null)
					e.evenement(this);
		}
	}

	/**
	 * Termine l'execution de cet element periodique
	 * @return faux si cet element etait deja termine
	 */
	public boolean terminer() {
		if(run) {
			run = false;
			return true;
		}
		return false;
	}

	/**
	 * Lance l'execution de cet element periodique
	 * @return faux si cet element etait deja lance
	 */
	public boolean lancer() {
		setLancement();
		if(run)
			return false;
		run = true;
		new Thread(this).start();
		return true;
	}
	
	/**
	 * Ajoute un listener des iterations de ce periodique
	 * @param l le listener a ajouter
	 */
	public void addPeriodiqueListener(PeriodiqueListener l) {
		addListener(PeriodiqueListener.class, l);
	}
	
	/**
	 * Ajoute un listener des iterations de ce periodique
	 * @param l le listener a ajouter
	 */
	public void removePeriodiqueListener(PeriodiqueListener l) {
		removeListener(PeriodiqueListener.class, l);
	}

	@Override
	public void run() {
		while(run) {
			Outil.attendre(Math.max(0, delai - getDecalage()));
			iterations++;
			notifyEvenements();
			for(final PeriodiqueListener l : getListeners(PeriodiqueListener.class))
				l.action(this);
		}
	}

	@Override
	public int compare(EvenementTempsDonne e1, EvenementTempsDonne e2) {
		return new Integer(e1.getTemps()).compareTo(e2.getTemps());
	}

}
