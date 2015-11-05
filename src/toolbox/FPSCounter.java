package toolbox;

public class FPSCounter {
	private static double fps;
	private static long frameStartTime;
	private static short frames = 0;

	public FPSCounter() {
		frameStartTime = System.nanoTime();
	}

	/**
	 * Needs to be called after every main
	 * method loop for the fps counting
	 * process to be properly calculated.
	 */
	public void updateCounter() {
		if ((System.nanoTime() - frameStartTime) / 1000000 >= 1000) {
			fps = frames;
			frames = 0;
			frameStartTime = System.nanoTime();
		}
		frames++;
	}
	
	/**
	 * Gets the current times
	 * Frames Per Second and 
	 * returns it as a double
	 * @return Frames Per Second
	 */
	public double getFPS() {
		return fps;
	}
}