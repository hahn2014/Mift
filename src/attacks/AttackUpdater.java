package attacks;

import attacks.fireball.Fireball;

public class AttackUpdater {

	public void update(AttackHolder holder) {
//************FIREBALL ATTACKS********************
		//check for deletables
		for (Fireball fire : holder.getFireballHolder().getAll()) {
			if (fire.isRenderable() == false) {
				holder.getFireballHolder().remove(fire);
				break;
			} else {
				fire.update();
			}
		}
	}
}