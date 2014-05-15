package fr.utt.lo02.uno.ui.composant.specialise.jeu;

import fr.utt.lo02.uno.io.Images;
import fr.utt.lo02.uno.jeu.effet.Effet;
import fr.utt.lo02.uno.jeu.listener.EffetListener;
import fr.utt.lo02.uno.ui.composant.PanelImage;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class PanelEffetsJoueur extends PanelImage implements EffetListener {
	private static final long serialVersionUID = 1L;
	private final Map<Effet, Component> effets;


	public PanelEffetsJoueur() {
		effets = new HashMap<Effet, Component>();
	}

	@Override
	public void ajoutEffet(Effet effet) {
		JComponent c = new JLabel(new ImageIcon(Images.getInstance().getImage(effet.getCheminImage())));
		c.setToolTipText(effet.toString());
		effets.put(effet, c);
		add(c);
		if(getParent() != null)
			getParent().validate();
		repaint();
	}

	@Override
	public void retireEffet(Effet effet) {
		if(effets.containsKey(effet)) {
			remove(effets.remove(effet));
			if(getParent() != null)
				getParent().validate();
			repaint();
		}
	}


}
