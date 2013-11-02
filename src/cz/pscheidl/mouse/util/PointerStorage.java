package cz.pscheidl.mouse.util;

import java.util.ArrayList;
import java.util.List;

public abstract class PointerStorage {

	List<MouseListener> listeners = new ArrayList<>();

	public PointerStorage() {

	}

	public abstract void mouseRefreshed(long time);

	public synchronized void addMouseListener(MouseListener listener) {
		listeners.add(listener);
	}

	public synchronized void removeMouseListener(MouseListener listener) {
		listeners.remove(listener);
	}

}
