package simcity.bank;

import simcity.Building;
import simcity.bank.interfaces.BankGreeter;

/**
 * Bank Class
 * Programmer: Brice Roland
 */

public class Bank extends Building {

	BankGreeter greeter;
	String bankCustomer = "bankCustomer";
	//Location location = new Location(xCoor, yCoor);
	int openTime, closeTime;
	
	
	public Bank(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		// TODO Auto-generated constructor stub
	}
	
	
	public void setGreeter(BankGreeter g) {
		greeter = g;
	}
	
	public BankGreeter getGreeter() {
		return greeter;
	}
	
	/*public void setLocation(Location l) {
		location = l;
	}
	
	public Location getLocation() {
		return location;
	}*/
	
	public int getOpenTime() { return openTime;}
	public int getCloseTime() { return closeTime;}
	
	public void setOpenTime(int oT) { openTime = oT;}
	public void setCloseTime(int cT) { closeTime = cT;}
}
