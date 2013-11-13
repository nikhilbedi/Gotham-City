//imports
package agent;

import simcity.PersonAgent;

public class Role {
	/**
	 * Base class for simple roles
	 */
	public Role(PersonAgent person){
		myPerson = person;
	}
	
	protected PersonAgent myPerson;
	
	public PersonAgent getPersonAgent() {
		return myPerson;
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
       // sb.append(getName());
        sb.append(": ");
        sb.append(msg);
        sb.append("\n");
        if (e != null) {
            sb.append(StringUtil.stackTraceString(e));
        }
        System.out.print(sb.toString());
    }
}
