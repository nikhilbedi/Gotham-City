package simcity.Market.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.* ;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketCustomerRole.CustomerState;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.Market.test.mock.MockMarketCustomer;
import simcity.Market.test.mock.MockMarketWorker;
import static org.junit.Assert.* ;

public class MarketCustomerTest {

	public MockMarketCashier cashier  = new MockMarketCashier("cashier");
	public MockMarketWorker worker = new MockMarketWorker("worker");
	public MarketCustomerRole customer = new MarketCustomerRole(new PersonAgent("customer"));
	
	public MarketCustomerGui gui= new MarketCustomerGui(customer);
	Item beef = new Item("Beef", 10.99, 100);
	Item chicken = new Item("Chicken", 8.99, 100);
	Item rice = new Item("Rice", 6.99, 100);
	Item potato = new Item("Potato", 5.99, 100);
	Item pizza = new Item("Pizza", 5.99, 100);
	Item salad = new Item("Salad", 3.99, 100);
	Item steak = new Item("Steak", 10.99, 100);
	
	@Test
	public void oneTest() throws InterruptedException{
	
		customer.setGui(gui);
		customer.setCashier(cashier);
		assertTrue("Scheduler should return false", !customer.pickAndExecuteAnAction());
		customer.getGroceries();
		assertTrue("Customer state should be need food", customer.state == CustomerState.needFood);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be need food", customer.state == CustomerState.inALine);
		customer.NextCustomerPlease();
		assertTrue("Customer state should be need food", customer.state == CustomerState.choseGroceries);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.movingToCashier);
		customer.AtCashier();
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.atCashier);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.ordering);
		customer.amountDue(17.98);
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.amountDue);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.paying);
		customer.HereIsChange(0.02);
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.gotChange);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.moving);
		customer.ArrivedToGetItem();
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.gettingItems);
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("Chicken", 2);
		customer.HereIsYourStuff(m);
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.gotItems);
		assertTrue("Scheduler should return true", customer.pickAndExecuteAnAction());
		assertTrue("Customer state should be moving to cashier", customer.state == CustomerState.leaving);
		assertTrue("Scheduler should return true", !customer.pickAndExecuteAnAction());
	}
	
	
}
