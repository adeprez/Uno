package fr.utt.lo02.uno.temps;

/**
 * Evenement allant se produire apres un temps donne
 */
public class Evenement implements EvenementFutur {
	private boolean desactive;
	private Evenementiel evt;
	private int delai;

	
	/**
	 * Cree un evenement allant se produire apres un temps donne
	 * @param delai le temps (en secondes) avant que cet evenement ne se produise
	 * @param evt l'evenement a effectuer
	 */
	public Evenement(int delai, Evenementiel evt) {
		this(delai);
		this.evt = evt;
	}
	
	/**
	 * Cree un evenement allant se produire apres un temps donne
	 * @param delai le temps (en secondes) avant que cet evenement ne se produise
	 */
	public Evenement(int delai) {
		this.delai = delai;
	}
	
	/**
	 * Assigne l'evenement allant se produire apres un temps donne
	 * @param evt l'evenement allant se produire apres un temps donne
	 */
	public void setEvenement(Evenementiel evt) {
		this.evt = evt;
	}
	
	/**
	 * Desactive l'action associe a cet evenement
	 */
	public void desactiver() {
		desactive = true;
	}
	
	/**
	 * @return vrai si l'evenement est actif
	 */
	public boolean estActif() {
		return !desactive;
	}

	@Override
	public int getTemps() {
		return delai;
	}

	@Override
	public void evenement(Periodique p) {
		if(!desactive)
			evt.evenement(p);
	}

	@Override
	public void setTemps(int temps) {
		delai = temps;
	}
	
	@Override
	public String toString() {
		return delai + " : " + evt;
	}

}
