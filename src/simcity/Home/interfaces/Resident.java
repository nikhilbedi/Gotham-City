package simcity.Home.interfaces;

import java.util.List;

import simcity.PersonAgent.RentBill;

public interface Resident {
	
	public abstract void gotHungry();

	public abstract void payRent(List<RentBill> rentBills);
	
	public abstract void AtTable();

	public abstract void atSink();

	public abstract void atPlatingArea();

	public abstract void atStove();

	public abstract void atBed();

	public abstract void atFridge();

	public abstract void exited();

	public abstract void checkMail();

	public abstract void atMailbox();

	public abstract void atHome();

	

	
	
}
