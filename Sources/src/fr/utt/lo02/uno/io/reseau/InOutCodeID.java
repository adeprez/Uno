package fr.utt.lo02.uno.io.reseau;

import fr.utt.lo02.uno.io.InOut;
import fr.utt.lo02.uno.io.exception.HorsLigneException;
import fr.utt.lo02.uno.io.exception.InvalideException;
import fr.utt.lo02.uno.io.interfaces.IOable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Flux d'entree et de sortie specifiant un code pour l'envoi et la reception de message, afin de securiser la position de debut de chaque paquet
 * @see InOut
 */
public class InOutCodeID extends InOut {
	private static final byte ID = 42;


	/**
	 * Cree un nouveau flux d'entree et de sortie specifiant un code pour l'envoi et la reception de message, 
	 * afin de securiser la position de debut de chaque paquet dont les flux d'entree et de sortie ne sont pas encore definis
	 */
	public InOutCodeID() {}

	/**
	 * Cree un nouveau flux d'entree et de sortie specifiant un code pour l'envoi et la reception de message, 
	 * afin de securiser la position de debut de chaque paquet
	 * @param reader le flux d'entree
	 * @param writer le flux de sortie
	 */
	public InOutCodeID(InputStream reader, OutputStream writer) {
		super(reader, writer);
	}

	@Override
	public boolean write(IOable write) {
		synchronized(getOut()) {
			try {
				writeByte(ID);
				return super.write(write);
			} catch(SocketException e) {
				return false;
			} catch(IOException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Override
	public byte[] lire() throws IOException, InvalideException, HorsLigneException {
		synchronized(getIn()) {
			if(lireByte() == ID) 
				return lire(lireShortInt());
			throw new InvalideException();
		}
	}

}
