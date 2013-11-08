//imports
package agent;

import simcity.PersonAgent;

public abstract class Role {
	/**
	 * Base class for simple roles
	 */
	
	PersonAgent myPerson;
	
	public PersonAgent getPersonAgent() {
		return myPerson;
	}
	
	public void setPerson(PersonAgent a){
		myPerson = a;
	}
	
	private void stateChanged() {
		myPerson.stateChanged();
	}
}