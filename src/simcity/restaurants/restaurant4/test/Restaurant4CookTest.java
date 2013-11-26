package simcity.restaurants.restaurant4.test;
import java.util.HashMap;
import java.util.Map;

import org.junit.* ;

import simcity.PersonAgent;
import simcity.Market.test.mock.MockMarketCashier;
import simcity.Market.test.mock.MockMarketWorker;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4CashierRole.Payment;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.Restaurant4CookRole.Food;
import static org.junit.Assert.* ;


public class Restaurant4CookTest {
	
	Restaurant4CookRole cook = new Restaurant4CookRole(new PersonAgent("cook"));
	Restaurant4CashierRole cashier = new Restaurant4CashierRole(new PersonAgent("cashier"));
	MockMarketCashier marketCashier = new MockMarketCashier("marketCashier");
	MockMarketWorker marketWorker = new MockMarketWorker("marketWorker");

	
	@Test
	public void oneTest() throws InterruptedException{
		marketCashier.setRest4Cashier(cashier);
		assertTrue("cook's scheduler should return true because he has less food then he needs", cook.pickAndExecuteAnAction());
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Chicken", 4);
		map.put("Steak", 4);
		map.put("Pizza", 4);
		map.put("Salad", 4);
		cook.HereIsYourFood(map);
		Food chicken = cook.foods.get("Chicken");
		Food steak = cook.foods.get("Steak");
		Food pizza = cook.foods.get("Pizza");
		Food salad = cook.foods.get("Salad");
		assertTrue("cook should have 6 chicken", chicken.amount == 6);
		assertTrue("cook should have 6 steak", steak.amount == 6);
		assertTrue("cook should have 6 pizza", pizza.amount == 6);
		assertTrue("cook should have 6 salad", salad.amount == 6);
	}

}
