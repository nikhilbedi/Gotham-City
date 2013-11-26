package simcity.restaurants.restaurant4.test.mock;

import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;

public class Restaurant4CashierMock extends Mock implements Restaurant4Cashier {

	public Restaurant4CashierMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void amountDue(double a, MarketCashier c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hereIsPayment(Restaurant4CustomerRole restaurant4CustomerRole,
			double money) {
		// TODO Auto-generated method stub
		
	}

}
