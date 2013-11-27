package simcity.restaurants.restaurant5;

import java.util.*;

import agent.Role;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant5.*;


public class Restaurant5 extends Restaurant {
	
	public Restaurant5(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		// TODO Auto-generated constructor stub
	}
	HostRole host = new HostRole("What is this?");
	CashierRole cashier = new CashierRole("What is this?");
	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	CookRole cook = new CookRole("What is this?");
	//open and closing hours? hmmm..
	
	public String getCustomerName(){
		return "restaurant5customer";
	}
	
	@Override
	public void setHost(Role host) {
		this.host = (HostRole) host;
	}
	
	@Override
	public Role getHost() {
		return (HostRole)host;
	}
	
	@Override
	public void setCashier(Role cashier) {
		this.cashier = (CashierRole)cashier;
	}
	
	@Override
	public Role getCashier() {
		return (CashierRole)cashier;
	}
}