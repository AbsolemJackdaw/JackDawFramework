package framework.input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import framework.GamePanel;

public class MouseHandler extends MouseAdapter implements MouseWheelListener {

	public static double mouseX = 0;
	public static double mouseY = 0;

	public static boolean click;
	private static boolean prevClick;

	public static Point clicked = null;

	public static int wheelY = 0;

	public static void update(){
		double x = GamePanel.INSTANCE.getLocationOnScreen().getX();
		double y = GamePanel.INSTANCE.getLocationOnScreen().getY();

		double xm = MouseInfo.getPointerInfo().getLocation().getX();
		double ym = MouseInfo.getPointerInfo().getLocation().getY();

		mouseX = xm-x;
		mouseY = ym-y;

		if(click && !prevClick)
			click = false ; // prevClick = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		click = true;
		clicked = e.getPoint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() > 0)
			wheelY -=10;
		else if(e.getWheelRotation() < 0)
			wheelY+=10;
	}
}
