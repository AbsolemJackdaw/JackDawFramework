package framework.resourceLoaders;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.HashMap;

public class Music {

    private static HashMap<String, Clip> clips = new HashMap<String, Clip>();
    private static int gap = 0;
    private static boolean mute = false;

    /**
     * stops sound for given sound
     *
     * @param soundName : name of the sound to end
     */
    public static void close(String soundName) {
        stop(soundName);
        clips.get(soundName).close();
    }

    /**
     * @return returns the frame length : a.k.a total length of the clip
     */
    public static int getFrameLength(String soundName) {
        return clips.get(soundName).getFrameLength();
    }

    /**
     * loops trough all registered sounds and stops them
     * calls : {@link Clip}.stop();
     * and
     * {@link Clip}.close();
     */
    public static void stopAll() {
        for (Clip c : clips.values()) {
            c.stop();
        }
    }

    public static int getPosition(String soundName) {
        return clips.get(soundName).getFramePosition();
    }

    /**
     * @return returns {@link Clip} for given (registered) String.
     * If the string is not recognized, will return null;
     */
    public static Clip getClip(String soundName) {
        Clip c = null;
        if (clips.containsKey(soundName))
            c = clips.get(soundName);
        return c;
    }

    public static float getFrameRate(String soundName) {
        Clip c = null;
        if (clips.containsKey(soundName))
            c = clips.get(soundName);

        float rate = 1f;
        if (c != null)
            rate = c.getFormat().getFrameRate();

        if (rate == 1f && c == null)
            System.out.println("Couldn't calculate frame rate");

        return rate;
    }

    /**
     * Adds the sound from 'path' to a {@link HashMap} under the key for 'name'
     *
     * @param path : given path for sound file (.mp3)
     * @param name : registers the sound to this name
     */
    public static void load(String path, String name) {

        System.out.println(path + " " + name);

        if (clips.get(name) != null)
            return;

        Clip clip;

        try (AudioInputStream ais = AudioSystem.getAudioInputStream(Music.class.getResourceAsStream(path));) {

            final AudioFormat baseFormat = ais.getFormat();
            final AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                    false);
            final AudioInputStream dais = AudioSystem.getAudioInputStream(
                    decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);

            clips.put(name, clip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());

            e.printStackTrace();
        }
    }

    public static void loop(String soundName) {
        loop(soundName, gap, gap, clips.get(soundName).getFrameLength() - 1);
    }

    public static void loop(String soundName, int frame) {
        loop(soundName, frame, gap, clips.get(soundName).getFrameLength() - 1);
    }

    public static void loop(String soundName, int start, int end) {
        loop(soundName, gap, start, end);
    }

    public static void loop(String soundName, int frame, int start, int end) {
        stop(soundName);
        if (mute)
            return;
        clips.get(soundName).setLoopPoints(start, end);
        clips.get(soundName).setFramePosition(frame);
        clips.get(soundName).loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Calls a new thread to play the sound on to prevent concurrent sounds
     *
     * @param soundName : registered name of the wanted sound to play
     */
    public static void play(final String soundName) {
        new Thread(
                () -> playMusic(soundName)).start();
    }

    /**
     * Calls a new thread to play the sound, with gap, on to prevent concurrent sounds
     *
     * @param soundName : registered name of the wanted sound to play
     * @param gap       : length of gap
     */
    public static void play(final String soundName, final int gap) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        playMusic(soundName, gap);
                    }
                }).start();
    }

    private static void playMusic(String soundName) {
        try {
            play(soundName, gap);
        } catch (Exception e) {
            System.err.println("An error occured playing a sound file !");
            e.printStackTrace();
        }
    }

    private static void playMusic(String soundName, int framePosition) {

        if (!clips.containsKey(soundName)) {
            System.err.println("the key " + soundName + " for sounds does not exist");
            return;
        }

        try {

            if (mute)
                return;

            final Clip c = clips.get(soundName);

            if (c == null)
                return;

            if (c.isRunning())
                c.stop();

            c.setFramePosition(framePosition);

            while (!c.isRunning())
                c.start();

        } catch (Exception e) {
            System.out.println("An error occured trying to play a sound file !");
            e.printStackTrace();
        }
    }

    public static void resume(String soundName) {
        if (mute)
            return;
        if (clips.get(soundName).isRunning())
            return;
        clips.get(soundName).start();
    }

    public static void setPosition(String soundName, int frame) {
        clips.get(soundName).setFramePosition(frame);
    }

    public static void stop(String soundName) {
        if (clips.get(soundName) == null)
            return;
        if (clips.get(soundName).isRunning())
            clips.get(soundName).stop();
    }

    /**
     * toggles mute on or off
     */
    public static void toggleMute() {
        mute = !mute;
    }

}