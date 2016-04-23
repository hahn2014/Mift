package attacks;

import java.io.Serializable;

public class Attack implements Serializable {
	private static final long serialVersionUID = -7689675181070152534L;

	public enum AttackType {
		fireball,
		waterball,
		lightning
	};
	private int damage = 100;
	private AttackType id = AttackType.fireball;
	private String name;
	private String definition;
	
	public Attack(AttackType id, String name, String definition, int damage) {
		this.id = id;
		this.name = name;
		this.definition = definition;
		this.damage = damage;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public AttackType getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDefinition() {
		return definition;
	}
}