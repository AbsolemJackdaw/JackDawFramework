package framework;


import framework.input.KeyHandler;
import framework.input.MouseHandler;
import framework.main.Main;
import framework.resourceLoaders.ImageLoader;
import framework.window.Window;
import javafx.embed.swing.JFXPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class GamePanel extends JFXPanel implements Runnable {

    public static GamePanel INSTANCE;
    private final int WINDOW_WIDTH = Window.getWidth();
    private final int WINDOW_HEIGHT = Window.getHeight();
    // image to get graphics from
    protected BufferedImage screen_canvas;
    protected Graphics2D graphics2D;
    // game thread
    private Thread thread;
    private boolean running;
    private GameStateHandler gameStateHandler;

    public GamePanel() {
        INSTANCE = this;

        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setFocusable(true);
        requestFocus();

        if (Main.mousePath != null) {
            System.out.println("custom mouse was set. path for mouse image is " + Main.mousePath);
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    ImageLoader.loadSprite(Main.mousePath), new Point(0, 0), "game cursor");
            setCursor(customCursor);
        }

        System.out.println("GamePanel : Initializing game");
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (thread == null) {

            MouseHandler mh = new MouseHandler();
            KeyHandler kh = new KeyHandler();

            thread = new Thread(this);

            addKeyListener(kh);
            addMouseListener(mh);
            addMouseWheelListener(mh);

            thread.start();
        }
    }

    @Override
    public void run() {
        init();
        runGameLoop();
    }

    /**
     * finishes drawing cycle
     */
    private void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(screen_canvas, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, null);
        g2.dispose();
    }

    /**
     * draws the current gamestate to the screen
     */
    protected void draw() {
        gameStateHandler.draw(graphics2D);
    }

    /**
     * runs an update of the gamestate, keyhandler, and then mouse. in that order.
     */
    protected void update() {
        gameStateHandler.update();
        KeyHandler.update();
        MouseHandler.update();
    }

    private void init() {

        System.out.println("launching...");

        screen_canvas = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics2D = (Graphics2D) screen_canvas.getGraphics();

        running = true;

        gameStateHandler = getCustomGameStateHandler();
    }

    /**
     * this needs to be absolutely overridden and given your class that extends {@link GameStateHandler}
     */
    public GameStateHandler getCustomGameStateHandler() {
        return new GameStateHandler();
    }

    private void runGameLoop() {
        //Best Update System I found on the net !
        //http://entropyinteractive.com/2011/02/game-engine-design-the-game-loop/
        //thanksx1000 to this dude, as well as cuddos

        // convert the time to seconds
        double lastTime = (double) System.nanoTime() / 1_000_000_000.0;
        double maxTimeDiff = 0.5;
        int skippedFrames = 1;
        int maxSkippedFrames = 5;
        double targetUpdates = 1.0 / 60.0;

        while (running) {
            // convert the time to seconds
            double currTime = (double) System.nanoTime() / 1_000_000_000.0;

            if ((currTime - lastTime) > maxTimeDiff)
                lastTime = currTime;

            if (currTime >= lastTime) {

                // assign the time for the next update
                lastTime += targetUpdates;
                update();
                //				updates++;

                if ((currTime < lastTime) || (skippedFrames > maxSkippedFrames)) {

                    draw();
                    drawToScreen();
                    skippedFrames = 1;
                    //					frames++;
                } else
                    skippedFrames++;
            } else {
                // calculate the time to sleep
                int sleepTime = (int) (1000.0 * (lastTime - currTime));

                // sanity check
                if (sleepTime > 0) {
                    // sleep until the next update
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
            }

            //			if(System.currentTimeMillis() - timer > 1000) {
            //				timer += 1000;
            ////				System.out.println(updates + " Ticks, Fps " + frames);
            //				updates = 0;
            //				frames = 0;
            //
            //			}
        }
    }
}
