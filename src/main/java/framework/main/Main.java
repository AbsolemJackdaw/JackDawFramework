package framework.main;

import java.awt.Container;

import javax.swing.JFrame;

import framework.window.Window;

public class Main{

	public static Container gamepanel;
	public static String mousePath = "";

	public static void main(String[] args) {
		
		String flagString = args[0];
		boolean flag = Boolean.parseBoolean(flagString);
		System.out.println("fullscreen flag is " + flag);

		boolean fullScreen = flag;
		
		mousePath = args[3];
		
		new Window(fullScreen);

		JFrame frame = new JFrame(args[1]);

		gamepanel = ContainerInstantator.getContainerClassFor(args[2]);
		frame.setContentPane(gamepanel);

		//set size before relative location, or it wont be centered
		frame.setResizable(false);

		frame.requestFocusInWindow();

		if(fullScreen){
			//fullscreen without borders
			frame.setUndecorated(true);
			//fullscreen
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
