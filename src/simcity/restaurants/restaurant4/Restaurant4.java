package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.List;

import agent.Role;
import simcity.Building;
import simcity.TheCity;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.HostRole;
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
 
	
	public Restaurant4(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		
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
		
}
