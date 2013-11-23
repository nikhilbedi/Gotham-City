//imports
package agent;

import simcity.PersonAgent;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;

public class Role {
	/**
	 * Base class for simple roles
	 */
	public Role(PersonAgent person){
		myPerson = person;
	}
	
	PersonAgent myPerson;
	
	public PersonAgent getPersonAgent() {
		return myPerson;
	}
	
	public void setPerson(PersonAgent a){
		myPerson = a;
	}
	
	public void stateChanged() {
		myPerson.stateChanged();
	}
	
	//Everyone needs to provide an @Override to their pickAndExecuteAnAction
	public boolean pickAndExecuteAnAction() {
		return true;
	}

}