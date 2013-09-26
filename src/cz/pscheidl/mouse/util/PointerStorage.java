package cz.pscheidl.mouse.util;

import java.util.ArrayList;
import java.util.List;

public class PointerStorage {
	
	List<MouseListener> listeners = new ArrayList<MouseListener>();
	
	public PointerStorage(){

	}
	
	public synchronized void mouseRefreshed(long time){
		
		for(MouseListener listener : listeners){
			listener.stateChanged(time);
		}
		
	}
	
	public synchronized void addMouseListener(MouseListener listener){
		listeners.add(listener);
	}
	
	public synchronized void removeMouseListener(MouseListener listener){
		listeners.remove(listener);
	}

}
