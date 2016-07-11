package framework;


import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import framework.input.KeyHandler;
import framework.input.MouseHandler;
import framework.main.Main;
import framework.resourceLoaders.ImageLoader;
import framework.window.Window;
import javafx.embed.swing.JFXPanel;

@SuppressWarnings("serial")
public class GamePanel extends JFXPanel implements Runnable{

	private int W = Window.getWidth();
	private int H = Window.getHeight();

	// game thread
	private Thread thread;
	private boolean running;

	// image to get graphics from
	protected BufferedImage image;
	protected Graphics2D g;

	private GameStateHandler ghs;

	public static GamePanel instance;

	public GamePanel() {
		instance = this;

		setPreferredSize(new Dimension(W, H));
		setFocusable(true);
		requestFocus();

		if(Main.mousePath.length() > 0)
		{
			System.out.println("custom mouse was set. path for mouse image is " + Main.mousePath);
			Cursor samCursor = Toolkit.getDefaultToolkit().createCustomCursor(
					ImageLoader.loadSprite(Main.mousePath), new Point(0, 0), "game cursor");
			setCursor(samCursor);
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

	/**finishes drawing cycle*/
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, W, H, null);
		g2.dispose();
	}

	/**draws the current gamestate to the screen */
	protected void draw() {
		ghs.draw(g);
	}

	/**runs an update of the gamestate, keyhandler, and then mouse. in that order.*/
	protected void update() {
		ghs.update();
		KeyHandler.update();
		MouseHandler.update();
	}

	private void init() {

		System.out.println("launching...");

		image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		ghs = getCustomGameStateHandler();
	}
	
	/**this needs to be absolutely overridden and given your class that extends Gamestatehandler*/
	public GameStateHandler getCustomGameStateHandler(){
		return null;
	}

	private void runGameLoop() {
		//Best Update System I found on the net !
		//http://entropyinteractive.com/2011/02/game-engine-design-the-game-loop/
		//thanksx1000 to this dude, as well as cuddos

		// convert the time to seconds
		double lastTime = (double)System.nanoTime() / 1000000000.0;
		double maxTimeDiff = 0.5;
		int skippedFrames = 1;
		int maxSkippedFrames = 5;
		double targetUpdates = 1.0/60.0;

		//		int updates = 0;
		//		int frames = 0;
		//		long timer = System.currentTimeMillis();

		while(running)
		{
			// convert the time to seconds
			double currTime = (double)System.nanoTime() / 1000000000.0;

			if((currTime - lastTime) > maxTimeDiff)
				lastTime = currTime;

			if(currTime >= lastTime){

				// assign the time for the next update
				lastTime += targetUpdates;
				update();
				//				updates++;

				if((currTime < lastTime) || (skippedFrames > maxSkippedFrames)){

					draw();
					drawToScreen();
					skippedFrames = 1;
					//					frames++;
				}
				else
					skippedFrames++;
			}else{
				// calculate the time to sleep
				int sleepTime = (int)(1000.0 * (lastTime - currTime));

				// sanity check
				if(sleepTime > 0)
				{
					// sleep until the next update
					try{
						Thread.sleep(sleepTime);
					}catch(InterruptedException e){
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
