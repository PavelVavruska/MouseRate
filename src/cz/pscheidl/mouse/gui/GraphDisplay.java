package cz.pscheidl.mouse.gui;

import java.awt.Color;
import java.awt.Graphics;

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
    private static final Color graphColor = Settings.getBgcolor().darker(); 
    private static final List<Double> averageHistory = new ArrayList<>();

    private double averageDelay;

    public GraphDisplay() {
        averageDelay = 0;
        setIgnoreRepaint(false);

    }

    @Override
    public void recieveMouseInfo(final long[] mouseArray) {

        double delaySum = 0;

        for (long delay : mouseArray) {
            delaySum += delay;
        }
        averageDelay = delaySum / mouseArray.length;
        averageDelay = averageDelay / NANOSECONDS_IN_MILLISECOND;
        averageHistory.add(averageDelay);
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

        if (averageHistory.size() > 0) {
            step = (step/averageHistory.size()); // relative shrinking
        }                
        for (double delay : averageHistory) // graph history data
        {
            x++;
            currentValue = (int) Math.round(delay);
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
