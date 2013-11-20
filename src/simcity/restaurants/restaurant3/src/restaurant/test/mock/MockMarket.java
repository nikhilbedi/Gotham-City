package simcity.restaurants.restaurant3.src.restaurant.test.mock;

import simcity.restaurants.restaurant3.src.restaurant.CookRole;
import simcity.restaurants.restaurant3.src.restaurant.MarketOrder;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Cashier;
import simcity.restaurants.restaurant3.src.restaurant.interfaces.Market;
//import restaurant.interfaces.Cook; 


public class MockMarket extends Mock implements Market{

	
		public Cashier cashier;
		public CookRole cook;
		public EventLog log = new EventLog();

		public MockMarket(String name) {
			super(name);
		}
		
		public MarketOrder msgOrderRestock(MarketOrder mo) {
			
			return null;
		}
		
}
