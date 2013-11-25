package simcity.Market.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.* ;

import static org.junit.Assert.* ;
import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketWorkerRole;
import simcity.Market.Order;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.Market.test.mock.MockMarketCustomer;
import simcity.Market.test.mock.MockMarketWorker;


public class MarketWorkerTest {
	public MockMarketCashier cashier  = new MockMarketCashier("cashier");
	public MarketWorkerRole worker = new MarketWorkerRole(new PersonAgent("Worker"));
	public MockMarketCustomer customer = new MockMarketCustomer("customer");	
	public MockMarketCustomer customer2 = new MockMarketCustomer("customer2");
	Item beef = new Item("Beef", 10.99, 100);
	Item chicken = new Item("Chicken", 8.99, 100);
	Item rice = new Item("Rice", 6.99, 100);
	Item potato = new Item("Potato", 5.99, 100);
	Item pizza = new Item("Pizza", 5.99, 100);
	Item salad = new Item("Salad", 3.99, 100);
	Item steak = new Item("Steak", 10.99, 100);
	MarketWorkerGui gui = new MarketWorkerGui(worker);
	
	@Test
	public void oneTest() throws InterruptedException{
		worker.setGui(gui);
		cashier.getInventory().put("Beef", beef);
		cashier.getInventory().put("Chicken", chicken);
		cashier.getInventory().put("Rice", rice);
		cashier.getInventory().put("Potato", potato);
		cashier.getInventory().put("Pizza", pizza);
		cashier.getInventory().put("Salad", salad);
		cashier.getInventory().put("Steak", steak);
		
		Order o = new Order(customer, "Chicken", 2, false);
		List<Order> orders = new ArrayList<Order>();
		orders.add(o);
		
		
}

}
