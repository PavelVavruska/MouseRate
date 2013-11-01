package cz.pscheidl.mouse.gui;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

/**
 * Makes your JFrames easy to drag all over the screen
 *
 * @author Pavel Pscheidl
 *
 */
public class MouseDrag implements MouseListener, Runnable {

    /**
     * Frame to move with
     */
    private JFrame frame;
    private Point originalMousePos;
    private Point originalFramePos;
    private Thread dragThread;

    boolean drag = false;

    public MouseDrag(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        originalMousePos = MouseInfo.getPointerInfo().getLocation();
        originalFramePos = frame.getLocation();
        drag = true;
        dragThread = new Thread(this);
        dragThread.start();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        drag = false;


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Calculates the <b>distance<b> between <b>original</b> Mouse position
     * (when mouse button was pressed) and position <b>right now</b>. Then
     * inverts the results for bot x and y axis.<br/>
     * This calculated distance is then added to original Frame position.
     *
     */
    private void drag() {

        Point currentMousePt;
        Point finalPoint;
        int distanceX, distanceY;

        currentMousePt = MouseInfo.getPointerInfo().getLocation();
        distanceX = 0 - (originalMousePos.x - currentMousePt.x);
        distanceY = 0 - (originalMousePos.y - currentMousePt.y);
        finalPoint = new Point(originalFramePos.x + distanceX,
                originalFramePos.y + distanceY);
        frame.setLocation(finalPoint);

    }

    @Override
    public void run() {
        while (drag) {

            drag();
        }

    }

}
