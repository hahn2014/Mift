package toolbox;

public class MemoryData {
	private static int mb = 1024 * 1024;
	private static Runtime runtime = Runtime.getRuntime();

	/**
	 * Get the amount of used memory in the stack
	 * @return Long
	 */
	public static long getUsedMemory() {
		return ((runtime.totalMemory() - runtime.freeMemory()) / mb);
	}

	/**
	 * Get the amount of unused memory in the stack
	 * @return Long
	 */
	public static long getFreeMemory() {
		return runtime.freeMemory() / mb;
	}

	/**
	 * Get the total memory implemented into system.
	 * @return Long
	 */
	public static long getTotalMemory() {
		return runtime.totalMemory() / mb;
	}

	/**
	 * Get the maximum allocated memory within the stack
	 * @return Long
	 */
	public static long getMaxMemory() {
		return runtime.maxMemory() / mb;
	}
}