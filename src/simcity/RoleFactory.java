package simcity;

import agent.Role;

class RoleFactory {
	public static Role makeMeRole(String type) {
		
		//bank
		if(type.equals("bankCustomer")) {
			return new Role(null);
		}
		else if(type.equals("restaurant1Customer")) {
			return new Role(null);
		}
		else if(type.equals("restaurant2Customer")) {
			return new Role(null);
		}
		else if(type.equals("restaurant3Customer")) {
			return new Role(null);
		}
		else if(type.equals("restaurant4Customer")) {
			return new Role(null);
		}
		else if(type.equals("restaurant5Customer")) {
			return new Role(null);
		}
		else if(type.equals("marketCustomer")) {
			return new Role(null);
		}
		
		
		return null;
	}
}

