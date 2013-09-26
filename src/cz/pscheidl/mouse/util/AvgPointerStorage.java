package cz.pscheidl.mouse.util;

public class AvgPointerStorage extends PointerStorage {

	private static final int NUM_RESULTS_RECORDED = 100;

	long[] mouseArray;
	int arrayPointer;

	public AvgPointerStorage() {
		super();
		mouseArray = new long[NUM_RESULTS_RECORDED];
		arrayPointer = 0;
	}

	@Override
	public synchronized void mouseRefreshed(long time) {

		mouseArray[arrayPointer] = time;
		arrayPointer++;

		if (arrayPointer == NUM_RESULTS_RECORDED) {
			notifyListeners();
			arrayPointer = 0;
		}

	}

	private void notifyListeners() {

		for (MouseListener listener : listeners) {
			listener.recieveMouseInfo(mouseArray);
		}

	}

}
