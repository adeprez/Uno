package fr.utt.lo02.uno.io;

import java.io.IOException;
import java.io.OutputStream;

import fr.utt.lo02.uno.io.interfaces.Fermable;
import fr.utt.lo02.uno.io.interfaces.IOable;
import fr.utt.lo02.uno.io.interfaces.Outer;
import fr.utt.lo02.uno.jeu.listener.Listenable;


/** 
 * Classe reprensentant un flux de sortie
 */
public class Out extends Listenable implements Outer, Fermable {
	private OutputStream out;


	/**
	 * Cree un nouveau flux de sorti, non d�fini
	 */
	public Out() {}

	/**
	 * Cree un nouveau flux de sortie a partir de l'outputstream specifie
	 * @param out le flux de sortie
	 */
	public Out(OutputStream out) {
		setOut(out);
	}

	/**
	 * Assigne le nouveau flux de sortie
	 * @param out le nouveau flux de sortie
	 */
	public void setOut(OutputStream out) {
		this.out = out;
	}
	
	/**
	 * @return le flux de sortie
	 */
	public OutputStream getOut() {
		return out;
	}

	/**
	 * Ecrit un octet sur le flux de sortie
	 * @param b l'octet a ecrire
	 * @throws IOException si une erreur d'entree/sortie survient
	 */
	protected void writeByte(byte b) throws IOException {
		out.write(b);
	}

	@Override
	public boolean fermer() {
		if(out == null)
			return true;
		try {
			out.flush();
			out.close();
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean write(IOable write) {
		try {
			byte[] b = write.getBytes();
			out.write(IO.getBytes(b.length));
			out.write(b);
			out.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}



}
