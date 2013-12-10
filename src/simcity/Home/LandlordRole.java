package simcity.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import simcity.PersonAgent;
import simcity.Home.ResidentRole.HomeState;
import simcity.Home.gui.ResidentGui;
import simcity.Home.interfaces.Landlord;
import simcity.PersonAgent.RentBill;
import agent.Role;

public class LandlordRole extends Role implements Landlord {
	public PersonAgent person;
	public float rent;
	public List<RentBill> rentBills = new ArrayList<RentBill>();
	public List<Home> homesOwned = new ArrayList<Home>();
	public List<Apartment> apartmentsOwned = new ArrayList<Apartment>();
	public int currentTime;
	public int timeToSendBills;
	public List<ResidentRole> residents;
	Random random = new Random();
	
	public enum HomeOwnerState {none, sendBills, sendingBills, billsSent}
	public HomeOwnerState hOwnerState = HomeOwnerState.none;//The start state
	
	
	public LandlordRole (PersonAgent p) {
		super(p);
		timeToSendBills = 12;
		rent = 10;
		//name = myPerson.name;	
	}
	public LandlordRole() {
		timeToSendBills = 12;
		rent = 10;
	}

	
	//messages
	public void msgSendRentBills() { //message from World Clock Timer
		hOwnerState = HomeOwnerState.sendBills;
		stateChanged();
	}
	
	//scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		if(hOwnerState == HomeOwnerState.sendBills){
			hOwnerState = HomeOwnerState.sendingBills;
			sendRentBills();
			return true;
		}
		if(hOwnerState == HomeOwnerState.billsSent){
			hOwnerState = HomeOwnerState.none;
			return true;
		}
		return false;
	}
	
	//actions
	
	public void sendRentBills() {
		for(Home h: homesOwned) {	
			RentBill rb;
			rb =  myPerson.new RentBill(myPerson, rent);
			rentBills.add(rb);
			h.setRentBills(rentBills);
		}
		for(Apartment a: apartmentsOwned) {
			for(ResidentRole r: residents){
				RentBill rb;
				rb = myPerson.new RentBill(myPerson, rent);
				rentBills.add(rb);
				a.setRentBills(rentBills);
			}
		}
		//hOwnerState = HomeOwnerState.billsSent;
	}

	private float getRent() {
		rent = random.nextInt(14) + 1; //randomizes rent amount from $1-15
		return rent;
	}


	//utilities
	public void updateCurrentTime(int time){
		currentTime = time;
		//stateChanged();
	}
	public boolean timeIsUp() {
		if(currentTime == timeToSendBills){
			return true;
		}
		return false;
	}
	
	public List<RentBill> getRentBills() {
		return rentBills;
	}

	public void setRentBills(List<RentBill> rentBills) {
		this.rentBills = rentBills;
	}

	public List<Home> getHomesOwned() {
		return homesOwned;
	}

	public void setHomesOwned(List<Home> homesOwned) {
		this.homesOwned = homesOwned;
	}
	
	public void setApartmentsOwned(List<Apartment> apartmentsOwned) {
		this.apartmentsOwned = apartmentsOwned;
	}
	
}