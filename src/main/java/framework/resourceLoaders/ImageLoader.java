package framework.resourceLoaders;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    /**
     * Loads images into the given array.
     *
     * @param p    : prefix to which _x is added, where x is number [0;arraymax]
     * @param list : the array to be filled with images
     */
    public static void loadImages(BufferedImage[] list, String p) {

        for (int i = 0; i < list.length; i++) {

            String path = p + "_" + i + ".png";

            System.out.println(path);

            BufferedImage tempImg = null;

            try {
                tempImg = ImageIO.read(ImageLoader.class.getResourceAsStream(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (tempImg != null)
                list[i] = tempImg;
        }

    }

    /**
     * loads a simple texture
     */
    public static BufferedImage loadSprite(String path) {

        BufferedImage tempImg = null;

        System.out.println(path);

        InputStream is = ImageLoader.class.getResourceAsStream(path);

        if (is != null)
            try {
                tempImg = ImageIO.read(is);
            } catch (IOException e) {
                System.out.println("image " + path + " could not be loaded");
                e.printStackTrace();
            }
        else
            System.out.println("no location for " + path);

        return tempImg;

    }
}
