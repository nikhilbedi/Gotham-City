package simcity;

import agent.Role;
import agent.StringUtil;
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
			/*if(r.pickAndExecuteAnAction()) { //Nikhil: changed robot code because this it didnt return true
				return true;
			}*/
		}

		return false;
	}
	
	/**
	 * Print message with exception stack trace
	 */
	protected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append(": ");
		sb.append(msg);
		sb.append("\n");
		if (e != null) {
			sb.append(StringUtil.stackTraceString(e));
		}
		System.out.print(sb.toString());
	}

}

