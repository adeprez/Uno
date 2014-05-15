package fr.utt.lo02.uno.io.reseau.serveur.filtreEnvoi;

import fr.utt.lo02.uno.io.interfaces.FiltreEnvoi;
import fr.utt.lo02.uno.io.interfaces.IOable;

/**
 * Filtre d'envoi exculant un client selon son identifiant
 */
public class FiltreEnvoiExclusionID implements FiltreEnvoi {
	private final int id;
	
	
	/**
	 * Cree un nouveau filtre d'envoi exculant un client selon son identifiant
	 * @param id l'identifiant du client a exclure
	 */
	public FiltreEnvoiExclusionID(int id) {
		this.id = id;
	}
	
	@Override
	public boolean doitEnvoyer(int id, IOable io) {
		return this.id != id;
	}

}
