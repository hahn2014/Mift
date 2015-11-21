package entities;

import java.util.ArrayList;
import java.util.List;

import entities.EntityType.entityType;

public class EntityTypeHolder {
	private List<EntityType> entities = new ArrayList<EntityType>();
	
	public EntityTypeHolder() {
		entities.add(new EntityType(entityType.PLAYER, 	"Player", 			"player", 		"player", 		0.6f));
		entities.add(new EntityType(entityType.ENEMY, 	"Enemy", 			"player", 		"enemy", 		0.6f));
		entities.add(new EntityType(entityType.TREE, 	"Low Poly Tree", 	"lowPolyTree", 	"lowPolyTree", 	0.75f));
		entities.add(new EntityType(entityType.BARREL, 	"Barrel", 			"barrel", 		"barrel", 		0.5f));
		entities.add(new EntityType(entityType.PINE, 	"Pine Tree", 		"pine", 		"pine1024", 	1.3f));
		
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
}