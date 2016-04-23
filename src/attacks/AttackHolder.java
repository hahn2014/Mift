package attacks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import attacks.Attack.AttackType;
import attacks.fireball.FireballHolder;
import attacks.waterball.WaterballHolder;

public class AttackHolder implements Serializable {
	private static final long serialVersionUID = -219459632414844752L;
	private List<Attack> attacks = new ArrayList<Attack>();
	private static FireballHolder fireHolder = new FireballHolder();
	private static WaterballHolder waterHolder = new WaterballHolder();
	
	public AttackHolder() {
		attacks.add(new Attack(AttackType.fireball, "Fire Ball", "Sends a ball of fire towards you enemies, burning anything it touches.", 200));
		attacks.add(new Attack(AttackType.waterball, "Water Ball", "Sends a gravity defying ball of water towards your enemies, downing them.", 100));
		attacks.add(new Attack(AttackType.lightning, "Lightning Strike", "Sends a bolt of lightning towards your enemies, electrocuting them.", 400));
	}
	
	public Attack get(int id) {
		return attacks.get(id);
	}
	
	public FireballHolder getFireballHolder() {
		return fireHolder;
	}
	
	public WaterballHolder getWaterballHolder() {
		return waterHolder;
	}
	
	public Attack get(AttackType type) {
		for (int i = 0; i < attacks.size(); i++) {
			if (type == attacks.get(i).getID()) {
				return attacks.get(i);
			}
		}
		return null;
	}
	
	public AttackType rotate(AttackType current) {
		for (int i = 0; i < attacks.size(); i++) {
			if (current == attacks.get(i).getID()) {
				if (i + 1 < attacks.size()) {
					return attacks.get(i + 1).getID();
				} else if (i + 1 >= attacks.size()) {
					return attacks.get(0).getID();
				}
			}
		}
		return attacks.get(0).getID();
	}
	
	public AttackType rotateReverse(AttackType current) {
		for (int i = attacks.size() - 1; i > 0; i--) {
			if (attacks.get(i).getID() == current) {
				if (i - 1 >= 0) {
					return attacks.get(i - 1).getID();
				}
			}
		}
		return attacks.get(attacks.size() - 1).getID();
	}
}