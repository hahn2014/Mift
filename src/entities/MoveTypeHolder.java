package entities;

import java.util.ArrayList;
import java.util.List;

import entities.MoveType.move_factor;

public class MoveTypeHolder {
	private List<MoveType> moves = new ArrayList<MoveType>();
	
	public MoveTypeHolder() {
		moves.add(new MoveType(move_factor.NOTHING, "Do Nothing", "Entity will not rotate or move"));
		moves.add(new MoveType(move_factor.MOVE_CIRCLES, "Move In Circles", "Entity will swirl in circles endlessly"));
		moves.add(new MoveType(move_factor.FACE_TOWARDS, "Face Towards Player", "Entity will menacingly stare at the player"));
		moves.add(new MoveType(move_factor.FACE_AWAY, "Face Away From Player", "Entity will face the complete oposite direction of the player"));
		moves.add(new MoveType(move_factor.MOVE_TOWARDS, "Move Towards Player", "Entity will move towards player and stare at them"));
		moves.add(new MoveType(move_factor.FOLLOW_NOT_LOOKING, "Follow When Not Looking", "Entity will follow player when his back is turned to it"));
	}
	
	public MoveType get(int id) {
		return moves.get(id);
	}
	
	public MoveType get(move_factor type) {
		for (int i = 0; i < moves.size(); i++) {
			if (type == moves.get(i).getID()) {
				return moves.get(i);
			}
		}
		return moves.get(0);
	}
	
	public move_factor rotate(move_factor current) {
		for (int i = 0; i < moves.size(); i++) {
			if (current == moves.get(i).getID()) {
				if (i + 1 < moves.size()) {
					return moves.get(i + 1).getID();
				} else if (i + 1 >= moves.size()) {
					return moves.get(0).getID();
				}
			}
		}
		return move_factor.NOTHING;
	}
	
	public move_factor rotateReverse(move_factor current) {
		for (int i = moves.size() - 1; i > 0; i--) {
			if (moves.get(i).getID() == current) {
				if (i - 1 >= 0) {
					return moves.get(i - 1).getID();
				}
			}
		}
		return moves.get(moves.size() - 1).getID();
	}
}