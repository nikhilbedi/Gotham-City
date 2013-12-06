package simcity.restaurants.restaurant3.test.mock;

import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.interfaces.*;
import simcity.tests.mock.*;

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
