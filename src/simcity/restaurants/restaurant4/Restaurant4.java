package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map;


import Gui.ScreenFactory;
import agent.Role;
import simcity.Building;
import simcity.TheCity;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CashierGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CookGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4HostGui;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4WaiterGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4 extends Restaurant {
	Restaurant4HostRole host = new Restaurant4HostRole();
	Restaurant4WaiterRole waiter = new Restaurant4WaiterRole();
	Restaurant4SharedDataWaiterRole sharedDataWaiter = new Restaurant4SharedDataWaiterRole();
	Restaurant4CashierRole cashier = new Restaurant4CashierRole();
	Restaurant4CookRole cook = new Restaurant4CookRole();
	public TheCity cp;
	private Menu menu;
 
	//gui
	/*Restaurant4WaiterGui waiterGui1 = new Restaurant4WaiterGui(waiter, 1, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4WaiterGui waiterGui2 = new Restaurant4WaiterGui(sharedDataWaiter, 2, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4CookGui cookGui = new Restaurant4CookGui(cook, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4HostGui hostGui = new Restaurant4HostGui(host, ScreenFactory.getMeScreen(this.getName()));
	Restaurant4CashierGui cashierGui = new Restaurant4CashierGui(cashier, ScreenFactory.getMeScreen(this.getName()));
	*/
	public Restaurant4(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		/*waiter.setGui(waiterGui1);
		sharedDataWaiter.setGui(waiterGui2);
		cook.setGui(cookGui);
		host.setGui(hostGui);
		cashier.setGui(cashierGui);
		*/
		setWeekdayHours(6, 6);
		setWeekendHours(6, 6);
		Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
		jobs.put("host", host);
		jobs.put("cashier", cashier);
		jobs.put("cook", cook);
		jobs.put("waiter1", waiter);
		jobs.put("waiter2", sharedDataWaiter);
		setJobRoles(jobs);
		//host.setWaiter(waiter);
	//	host.setWaiter(sharedDataWaiter);
		//waiter.setHost(host);
		//sharedDataWaiter.setHost(host);
	//	waiter.setCashier(cashier);
	//	sharedDataWaiter.setCashier(cashier);
	//	waiter.setCook(cook);
	//	sharedDataWaiter.setCook(cook);
	//	cook.setCashier(cashier);
	}	

	@Override
	public void setHost(Role host) {
		this.host = (Restaurant4HostRole) host;
	}
	
	@Override
	public Role getHost() {
		return (Restaurant4HostRole)host;
	}
	
	@Override
	public void setCashier(Role cashier) {
		this.cashier = (Restaurant4CashierRole)cashier;
	}
	
	@Override
	public Role getCashier() {
		return (Restaurant4CashierRole)cashier;
	}
	

	public Restaurant4WaiterRole  getWaiter(){
		return waiter;
	}
	
	public  Restaurant4Cook getCook(){
		return cook;
	}
	

	  public void setCity(TheCity c){
	    	cp = c;
	    }

	public void  setHost(Restaurant4HostRole h){
		host = h;
	}
	
	public void  setWaiter(Restaurant4Waiter h){
		waiter = (Restaurant4WaiterRole) h;
	}
	
	public void setSharedDataWaiter(Restaurant4Waiter h){
		sharedDataWaiter = (Restaurant4SharedDataWaiterRole) h;
	}
	
	public void setCook(Restaurant4CookRole c){
		cook = c;
	}
	
	public void setCashier(Restaurant4CashierRole c){
		cashier = c;
	}
	
	@Override
	public String getCustomerName(){
		return "restaurant4customer";
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 4");
		info.add("Created by: Meruyert Aitbay");
		info.add("this is even more super class info");
		return info;
	}
		
}
