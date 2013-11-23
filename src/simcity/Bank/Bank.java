package simcity.Bank;

import simcity.Bank.interfaces.BankGreeter;

/**
 * Bank Class
 * Programmer: Brice Roland
 */

public class Bank /*extends Building*/{
	BankGreeter greeter;
	//Location location = new Location(xCoor, yCoor);
	int openTime, closeTime;
	
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
