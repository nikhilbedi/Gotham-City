package simcity.restaurants.restaurant2;

import java.util.*;

import agent.Role;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.CookRole;

/**
 * Brice's restaurant
 * @author Brice
 *
 */
public class Restaurant2 extends Restaurant {
	
	public Restaurant2(String type, int entranceX, int entranceY, int guiX,
			int guiY) {
		super(type, entranceX, entranceY, guiX, guiY);
		// TODO Auto-generated constructor stub
	}
	HostRole host = new HostRole();
	CashierRole cashier = new CashierRole();
	List<WaiterRole> waiters = new ArrayList<WaiterRole>();
	CookRole cook = new CookRole();
	//open and closing hours? hmmm..
	
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
	
	@Override
	public void setCook(Role cook) {
		this.cook = (CookRole)cook;
	}

	@Override
	public Role getCook() {
		return (CookRole)cook;
	}
	
	@Override
	public String getCustomerName(){
		return "restaurant2Customer";
	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Restaurant 2");
		info.add("Created by: Brice Roland");
		info.add("this is even more super class info");
		return info;
	}
}