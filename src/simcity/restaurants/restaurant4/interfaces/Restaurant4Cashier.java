package simcity.restaurants.restaurant4.interfaces;

import java.util.List;

import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.Restaurant4CashierRole.Payment;

public interface Restaurant4Cashier {

	void amountDue(double a, MarketCashier c);

	void hereIsPayment(Restaurant4CustomerRole restaurant4CustomerRole,
			double money);
	
	abstract List<Payment> getPayments();
}
