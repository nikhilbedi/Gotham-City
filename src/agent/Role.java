//imports
package agent;

import Gui.RoleGui;
import simcity.PersonAgent;

public class Role {

	protected PersonAgent myPerson;
	public boolean active; 
	
	protected RoleGui gui;

	public RoleGui getGui() {
		return gui;
	}
	public void setGui(RoleGui g){
		gui = g;
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
	
    /**
     * Return agent name for messages.  Default is to return java instance
     * name.
     */
    protected String getName() {
        return StringUtil.shortName(myPerson.getName());
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
        sb.append(myPerson.getName());
        sb.append(": ");
        sb.append(msg);
        sb.append("\n");
        if (e != null) {
            sb.append(StringUtil.stackTraceString(e));
        }
        System.out.print(sb.toString());
    }
	
	//Everyone needs to provide an @Override to their pickAndExecuteAnAction
	public boolean pickAndExecuteAnAction() {
		return true;
	}
	public void startBuildingMessaging(){
		System.err.println("Start messaging super call");
	}

}