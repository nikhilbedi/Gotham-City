package simcity.restaurants.restaurant1;

import java.util.*;

import simcity.restaurants.*;

/**
 * Nikhil's restaurant
 * @author nikhil
 *
 */
public class Restaurant1 extends Restaurant {
	public Restaurant1(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		// TODO Auto-generated constructor stub
	}
	HostRole host = new HostRole();
	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	CookRole cook = new CookRole();
	//open and closing hours? hmmm..
}