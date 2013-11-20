package simcity.restaurant_evan.src.restaurant.test.mock;


import simcity.restaurant_evan.src.restaurant.CustomerRole;
import simcity.restaurant_evan.src.restaurant.Order;

public class MockWaiter extends Mock implements Waiter{

	
	public EventLog log = new EventLog();

	public MockWaiter(String name) {
		super(name);
		
	}

	@Override
	public void msgSitAtTable(CustomerRole c, int table) {
		log.add(new LoggedEvent("Received msgSitAtTable from Host"));
		
	}

	@Override
	public void msgReadyToOrder(CustomerRole cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Ready to Order"));
	}

	@Override
	public void msgHereIsMyChoice(CustomerRole cust, String choice) {
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
	public void msgIWantTheCheck(CustomerRole cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("I want the check"));
	}

	@Override
	public void msgWaiterHereIsCheck(Order o) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Waiter here is check"));
	}

	@Override
	public void msgDoneEatingAndLeavingTable(CustomerRole cust) {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("Eating anf leaving table"));
	}

}
