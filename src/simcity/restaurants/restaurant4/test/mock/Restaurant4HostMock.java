package simcity.restaurants.restaurant4.test.mock;

import java.util.List;

import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;

public class Restaurant4HostMock extends Mock implements Restaurant4Host {

	public Restaurant4HostMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void leaving(Restaurant4CustomerRole restaurant4CustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgIWantFood(Restaurant4CustomerRole restaurant4CustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Restaurant4Customer> getWaitingCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

}
