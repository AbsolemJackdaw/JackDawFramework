package framework;

import java.awt.Graphics2D;

public abstract class GameState {

	public GameState(GameStateHandler gsh){};
		
	protected GameStateHandler gsh;

	public void draw (Graphics2D g){};

	public void update(){};

}