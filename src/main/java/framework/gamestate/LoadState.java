package framework.gamestate;

import javax.swing.SwingWorker;

import framework.GameState;
import framework.GameStateHandler;
import framework.resourceLoaders.ImageLoader;
import framework.resourceLoaders.Music;

import java.awt.*;

/**
 * A class that handles background loading. 
 * 
 * Override the loadResources method to load images from {@link ImageLoader} 
 * or sounds for {@link Music},  etc.
 * */
public class LoadState extends GameState {

	private int timePassedLoading = 0;
	private boolean doneLoadingResources = false;
	
	public LoadState(GameStateHandler gsh) {
		super(gsh);
		loadResourcesInSwingWorker();
	}

	@Override
	public void draw(Graphics2D g) {

	}

	/**
	 * Starts a new {@link SwingWorker} in which 
	 * The time passed loading resources is tracked
	 * and the method loadResources is called, which 
	 * a user can override and put in images and music to load.
	 */
	private void loadResourcesInSwingWorker(){

		new SwingWorker<Integer, Void>() {
			
			@Override
			protected Integer doInBackground() throws Exception {
				timePassedLoading = 1;
				
				//load resources
				loadResources();
				
				//loadResources ended or got interrupted
				doneLoadingResources = true;
				return null;
			}
			
			@Override
			protected void done() {
				super.done();
				System.out.println("Resources Finished Loading.");
				System.out.println("Either because they finished, or an error occured.");
				System.out.println("It took " + timePassedLoading + " Ticks");
			}
		}.execute();
	}
	
	/**
	 * Gets called in a {@link SwingWorker},
	 * that runs in the background. Use this
	 * to load in any images, sounds, etc. needed for the game
	 * while being able to use the {@link GameState}'s update and draw method.
	 * */
	protected void loadResources(){
		
	}
	
	@Override
	public void update() {
		if(!doneLoadingResources)
			timePassedLoading++;
	}
	
	/**
	 * @return returns time that has passed loading the resources
	 */
	public int getPassedTime(){
		return timePassedLoading;
	}
	
	/**
	 * @return : returns whether the resources in background are done loading or not
	 */
	public boolean isDoneLoadingResources(){
		return doneLoadingResources;
	}
}
