package attacks;

import java.util.List;

import org.lwjgl.opengl.GL11;

import attacks.fireball.Fireball;
import attacks.fireball.FireballRenderer;
import attacks.waterball.Waterball;
import entities.Camera;
import entities.OverheadCamera;
import main.Mift;
import renderEngine.MasterRenderer;

public class AttacksRenderer {
	AttackShader shader = new AttackShader();
	FireballRenderer fireRender;

	public AttacksRenderer() {
		fireRender = new FireballRenderer(shader);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
		shader.loadProjectionMatrix(MasterRenderer.getProjectionMatrix());
		shader.stop();
	}

	public void render(Camera camera, List<Fireball> fireballs, List<Waterball> waterballs) {
//************FIREBALL ATTACKS********************
		for (Fireball fire : fireballs) {
			fireRender.render(fire, camera);
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
			//waterRender.render(water, camera);
		}
		//check for deletables
		for (Waterball water : waterballs) {
			if (water.isRenderable() == false) {
				Mift.waterballHolder.remove(water);
				break;
			}
		}
	}
	
	public void render(OverheadCamera camera, List<Fireball> fireballs, List<Waterball> waterballs) {
		//************FIREBALL ATTACKS********************
		for (Fireball fire : fireballs) {
			fireRender.render(fire, camera);
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
			//waterRender.render(water, camera);
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