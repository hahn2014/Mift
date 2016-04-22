package toolbox;

import org.lwjgl.util.vector.Vector3f;

public class Ray {
	Vector3f pos;
	Vector3f rot;
	
	public Ray(Vector3f pos, Vector3f rot) {
		this.rot = rot;
		this.pos = pos;
	}
}
