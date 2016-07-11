package framework.window;

import java.awt.Dimension;
import java.awt.Toolkit;


public class Window {

	private static double scale = 1f;
	private static int screenWidth;
	private static int screenHeight;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Window(boolean isfullscreen){
		
		if(isfullscreen)
		screenWidth = screenSize.width;
		else
			screenWidth = 1024;
		
		screenHeight = screenWidth * 9 / 16;
		
		scale = (double)screenWidth / 1024f;
	}
	
	public static int getWidth(){
		return screenWidth;
	}
	
	public static int getHeight(){
		return screenHeight;
	}
	
	public static double getScale(){
		return scale;
	}
	
	public static int getGameScale(double number){
		double f = number * getScale();
		double f2 = Math.ceil(f);
		
		return (int)f2;
	}
}
