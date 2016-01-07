package paths;

import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;

import entities.Enemy;

public class PathCreator {
	private int totalPaths = 0;
	private Random random = new Random(Sys.getTime());
	
	public int getPaths() {
		return totalPaths;
	}
	
	public Path createRandomPath(long seedFactor, Enemy e) {
		random.setSeed(seedFactor);
		int numberOfPoints = 2;//random.nextInt(8) + 4;
		System.out.println("Number Of Points -> " + numberOfPoints);
		this.totalPaths += 1;
		Path p = new Path(this.totalPaths);
		for (int i = 0; i < numberOfPoints; i++) {
			p.addPoint(random.nextInt(32) + 5, random.nextInt(32) + 5);
			System.out.println("DEBUG: new point for enemy " + e.getID() + " [" + p.getPoint(i).getX() + ", " + p.getPoint(i).getZ() + "]");
		}
		return p;
	}
	
	public Path makePath(List<Point> p) {
		this.totalPaths += 1;
		return new Path(this.totalPaths, p);
	}
}