package simcity.restaurants.restaurant4.test;
import org.junit.* ;

import simcity.PersonAgent;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4CashierRole.Payment;
import static org.junit.Assert.* ;


public class Restaurant4CashierTest {
	Restaurant4CashierRole cashier = new Restaurant4CashierRole(new PersonAgent("cashier"));
	MockMarketCashier marketCashier = new MockMarketCashier("marketCashier");
	
	//one marketCashier - restaurantCashier interaction
	@Test
	public void OneTest() throws InterruptedException{
		assertTrue("cashier's payment list must be empty", cashier.getPayments().size() == 0);
		assertTrue("scheduler should return false ", !cashier.pickAndExecuteAnAction());
		cashier.amountDue(20.00, marketCashier);
		assertTrue("cashier's payment list must be empty", cashier.getPayments().size() == 1);
		assertTrue("state must be pending",  cashier.getPayments().get(0).state == Payment.OrderState.pending);
		assertTrue("scheduler should return true ", cashier.pickAndExecuteAnAction());
		assertTrue("state must be pending",  cashier.getPayments().get(0).state == Payment.OrderState.paying);
		cashier.HereIsYourChange(2.0199999999999996, marketCashier);
		assertTrue("scheduler should return true ", cashier.pickAndExecuteAnAction());
		assertTrue("state must be pending",  cashier.getPayments().get(0).state == Payment.OrderState.gotChange);
		assertTrue("cashier's payment list must be empty", cashier.getPayments().size() == 0);
		assertTrue("scheduler should return false ", !cashier.pickAndExecuteAnAction());
	}

}
