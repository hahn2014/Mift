package entities;

import org.lwjgl.util.vector.Vector3f;

import main.Mift;

public class Sun extends Light {
	private static final float timeDivider = 1000.0f;
	private static final float realTimeMultiplyer = 22 * Mift.dayTimeMultiplier / timeDivider; //1 minute in game = about 1 seconds in real life
	private static final float realTimeSeconds = 0.1f; //0.01345f; //will slow the increments down to a days second within 5%
	private static final float stepSeconds = (realTimeSeconds * realTimeMultiplyer);
	private static final int dayStart = 0; 			//12:00.00am
	private static final int dayMid = 21600; 		//06:00.00am
	private static final int dayEnd = 43199; 		//11:59.59pm
	private static final int nightStart = 43200; 	//12:00.00pm
	private static final int nightMid = 64800;		//06:00.00pm
	private static final int nightEnd = 86399; 		//11:59.59pm
	private static float currentTime = 0; 			//12:00.00am
	private static float hour = 3600;				//one hour time in seconds
	
	private static int daysLived = 1;
	
	
	private boolean isDay = true;
	public static boolean cg_animate_day = true;
	
	public Sun(Vector3f position, Vector3f color, float brightness) {
		super(position, color, brightness);
	}
	
	public Sun(Vector3f position, int r, int g, int b, float brightness) {
		super(position, getColorRGB(r, g, b), brightness);
	}

	public void move() {
		super.setAttenuation(new Vector3f(0.75f, 0, 0));
		if (cg_animate_day) {
			//first things first, lets get the updated TOD for the game
			if (isDay) { //day time calculations
				if (currentTime + 1 <= dayEnd) { //still got time during the day
					currentTime += stepSeconds;
					if (currentTime >= dayMid) { //6am to noon
						moveDistanceCalculation(this.getPosition(), new Vector3f(500, 1600, 500), (dayEnd - currentTime) / stepSeconds);
					} else { //midnight to 6am
						moveDistanceCalculation(this.getPosition(), new Vector3f(750, 800, 500), (dayMid - currentTime) / stepSeconds);
					}
				} else { //day is over, switch to night
					currentTime = nightStart;
					this.setBrightness(1.0f);
					isDay = false;
				}
			} else { //night time calculations
				if (currentTime + 1 <= nightEnd) { //still got time during the night
					currentTime += stepSeconds;
					if (currentTime >= nightMid) {
						if (currentTime <= nightEnd - hour) { //6pm to 11pm
							moveDistanceCalculation(this.getPosition(), new Vector3f(0, 50, 500), (nightEnd - hour - currentTime) / stepSeconds);
						} else { //11pm to midnight
							//zoom to start pos
							moveDistanceCalculation(this.getPosition(), new Vector3f(1000, 50, 500), hour / stepSeconds);
							super.setBrightness(0.0f);
						}
					} else { //noon to 6pm
						moveDistanceCalculation(this.getPosition(), new Vector3f(250, 800, 500), (nightMid - currentTime) / stepSeconds);
					}
				} else { //night is over switch to day
					currentTime = dayStart;
					setPosition(new Vector3f(1000, 50, 500)); //reset sun location
					isDay = true;
					daysLived++;
				}
			}
		}
	}
	
	public void setTOD(int tod) {
		currentTime = tod;
	}
	
	public static Vector3f getColorRGB(int r, int g, int b) {
		return new Vector3f((r * 2) / 255, (g * 2) / 255, (b * 2) / 255);
	}
	
	public int getCurrentTimeInt() {
		return Math.round(currentTime);
	}
	
	public String getCurrentTimeDebug() {
	    int seconds = Math.round(currentTime) % 60;
	    int totalMinutes = Math.round(currentTime) / 60;
	    int minutes = totalMinutes % 60;
	    int hours = totalMinutes / 60;
	    boolean pm = false;
	    if (hours >= 12) {
	    	pm = true;
	    }
		return (pm ? hours - 12 : hours) + ":" + minutes + "." + seconds + (pm ? "pm" : "am");
	}
	
	private void moveDistanceCalculation(Vector3f curPos, Vector3f newPos, float time) {
		float xDist = newPos.x - curPos.x;
		float yDist = newPos.y - curPos.y;
		float zDist = newPos.z - curPos.z;

		float xMove = xDist / time;
		float yMove = yDist / time;
		float zMove = zDist / time;
		
		super.increaseX(xMove);
		super.increaseY(yMove);
		super.increaseZ(zMove);
	}
	
	public int getDay() {
		return daysLived;
	}
	
	public void setDay(int day) {
		daysLived = day;
	}
	
	public void resetWorldTime() {
		daysLived = 1;
		currentTime = dayStart;
	}
}