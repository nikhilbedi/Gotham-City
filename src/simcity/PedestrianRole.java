package simcity; 

import java.util.concurrent.Semaphore;

import simcity.interfaces.Pedestrian;
import agent.Role;


public class PedestrianRole extends Role implements Pedestrian {

	Location destination;
	PersonAgent myPerson;
	Semaphore travelling = new Semaphore(0, true);
	boolean reachedLocation = false;


	public PedestrianRole(PersonAgent person, Location location) {
		destination = location;
		myPerson = person;
	}


	//Messages
	/**
	 * Message from the GUI to notify the location has been reached
	 */
	public void reachedLocation() {
		travelling.release();
	}

	/**
	 * If the pedestrian has not reached his desired location, then travel there.
	 * 
	 */
	public boolean pickAndExecuteAnAction() {
		if(true) {
			travelToLocation();
			return true;
		}
		return false;
	}

	private void travelToLocation() {
		//check mode of transportation
		//DoGoToDestination();
		try {
			travelling.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Message myPerson
		//myPerson.reachedBuilding(destination);
	}
}