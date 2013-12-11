package simcity;

import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import agent.Role;
import simcity.Home.LandlordRole;
import simcity.Home.ResidentRole;
import simcity.Market.MarketCustomerRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant3.Restaurant3CustomerRole;
import simcity.restaurants.restaurant2.Restaurant2CustomerRole;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant5.Restaurant5CustomerRole;
import simcity.bank.*;


public class RoleFactory {
	Screen bank;

	public static Role makeMeRole(String type) {
		if(type.equalsIgnoreCase("bankRobber")){
			return new BankRobberRole();
		}
		else if(type.equalsIgnoreCase("landlord")) {
			return new LandlordRole();
		}
		//home resident
		else if(type.equals("residentRole")) {
			return new ResidentRole();
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
			Restaurant2CustomerRole r = new Restaurant2CustomerRole();
			return r;
			//return new Role();
		}
		//Evan's Restaurant
		else if(type.equalsIgnoreCase("restaurant3Customer")) {
			Restaurant3CustomerRole r = new Restaurant3CustomerRole();
			return r;
		}
		//Mika's Restaurant
		else if(type.equalsIgnoreCase("restaurant4Customer")) {
			Restaurant4CustomerRole r = new Restaurant4CustomerRole();
			return r;
		}
		//Hunter's Restaurant
		else if(type.equalsIgnoreCase("restaurant5Customer")) {
			Restaurant5CustomerRole r = new Restaurant5CustomerRole();
			return r;
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

