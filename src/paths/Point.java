package paths;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

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
	
	public Vector3f getPosition3f() {
		return new Vector3f(x, 0, z);
	}
	
	public Vector2f getPosition2f() {
		return new Vector2f(x, z);
	}
	
	public String getPositionDebug() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(getX());
		sb.append(", ");
		sb.append(getZ());
		sb.append("]");
		return sb.toString();
	}
}