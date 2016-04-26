package myo;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.FirmwareVersion;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.Vector3;
import com.thalmic.myo.enums.Arm;
import com.thalmic.myo.enums.WarmupResult;
import com.thalmic.myo.enums.WarmupState;
import com.thalmic.myo.enums.XDirection;

import io.Logger;

public class MyoManager extends AbstractDeviceListener {
	private Myo attackMyoMyo = null; // Myo device we recieve, right arm
	private Myo moveMyoMyo = null; // Left arm
	private static AttackMyo attackMyo = new AttackMyo(); // Right arm
	private static MoveMyo moveMyo = new MoveMyo(); // Left arm
	
	public static AttackMyo getAttackMyo() {
		return attackMyo;
	}
	
	public static MoveMyo getMoveMyo() {
		return moveMyo;
	}
	
	public MyoManager () {}
	
	public MyoDevice getMyo(Myo myo) {
		if (myo == attackMyoMyo) {
			return attackMyo;
		} else if (myo == moveMyoMyo) {
			return moveMyo;
		}
		return null;
	}
	
	@Override
	public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		if (getMyo(myo) != null) {
			getMyo(myo).onPair(myo, timestamp, firmwareVersion);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onUnpair(Myo myo, long timestamp) {
		if (getMyo(myo) != null) {
			getMyo(myo).onUnpair(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onConnect(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		if (getMyo(myo) != null) {
			getMyo(myo).onConnect(myo, timestamp, firmwareVersion);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onDisconnect(Myo myo, long timestamp) {
		if (getMyo(myo) != null) {
			getMyo(myo).onDisconnect(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection, float rotation, WarmupState warmupState) {
		if (arm == Arm.ARM_RIGHT) {
			Logger.info("Myo on right arm synced");
			if (moveMyoMyo == null) {
				moveMyoMyo = myo;
			} else {
				Logger.warn("Already synced the right arm!");
				attackMyoMyo = myo;
			}
		} else if (arm == Arm.ARM_LEFT) {
			Logger.info("Myo on left arm synced");
			if (attackMyoMyo == null) {
				attackMyoMyo = myo;
			} else {
				Logger.warn("Already synced the left arm!");
				moveMyoMyo = myo;
			}
		} else if (arm == Arm.ARM_UNKNOWN) {
			Logger.error("Unknown arm state");
		} else {
			Logger.error("Shit's fucked up yo");
		}
		getMyo(myo).onArmSync(myo, timestamp, arm, xDirection, rotation, warmupState);
	}

	@Override
	public void onArmUnsync(Myo myo, long timestamp) {
		if (getMyo(myo) != null) {
			getMyo(myo).onArmUnsync(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onUnlock(Myo myo, long timestamp) {
		if (getMyo(myo) != null) {
			getMyo(myo).onUnlock(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onLock(Myo myo, long timestamp) {
		if (getMyo(myo) != null) {
			getMyo(myo).onLock(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onPose(Myo myo, long timestamp, Pose pose) {
		if (getMyo(myo) != null) {
			getMyo(myo).onPose(myo, timestamp, pose);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
		if (getMyo(myo) != null) {
			getMyo(myo).onOrientationData(myo, timestamp, rotation);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
		if (getMyo(myo) != null) {
			getMyo(myo).onAccelerometerData(myo, timestamp, accel);
		} else {
			//Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
		if (getMyo(myo) != null) {
			getMyo(myo).onGyroscopeData(myo, timestamp, gyro);
		} else {
			//Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onRssi(Myo myo, long timestamp, int rssi) {
		if (getMyo(myo) != null) {
			getMyo(myo).onRssi(myo, timestamp, rssi);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onEmgData(Myo myo, long timestamp, byte[] emg) {
		if (getMyo(myo) != null) {
			getMyo(myo).onEmgData(myo, timestamp, emg);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onBatteryLevelReceived(Myo myo, long timestamp, int level) {
		if (getMyo(myo) != null) {
			getMyo(myo).onBatteryLevelReceived(myo, timestamp, level);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}

	@Override
	public void onWarmupCompleted(Myo myo, long timestamp, WarmupResult warmupResult) {
		if (getMyo(myo) != null) {
			getMyo(myo).onWarmupCompleted(myo, timestamp, warmupResult);
		} else {
			Logger.warn("Unmanaged Myo in " + new Object() {}.getClass().getEnclosingMethod().getName() + "[" + myo.toString() + "]");
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Attack:\n");
		if (attackMyo != null) {
			sb.append(attackMyo.toString());
		} else {
			sb.append("null\n");
		}
		sb.append("Move:\n");
		if (moveMyo != null) {
			sb.append(moveMyo.toString());
		} else {
			sb.append("null\n");
		}
		return sb.toString();
	}
}
