package attacks.fireball;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public class FireballHolder {
	List<Fireball> fireballs = new ArrayList<Fireball>();
	
	public List<Fireball> getAll() {
		return fireballs;
	}
	
	public Fireball get(int index) {
		return fireballs.get(index);
	}
	
	public void createFireball(Vector3f pos) {
		add(new Fireball(pos));
	}
	
	private void add(Fireball fb) {
		fireballs.add(fb);
	}
	
	public void remove(Fireball ball) {
		fireballs.remove(ball);
	}
}