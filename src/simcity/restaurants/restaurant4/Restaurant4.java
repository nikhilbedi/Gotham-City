package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.List;

import simcity.Building;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4 extends Building {
	private Restaurant4Host host;
	private List<Restaurant4Waiter> waiters = new ArrayList<Restaurant4Waiter>();
	private Restaurant4Cashier cashier;
	private Restaurant4Cook cook;
	
	private Menu menu;
 
	
	public Restaurant4(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		// TODO Auto-generated constructor stub	
	}	
	
	
	public void  setHost(Restaurant4Host h){
		host = h;
	}
	
	public void  setWaiter(Restaurant4Waiter h){
		waiters.add(h);
	}
	
	public void setCook(Restaurant4Cook c){
		cook = c;
	}
	
	public void setCashier(Restaurant4Cashier c){
		cashier = c;
	}
		
}
