package framework;

import java.awt.Graphics2D;

public abstract class GameState {

	protected GameStateHandler gsh;

	public GameState(GameStateHandler gsh){this.gsh = gsh;};
		
	public void draw (Graphics2D g){};

	public void update(){};

}