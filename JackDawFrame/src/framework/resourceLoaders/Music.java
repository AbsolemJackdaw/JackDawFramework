package framework.resourceLoaders;

import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {

	private static HashMap<String, Clip> clips;
	private static int gap;
	private static boolean mute = false;

	public static void close(String s) {
		stop(s);
		clips.get(s).close();
	}

	public static int getFrames(String s) {
		return clips.get(s).getFrameLength();
	}

	public static void stopAll(){
		for(Clip c : clips.values())
			c.stop();
	}

	public static int getPosition(String s) {
		return clips.get(s).getFramePosition();
	}

	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}

	public static Clip getClip(String s)
	{
		Clip c = null;
		if(clips.containsKey(s))
			c = clips.get(s);
		return c;
	}

	public static float getFrameRate(String s){
		Clip c = null;
		if(clips.containsKey(s))
			c = clips.get(s);

		float rate = 1f;
		if(c != null)
			rate = c.getFormat().getFrameRate();

		if(rate == 1f && c == null)
			System.out.println("Couldn't calculate frame rate");

		return rate;
	}

	public static void load(String s, String n) {
		
		System.out.println(s + " " + n);
		
		if (clips.get(n) != null)
			return;
		
		Clip clip;

		AudioInputStream ais;
		
		try {
			ais = AudioSystem.getAudioInputStream(Music.class.getResourceAsStream(s));
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
			
			clips.put(n, clip);
			
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

//	public static void oldload(String s, String n) {
//
//		System.out.println(s + " " + n);
//
//		if (clips.get(n) != null)
//			return;
//
//		Clip clip = null;
//		AudioInputStream ais = null;
//		InputStream is = null;
//
//		try {
//			is = Music.class.getClass().getResourceAsStream(s);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if(is == null){
//			System.out.println(s + " is not a valid directory or file !");
//			System.out.println("skipping file for name " + n);
//			return;
//		}
//
//		try {
//			ais = AudioSystem.getAudioInputStream(is);
//		} catch (UnsupportedAudioFileException | IOException e1) {
//			e1.printStackTrace();
//		}
//
//		final AudioFormat baseFormat = ais.getFormat();
//		final AudioFormat decodeFormat = new AudioFormat(
//				AudioFormat.Encoding.PCM_SIGNED,
//				baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
//				baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
//				false);
//		final AudioInputStream dais = AudioSystem.getAudioInputStream(
//				decodeFormat, ais);
//		try {
//			clip = AudioSystem.getClip();
//		} catch (LineUnavailableException e) {
//			e.printStackTrace();
//		}
//
//
//		try {
//			clip.open(dais);
//		} catch (LineUnavailableException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			if(clip != null)
//				clips.put(n, clip);
//		}
//	}

	public static void loop(String s) {
		loop(s, gap, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int frame) {
		loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
	}

	public static void loop(String s, int start, int end) {
		loop(s, gap, start, end);
	}

	public static void loop(String s, int frame, int start, int end) {
		stop(s);
		if (mute)
			return;
		clips.get(s).setLoopPoints(start, end);
		clips.get(s).setFramePosition(frame);
		clips.get(s).loop(Clip.LOOP_CONTINUOUSLY);
	}

	public static void play(final String s){
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						playMusic(s);	
					}
				}).start();
	}

	public static void play(final String s, final int gap){
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						playMusic(s, gap);	
					}
				}).start();
	}

	private static void playMusic(String s) {
		if(!clips.containsKey(s)){
			System.out.println("the key " + s + " for sounds does not exist");
			return;
		}

		try {
			play(s, gap);
		} catch (Exception e) {
			System.out.println("An error occured playing a sound file !");
			e.printStackTrace();
		}
	}

	private static void playMusic(String s, int i) {

		if(!clips.containsKey(s)){
			System.out.println("the key " + s + " for sounds does not exist");
			return;
		}

		try {

			if (mute)
				return;

			final Clip c = clips.get(s);

			if (c == null)
				return;

			if (c.isRunning())
				c.stop();

			c.setFramePosition(i);

			while (!c.isRunning())
				c.start();

		} catch (Exception e) {
			System.out.println("An error occured trying to play a sound file !");
			e.printStackTrace();
		}
	}

	public static void resume(String s) {
		if (mute)
			return;
		if (clips.get(s).isRunning())
			return;
		clips.get(s).start();
	}

	public static void setPosition(String s, int frame) {
		clips.get(s).setFramePosition(frame);
	}

	public static void stop(String s) {
		if (clips.get(s) == null)
			return;
		if (clips.get(s).isRunning())
			clips.get(s).stop();
	}

	/**toggles mute on or off*/
	public static void toggleMute(){
		mute = !mute;
	}

}