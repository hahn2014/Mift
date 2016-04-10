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
	private AttackMyo attackMyo = null; // Right arm
	private MoveMyo moveMyo = null; // Left arm
	
	public MyoManager () {
		
	}
	
	@Override
	public void onPair(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {

	}

	@Override
	public void onUnpair(Myo myo, long timestamp) {
		
	}

	@Override
	public void onConnect(Myo myo, long timestamp, FirmwareVersion firmwareVersion) {
		
	}

	@Override
	public void onDisconnect(Myo myo, long timestamp) {
		
	}

	@Override
	public void onArmSync(Myo myo, long timestamp, Arm arm, XDirection xDirection, float rotation, WarmupState warmupState) {
		if (arm == Arm.ARM_RIGHT) {
			attackMyoMyo = myo;
		} else if (arm == Arm.ARM_LEFT) {
			moveMyoMyo = myo;
		} else {
			Logger.error("Unknown Myo in onArmSync!");
		}
	}

	@Override
	public void onArmUnsync(Myo myo, long timestamp) {
		if (attackMyoMyo != null && attackMyoMyo == myo) {
			attackMyo.onArmUnsync(myo, timestamp);
		} else if (moveMyoMyo != null && moveMyoMyo == myo) {
			moveMyo.onArmUnsync(myo, timestamp);
		} else {
			Logger.warn("Unmanaged Myo in onArmUnsync! [" + myo.toString() + "]");
		}
	}

	@Override
	public void onUnlock(Myo myo, long timestamp) {
	}

	@Override
	public void onLock(Myo myo, long timestamp) {
	}

	@Override
	public void onPose(Myo myo, long timestamp, Pose pose) {
	}

	@Override
	public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
		if (attackMyoMyo != null && attackMyoMyo == myo) {
			attackMyo.onOrientationData(myo, timestamp, rotation);
		} else if (moveMyoMyo != null && moveMyoMyo == myo) {
			moveMyo.onOrientationData(myo, timestamp, rotation);
		} else {
			Logger.warn("Unmanaged Myo in onOrientationData! [" + myo.toString() + "]");
		}
	}

	@Override
	public void onAccelerometerData(Myo myo, long timestamp, Vector3 accel) {
	}

	@Override
	public void onGyroscopeData(Myo myo, long timestamp, Vector3 gyro) {
	}

	@Override
	public void onRssi(Myo myo, long timestamp, int rssi) {
	}

	@Override
	public void onEmgData(Myo myo, long timestamp, byte[] emg) {
	}

	@Override
	public void onBatteryLevelReceived(Myo myo, long timestamp, int level) {
	}

	@Override
	public void onWarmupCompleted(Myo myo, long timestamp, WarmupResult warmupResult) {
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Attack:\n");
		sb.append(attackMyo.toString());
		sb.append("Move:\n");
		sb.append(moveMyo.toString());
		return sb.toString();
	}
}
