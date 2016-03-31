package attacks;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import attacks.fireball.Fireball;
import attacks.fireball.FireballRenderer;
import attacks.waterball.Waterball;
import main.Mift;

public class AttacksRenderer {
	private AttackShader shader =  new AttackShader();
	Matrix4f projectionMatrix = Mift.renderer.getProjectionMatrix();
	FireballRenderer fireRender = new FireballRenderer(shader);

	public AttacksRenderer() {
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(List<Fireball> fireballs, List<Waterball> waterballs) {
//************FIREBALL ATTACKS********************
		for (Fireball fire : fireballs) {
			fireRender.render(fire);
		}
		//check for deletables
		for (Fireball fire : fireballs) {
			if (fire.isRenderable() == false) {
				Mift.fireballHolder.remove(fire);
				break;
			}
		}
//************WATERBALL ATTACK********************
		for (@SuppressWarnings("unused") Waterball water : waterballs) {
			//render
		}
		//check for deletables
		for (Waterball water : waterballs) {
			if (water.isRenderable() == false) {
				Mift.waterballHolder.remove(water);
				break;
			}
		}
	}
}