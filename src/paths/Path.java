package paths;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private int id;
	private int currentPoint;
	private List<Point> points = new ArrayList<Point>();
	
	public Path(int id) {
		this.id = id;
	}
	
	public Path(int id, List<Point> points) {
		this.id = id;
		this.points = points;
	}
	
	public Point getPoint(int index) {
		if (index < points.size() - 1 && index > -1) {
			return points.get(index);
		} else {
			return points.get(points.size() - 1);
		}
	}
	
	public int getID() {
		return id;
	}
	
	public int getX() {
		return getPoint(currentPoint).getX();
	}
	
	public int getZ() {
		return getPoint(currentPoint).getZ();
	}
	
	public int getCurrentPointID() {
		return currentPoint;
	}
	
	public Point getCurrentPoint() {
		return getPoint(currentPoint);
	}
	
	public boolean increaseCurrentPoint() {
		if (currentPoint + 1 <= points.size()) {
			this.currentPoint++;
			return true;
		} else {
			return false;
		}
	}
	
	public void addPoint(int x, int z) {
		points.add(new Point(x, z));
	}
}