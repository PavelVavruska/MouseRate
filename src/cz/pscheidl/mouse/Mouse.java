package cz.pscheidl.mouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cz.pscheidl.mouse.gui.DelayDisplay;
import cz.pscheidl.mouse.gui.MouseDrag;
import cz.pscheidl.mouse.gui.RateDisplay;
import cz.pscheidl.mouse.gui.GraphDisplay;
import cz.pscheidl.mouse.hardware.MouseWatcher;
import cz.pscheidl.mouse.settings.Settings;

/**
 * Represents <b>main</b> application <b>window</b> and application entry
 * point.<br/>
 * Follows <b>SINGLETON</b> design pattern.
 *
 * @author Pavel Pscheidl
 *
 */
public class Mouse extends JFrame {

    private static final long serialVersionUID = -2177780752366304229L;

    private static Mouse instance = null;
    private final MouseWatcher mouseWatcher;
    
    /**
     * GUI settings
     */
    private final Dimension windowSize = new Dimension(600, 250);

    /*
     * GUI Components
     */
    private JPanel basicPanel;
    private JButton closeApp;
    private JLabel hz, ms, hzDescription, msDescription;
    private DelayDisplay delayDisplay;
    private RateDisplay rateDisplay;
    private GraphDisplay graphDisplay;

    private Mouse() {

        /**
         * Basic window settings
         */
        setSize(windowSize);
        setLocation(100, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(null);
        setFocusable(true);
        setIconImage(Settings.getAppicon().getImage());

        /**
         * Initialize components Basic panel must be always initialized first
         */
        initializeBasicPanel();
        initializeCloseApp();
        initializeLabels();
        initializeDisplays();

        /**
         * Listeners - to - add
         */
        addListeners();

        mouseWatcher = MouseWatcher.getInstance();
        mouseWatcher.getStorage().addMouseListener(graphDisplay);
        mouseWatcher.getStorage().addMouseListener(delayDisplay);
        mouseWatcher.getStorage().addMouseListener(rateDisplay);
        
        
        mouseWatcher.startWatching();

        /**
         * At the end, when all components are initialized, JFrame is made
         * visible.
         */
        setVisible(true);

    }

    /**
     * Adds all necessary listeners. All listeners should be initialized and
     * added here.
     */
    private void addListeners() {

        /**
         * Closes whole application after pressing ESCAPE KEY
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    exit();
                }
            }
        });

        /**
         * Makes this window movable via mouse
         */
        addMouseListener(new MouseDrag(this));

    }

    private void initializeCloseApp() {

        ImageIcon closeAppBg = new ImageIcon(getClass().getResource(
                "/cz/pscheidl/mouse/files/closeAppBackground.png"));
        closeApp = new JButton(closeAppBg);
        closeApp.setBorder(null);
        closeApp.setPressedIcon(null);
        closeApp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exit();
            }
        });
        basicPanel.add(closeApp);
        closeApp.setBounds(10, 10, closeAppBg.getIconWidth(),
                closeAppBg.getIconHeight());

    }

    private void initializeBasicPanel() {
        basicPanel = new JPanel();
        basicPanel.setSize(windowSize);
        basicPanel.setBackground(Settings.getBgcolor());
        basicPanel.setLayout(null);
        add(basicPanel);

    }

    private void initializeLabels() {

        hz = new JLabel("Hz");
        hz.setFont(Settings.getMouseFont());
        basicPanel.add(hz);
        hz.setForeground(Color.WHITE);
        hz.setBounds(150, 77, 40, 20);

        hzDescription = new JLabel("average frequency");
        hzDescription.setFont(Settings.getMouseFont());
        basicPanel.add(hzDescription);
        hzDescription.setForeground(Color.WHITE);
        hzDescription.setBounds(74, 160, 220, 20);

        ms = new JLabel("ms");
        ms.setFont(Settings.getMouseFont());
        basicPanel.add(ms);
        ms.setForeground(Color.WHITE);
        ms.setBounds(435, 77, 40, 20);

        msDescription = new JLabel("avg update delay");
        msDescription.setFont(Settings.getMouseFont());
        basicPanel.add(msDescription);
        msDescription.setForeground(Color.WHITE);
        msDescription.setBounds(360, 160, 220, 20);

    }

    private void initializeDisplays() {
        graphDisplay = new GraphDisplay();
        basicPanel.add(graphDisplay);
        graphDisplay.setBounds(0, 200, 600, 250);

        delayDisplay = new DelayDisplay();
        basicPanel.add(delayDisplay);
        delayDisplay.setBounds(361, 105, 200, 40);

        rateDisplay = new RateDisplay();
        basicPanel.add(rateDisplay);
        rateDisplay.setBounds(100, 105, 200, 40);

    }

    private void exit() {
        mouseWatcher.destroyWatcher();
        System.exit(0);
    }

    /**
     *
     * @return Returns Mouse (main window) instance
     */
    public static Mouse getInstance() {
        if (instance == null) {
            instance = new Mouse();
        }
        return instance;
    }

    public static void main(String[] args) {
        Mouse.getInstance();
    }

}
