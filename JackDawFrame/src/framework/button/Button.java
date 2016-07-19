package framework.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import framework.GameStateHandler;
import framework.input.MouseHandler;
import framework.window.Window;

public class Button {

	protected double posX;
	protected double posY;
	protected final String name;

	protected int sizeX;
	protected int sizeY;
	
	protected Font text;

	private Rectangle box;

	protected boolean isLit;

	public Button(String name, int x, int y, int sizeX, int sizeY) {
		this.name = name;
		
		posX=x;
		posY=y;
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		text = new Font("SquareFont", Font.PLAIN, Window.getGameScale(35));

		box = new Rectangle((int)posX - Window.getGameScale(sizeX/2), (int)posY+Window.getGameScale(sizeY/2), Window.getGameScale(sizeX), Window.getGameScale(sizeY));
	}

	public Button(String name, int x, int y) {
		this(name, x, y, 128, 64);
	}

	public void draw(Graphics2D g){

		g.setColor(isLit ? Color.green.darker() : Color.white);
		g.setFont(text);

		int h = g.getFontMetrics().getHeight();
		int w = g.getFontMetrics().stringWidth(name);

		g.drawImage(isLit ? getButtonImageLit() : getButtonImage() , (int)posX- Window.getGameScale(sizeX/2), (int)posY, Window.getGameScale(getButtonImage().getWidth()*4), Window.getGameScale(getButtonImage().getHeight()*4), null);

		g.drawString(name, 
				((int)posX - Window.getGameScale(sizeX/2)) - w/2 + Window.getGameScale(sizeX/2),
				(int)posY + h + Window.getGameScale(sizeY/2 + 2) );

	}

	public void update(GameStateHandler gsh){
		if(getBox().contains(MouseHandler.mouseX , MouseHandler.mouseY))
			setLit(true);
		else
			setLit(false);

		if(MouseHandler.click && isLit())
			click(gsh);
	}

	public void click(GameStateHandler gsh){};

	public Rectangle getBox() {
		return box;
	}

	public void setLit(boolean b){
		isLit = b;
	}

	public boolean isLit() {
		return isLit;
	}

	public String getName() {
		return name;
	}
	
	public BufferedImage getButtonImageLit(){
		return null;
	}
	
	public BufferedImage getButtonImage(){
		return null;
	}
}
