package fr.utt.lo02.uno.temps;

/**
 * Evenement se reproduisant a intervale de temps regulier
 */
public class EvenementRecursif extends Evenement {
	private final int delai;
	private boolean fini;

	
	/**
	 * Cree un nouvel evenement allant se reproduire a intervale de temps regulier
	 * @param delai le delai entre chaque evenement
	 * @param evt l'evenement a executer
	 */
	public EvenementRecursif(int delai, Evenementiel evt) {
		super(delai, evt);
		this.delai = delai;
	}
	
	/**
	 * Met fin a la recursivite de cet evenement
	 */
	public void terminer() {
		fini = true;
	}

	@Override
	public void evenement(Periodique p) {
		super.evenement(p);
		setTemps(delai);
		if(!fini)
			p.addEvenementFutur(this);
	}
	
}
