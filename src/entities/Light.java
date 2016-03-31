package entities;

import org.lwjgl.util.vector.Vector3f;

import toolbox.Maths;

public class Light {

	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1, 0, 0);

	public Light(Vector3f position, Vector3f color, float brightness) {
		this.position = position;
		this.color = color;
	}

	public Light(Vector3f position, Vector3f color, Vector3f attenuation, float brightness) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}
	
	public float getBrightness() {
		return 1;
	}
	
	public void setBrightness(float bright) {
		
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}
	
	public void setAttenuation(Vector3f at) {
		this.attenuation = at;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public String getPositionDebug() {
		return "[" + (int)position.x + ", " + (int)position.y + ", " + (int)position.z + "]";
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void increasePosition(Vector3f positionIncrease) {
		increaseX(Maths.normalize(positionIncrease.x));
		increaseY(Maths.normalize(positionIncrease.y));
		increaseZ(Maths.normalize(positionIncrease.z));
	}
	
	public void decreasePosition(Vector3f positionDecrease) {
		increaseX(Maths.normalize(-positionDecrease.x));
		increaseY(Maths.normalize(-positionDecrease.y));
		increaseZ(Maths.normalize(-positionDecrease.z));
	}
	
	public void increaseAttenuation(Vector3f increase) {
		attenuation.setX(increase.getX());
		attenuation.setY(increase.getY());
		attenuation.setZ(increase.getZ());
	}
	
	public void decreaseAttenuation(Vector3f decrease) {
		attenuation.setX(-decrease.getX());
		attenuation.setY(-decrease.getY());
		attenuation.setZ(-decrease.getZ());
	}
	
	public void increaseX(float factor) {
		this.position.setX(this.position.getX() + factor);
	}
	
	public void increaseY(float factor) {
		this.position.setY(this.position.getY() + factor);
	}
	
	public void increaseZ(float factor) {
		this.position.setZ(this.position.getZ() + factor);
	}
	
	public Vector3f getColor() {
		return color;
	}

	public void setcolor(Vector3f color) {
		this.color = color;
	}
}