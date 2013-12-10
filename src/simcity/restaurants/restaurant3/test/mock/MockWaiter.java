package simcity.restaurants.restaurant3.test.mock;

import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.interfaces.*;
import simcity.tests.mock.*;

public class MockWaiter extends Mock implements Waiter{

	
	public EventLog log = new EventLog();

	public MockWaiter(String name) {
		super(name);
		
	}

	@Override
	public void msgSitAtTable(Customer c, int table) {
		log.add(new LoggedEvent("Received msgSitAtTable from Host"));
		
	}

	@Override
	public void msgReadyToOrder(Customer cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Ready to Order"));
	}

	@Override
	public void msgHereIsMyChoice(Customer cust, String choice) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Here is my choice"));
	}

	@Override
	public void msgWaiterOutOfFood(Order o) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Waiter out of food"));
	}

	@Override
	public void msgOrderIsReady(Order order) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Order is ready"));
		
	}

	@Override
	public void msgIWantTheCheck(Customer cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("I want the check"));
	}

	@Override
	public void msgWaiterHereIsCheck(Order o) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Waiter here is check"));
	}

	@Override
	public void msgDoneEatingAndLeavingTable(Customer cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Eating anf leaving table"));
	}
	
	public int getCustomersCount() {
		return -1;
	}

	@Override
	public void msgAtTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtHost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtCashier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtCook() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void msgAtStand() {
		// TODO Auto-generated method stub
		
	}

}
