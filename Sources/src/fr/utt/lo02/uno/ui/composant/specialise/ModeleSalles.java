package fr.utt.lo02.uno.ui.composant.specialise;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import fr.utt.lo02.uno.io.reseau.InfoSalle;

public class ModeleSalles extends DefaultListModel {
	private static final long serialVersionUID = 1L;
	private final List<InfoSalle> infos;
	
	
	public ModeleSalles() {
		infos = new ArrayList<InfoSalle>();
	}
	
	public void add(InfoSalle info) {
		infos.add(info);
		change();
	}
	
	@Override
	public void clear() {
		super.clear();
		infos.clear();
		change();
	}
	
	public void change() {
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
		for(final ListDataListener l : getListDataListeners())
			l.contentsChanged(e);
	}
	
	@Override
	public Object getElementAt(int index) {
		return infos.get(index);
	}

	@Override
	public int getSize() {
		return infos.size();
	}

}
