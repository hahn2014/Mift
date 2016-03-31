package audio;

import java.io.File;
import java.io.IOException;

import org.lwjgl.util.vector.Vector3f;

import io.Logger;
import main.ImportLibs;

public class AudioTester {
	public static void main(String[] args) throws IOException, Exception {
		new ImportLibs();
		AudioMaster.init();
		AudioMaster.setListenerData(0, 0, 0);
		
		File file = new File("res/audio/bounce.wav");
		Logger.debug(file.exists() + "");
		int buffer = AudioMaster.loadSound(file);
		Source source = new Source(new Vector3f(8, 0, 0));
		source.setLooping(true);
		source.setVolume(20);
		source.play(buffer);
		
		char c = ' ';
		while (c != 'q') {
			c = (char)System.in.read();
			if (c == 'p') {
				if (source.isPlaying()) {
					source.pause();
					Logger.debug("paused");
				} else {
					source.resumePlaying();
					Logger.debug("resumed");
				}
			}
		}
		source.delete();
		AudioMaster.cleanUp();
	}
}