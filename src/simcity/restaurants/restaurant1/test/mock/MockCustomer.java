package simcity.restaurants.restaurant1.test.mock;

import simcity.restaurants.restaurant1.interfaces.Cashier;
import simcity.restaurants.restaurant1.interfaces.Customer;
import simcity.restaurants.restaurant1.CashierRole.Check;
import simcity.tests.mock.*;
/**
 * A sample MockCustomer built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public class MockCustomer extends Mock implements Customer {

    /**
     * Reference to the Cashier under test that can be set by the unit test.
     */
    public Cashier cashier;
    // public Check myCheck;
    public EventLog log = new EventLog();

    public MockCustomer(String name) {
    	super(name);
    }
    
    @Override
    public void hereIsChange(double amount) {
    	log.add(new LoggedEvent("Cashier gave me change. I have " + amount + " money left."));
    }

    //message from waiter to customer to launch the customer to go pay the cashier
    public void hereIsBill(Check ch) {
		log.add(new LoggedEvent("Received hereIsBill from waiter."));
		//myCheck = ch;
		
		//nonnormative scenario: customer doesnt have enough money for any meal
    	if(name.equals("flake"))
    		cashier.hereIsPayment(ch, 4);
    	
    	//Normative: pay cashier enough to cover any meal
    	else
    		cashier.hereIsPayment(ch, 20);
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
