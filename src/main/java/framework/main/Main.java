package framework.main;

import framework.resourceLoaders.ResourceLocation;
import framework.window.Window;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static Container gamepanel;
    public static ResourceLocation mousePath;

    public static void main(String[] args) {

        String flagString = args[0];
        boolean flag = Boolean.parseBoolean(flagString);
        System.out.println("fullscreen flag is " + flag);

        boolean fullScreen = flag;

        String mouseString = args[3];
        if (mouseString != null && !mouseString.isEmpty())
            mousePath = new ResourceLocation(mouseString);

        new Window(fullScreen);

        JFrame frame = new JFrame(args[1]);

        try {
            gamepanel = ContainerInstantator.getContainerClassFor(args[2]);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("Error occured when reading GamePanel from arguments of launcher.");
            System.err.println(e.getMessage() + " does not seem to exist");

            System.exit(0);
        }

        frame.setContentPane(gamepanel);

        //set size before relative location, or it wont be centered
        frame.setResizable(false);

        frame.requestFocusInWindow();

        if (fullScreen) {
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
