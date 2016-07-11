package framework.main;

import java.awt.Container;

public class ContainerInstantator {

	/**
	 * Instantiates the gamepanel that needs to be used
	 * */
	public static Container getContainerClassFor(String path){

		//get the gamepanel container class from class path
		Class<?> cl = null; 
		try {
			cl = Class.forName(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		//set and get final container
		Container container = null;
		
		try {
			container = (Container) cl.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		if(container==null)
			System.exit(0);

		return container;

	}
}
