package simcity.restaurants.restaurant4.interfaces;

import java.util.Map;

import agent.Role;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketWorkerRole;
import simcity.Market.interfaces.MarketCashier;

public interface Restaurant4Cook {

	abstract boolean getPause();

	abstract void Ready(String string);

	abstract void setSalad();

	abstract void HereIsOrder(Restaurant4Waiter restaurant4WaiterRole,
			String choice, int table);
	abstract void HereIsYourFood(Map<String, Integer> k, MarketWorkerRole role);

	abstract void gotFood(String food);
	abstract void setMarketCashier(MarketCashier marketCashier);

	abstract void OrderOnTheStand();
}
