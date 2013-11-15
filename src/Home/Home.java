package Home;

import java.util.ArrayList;
import java.util.List;

import simcity.PersonAgent.RentBill;


public class Home {

	//Location location;
	String type;
	public List<String> groceryList;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;
	public ResidentRole resident;
	
	public Home(String type){
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();
		
	
		
	}
	
		
	}	

