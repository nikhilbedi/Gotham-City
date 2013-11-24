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
import simcity.agent.Role;

public class LandlordRole extends Role implements Landlord {
	private String name;
	Timer timer = new Timer();
	
	String type;
	public PersonAgent person;
	float rent;
	private List<RentBill> rentBills = new ArrayList<RentBill>();
	private List<Home> homesOwned = new ArrayList<Home>();
	Random random = new Random();
	
	public enum HomeOwnerState {none, sendBills, sendingBills, billsSent}
	private HomeOwnerState hOwnerState = HomeOwnerState.none;//The start state
	
	
	public LandlordRole (PersonAgent p) {
		//super(p);
		myPerson = p;
		name = myPerson.name;
		
	}
	public LandlordRole() {
		
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
		}
		return false;
	}
	
	//actions
	
	private void sendRentBills() {
		for(Home h: homesOwned) {	
			RentBill rb;
			rb =  myPerson.new RentBill(myPerson, getRent());
			rentBills.add(rb);
			h.setRentBills(rentBills);
		}
	}

	private float getRent() {
		rent = random.nextInt(14) + 1; //randomizes rent amount from $1-15
		return rent;
	}





	//utilities
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
	
}