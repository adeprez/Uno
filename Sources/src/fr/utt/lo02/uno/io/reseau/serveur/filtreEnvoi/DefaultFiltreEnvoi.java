package fr.utt.lo02.uno.io.reseau.serveur.filtreEnvoi;

import fr.utt.lo02.uno.io.interfaces.FiltreEnvoi;
import fr.utt.lo02.uno.io.interfaces.IOable;

/**
 * Filtre d'envoi par defaut, acceptant tous les clients
 */
public class DefaultFiltreEnvoi implements FiltreEnvoi {


	@Override
	public boolean doitEnvoyer(int id, IOable io) {
		return true;
	}

}
