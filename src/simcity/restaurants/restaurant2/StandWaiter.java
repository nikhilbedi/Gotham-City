package simcity.restaurants.restaurant2;

import Gui.RoleGui;
import simcity.PersonAgent;
import simcity.restaurants.restaurant2.gui.WaiterGui;



public class StandWaiter extends WaiterRole{
	public StandWaiter(PersonAgent person) {
		super(person);
	}
	
	public StandWaiter() {
		super();
	}
	
	public void setGui(RoleGui gui) {
		super.setGui(gui);
		waiterGui = (WaiterGui)gui;
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
