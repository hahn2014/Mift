package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.*;

public class SoundTest {
	
	private AdvancedPlayer player;
	@SuppressWarnings("unused")
	private int pausedOnFrame = 0;

	public SoundTest() {
		try {
			player = new AdvancedPlayer(new FileInputStream("res/Sounds/el_chapo.mp3"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		playSong();
	}
	
	public void playSong() {
		try {
			player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSongPlaying() {
		return true;
	}
	
	public void stopSong() {
		player.stop();
	}
	
	public void pauseSong() {
		
	}
	
	public boolean isSongPaused() {
		return false;
	}
}