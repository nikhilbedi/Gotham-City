package simcity.Market.interfaces;

import java.util.List;

import simcity.Market.Order;

public interface MarketCustomer {

	abstract void HereIsChange(double d);

	abstract void amountDue(double amountDue);

	abstract void HereIsYourStuff(List<Order> orders);

	abstract void outOf(Order order);

	abstract void AtCashier();

	abstract void NextCustomerPlease();

	abstract void ArrivedToGetItem();
	abstract String getName();
	
}
