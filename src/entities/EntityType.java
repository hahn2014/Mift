package entities;

public class EntityType {
	public enum entityType {
		DEFAULT,
		PLAYER,
		PLAYER_LEGS,
		ENEMY,
		TREE,
		BARREL,
		PINE,
		FERN,
		GRASS,
		ATK_FIREBALL,
		ATK_WATERBALL
	};
	
	private entityType id = entityType.DEFAULT;
	private String name = "defualt entity";
	private String obj_name = "default_entity";
	private String texture_name = "default_entity";
	private float scale = 0.6f;
	
	public EntityType(entityType id, String name, String obj, String texture, float scale) {
		this.id = id;
		this.name = name;
		this.obj_name = obj;
		this.texture_name = texture;
		this.scale = scale;
	}
	
	public entityType getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getObjName() {
		return obj_name;
	}
	
	public String getTextureName() {
		return texture_name;
	}
	
	public float getScale() {
		return scale;
	}
}