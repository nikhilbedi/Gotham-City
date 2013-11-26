package simcity.restaurants.restaurant4.interfaces;

import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;

public interface Restaurant4Cashier {

	void amountDue(double a, MarketCashier c);

	void hereIsPayment(Restaurant4CustomerRole restaurant4CustomerRole,
			double money);

}
