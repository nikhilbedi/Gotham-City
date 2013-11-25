package simcity.Home;

import java.util.ArrayList;
import java.util.List;

import simcity.Building;
import simcity.PersonAgent.RentBill;

public class Home extends Building{
	String type;
	public List<String> groceryList;
	public List<RentBill> rentBills;
	public List<Food> fridgeFoods;

	public ResidentRole resident; //When the PersonAgent reaches thehome, he needs to be able to look at this class and add this role to his list

	public Home(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		this.type = type;
		groceryList = new ArrayList<String>();
		rentBills = new ArrayList<RentBill>();
		fridgeFoods = new ArrayList<Food>();
		resident = new ResidentRole();
	}
}	

