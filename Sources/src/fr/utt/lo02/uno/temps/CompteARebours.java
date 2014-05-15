package fr.utt.lo02.uno.temps;

/**
 * Classe representant un compte a rebourss
 */
public class CompteARebours extends Horloge {
	private int temps;
	
	
	/**
	 * Cree un nouveau compte a rebours de delai 0
	 */
	public CompteARebours() {}
	
	/**
	 * Cree un nouveau compte a rebours
	 * @param temps le temps avant la fin de ce compte a rebours, une fois lance
	 * @see #lancer()
	 */
	public CompteARebours(int temps) {
		this.temps = temps;
	}
	
	/**
	 * Reassigne le temps restant avant la fin de ce compte a rebours, et le lance
	 * @param temps le temps avant la fin de ce compte a rebours
	 * @see #lancer()
	 */
	public void setTemps(int temps) {
		this.temps = temps;
		lancer();
	}
	
	/**
	 * @return le temps maximum apres lequel ce compte a rebours se fini
	 */
	public int getMax() {
		return temps;
	}
	
	/**
	 * @return vrai si ce compte a rebours est termine
	 */
	public boolean estFini() {
		return getTemps() <= 0;
	}
	
	/**
	 * Ajoute un ecouteur de la fin de ce compte a rebours
	 * @param l le nouvel ecouteur
	 */
	public void addFinCompteAReboursListener(FinCompteAReboursListener l) {
		addListener(FinCompteAReboursListener.class, l);
	}

	/**
	 * Retire un ecouteur de la fin de ce compte a rebours
	 * @param l l'ancien ecouteur
	 */
	public void removeFinCompteAReboursListener(FinCompteAReboursListener l) {
		removeListener(FinCompteAReboursListener.class, l);
	}
	
	@Override
	public int getTemps() {
		return Math.max(0, temps - super.getTemps());
	}
	
	@Override
	public void notifyHorlogeListeners() {
		super.notifyHorlogeListeners();
		if(estFini())
			terminer();
	}
	
	@Override
	public boolean terminer() {
		if(super.terminer()) {
			for(final FinCompteAReboursListener l : getListeners(FinCompteAReboursListener.class))
				l.finCompteARebours(temps, this);
			return true;
		}
		return false;
	}
	
	
}
