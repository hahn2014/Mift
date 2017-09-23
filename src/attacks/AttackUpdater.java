package attacks;

import attacks.fireball.Fireball;
import attacks.waterball.Waterball;

public class AttackUpdater {

	public void update(AttackHolder holder) {
//************FIREBALL ATTACKS********************
		//check for deletables
		for (Fireball fire : holder.getFireballHolder().getAll()) {
			if (fire.isRenderable() == false) {
				//create particle explosion
//				ParticleEmitter pe = new ParticleEmitter(new ParticleTexture(Mift.loader.loadParticleTexture("fire"), 8, true), 120, 10, 0.1f, 1, 2);
//				pe.setLifeError(0.1f);
//				pe.setSpeedError(0.25f);
//				pe.setScaleError(0.5f);
//				pe.randomizeRotation();
//				pe.randomizeDirection();
				
				//remove ball
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