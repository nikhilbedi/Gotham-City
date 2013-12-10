package simcity.restaurants.restaurant2;

import simcity.PersonAgent;



public class StandWaiter extends WaiterRole{
	public StandWaiter(PersonAgent person) {
		super(person);
	}
	
	public StandWaiter() {
		super();
	}
	
	@Override
	public void dropOffOrders() {
		for(int c = 0; c < myCustomers.size(); c++) {
			if(myCustomers.get(c).s == customerState.orderBeingDroppedOff) {
				print("Putting on the stand order for table " + myCustomers.get(c).table);
				RevolvingStand.addOrder(this, myCustomers.get(c).choice, myCustomers.get(c).table, cook);
				myCustomers.get(c).s = customerState.waitingForFood;
			}
		}
	}
}
