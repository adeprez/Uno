package fr.utt.lo02.uno.temps;

/**
 * Objet representant une horloge, pouvant accepter des evenements et ecouter le deroulement des secondes
 */
public class Horloge extends Periodique {


	/**
	 * Cree une nouvelle horloge, pouvant accepter des evenements et ecouter le deroulement des secondes
	 */
	public Horloge() {
		super(1000);
	}
	
	/**
	 * @return le nombre de secondes ecoulees depuis le lancement de cette horloge
	 */
	public int getTemps() {
		return getIterations();
	}

	/**
	 * @return les secondes de cette horloge (de 0 a 60)
	 */
	public int getSecondes() {
		return getTemps() % 60;
	}

	/**
	 * @return les minutes de cette horloge (de 0 a 60)
	 */
	public int getMinutes() {
		return getTemps()/60;
	}

	/**
	 * Ajoute un ecouteur pour l'ecoulement des secondes
	 * @param l le nouvel ecouteur a ajouter
	 */
	public void addHorlogeListener(HorlogeListener l) {
		addListener(HorlogeListener.class, l);
	}

	/**
	 * Retire un ecouteur pour l'ecoulement des secondes
	 * @param l l'ancien ecouteur a retirer
	 */
	public void removeHorlogeListener(HorlogeListener l) {
		removeListener(HorlogeListener.class, l);
	}
	
	/**
	 * Notifie les ecouteurs des secondes qu'une nouvelle seconde s'est ecoulee
	 */
	public void notifyHorlogeListeners() {
		for(final HorlogeListener l : getListeners(HorlogeListener.class))
			l.action(this);
	}

	@Override
	public void notifyEvenements() {
		notifyHorlogeListeners();
		super.notifyEvenements();
	}

	@Override
	public String toString() {
		int s = getSecondes();
		return getMinutes() + ":" + (s < 10 ? "0" : "") + s;
	}


}
