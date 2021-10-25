package framework;


import framework.gamestate.GameStateIntro;
import framework.input.MouseHandler;
import framework.resourceLoaders.exceptions.StateExistsException;
import framework.resourceLoaders.exceptions.StateNotFoundException;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class GameStateHandler {

    public static final String GAME_STATE_FRAMEWORK_INTRO = "introframework";
    private static HashMap<String, Class<? extends GameState>> gameStates;
    public String currentGameStateName = "";
    private GameState currentGameState = null;

    public GameStateHandler() {

        gameStates = new HashMap<>();

        addGameState(GameStateIntro.class, GAME_STATE_FRAMEWORK_INTRO);
        changeGameState(GAME_STATE_FRAMEWORK_INTRO);
    }

    /**
     * @return returns the index of the current loaded gamestate
     */
    public String getCurrentStateIndex() {
        return currentGameStateName;
    }

    /**
     * @return returns the currently loaded, updating and drawing gamestate
     */
    public GameState getCurrentGameStateName() {
        return currentGameState;
    }

    public void draw(Graphics2D g) {

        if (currentGameState != null)
            currentGameState.draw(g);
    }

    public void update() {

        if (currentGameState != null)
            currentGameState.update();
    }

    /**
     * sets previous state to null and gets a new instance for the given game state index
     *
     * @param state : the index fo the gamestate to be loaded
     */
    public void changeGameState(String state) {
        MouseHandler.clicked = null;

        unloadState();
        try {
            loadState(state);
        } catch (StateNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        currentGameStateName = state;

    }

    private void unloadState() {
        currentGameState = null;
    }

    private void loadState(String state) throws StateNotFoundException {

        if (!gameStates.containsKey(state))
            throw new StateNotFoundException("No Such State found !! > " + state);

        Class<? extends GameState> cgs = gameStates.get(state);

        try {
            currentGameState = cgs.getConstructor(GameStateHandler.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.err.println(e.getMessage());
        }

        if (currentGameState == null) {
            throw new NullPointerException("fatal error. the gamestate for " + state + " was none existent !");
        }
    }

    /**
     * adds a GameState instance to the pool of gamestate instances
     *
     * @param myGameState : the class that extends gamestate and should be registered
     * @param uniqueID    : the unique identifier it should be registered with
     */

    protected void addGameState(Class<? extends GameState> myGameState, String uniqueID) {
        if (uniqueID == null || uniqueID.isEmpty() || myGameState == null)
            throw new NullPointerException("cannot register absent values for gamestates");

        if (gameStates.containsKey(uniqueID))
            throw new StateExistsException(uniqueID + " was already assigned to " + gameStates.get(uniqueID).getName());

//        if (gameStates.containsValue(myGameState))
//            throw new StateExistsException("an instance for this game state already exists.");

        gameStates.put(uniqueID, myGameState);
    }
}
