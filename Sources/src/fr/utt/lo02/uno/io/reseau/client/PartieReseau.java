package fr.utt.lo02.uno.io.reseau.client;

import fr.utt.lo02.uno.io.IO;
import fr.utt.lo02.uno.io.reseau.TypePaquet;
import fr.utt.lo02.uno.io.reseau.listeners.ReceiveListener;
import fr.utt.lo02.uno.jeu.Partie;
import fr.utt.lo02.uno.jeu.carte.ensemble.PlateauJeu;
import fr.utt.lo02.uno.temps.EvenementFutur;

/**
 * Objet represantant une {@link Partie} en reseau
 * @see SalleReseau
 */
public class PartieReseau extends Partie implements ReceiveListener {
	private final SalleReseau salle;

	
	/**
	 * Cree une nouvelle {@link Partie} en reseau
	 * @param salle le nom de la salle
	 * @param plateau le flux permettant de construire le plateau de jeu
	 */
	public PartieReseau(SalleReseau salle, IO plateau) {
		super(salle.getJeu().getListeJoueurs(), new PlateauJeu(plateau));
		this.salle = salle;
		salle.getClient().addReceiveListener(this);
	}

	/**
	 * @return la salle reseau correspondant a cette partie
	 */
	public SalleReseau getSalle() {
		return salle;
	}
	
	@Override
	public void commence() {
		
	}
	
	@Override
	public EvenementFutur ajoutEvenement(EvenementFutur e) {
		return e;
	}

	@Override
	public void affecteComportementOrdinateurs() {
		
	}
	
	@Override
	public void finPartie() {
		salle.getClient().removeReceiveListener(this);
		super.finPartie();
	}
	
	@Override
	public void recu(TypePaquet type, IO io) {
		switch(type) {
		case ACTION:
			try {
				if(getJoueurs().getJoueur().getTour() != null)
					getJoueurs().getJoueur().getTour().faireAction(io.nextPositif());
			} catch(Exception e) {
				e.printStackTrace();
			}
			break;
			
		case TOUR:
			getJoueurs().setIndexJoueur(io.nextPositif());
			commenceTour();
			break;
			
		case FIN_PARTIE:
			finPartie();
			break;
			
		default:
			break;
		}
	}
	
	
}
