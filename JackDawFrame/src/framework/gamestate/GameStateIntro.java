package framework.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import framework.GameState;
import framework.GameStateHandler;
import framework.resourceLoaders.ImageLoader;
import framework.window.Window;

public class GameStateIntro extends GameState {

	private BufferedImage logo = null;
	private int tickTimerCounter = 60*5;
	private float alpha = 1f;
	private float step = 1f / (60f*2f);

	public GameStateIntro(GameStateHandler gsh) {
		super(gsh);

		logo = ImageLoader.loadSprite("/jackdawframe/images/logo/logo.png");

	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);

		g.drawImage(logo, 
				(Window.getWidth()/4)*2 - Window.getGameScale(64),
				(Window.getHeight()/4)*2 - Window.getGameScale(64),
				Window.getGameScale(128), 
				Window.getGameScale(128), 
				null);

		g.setColor(Color.GREEN.darker());

		g.setFont(new Font("Times New Roman", Font.PLAIN, Window.getGameScale(80)));

		g.drawString("JackDaw", 
				Window.getWidth()/2 - g.getFontMetrics().stringWidth("JackDaw")/2,
				(Window.getHeight()/4)*1 + g.getFontMetrics().getHeight()/2);

		g.drawString("FrameWork", 
				Window.getWidth()/2 - g.getFontMetrics().stringWidth("FrameWork")/2,
				(Window.getHeight()/4)*3);


		g.setColor(new Color(0f, 0f, 0f, alpha));
		g.fillRect(0, 0, Window.getWidth(), Window.getHeight());
	}

	@Override
	public void update() {
		super.update();

		tickTimerCounter--;

		if(tickTimerCounter < 60*2){
			alpha += step;
		}else if(tickTimerCounter > 60*3){
			alpha -= step;
		}

		if(alpha > 1f)
			alpha = 1f;
		if(alpha < 0f)
			alpha = 0f;
		
		if(tickTimerCounter <= 0){
			gsh.changeGameState(0);
		}
	}
}
