package framework;

import java.awt.*;

public abstract class GameState {

    public static final String FIRST_SCREEN = "first_screen";
    protected GameStateHandler gsh;

    public GameState(GameStateHandler gsh) {
        this.gsh = gsh;
    }

    public abstract void draw(Graphics2D g);

    public abstract void update();
}