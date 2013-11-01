package cz.pscheidl.mouse.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JPanel;

import cz.pscheidl.mouse.settings.Settings;
import cz.pscheidl.mouse.util.MouseListener;

/**
 * Counts average mouse rate and paints it actively.
 * 
 * @author Pavel Pscheidl
 * 
 */
public class RateDisplay extends JPanel implements MouseListener {
	private static final long serialVersionUID = -2920615231416789322L;
	private static final int NANOSECONDS_IN_MILLISECOND = 1000000;
	private static final int MILLISECONDS_IN_SECOND = 1000;

	Font displayFont;
	NumberFormat avgFormat;

	double averageRate = 0;

	public RateDisplay() {
		setIgnoreRepaint(true);
		displayFont = Settings.getMouseFont().deriveFont(36.0F);
		avgFormat = new DecimalFormat("000.##");
	}

	@Override
	public void recieveMouseInfo(long[] mouseArray) {

		double delaySum = 0;

		for (long delay : mouseArray) {
			delaySum += delay;
		}

		averageRate = MILLISECONDS_IN_SECOND
				/ ((delaySum / mouseArray.length) / NANOSECONDS_IN_MILLISECOND);
		paint(getGraphics());

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Settings.getBgcolor());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(displayFont);
		g.drawString(avgFormat.format(averageRate), 0, 40);
	}

}
