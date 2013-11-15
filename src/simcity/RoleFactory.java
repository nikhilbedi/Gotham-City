package simcity;

import agent.Role;

class RoleFactory {
	public static Role makeMeRole(String type) {
		//home resident
		if(type.equals("homeResident")) {
			return new Role(null);
		}
		
		//bank
		else if(type.equals("bankCustomer")) {
			return new Role(null);
		}
		
		//the different restaurant customers
		//Nikhil's Restaurant
		else if(type.equals("restaurant1Customer")) {
			return new Role(null);
		}
		//Brice's Restaurant
		else if(type.equals("restaurant2Customer")) {
			return new Role(null);
		}
		//Evan's Restaurant
		else if(type.equals("restaurant3Customer")) {
			return new Role(null);
		}
		//Mika's Restaurant
		else if(type.equals("restaurant4Customer")) {
			return new Role(null);
		}
		//Hunter's Restaurant
		else if(type.equals("restaurant5Customer")) {
			return new Role(null);
		}
		
		//market(s)
		else if(type.equals("marketCustomer")) {
			return new Role(null);
		}
		
		//Restaurant jobs
		else if(type.equals("restaurant1Waiter")) {
			return new Role(null);
		}
			
		
		//etc... I don't know if we need to do this for jobs. 
		//We will stop here because becoming a customer is the most necessary.
		
		return null;
	}
}

