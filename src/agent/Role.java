//imports
package agent;

import Gui.RoleGui;
import simcity.PersonAgent;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;

public class Role {
	protected PersonAgent myPerson;
	protected PersonAgent theManLeavingMe;
	protected RoleGui gui;
	private boolean active;
	protected boolean onWork = false;

	public RoleGui getGui() {
		return gui;
	}
	public void setGui(RoleGui g){
		gui = g;
	}
	
	/**
	 * This will be used so if a role still has customers, 
	 * that role will finish without being given more customers
	 * @param b If it is true, that means it can take customers. If it is false, the host/greeter/etc. won't give new customers
	 */
	public void setWorkStatus(boolean b) {
		onWork = b;
	}
	
	public boolean checkWorkStatus() {
		return onWork;
	}
	
	public void getReadyToLeave(PersonAgent whoTurnedMeOff) {
		theManLeavingMe = whoTurnedMeOff;
		stateChanged();
	}

	protected void leaveWork() {
		theManLeavingMe.leaveWork(this);
		theManLeavingMe = null;
	}
	
	/**
	 * Base class for simple roles
	 */
	public Role(PersonAgent person, RoleGui gui){
		this.gui = gui;
		myPerson = person;
	}

	public Role(PersonAgent person){
		myPerson = person;
	}

	public Role() {

	}

	public PersonAgent getPersonAgent() {
		return myPerson;
	}
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	/**
	 * Return agent name for messages.  Default is to return java instance
	 * name.
	 */
	public String getName() {
		if(myPerson != null)
			return StringUtil.shortName(myPerson.getName());
		else
			return "";
	}

	public void setPerson(PersonAgent a){
		myPerson = a;
	}

	public void stateChanged() {
		myPerson.stateChanged();
	}

	/**
	 * Print message
	 */
	protected void print(String msg) {
		print(msg, null);
	}

	/**
	 * Print message with exception stack trace
	 */
	protected void print(String msg, Throwable e) {
		StringBuffer sb = new StringBuffer();
		if(myPerson != null){
			sb.append(myPerson.getName());
			sb.append(": ");
			sb.append(msg);
			sb.append("\n");
			if (e != null) {
				sb.append(StringUtil.stackTraceString(e));
			}
			System.out.print(sb.toString());
		}
	}

	//Everyone needs to provide an @Override to their pickAndExecuteAnAction
	public boolean pickAndExecuteAnAction() {
		return true;
	}
	public void startBuildingMessaging(){
		System.err.println("Start messaging super call");
	}

}