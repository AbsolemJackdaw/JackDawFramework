package framework.main;

import framework.GamePanel;

import java.awt.*;

public class ContainerInstantator {

    /**
     * Instantiates the gamepanel that needs to be used
     */
    public static Container getContainerClassFor(String path) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        if (path == null || path.isEmpty())
            throw new NullPointerException("game panel argument in launcher is absent or empty");


        Class<?> cl = Class.forName(path);
        Object inst = cl.newInstance();
        if (inst instanceof GamePanel panel)
            return panel;

        return null;
    }
}
