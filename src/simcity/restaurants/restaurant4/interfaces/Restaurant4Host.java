package simcity.restaurants.restaurant4.interfaces;

import java.util.List;

import simcity.restaurants.restaurant4.Restaurant4CustomerRole;

public interface Restaurant4Host {

	void leaving(Restaurant4CustomerRole restaurant4CustomerRole);

	void msgIWantFood(Restaurant4CustomerRole restaurant4CustomerRole);
	
	List<Restaurant4Customer> getWaitingCustomers();

}
