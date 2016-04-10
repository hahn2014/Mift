package myo_connect.mason;

import java.io.IOException;
import java.util.concurrent.Executors;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

public class MyoConnect {
	public static void main(String[] args) {
		try {
			System.out.println(System.getProperties());
			Hub hub = new Hub("");
			System.out.println("Attemping to find Myo");
			Myo myo = hub.waitForMyo(10000);
			if (myo == null) {
				throw new RuntimeException("Unable to find a Myo!");
			} else {
				System.out.println("Connected to Myo!");
			}
			final DeviceListener dataCollector = new DataCollector();
			hub.addListener(dataCollector);

			while (true) {
				hub.run(1000 / 20);
				System.out.print(dataCollector);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
