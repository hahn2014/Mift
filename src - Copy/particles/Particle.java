package particles;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.OverheadCamera;
import renderEngine.DisplayManager;

public class Particle {

	private Vector3f position;
	private Vector3f velocity;
	private Vector3f color;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	private float elapsedTime = 0;
	private float distance;
	private final float GRAVITY = -50f;
	private Random random = new Random();
	
	private ParticleTexture texture;
	
	private Vector2f textureOffset1 = new Vector2f();
	private Vector2f textureOffset2 = new Vector2f();
	private float blend;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		random.setSeed(Sys.getTime());
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.color = new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleHolder.addParticle(this);
	}
	
	public float getDistance() {
		return distance;
	}

	public Vector2f getTextureOffset1() {
		return textureOffset1;
	}

	public Vector2f getTextureOffset2() {
		return textureOffset2;
	}

	public float getBlend() {
		return blend;
	}
	
	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public Vector3f getColorRGB() {
		return new Vector3f(255 * color.getX(), 255 * color.getY(), 255 * color.getZ());
	}
	
	protected boolean update(Camera normalCamera) {
		velocity.y += GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);

		distance = Vector3f.sub(normalCamera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return (elapsedTime < lifeLength);
	}
	
	protected boolean update(OverheadCamera overheadCamera) {
		velocity.y += GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		
		Vector3f change = new Vector3f(velocity);
		change.scale(DisplayManager.getFrameTimeSeconds());
		Vector3f.add(change, position, position);
		
		distance = Vector3f.sub(overheadCamera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return (elapsedTime < lifeLength);
	}
	
	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int)Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(textureOffset1, index1);
		setTextureOffset(textureOffset2, index2);
	}
	
	private void setTextureOffset(Vector2f offset, int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float)column / texture.getNumberOfRows();
		offset.y = (float)row / texture.getNumberOfRows();
	}
}