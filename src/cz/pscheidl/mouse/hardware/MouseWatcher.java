package cz.pscheidl.mouse.hardware;

import java.awt.MouseInfo;
import java.awt.Point;

import cz.pscheidl.mouse.util.PointerStorage;

public class MouseWatcher implements Runnable{
	
	private static MouseWatcher instance = null;
	
	private Thread mouseWatcherThread;
	private boolean isRunning = false;
	private PointerStorage storage = new PointerStorage();
	
	

	private MouseWatcher(){
		mouseWatcherThread = new Thread(this);
		isRunning = true;
		mouseWatcherThread.start();
	}

	@Override
	public synchronized void run() {
		
		Point oldMouseLocation = new Point(0, 0);
		Point currentMouseLocation = new Point(0, 0);
		long lastUpdateTime = System.nanoTime();
		long updateDuration = 0;
		
		while(isRunning){
			
			currentMouseLocation = MouseInfo.getPointerInfo().getLocation();
			
			if( !oldMouseLocation.equals(currentMouseLocation) ){
				updateDuration = System.nanoTime() - lastUpdateTime;
				lastUpdateTime = System.nanoTime();
				oldMouseLocation = currentMouseLocation;
				storage.mouseRefreshed(updateDuration);
			}
		}
	}
	
	public synchronized void isRunning(boolean isRunning){
		if(isRunning == true && mouseWatcherThread.isInterrupted() ){
			this.isRunning = isRunning;
		} else if(isRunning == true && mouseWatcherThread.isInterrupted() == true){
			return;
		} else if (isRunning == false &&  mouseWatcherThread.isInterrupted() == false){
			this.isRunning = isRunning;
		}
	}
	
	public boolean getIsRunning(){
		return isRunning;
	}
	
	public static MouseWatcher getInstance(){
		
		if(instance == null){
			instance = new MouseWatcher();
		}
		
		return instance;
	}
	
	public PointerStorage getStorage() {
		return storage;
	}

}
