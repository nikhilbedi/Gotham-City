package simcity.Market.test;



import java.util.ArrayList;
import java.util.List;

import org.junit.runners.Suite;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketCashierRole.Check;
import simcity.Market.Order;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.Market.test.mock.MockMarketCustomer;
import simcity.Market.test.mock.MockMarketWorker;
//import junit.framework.*;
//import junit.framework.Test;



import org.junit.* ;

import static org.junit.Assert.* ;

	public class MarketCashierTest{
		public MarketCashierRole cashier  = new MarketCashierRole(new PersonAgent("cashier"));
		public MockMarketWorker worker = new MockMarketWorker("worker");
		public MockMarketCustomer customer = new MockMarketCustomer("customer");	
		public MockMarketCustomer customer2 = new MockMarketCustomer("customer2");
		Item beef = new Item("Beef", 10.99, 100);
		Item chicken = new Item("Chicken", 8.99, 100);
		Item rice = new Item("Rice", 6.99, 100);
		Item potato = new Item("Potato", 5.99, 100);
		Item pizza = new Item("Pizza", 5.99, 100);
		Item salad = new Item("Salad", 3.99, 100);
		Item steak = new Item("Steak", 10.99, 100);

		
		@Test
		public void oneCustomer() throws InterruptedException{
			
			cashier.getInventory().put("Beef", beef);
			cashier.getInventory().put("Chicken", chicken);
			cashier.getInventory().put("Rice", rice);
			cashier.getInventory().put("Potato", potato);
			cashier.getInventory().put("Pizza", pizza);
			cashier.getInventory().put("Salad", salad);
			cashier.getInventory().put("Steak", steak);
			cashier.setWorker(worker);
			assertTrue("Cashier's waiting customers list must be empty",cashier.getCustomers().size()==0);
			assertTrue("Scheduler should not return true", !cashier.pickAndExecuteAnAction() );
			cashier.needFood(customer);
            assertTrue("Cashier's waiting customers list must have 1 customer",cashier.getCustomers().size()==1);
            assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction() );
			Order o  = new Order(customer, "Chicken", 2, false);
			Order o1 = new Order(customer, "Rice", 2, false );
			List<Order> orders = new ArrayList<Order>();
			orders.add(o);
			orders.add(o1);
			assertTrue("Cashier should have no checks in his checks List", cashier.getChecks().size()==0);
			cashier.INeed(orders);
			assertTrue("Cashier should have one check in his checkList", cashier.getChecks().size()==1);
			assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.pending);
			assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
			assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.checkingAmount);
			assertTrue("AmountDue should be 31.96", cashier.getChecks().get(0).amountDue==31.96);
			cashier.hereIsMoney(customer, 40.00);
			assertTrue("State should be paying ",  cashier.getChecks().get(0).state == Check.CheckState.gotMoney);
			assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
			assertTrue("Cashier should have empty list of checks ", cashier.getChecks().size()==0);
			assertTrue("Current customer should be null ", cashier.currentCustomer == null);
			assertTrue("Scheduler should return false", !cashier.pickAndExecuteAnAction());
		
		}
		
		@Test
		public void twoCustomers() throws InterruptedException{
			cashier.getInventory().put("Beef", beef);
			cashier.getInventory().put("Chicken", chicken);
			cashier.getInventory().put("Rice", rice);
			cashier.getInventory().put("Potato", potato);
			cashier.getInventory().put("Pizza", pizza);
			cashier.getInventory().put("Salad", salad);
			cashier.getInventory().put("Steak", steak);
			cashier.setWorker(worker);
			assertTrue("Cashier's waiting customers list must be empty",cashier.getCustomers().size()==0);
			assertTrue("Scheduler should not return true", !cashier.pickAndExecuteAnAction() );
			cashier.needFood(customer);
			cashier.needFood(customer2);
			assertTrue("Cashier's waiting customers list must have 1 customer",cashier.getCustomers().size()==2);
			assertTrue("Current customer shoul be equal to customer", cashier.currentCustomer == customer);
			  assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction() );
				Order o  = new Order(customer, "Chicken", 2, false);
				Order o1 = new Order(customer, "Rice", 2, false );
				List<Order> orders = new ArrayList<Order>();
				orders.add(o);
				orders.add(o1);
				assertTrue("Cashier should have no checks in his checks List", cashier.getChecks().size()==0);
				cashier.INeed(orders);
				assertTrue("Cashier should have one check in his checkList", cashier.getChecks().size()==1);
				assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.pending);
				assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
				assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.checkingAmount);
				assertTrue("AmountDue should be 31.96", cashier.getChecks().get(0).amountDue==31.96);
				cashier.hereIsMoney(customer, 40.00);
				assertTrue("State should be gotMoney ",  cashier.getChecks().get(0).state == Check.CheckState.gotMoney);
				assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
				assertTrue("Cashier should have empty list of checks ", cashier.getChecks().size()==0);
				assertTrue("Current customer should be equal to customer2", cashier.currentCustomer == customer2);
				assertTrue("Scheduler should return false", cashier.pickAndExecuteAnAction());
				Order o2  = new Order(customer2, "Beef", 2, false);
				Order o3 = new Order(customer2, "Salad", 2, false );
				List<Order> orders2 = new ArrayList<Order>();
				orders2.add(o2);
				orders2.add(o3);
				assertTrue("Cashier should have no checks in his checks List", cashier.getChecks().size()==0);
				cashier.INeed(orders2);
				assertTrue("Cashier should have one check in his checkList", cashier.getChecks().size()==1);
				assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.pending);
				assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
				assertTrue("State should be pending", cashier.getChecks().get(0).state == Check.CheckState.checkingAmount);
				assertTrue("AmountDue should be 31.96", cashier.getChecks().get(0).amountDue==29.96);
				cashier.hereIsMoney(customer2, 30.00);
				assertTrue("State should be got money ",  cashier.getChecks().get(0).state == Check.CheckState.gotMoney);
				assertTrue("Scheduler should return true", cashier.pickAndExecuteAnAction());
		}
		
		@Test
		public void RestaurantOrder() throws InterruptedException{
			
			
		}
		
		
}
