package water;

public class WaterTile {

	public static final float SIZE = 500;

	public static final float height = -11f;
	private float x, z;

	public WaterTile(float centerX, float centerZ) {
		this.x = centerX;
		this.z = centerZ;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}
}