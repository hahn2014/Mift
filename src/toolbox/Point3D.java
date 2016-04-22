package toolbox;

import org.lwjgl.util.vector.Vector3f;

public class Point3D {
	private int x, y, z;
	public Point3D (int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D getPoint() {
		return this;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public Vector3f getPosition3f() {
		return new Vector3f(x, y, z);
	}
	
	public String getPositionDebug() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(getX());
		sb.append(", ");
		sb.append(getY());
		sb.append(", ");
		sb.append(getZ());
		sb.append("]");
		return sb.toString();
	}
}