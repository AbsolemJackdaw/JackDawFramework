package framework.resourceLoaders;

import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class StreamMusic {

	private static HashMap<String, Boolean> shouldEnd = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> shouldLoop = new HashMap<String, Boolean>();

	/**streams music and plays it once*/
	public static void stream(String path){
		play(path,false);
	}

	/**streams music and loops it untill endStream is called*/
	public static void loopStream(String path){
		play(path, true);
	}
	
	public static void play(String path, boolean loop){
		shouldEnd.put(path, false);
		shouldLoop.put(path, loop);
		new Thread(runStreaming(path)).start();
	}

	/**ends the stream and loop*/
	public static void endStream(String path){
		shouldEnd.replace(path, true);
		shouldLoop.put(path, false);
	}
	
	/**loops trough all playing sounds and flags them to end*/
	public static void stopAll(){
		for(String path : shouldEnd.keySet())
			shouldEnd.replace(path, true);
		for(String path : shouldLoop.keySet())
			shouldLoop.replace(path, false);
	}

	private static Runnable runStreaming(String path){

		Runnable runner = new Runnable() {

			@Override
			public void run() {
				final AudioFormat baseFormat;
				final AudioFormat decodeFormat;
				final AudioInputStream dais;
				SourceDataLine.Info info;

				try {
					//get audio input
					AudioInputStream ais;
					ais = AudioSystem.getAudioInputStream(StreamMusic.class.getResourceAsStream(path));
					//get audioformat for inputstream
					baseFormat = ais.getFormat();
					//make pcm format
					decodeFormat = new AudioFormat(
							AudioFormat.Encoding.PCM_SIGNED,
							baseFormat.getSampleRate(), 
							16, 
							baseFormat.getChannels(),
							baseFormat.getChannels() * 2, 
							baseFormat.getSampleRate(),
							true); //big endian

					//convert audio
					dais = AudioSystem.getAudioInputStream(
							decodeFormat, ais);

					//checking for a supported output line
					info = new DataLine.Info(
							SourceDataLine.class,
							dais.getFormat(),
							((int) dais.getFrameLength() * decodeFormat.getFrameSize()));

					if (!AudioSystem.isLineSupported(info)) {
						System.out.println("Line matching " + info + " is not supported. " + path);
						return;
					}

					//opening the sound output line
					SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
					line.open(dais.getFormat());
					line.start();

					int numRead = 0;
					byte[] buf =  new byte[1100]; //new byte[line.getBufferSize()];

					while ((numRead = dais.read(buf, 0, buf.length)) >= 0) {
						if(shouldEnd.containsKey(path) && shouldEnd.get(path)){
							line.drain();
							line.stop();
							shouldEnd.remove(path);
							shouldLoop.remove(path);
							return;
						}
						int offset = 0;
						while (offset < numRead) {
							offset += line.write(buf, offset, numRead - offset);
						}
					}
					line.drain();
					line.stop();

				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//replay if looped
					if(shouldLoop.containsKey(path) && shouldLoop.get(path)){
						runStreaming(path);
					}else{
						shouldEnd.remove(path);
						shouldLoop.remove(path);
					}
					//end of thread;
				}
			}
		};

		return runner;
	}
	
}