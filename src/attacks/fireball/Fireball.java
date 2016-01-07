package attacks.fireball;

import org.lwjgl.util.vector.Vector3f;
import main.Mift;

public class Fireball {
	private Vector3f currentPosition;
	@SuppressWarnings("unused")
	private Vector3f gotoPosition;
	@SuppressWarnings("unused")
	private Vector3f goingTo;
	@SuppressWarnings("unused")
	private float speed;
	
	public Fireball (Vector3f pos, Vector3f topos, float speed) {
		this.currentPosition = new Vector3f(pos.getX(), pos.getY() + 7, pos.getZ());
		this.gotoPosition = topos;
		this.speed = speed;
	}
	
	public void update() {
		Mift.particleEmitter.generateAttackParticles(this.currentPosition);
	}
}