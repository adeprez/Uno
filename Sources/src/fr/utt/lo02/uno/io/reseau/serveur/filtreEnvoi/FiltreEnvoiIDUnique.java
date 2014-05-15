package fr.utt.lo02.uno.io.reseau.serveur.filtreEnvoi;

import fr.utt.lo02.uno.io.interfaces.FiltreEnvoi;
import fr.utt.lo02.uno.io.interfaces.IOable;

/**
 * Filtre d'envoi n'acceptant qu'un client, selon son identifiant
 */
public class FiltreEnvoiIDUnique implements FiltreEnvoi {
	private final int id;
	
	
	/**
	 * Cree un nouveau filtre d'envoi n'acceptant qu'un client, selon son identifiant
	 * @param id l'identifiant du client a accepter
	 */
	public FiltreEnvoiIDUnique(int id) {
		this.id = id;
	}
	
	@Override
	public boolean doitEnvoyer(int id, IOable io) {
		return this.id == id;
	}

}
