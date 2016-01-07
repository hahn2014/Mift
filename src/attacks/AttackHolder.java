package attacks;

import java.util.ArrayList;
import java.util.List;

import attacks.Attack.AttackType;

public class AttackHolder {
	private List<Attack> attacks = new ArrayList<Attack>();
	
	public AttackHolder() {
		attacks.add(new Attack(AttackType.fireball, "Fire Ball", "Throws a average sized fireball in the direction you are facing", 100));
		attacks.add(new Attack(AttackType.waterball, "Water Ball", "Throws a average sized waterball in the direction you are facing", 100));
		attacks.add(new Attack(AttackType.groundpound, "Ground Pound", "Super powerfull earth trembling smash that will obliterate any enemy around", 100));
		attacks.add(new Attack(AttackType.lightning, "Lightning Strike", "Summon the powers of Thor with a powerful lightning bolt to electrocute your opponents", 100));
		attacks.add(new Attack(AttackType.airblast, "Air Blast", "Shoot a blast of powerful wind at an enemy to inflict damage upon them", 100));
	}
	
	public Attack get(int id) {
		return attacks.get(id);
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