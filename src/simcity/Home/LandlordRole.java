package simcity.Home;

import java.util.Timer;

import simcity.PersonAgent;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Landlord;
import simcity.agent.Role;

public class LandlordRole extends Role implements Landlord {
	private String name;
	Timer timer = new Timer();
	
	String type;
	public PersonAgent person;
	
	
	public LandlordRole (PersonAgent p) {
		//super(p);
		myPerson = p;
		name = myPerson.name;
		
	
	}
}