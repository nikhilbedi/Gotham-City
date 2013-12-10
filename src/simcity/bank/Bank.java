package simcity.bank;

import java.util.Vector;

import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.bank.interfaces.BankGreeter;
import simcity.bank.interfaces.BankTeller;
import trace.AlertLog;
import trace.AlertTag;

/**
 * Bank Class
 * Programmer: Brice Roland
 */

public class Bank extends Building {
	
	/**
	 * Note: instructions will vary slightly if you're not using interfaces (but you should be for unit testing)
	 * 
	 * 1. Create roles for the building jobs
	 * 
	 * note: make sure you import the correct interface for your specific role class when doing restaurant
	 * 
	 * You will need to create default constructors in your specific roles like so:
	 * 
	 * public ExampleRole(){
	 * 	super();
	 * }
	 * 
	 * easy right?
	 */
	//uncomment below when ready to do away with Robots
	BankGreeter greeter = new BankGreeterRole();
	BankTeller teller = new BankTellerRole();
	
	/**
	 * 2.
	 * Create Guis for the roles
	 * 
	 * you will need to create a constructor for the Guis as well like so
	 * 
	 * note: you may need to import Role (import agent.*)
	 * 
	 * Public ExampleGui(InterFace i, Screen s){
	 * 		super( (Role)i, s);
	 * }
	 * 
	 * badda bing
	 */
	BankGreeterGui greeterGui; 
	BankTellerGui tellerGui;
	
	String bankCustomer = "bankCustomer", bankTeller = "bankTeller", bankGreeter = "bankGreeter";
	//Location location = new Location(xCoor, yCoor);
	int openTime, closeTime;
	public Bank(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		//this is the hours for Bank1
				setWeekdayHours(9, 17);
				setWeekendHours(0,0);
	}
	
	public Bank(String type, int entranceX, int entranceY, int guiX,
			int guiY, int exitX, int exitY) {
		super(type, entranceX, entranceY, guiX, guiY, exitX, exitY);
		
		/**
		 * 3.
		 * Set Guis for the roles
		 * Set the hours for the buildings (this only needs to be done once)
		 * 
		 * reference the excel sheet for this 
		 * Note: the number should only be at max 2 digits
		 * 
		 * ----(Update: set hours will be in the gui once multiple instances of each building are involved but not for now)
		 * 
		 * 
		 */
		greeterGui = new BankGreeterGui(greeter, ScreenFactory.getMeScreen("Bank"));
		tellerGui = new BankTellerGui(teller, ScreenFactory.getMeScreen("Bank"));
		AlertLog.getInstance().logInfo(AlertTag.GUI, "Bank",
				"Your message here" + ScreenFactory.getMeScreen("bank"));
		//this is the hours for Bank1
		setWeekdayHours(9, 17);
		setWeekendHours(0,0);
		
		((BankGreeterRole) greeter).setGui((RoleGui)greeterGui);
		((BankTellerRole) teller).setGui((RoleGui)tellerGui);
	
		/**
		 * 4.
		 * add the roles to the jobRoles map of the Building class
		 * 
		 * see syntax below
		 * 
		 * We concluded it's better to just hard code the reference Strings instead of getting them for the Role for readability
		 */
	
		jobRoles.put("BankGreeter", (Role)greeter);
	//	jobRoles.put("BankGreeter Late",  (Role)greeter);

		jobRoles.put("BankTeller",(Role)teller);
		//jobRoles.put("BankTeller Late", (Role)teller);
		
	}
	
	
	public void setGreeter(BankGreeter g) {
		greeter = g;
	}
	
	public BankGreeter getGreeter() {
		return greeter;
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Bank");
		info.add("Created by: Brice Roland");
		info.add("this is even more super class info");
		return info;
	}

	
	public int getOpenTime() { return openTime;}
	public int getCloseTime() { return closeTime;}
	
	public void setOpenTime(int oT) { openTime = oT;}
	public void setCloseTime(int cT) { closeTime = cT;}
}
