package simcity.restaurants.restaurant1.test.mock;

import simcity.restaurants.restaurant1.interfaces.Cashier;
import simcity.restaurants.restaurant1.interfaces.Customer;
import simcity.restaurants.restaurant1.interfaces.Waiter;
import simcity.restaurants.restaurant1.CashierRole.Check;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.tests.mock.*;

/**
 * A MockWaiter built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public class MockWaiter extends Mock implements Waiter {

    /**
     * Reference to the Cashier under test that can be set by the unit test.
     */
    public Cashier cashier;
    public EventLog log = new EventLog();

    public MockWaiter(String name) {
	super(name);

    }
    
    /**
     * Hunter: Nikhil all this was auto-Generated so there wouldn't be an error on launch
     */

    @Override
    public void hereIsCheck(Check ch){
    	log.add(new LoggedEvent("Received check for " + ch.customer));
    }

	@Override
	public void goOffBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goOnBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doNotGoOnBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pleaseSeatCustomer(Customer cust, int table, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneWithTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shouldTakeBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ledCustomerToTableAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reachedCookAnimation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onBreak() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readyToOrder(Customer cust) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hereIsMyChoice(String choice, Customer cust) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readyForCheck(Restaurant1CustomerRole role) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doneAndLeaving(Customer cust) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void customerLeft(Customer cust) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void orderDone(int table, String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOfFood(int table, String choice) {
		// TODO Auto-generated method stub
		
	}
    
    /*
      @Override
      public void HereIsYourTotal(double total) {
      log.add(new LoggedEvent("Received HereIsYourTotal from cashier. Total = "+ total));

      if(this.name.toLowerCase().contains("thief")){
      //test the non-normative scenario where the customer has no money if their name contains the string "theif"
      cashier.IAmShort(this, 0);

      }else if (this.name.toLowerCase().contains("rich")){
      //test the non-normative scenario where the customer overpays if their name contains the string "rich"
      cashier.HereIsMyPayment(this, Math.ceil(total));

      }else{
      //test the normative scenario
      cashier.HereIsMyPayment(this, total);
      }
      }

      @Override
      public void HereIsYourChange(double total) {
      log.add(new LoggedEvent("Received HereIsYourChange from cashier. Change = "+ total));
      }

      @Override
      public void YouOweUs(double remaining_cost) {
      log.add(new LoggedEvent("Received YouOweUs from cashier. Debt = "+ remaining_cost));
      }
    */
}
