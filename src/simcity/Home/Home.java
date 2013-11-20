package simcity.Home;

import java.util.ArrayList;
import java.util.List;

import simcity.PersonAgent.RentBill;


public class Home {

	//Location location;
	String type;
	public List<String> groceryList;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;
	public ResidentRole resident; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list
	
	public Home(String type){
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();	
		//resident = new ResidentRole();  
		//You'll notice for this particular role, resident needs a Person as a parameter. 
		//But, we don't necessarily immediately know who this Person is.
		//Please add ANOTHER constructor that doesnt need a Person parameter
		//(You can still keep the other constructor, resulting in two constructors)
	}
	
}	

