package simcity;

import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import agent.Role;

import simcity.bank.*;


public class RoleFactory {
	Screen bank;
	
	public static Role makeMeRole(String type) {
		//home resident
		if(type.equals("homeResident")) {
			return new Role();
		}
		
		//bank
		else if(type.equals("bankCustomer")) {
		BankCustomerRole r = new BankCustomerRole();
		//Do you set info here?
		System.out.println("this is another message" + r.getGui());
		return r;
		}
		
		//the different restaurant customers
		//Nikhil's Restaurant
		else if(type.equals("restaurant1Customer")) {
			return new Role();
		}
		//Brice's Restaurant
		else if(type.equals("restaurant2Customer")) {
			return new Role();
		}
		//Evan's Restaurant
		else if(type.equals("restaurant3Customer")) {
			return new Role();
		}
		//Mika's Restaurant
		else if(type.equals("restaurant4Customer")) {
			return new Role();
		}
		//Hunter's Restaurant
		else if(type.equals("restaurant5Customer")) {
			return new Role();
		}
		
		//market(s)
		else if(type.equals("marketCustomer")) {
			return new Role();
		}
		
		//Restaurant jobs
		else if(type.equals("restaurant1Waiter")) {
			return new Role();
		}
			
		
		//etc... I don't know if we need to do this for jobs. 
		//We will stop here because becoming a customer is the most necessary.
		
		return null;
	}
}

