package simcity;

import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Market.MarketCustomerRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.bank.*;


public class RoleFactory {
	Screen bank;

	public static Role makeMeRole(String type) {
		//home resident
		if(type.equalsIgnoreCase("homeResident")) {
			return new Role();
		}

		//bank
		else if(type.equalsIgnoreCase("bankCustomer")) {
			BankCustomerRole r = new BankCustomerRole();
			//Do you set info here?
			return r;
		}

		//the different restaurant customers
		//Nikhil's Restaurant
		else if(type.equalsIgnoreCase("restaurant1Customer")) {
			Restaurant1CustomerRole r = new Restaurant1CustomerRole();
			return r;
		}
		//Brice's Restaurant
		else if(type.equalsIgnoreCase("restaurant2Customer")) {
			return new Role();
		}
		//Evan's Restaurant
		else if(type.equalsIgnoreCase("restaurant3Customer")) {
			return new Role();
		}
		//Mika's Restaurant
		else if(type.equalsIgnoreCase("restaurant4Customer")) {
			return new Role();
		}
		//Hunter's Restaurant
		else if(type.equalsIgnoreCase("restaurant5Customer")) {
			return new Role();
		}

		//market(s)
		else if(type.equalsIgnoreCase("marketCustomer")) {
			MarketCustomerRole r = new MarketCustomerRole();
			return r;
		}

		//Restaurant jobs
		else if(type.equalsIgnoreCase("restaurant1Waiter")) {

			return new Role();
		}


		//etc... I don't know if we need to do this for jobs. 
		//We will stop here because becoming a customer is the most necessary.

		return null;
	}
}

