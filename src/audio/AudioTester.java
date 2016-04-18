package audio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import io.Logger;
import main.ImportLibs;

public class AudioTester {
	public static void main(String[] args) throws IOException, Exception {
		new ImportLibs();
		
		AL.create();
		File file = new File("res/audio/bounce.wav");
		Logger.info("file was " + (file.exists() ? "found" : "not found"));
		FileInputStream fis = new FileInputStream(file);
		
		
		WaveData soundData = WaveData.create(new BufferedInputStream(fis));

        int buffer = AL10.alGenBuffers();
        AL10.alBufferData(buffer, soundData.format, soundData.data, soundData.samplerate);
        int source = AL10.alGenSources();

        AL10.alSourcei(source, AL10.AL_BUFFER, buffer);
        AL10.alSource3f(source, AL10.AL_POSITION, 0, 0, 0);
        AL10.alSource3f(source, AL10.AL_VELOCITY, 0, 0, 0);
		
        soundData.dispose();
		
	    System.out.println("[Menu]");
	    System.out.println("p - Play the sample.");
	    System.out.println("s - Stop the sample.");
	    System.out.println("h - Pause the sample.");
	    System.out.println("q - Quit the program.");
		
		char c = ' ';
		boolean running = true;
		while(running) {
			try {
				c = (char)System.in.read();
			} catch (Exception ex) {
				c = 'q';
			}
			switch(c) {
				case 'p': AL10.alSourcePlay(source); break;
				case 's': AL10.alSourceStop(source); break;
		        case 'h': AL10.alSourcePause(source); break;
		        case 'q': running = false; break;
			};
			if (AL10.alGetError() != AL10.AL_NO_ERROR) {
				Logger.error("there was an error -> " + AL10.alGetError());
			}
		}
		AL10.alDeleteBuffers(buffer);
		AL10.alDeleteSources(source);
	}
}