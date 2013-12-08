package simcity.Home;

import java.util.ArrayList;
import java.util.List;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.PersonAgent.RentBill;
import simcity.Home.gui.ApartmentResidentGui;
import simcity.Home.test.mock.MockResident;


public class Apartment extends Home {

	String type;
	int size;
	int roomNumber;
	public List<Integer> roomNumbers;
	public List<Home> rooms;
	public List<String> groceryList;
	//public List<String> groceryBag;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;

	public List<ResidentRole> residents; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list
	//public String resident = "resident";
	
	public Apartment(String type, int entranceX, int entranceY, int guiX,
			int guiY){
		super(type, entranceX, entranceY, guiX, guiY);
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();	
		residents = new ArrayList<ResidentRole>();
		ResidentRole res1 = new ResidentRole();
		ApartmentResidentGui res1gui = new ApartmentResidentGui(this, res1, ScreenFactory.getMeScreen("Apartment 1"));
		res1.setGui((RoleGui)new ApartmentResidentGui());
		
		residents.add(new ResidentRole());
		rooms = new ArrayList<Home>();
		roomNumbers = new ArrayList<Integer>();
		
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
	public List<Home> getRooms() {
		return rooms;
	}
	public void setRooms(List<Home> homes) {
		this.rooms = rooms;
	}
	@Override
	public ResidentRole getResident() {
		return residents.get(0);
	}
}	

