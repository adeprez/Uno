package fr.utt.lo02.uno.io.interfaces;

/**
 * Interface definissant un flitre pour l'envoi a travers un flux
 */
public interface FiltreEnvoi {
	
	/**
	 * @param id l'identifiant du client
	 * @param io l'element a envoyer
	 * @return vrai si le flux doit etre envoye
	 */
	public boolean doitEnvoyer(int id, IOable io);
	
}
