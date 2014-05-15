package fr.utt.lo02.uno.ui.composant.ecran;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.langue.Texte;
import fr.utt.lo02.uno.ui.composant.Bouton;


public class EcranRegles extends Ecran implements ActionListener {
	private static final long serialVersionUID = 1L;

	
	public EcranRegles() {
		setLayout(new BorderLayout());
		setImage(Images.getInstance().getImage("fond.jpg"));
		setMax(true);
		setName(Texte.get("Règles du jeu"));
		
		JPanel haut = new JPanel();
		AbstractButton btn = new Bouton(Texte.get("Retour"), new ImageIcon(Images.getInstance().getImage("retour.png")));
		haut.setOpaque(false);
		haut.add(btn);
		btn.addActionListener(this);
		add(haut, BorderLayout.NORTH);
		
		try {
			JEditorPane texte = new JEditorPane(getClass().getResource("/regles.html"));
			texte.setOpaque(false);
			JScrollPane jsp = new JScrollPane(texte);
			jsp.getViewport().setOpaque(false);
			add(jsp, BorderLayout.CENTER);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		changeEcran(new EcranAccueil());
	}
	
	
}
