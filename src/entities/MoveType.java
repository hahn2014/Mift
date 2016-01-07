package entities;

import java.util.Random;

import org.lwjgl.Sys;

public class MoveType {
	public enum move_factor {
		NOTHING, MOVE_TOWARDS, FACE_TOWARDS, FACE_AWAY, MOVE_CIRCLES, FOLLOW_PATH, FOLLOW_NOT_LOOKING
	}

	private static Random random = new Random();
	private move_factor id = move_factor.NOTHING;
	private String name = "defualt move";
	private String description = "default description";

	public MoveType(move_factor id, String name, String desc) {
		random.setSeed(Sys.getTime());
		this.id = id;
		this.name = name;
		this.description = desc;
	}

	public move_factor getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public static <T extends Enum<move_factor>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}