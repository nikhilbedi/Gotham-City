package simcity.Home;

import java.util.ArrayList;
import java.util.List;

import simcity.Building;
import simcity.PersonAgent.RentBill;
import simcity.Home.test.mock.MockResident;


public class Apartment extends Building{

	String type;
	public List<String> groceryList;
	//public List<String> groceryBag;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;

	public List<ResidentRole> resident; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list
	//public String resident = "resident";
	
	public Apartment(String type, int entranceX, int entranceY, int guiX,
			int guiY){
		super(type, entranceX, entranceY, guiX, guiY);
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();	
		resident = new ArrayList<ResidentRole>();  
		//You'll notice for this particular role, resident needs a Person as a parameter. 
		//But, we don't necessarily immediately know who this Person is.
		//Please add ANOTHER constructor that doesnt need a Person parameter
		//(You can still keep the other constructor, resulting in two constructors)
	}

	public List<RentBill> getRentBills() {
		return rentBills;
	}

	public void setRentBills(List<RentBill> rentBills) {
		this.rentBills = rentBills;
	}
}	

