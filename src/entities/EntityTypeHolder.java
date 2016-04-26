package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entities.EntityType.entityType;

public class EntityTypeHolder implements Serializable {
	private static final long serialVersionUID = 3658828953549257689L;
	private List<EntityType> entities = new ArrayList<EntityType>();
	
	public EntityTypeHolder() {
		entities.add(new EntityType(entityType.PLAYER, 			"Player", 			"player", 		"player", 		3.0f));
		entities.add(new EntityType(entityType.ENEMY, 			"Enemy", 			"dino", 		"dino", 		3.8f));
		entities.add(new EntityType(entityType.TREE, 			"Low Poly Tree", 	"lowPolyTree", 	"lowPolyTree", 	0.75f));
		entities.add(new EntityType(entityType.BARREL, 			"Barrel", 			"barrel", 		"barrel", 		0.5f));
		entities.add(new EntityType(entityType.PINE, 			"Pine Tree", 		"pine", 		"pine", 		1.3f));
		entities.add(new EntityType(entityType.FERN, 			"Fern", 			"fern", 		"fern", 		1.0f));
		entities.add(new EntityType(entityType.ATK_FIREBALL, 	"Fireball Attack", 	"attackSphere", "sphere", 		1f));
		entities.add(new EntityType(entityType.ATK_WATERBALL, 	"Waterball Attack", "attackSphere", "waterball", 	1f));
	}
	
	public EntityType get(int id) {
		return entities.get(id);
	}
	
	public EntityType get(entityType type) {
		for (int i = 0; i < entities.size(); i++) {
			if (type == entities.get(i).getID()) {
				return entities.get(i);
			}
		}
		return null;
	}
	
	public entityType rotate(entityType current) {
		for (int i = 0; i < entities.size(); i++) {
			if (current == entities.get(i).getID()) {
				if (i + 1 < entities.size()) {
					return entities.get(i + 1).getID();
				} else if (i + 1 >= entities.size()) {
					return entities.get(0).getID();
				}
			}
		}
		return null;
	}
	
	public entityType rotateReverse(entityType current) {
		for (int i = entities.size() - 1; i > 0; i--) {
			if (entities.get(i).getID() == current) {
				if (i - 1 >= 0) { //we can keep going down in the list of models
					return entities.get(i - 1).getID();
				}
			}
		}
		return entities.get(entities.size() - 1).getID(); //set the last model here
	}
}