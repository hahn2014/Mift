package myo;

import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;

import io.Logger;

public class MoveMyo extends MyoDevice {
	private static final int SCALE = 18;
	public static double rollW;
	public static double pitchW;
	public static  double yawW;
	public static double rollDiff;
	public static double pitchDiff;
	public static double yawDiff;
	private Pose currentPose;
	private Arm whichArm;

	public MoveMyo() {
		super();
	}
	
	public int getPitch() {
		return (int) pitchW;
	}
	
	public int getYaw() {
		return (int) yawW;
	}

	@Override
	public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
		Quaternion normalized = rotation.normalized();

		double roll = Math
				.atan2(2.0f * (normalized.getW() * normalized.getX() + normalized.getY() * normalized.getZ()),
						1.0f - 2.0f * (normalized.getX() * normalized.getX() + normalized.getY() * normalized.getY()));
		double pitch = Math
				.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
		double yaw = Math
				.atan2(2.0f * (normalized.getW() * normalized.getZ() + normalized.getX() * normalized.getY()),
						1.0f - 2.0f * (normalized.getY() * normalized.getY() + normalized.getZ() * normalized.getZ()));
		
		rollDiff = rollW - (roll * (360 / Math.PI));
		pitchDiff = pitchW - (pitch * (360 / Math.PI));
		yawDiff = yawW - (yaw * (360 / Math.PI));
		
		//Logger.info("roll=" + rollDiff + ", pitch=" + pitchDiff + ", yaw=" + yawDiff);
		
		pitchW = pitch * (360 / Math.PI);
		yawW = yaw * (360 / Math.PI);
		rollW = roll * (360 / Math.PI);
	}
	
	public double getRollDiff() {
		return Math.abs(rollDiff) > 0.1 ? rollDiff : 0;
	}
	
	public double getPitchDiff() {
		return Math.abs(pitchDiff) > 0.1 ? pitchDiff : 0;
	}
	
	public double getYawDiff() {
		return Math.abs(yawDiff) > 0.1 ? yawDiff : 0;
	}
	

	@Override
	public void onPose(Myo myo, long timestamp, Pose pose) {
		super.onPose(myo, timestamp, pose);
	}

	@Override
	public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection, float rotation, WarmupState warmupState) {
		super.onArmSync(myo, timestamp, arm, xDirection, rotation, warmupState);
	}

	@Override
	public void onArmUnsync(Myo myo, long timestamp) {
		super.onArmUnsync(myo, timestamp);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("\r");
		builder.append("MOVE> ");

		String xDisplay = String.format("[%s%s]",
				repeatCharacter('*', (int) rollW),
				repeatCharacter(' ', (int) (SCALE - rollW)));
		String yDisplay = String.format("[%s%s]",
				repeatCharacter('*', (int) pitchW),
				repeatCharacter(' ', (int) (SCALE - pitchW)));
		String zDisplay = String.format("[%s%s]",
				repeatCharacter('*', (int) yawW),
				repeatCharacter(' ', (int) (SCALE - yawW)));

		String armString = null;
		if (whichArm != null) {
			armString = String.format("[%s]", whichArm == Arm.ARM_LEFT ? "L"
					: "R");
		} else {
			armString = String.format("[?]");
		}
		String poseString = null;
		if (currentPose != null) {
			String poseTypeString = currentPose.getType().toString();
			poseString = String.format(
					"[%s%" + (SCALE - poseTypeString.length()) + "s]",
					poseTypeString, " ");
		} else {
			poseString = String.format("[%14s]", " ");
		}
		builder.append(xDisplay);
		builder.append(yDisplay);
		builder.append(zDisplay);
		builder.append(armString);
		builder.append(poseString);
		return builder.toString();
	}
}
