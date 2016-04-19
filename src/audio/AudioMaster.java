package audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import io.Logger;

public class AudioMaster {

	private static List<Integer> buffers = new ArrayList<Integer>();

	public static void init() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		AL10.alGetError();
	}

	public static void setListenerData(float x, float y, float z) {
		AL10.alListener3f(AL10.AL_POSITION, x, y, z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}
	
	public static int loadSound(File file) throws FileNotFoundException {
		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);
		FileInputStream fis = new FileInputStream(file);
		WaveData wavefile = WaveData.create(fis);
		AL10.alBufferData(buffer, wavefile.format, wavefile.data, wavefile.samplerate);
		wavefile.dispose();
		if (AL10.alGetError() != AL10.AL_NO_ERROR) {
			Logger.error("there was an error -> " + AL10.alGetError());
		}
		return buffer;
	}

	public static void cleanUp() {
		for (int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		AL.destroy();
	}
}