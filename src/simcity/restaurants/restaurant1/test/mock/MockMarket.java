package simcity.restaurants.restaurant1.test.mock;

import simcity.restaurants.restaurant1.interfaces.Cashier;
import simcity.restaurants.restaurant1.interfaces.Market;
import simcity.restaurants.restaurant1.CashierRole.Check;
import simcity.tests.mock.*;

/**
 * A MockMarket built to unit test a CashierAgent.
 *
 * @author Nikhil Bedi
 *
 */
public class MockMarket extends Mock implements Market {

    /**
     * Reference to the Cashier under test that can be set by the unit test.
     */
    public Cashier cashier;
    public EventLog log = new EventLog();

    public MockMarket(String name) {
    	super(name);

    }

    @Override
    public void hereIsPayment(double amount){
    	if(amount >= 10.0) {
    		log.add(new LoggedEvent("received full payment from cashier"));
    	}
    	else {
    		log.add(new LoggedEvent("cutting supply to this restaurant. No more orders for them"));
    		
    	}
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
