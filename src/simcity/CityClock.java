package simcity;

import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;


public final class CityClock {
	private static Vector<PersonAgent> people = new Vector<PersonAgent>();
	private static Timer timer = new Timer();
	private static int currentTime = 1;
	private static long hourLength = 4000;//here is where you set the length of an hour in milliseconds

	public static void startTime() {
		final long hourTemp = hourLength;
		timer.schedule(new TimerTask() {
			public void run() {
				currentTime++;
				if(currentTime == 25) {
					currentTime = 1;
				}
				System.out.println("The time is now: " + currentTime);
				TheCity.bar.updateTime();
				for(PersonAgent p : people) {
					p.updateTime(currentTime);
				}
				startTime();
			}
		},
		hourTemp);
	}

	public static void addPersonAgent(PersonAgent p) {
		people.add(p);
	}
	
	public static void setTimeInterval(long hl) {
		hourLength = hl;
	}
	public static Vector<PersonAgent> getPeople(){
		return people;
	}
}