package attacks;

import attacks.fireball.Fireball;
import attacks.waterball.Waterball;

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
		//check for deletables
		for (Waterball water : holder.getWaterballHolder().getAll()) {
			if (water.isRenderable() == false) {
				holder.getWaterballHolder().remove(water);
				break;
			} else {
				water.update();
			}
		}
	}
}