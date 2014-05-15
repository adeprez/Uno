package fr.utt.lo02.uno.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.ui.composant.ecran.Ecran;
import fr.utt.lo02.uno.ui.listener.ChangeEcranListener;


public class Fenetre extends JFrame implements ChangeEcranListener {
	private static final long serialVersionUID = 1L;
	private Ecran ecran;


	public Fenetre() {
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(getRootPane());
		setIconImage(Images.getInstance().getImage("icone_16.png"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public Fenetre(Ecran ecran) {
		this();
		changeEcran(ecran);
		setVisible(true);
	}
	
	@Override
	public void changeEcran(Ecran nouveau) {
		if(ecran != null) {
			ecran.removeChangeEcranListener(this);
			ecran.fermer();
		}
		if(nouveau == null) {
			dispose();
		} else {
			ecran = nouveau;
			nouveau.addChangeEcranListener(this);
			setTitle("UNO - " + nouveau.getName());
			setContentPane(nouveau);
			validate();
			repaint();
		}
	}

	@Override
	public void focus() {
		setVisible(true);
	}

	@Override
	public boolean askShowed() {
		return getState() != JFrame.ICONIFIED;
	}

}
