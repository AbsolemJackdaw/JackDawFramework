package framework;


import java.awt.Graphics2D;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import framework.input.MouseHandler;

public class GameStateHandler {

	public int currentGameState = 0;

	//	public static GameState[] states;

	private static HashMap<Integer, Class<? extends GameState>> thestates;
	private GameState theCurrentState = null; 

	public GameStateHandler() {

		//		states = new GameState[totalGameStates()];
		thestates = new HashMap<Integer, Class<? extends GameState>>();

	}

	/**@return returns the index of the current loaded gamestate*/
	public int getCurrentStateIndex() {
		return currentGameState;
	}

	/**@return returns the currently loaded, updating and drawing gamestate*/
	public GameState getCurrentGameState(){
		return theCurrentState;
	}

	public void draw(Graphics2D g) {
		//		states[currentGameState].draw(g);

		if(theCurrentState != null)
			theCurrentState.draw(g);
	}

	public void update() {
		//		states[currentGameState].update();

		if(theCurrentState != null)
			theCurrentState.update();
	}

	/**
	 * sets previous state to null and gets a new instance for the given game state index
	 * @param state : the index fo the gamestate to be loaded
	 * */
	public void changeGameState(int state) {
		MouseHandler.clicked = null;

		unloadState(currentGameState);
		currentGameState = state;
		loadState(state);
	}

	private void unloadState(int state){
		//		states[state] = null;
		theCurrentState  = null;
	}

	private void loadState(int state){
		Class<? extends GameState> cgs  = thestates.get(currentGameState);
		GameState gs = null;

		try {
			gs = cgs.getConstructor(GameStateHandler.class).newInstance(this);
		} catch (
				InstantiationException |
				IllegalAccessException |
				IllegalArgumentException |
				InvocationTargetException |
				NoSuchMethodException |
				SecurityException e) {

			e.printStackTrace();
		}

		if(gs == null){
			System.out.println("fatal error. the gamestate for " + state + " was unexistent !");
			System.exit(0);
		}

		theCurrentState = gs;
	}

	/**
	 * adds a GameState instance to the pool of gamestate instances
	 * @param myGameState : the class that extends gamestate and should be registered
	 * @param index : the unique identifier it should be registered with*/
	
	protected void addGameState(Class<? extends GameState> myGameState, int index){

		if(!thestates.containsKey(index) && !thestates.containsValue(myGameState))
			thestates.put(index, myGameState);

		else if (thestates.containsKey(index)){
			System.out.println("You tried registereding " + index + " for " + myGameState.getName());

			System.out.println("But the game state for index "+index+
					" wasn't registered because it was already registered for "+
					thestates.get(index).getName());
		}
		else if (thestates.containsValue(myGameState)){
			System.out.println("The gamestate " + myGameState + "was skipped because it was already registered.");
			System.out.println("Gamestates should be registered only once !");
			
		}
	}
}
