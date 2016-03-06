package audio;

import java.io.IOException;

import main.ImportLibs;

public class AudioTester {
	public static void main(String[] args) {
		new ImportLibs();
		AudioMaster.init();
		AudioMaster.setListenerData();
		
		int buffer = AudioMaster.loadSound("audio/bounce.wav");
		Source source = new Source();
		
		char c = ' ';
		while (c != 'q') {
			try {
				c = (char)System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (c == 'p') {
				source.play(buffer);
			}
		}
		source.delete();
		AudioMaster.cleanUp();
	}
}