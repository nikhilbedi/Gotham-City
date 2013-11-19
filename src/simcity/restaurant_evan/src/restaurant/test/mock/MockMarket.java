package simcity.restaurant_evan.src.restaurant.test.mock;

import simcity.restaurant_evan.src.restaurant.CookRole;
import simcity.restaurant_evan.src.restaurant.MarketOrder;
import simcity.restaurant_evan.src.restaurant.interfaces.Cashier;
import simcity.restaurant_evan.src.restaurant.interfaces.Market;
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
