package framework.resourceLoaders;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {

    /**
     * Loads images into the given array.
     *
     * @param p    : prefix to which _x is added, where x is number [0;arraymax]
     * @param list : the array to be filled with images
     */
    @Deprecated
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
     * Loads all images given up as path arguments
     *
     * @param resLocs : paths too images to load
     * @return a list of {@link BufferedImage}s
     */
    public static List<BufferedImage> loadList(ResourceLocation... resLocs) {

        ArrayList<BufferedImage> list = new ArrayList<>();
        int index = 0;
        for (ResourceLocation resLoc : resLocs) {
            String path = resLoc.raw() + "_" + index++ + ".png";
            try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
                BufferedImage img = ImageIO.read(stream);
                list.add(img);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Loads all images given up as path arguments
     *
     * @param pathPrefix          : path to texture. will get suffixed with '_x' where x is in range [0;totalTexturesToLoad]
     * @param totalTexturesToLoad : total number of textures to load in the given directory.
     * @return a list of {@link BufferedImage}s
     */
    public static List<BufferedImage> loadList(ResourceLocation pathPrefix, int totalTexturesToLoad) {

        ArrayList<BufferedImage> list = new ArrayList<>();
        int index = 0;
        while (index < totalTexturesToLoad) {
            String path = pathPrefix.raw() + "_" + index++ + ".png";
            try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
                BufferedImage img = ImageIO.read(stream);
                list.add(img);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Loads all images given up as path arguments
     *
     * @param pathPrefix          : path to texture. will get suffixed with '_x' where x is in range [0;totalTexturesToLoad]
     * @param totalTexturesToLoad : total number of textures to load in the given directory.
     * @return array of {@link BufferedImage}s
     */
    public static BufferedImage[] loadArray(ResourceLocation pathPrefix, int totalTexturesToLoad) {

        BufferedImage[] list = new BufferedImage[totalTexturesToLoad];
        int index = 0;
        while (index < totalTexturesToLoad) {
            String path = pathPrefix.raw() + "_" + index + ".png";
            try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
                BufferedImage img = ImageIO.read(stream);
                list[index++] = img;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * Loads all images given up as path arguments
     *
     * @param resLocs : paths too images to load
     * @return array of {@link BufferedImage}s
     */
    public static BufferedImage[] loadArray(ResourceLocation... resLocs) {

        BufferedImage[] list = new BufferedImage[resLocs.length];
        int index = 0;
        for (ResourceLocation resLoc : resLocs) {
            String path = resLoc.raw() + "_" + index + ".png";
            try (InputStream stream = ImageLoader.class.getResourceAsStream(path)) {
                BufferedImage img = ImageIO.read(stream);
                list[index++] = img;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * loads a simple texture
     */
    public static BufferedImage loadSprite(ResourceLocation resLoc) {

        BufferedImage tempImg = null;

        try (InputStream is = ImageLoader.class.getResourceAsStream(resLoc.raw())) {
            tempImg = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return tempImg;

    }
}
