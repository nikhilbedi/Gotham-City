package simcity.Market.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.MarketWorkerRole;
import simcity.Market.MarketWorkerRole.CustomerDelivery;
import simcity.Market.MarketWorkerRole.RestaurantDelivery;
import simcity.Market.Order;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.Market.test.mock.MockMarketCustomer;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CashierMock;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CookMock;


public class MarketWorkerTest {
	public MockMarketCashier cashier  = new MockMarketCashier("cashier");
	public MarketWorkerRole worker = new MarketWorkerRole(new PersonAgent("Worker"));
	public MockMarketCustomer customer = new MockMarketCustomer("customer");	
	public MockMarketCustomer customer2 = new MockMarketCustomer("customer2");
	public Restaurant4CookRole cook = new Restaurant4CookRole(new PersonAgent("cook"));
	public Restaurant4CashierMock cashierRest = new Restaurant4CashierMock("cashierRest");
	Item beef = new Item("Beef", 10.99, 100);
	Item chicken = new Item("Chicken", 8.99, 100);
	Item rice = new Item("Rice", 6.99, 100);
	Item potato = new Item("Potato", 5.99, 100);
	Item pizza = new Item("Pizza", 5.99, 100);
	Item salad = new Item("Salad", 3.99, 100);
	Item steak = new Item("Steak", 10.99, 100);
	MarketWorkerGui gui = new MarketWorkerGui(worker);
	
	@Test
	public void oneCustomerTest() throws InterruptedException{
		worker.setGui(gui);
		worker.getInventory().put("Beef", beef);
		worker.getInventory().put("Chicken", chicken);
		worker.getInventory().put("Rice", rice);
		worker.getInventory().put("Potato", potato);
		worker.getInventory().put("Pizza", pizza);
		worker.getInventory().put("Salad", salad);
		worker.getInventory().put("Steak", steak);
		assertTrue("Workers deliveries list should contain one delivery", worker.getCustomerDeliveries().size()==0);
		assertTrue("Scheduler should return false", !worker.pickAndExecuteAnAction());
		Order o = new Order(customer, "Chicken", 2, false);
		List<Order> orders = new ArrayList<Order>();
		orders.add(o);
		worker.Bring(orders);
		assertTrue("Workers deliveries list should contain one delivery", worker.getCustomerDeliveries().size()==1);
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.pending);
		worker.delivering.release();
		assertTrue("Scheduler should return true", worker.pickAndExecuteAnAction());
		assertTrue("Delivery state should be getting", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.getting);
		worker.Brought(customer);
		assertTrue("Workers deliveries list should contain one delivery", worker.getCustomerDeliveries().size()==0);
}
	
	//two customers and restaurant orders added at the same time
	@Test
	public void twoCustomersOneRestaurantTest() throws InterruptedException {
		worker.setGui(gui);
		worker.getInventory().put("Beef", beef);
		worker.getInventory().put("Chicken", chicken);
		worker.getInventory().put("Rice", rice);
		worker.getInventory().put("Potato", potato);
		worker.getInventory().put("Pizza", pizza);
		worker.getInventory().put("Salad", salad);
		worker.getInventory().put("Steak", steak);
		assertTrue("Workers deliveries list should contain no delivery", worker.getCustomerDeliveries().size()==0);
		assertTrue("Workers deliveries list should contain no delivery", worker.getRestaurantDeliveries().size()==0);
		assertTrue("Scheduler should return false", !worker.pickAndExecuteAnAction());
		Order o = new Order(customer, "Chicken", 2, false);
		List<Order> orders = new ArrayList<Order>();
		orders.add(o);
		Order o1 = new Order(customer2, "Chicken", 2, false);
		List<Order> orders2 = new ArrayList<Order>();
		orders2.add(o1);
		worker.Bring(orders);
		worker.Bring(orders2);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Chicken", 2);
		//worker.SendFood(map, cook);
		assertTrue("Workers deliveries list should contain one delivery", worker.getRestaurantDeliveries().size()==1);
		assertTrue("Delivery state should be pending", worker.getRestaurantDeliveries().get(0).state == RestaurantDelivery.DeliveryState.pending);
		assertTrue("Workers deliveries list should contain two deliveries", worker.getCustomerDeliveries().size()==2);
		//assertTrue("Scheduler should return false", worker.pickAndExecuteAnAction());
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.pending);
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(1).state == CustomerDelivery.DeliveryState.pending);
		worker.delivering.release();
		assertTrue("Scheduler should return true", worker.pickAndExecuteAnAction());
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.getting);
		worker.Brought(customer);
		assertTrue("Workers deliveries list should contain one delivery", worker.getCustomerDeliveries().size()==1);
		//worker.delivering.release();
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.pending);
		worker.delivering.release();
		assertTrue("Scheduler should return true", worker.pickAndExecuteAnAction());
		assertTrue("Delivery state should be pending", worker.getCustomerDeliveries().get(0).state == CustomerDelivery.DeliveryState.getting);
		worker.Brought(customer2);
		assertTrue("Workers deliveries list should contain no delivery", worker.getCustomerDeliveries().size()==0);
		worker.delivering.release();
		assertTrue("Scheduler should return true", worker.pickAndExecuteAnAction());
		assertTrue("Delivery state should be pending", worker.getRestaurantDeliveries().get(0).state == RestaurantDelivery.DeliveryState.getting);
		worker.Sent(cook);
		assertTrue("Workers deliveries list should contain no delivery", worker.getRestaurantDeliveries().size()==0);
		assertTrue("Scheduler should return false", !worker.pickAndExecuteAnAction());
	}

}
