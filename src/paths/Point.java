package paths;

public class Point {
	private int x, z;
	public Point (int x, int z) {
		this.x = x;
		this.z = z;
	}
	
	public Point getPoint() {
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getZ() {
		return z;
	}
}