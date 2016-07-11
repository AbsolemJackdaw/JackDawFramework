package framework;


import java.awt.Graphics2D;

import framework.input.MouseHandler;

public class GameStateHandler {

	public int currentGameState;

	public static GameState[] states;

	public GameStateHandler() {
		
		states = new GameState[20];
		
		//fail safe
		currentGameState = 0;
		loadState(0);
	}

	/**@return returns the index of the current loaded gamestate*/
	public int getCurrentGameState() {
		return currentGameState;
	}

	public void draw(Graphics2D g) {
		states[currentGameState].draw(g);
	}

	public void update() {
		states[currentGameState].update();
	}

	/**sets previous state to null and gets a new instance for the given game state index
	 * 
	 * @param state : the index fo the gamestate to be loaded*/
	public void changeGameState(int state) {
		MouseHandler.clicked = null;
		
		unloadState(currentGameState);
		currentGameState = state;
		loadState(state);
	}

	private void unloadState(int state){
		states[state] = null;
	}

	private void loadState(int state){
	}
	
	/**Should return how many game states are available.
	 * default is 20.
	 * implemented to better handle */
	protected int totalGameStates(){
		return 20;
	}
}
