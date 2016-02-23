package attacks.waterball;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Vector3f;

public class WaterballHolder {
	List<Waterball> waterballs = new ArrayList<Waterball>();
	
	public List<Waterball> getAll() {
		return waterballs;
	}
	
	public Waterball get(int index) {
		return waterballs.get(index);
	}
	
	public void createWaterball(Vector3f pos) {
		add(new Waterball(pos));
	}
	
	private void add(Waterball fb) {
		waterballs.add(fb);
	}
	
	public void remove(Waterball ball) {
		waterballs.remove(ball);
	}
}