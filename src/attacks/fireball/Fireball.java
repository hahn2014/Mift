package attacks.fireball;

import org.lwjgl.util.vector.Vector3f;

import attacks.Attack.AttackType;
import entities.Enemy;
import entities.Entity;
import entities.EntityType.entityType;
import main.Mift;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import particles.ParticleEmitter;
import particles.ParticleTexture;
import textures.ModelTexture;
import toolbox.Maths;

public class Fireball extends Entity {
	private static final long serialVersionUID = -5761273687900548386L;
	
	private final int speed = 8;
	private boolean isRenderable = true;
	private float timeAlive = 0;
	private final float maxTimeAlive = 300;
	private Vector3f gotoPos;
	private ParticleEmitter pe;
	
	private TexturedModel model;
	
	public Fireball(TexturedModel model, Vector3f curPos, Vector3f gotoPos, float scale) {
		super(model, curPos, 0, 0, 0, scale, entityType.ATK_FIREBALL);
		this.isRenderable = true;
		this.gotoPos = gotoPos;
		this.model = new TexturedModel(OBJFileLoader.loadOBJ("attackSphere"), new ModelTexture(Mift.loader.loadTexture("sphere")));
		this.pe = new ParticleEmitter(new ParticleTexture(Mift.loader.loadParticleTexture("fire"), 8, false), 13.0F, 10.0F, 0.5F, 100.0F, 3.0F);
	}
	
	public void update() {
		if (timeAlive + 1 <= maxTimeAlive) {
			move();
			collisionCheck();
			pe.generateParticles(getCurrentPosition());
			pe.randomizeDirection();
			pe.randomizeRotation();
			timeAlive++;
		} else {
			this.isRenderable = false;
		}
	}
	
	private void move() {
		Vector3f distance = new Vector3f(getPosition().x - gotoPos.x, getPosition().y - gotoPos.y, getPosition().z - gotoPos.z);
		Vector3f toTravel = new Vector3f(distance.x / (maxTimeAlive - timeAlive) * speed, distance.y / (maxTimeAlive - timeAlive) * speed, distance.z / (maxTimeAlive - timeAlive) * speed);
		
		if ((distance.x < 1 && distance.x > -1) && (distance.y < 1 && distance.y > -1) && (distance.z < 1 && distance.z > -1)) {
			timeAlive = maxTimeAlive;
		} else {
			super.increaseXPos(-toTravel.x);
			super.increaseYPos(-toTravel.y);
			super.increaseZPos(-toTravel.z);
		}
	}
	
	private void collisionCheck() {
		//check entity collision
		Entity entitytodel = null;
		for (Entity e : Mift.entities) {
			if (e.getType() != entityType.ATK_FIREBALL && e.getType() != entityType.PLAYER) {
				if (Maths.distanceFormula3D(getCurrentPosition(), e.getPosition()) <= 10) {
					entitytodel = e;
				}
			}
		}
		if (entitytodel != null) {
			Mift.entities.remove(entitytodel);
			timeAlive = maxTimeAlive;
		}
		//check enemy collision
		Enemy enemytodel = null;
		for (Enemy e : Mift.enemies) {
			if (Maths.distanceFormula3D(getCurrentPosition(), e.getPosition()) <= 10) {
				enemytodel = e;
				e.attackCollision(Mift.attackHolder.get(AttackType.fireball).getDamage());
			}
		}
		if (enemytodel != null) {
			Mift.enemies.remove(enemytodel);
			timeAlive = maxTimeAlive;
		}
	}
	
	public boolean isRenderable() {
		return isRenderable;
	}

	public void setRenderable(boolean isRenderable) {
		this.isRenderable = isRenderable;
	}

	public TexturedModel getTexturedModel() {
		return model;
	}

	public Vector3f getCurrentPosition() {
		return super.getPosition();
	}

	public void setCurrentPosition(Vector3f currentPosition) {
		super.setPosition(currentPosition);
	}
	
	public void increasePosition() {
		this.setCurrentPosition(new Vector3f(super.getPosition().getX() + 1, super.getPosition().getY(), super.getPosition().getZ() + 1));
	}
}