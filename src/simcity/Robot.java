package simcity;

import agent.Role;
import Gui.Screen;

public class Robot extends PersonAgent{
	public Robot(String name) {
		super(name);
	}
	public Robot(String name, PersonGui g, Screen s) {
		super(name, g, s);
	}
	public boolean pickAndExecuteAnAction() {
		//Role Scheduler
		//This should be changed to activeRole.pickAndExecuteAnAction();
		for(Role r : roles) {
			//System.out.println("Calling role schedulers");
			r.pickAndExecuteAnAction();
		}

		return false;
	}

}

