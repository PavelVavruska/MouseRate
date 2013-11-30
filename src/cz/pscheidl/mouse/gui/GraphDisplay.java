package cz.pscheidl.mouse.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JPanel;
import cz.pscheidl.mouse.settings.Settings;
import cz.pscheidl.mouse.util.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Counts average delay and draws it actively.
 * 
 * @author Pavel Vavruška
 * 
 */
public class GraphDisplay extends JPanel implements MouseListener {

	private static final long serialVersionUID = -2920615231416789322L;
	private static final int NANOSECONDS_IN_MILLISECOND = 1000000;

	private double averageDelay;
        private final List<Long> averageHistory = new ArrayList<Long>();
        private final Color graphColor;
   
	public GraphDisplay() {
                averageDelay = 0;
		setIgnoreRepaint(false);
                graphColor = Settings.getBgcolor().darker();
	}

	@Override
	public void recieveMouseInfo(long[] mouseArray) {

		double delaySum = 0;

		for (long delay : mouseArray) {
			delaySum += delay;                        
		}
		averageDelay = (delaySum / mouseArray.length)
				/ NANOSECONDS_IN_MILLISECOND;
                averageHistory.add((long) averageDelay);
		paint(getGraphics());

	}

	@Override
	public void paint(Graphics g) {
            
                int x = 0; // position on x-axis
                int lastValue = 0; // value on the left
                int currentValue; // value on the right
                float step = getWidth(); // size of single part of graph
            
		g.setColor(graphColor);
		g.fillRect(0, 0, getWidth(), getHeight());
                
                if (averageHistory.size()>0) {
                    step = (step/averageHistory.size()); // relative shrinking
                }                
                for (long delay : averageHistory) // graph history data
                {
                        x++;
                        currentValue = Math.round(delay);
                        if (lastValue == 0) // handling first input
                        {                             
                            lastValue=currentValue;
                        }
                        g.setColor(graphColor.brighter());
			g.drawLine(Math.round(step*x-step),
                                   lastValue,
                                   Math.round(step*x),
                                   currentValue); // single part of graph
                        lastValue = currentValue;
		}

	}

}
