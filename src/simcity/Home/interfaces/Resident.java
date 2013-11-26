package simcity.Home.interfaces;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import simcity.Home.Food;
import simcity.Home.ResidentRole.HomeEvent;
import simcity.Home.ResidentRole.HomeState;
import simcity.PersonAgent.RentBill;
import simcity.PersonAgent.RentState;

public interface Resident {
	
	//messages
	public abstract void gotHungry();
	
	public abstract void gotSleepy();
	
	public abstract void wakeUp();
	

	
	public abstract void AtTable();

	public abstract void atSink();

	public abstract void atPlatingArea();

	public abstract void atStove();

	public abstract void atBed();

	public abstract void atFridge();

	public abstract void atMailbox();
	
	public abstract void exited();
	
	
	//actions
	public abstract void putGroceriesInFridge(Map<String, Integer> groceryBag);
	
	public abstract void goToMailbox();
	
	public abstract void checkMail();

	public abstract void payRent(List<RentBill> rentBills);
		
	public abstract void goToBed();

	public abstract void returnToHomePosition();
	

	public abstract void clearFood();
	
	public abstract void exitHome();


	public abstract void atHome();
	
	
}
