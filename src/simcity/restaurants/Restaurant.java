package simcity.restaurants;

import java.util.Vector;

import agent.Role;
import simcity.Building;

/**
 * This base class is necessary so we can hold all of our restaurants as similar objects, 
 * they are all very different
 * @author nikhil
 *
 */
public class Restaurant extends Building {

	//protected Role host;
//	protected Role cashier;
	
	public Restaurant(String type, int entranceX, int entranceY, int guiX, int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
	}
	
	public Restaurant(String type, int entranceX, int entranceY, int guiX, int guiY, String address) {
		super(type, entranceX, entranceY, guiX, guiY);
	}
	
	//Everything below should be overridden by restaurants
	
	public void setHost(Role role) {
	//	this.host = host;
	}
	
	public Role getHost() {
		//return host;
		return null;
	}
	
	public void setCashier(Role role) {
		//this.cashier = cashier;
	}
	
	public Role getCashier() {
		//return cashier;
		return null;
	}
	
	public void setCook(Role role) {
		//this.cashier = cashier;
	}
	
	public Role getCook() {
		//return cashier;
		return null;
	}
	
	//This must be overidden
	public String getCustomerName(){
		return "This should not be called";
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("this is some test info");
		info.add("this is some more test info");
		info.add("this is even more test info");
		return info;
	}
}