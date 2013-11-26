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
	private Restaurant4HostRole host;
	private List<Restaurant4Waiter> waiters = new ArrayList<Restaurant4Waiter>();
	private Restaurant4CashierRole cashier;
	private Restaurant4Cook cook;
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
	

	public List<Restaurant4Waiter>  getWaiter(){
		return waiters;
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
		waiters.add(h);
	}
	
	public void setCook(Restaurant4Cook c){
		cook = c;
		System.out.println("Creatd coook!!!!!!");
		/*List<Market> m  = cp.getMarketList();
		cook.setMarketCashier(m.get(0).getCashier());*/
	}
	
	public void setCashier(Restaurant4CashierRole c){
		cashier = c;
	}
	
	@Override
	public String getCustomerName(){
		return "restaurant4customer";
	}
		
}
