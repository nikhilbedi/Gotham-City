package simcity.restaurants.restaurant2.interfaces;

import simcity.restaurants.restaurant2.Check;
import simcity.restaurants.restaurant2.Market;

public interface Cashier {

	public abstract void msgNeedCheck(Waiter w, double price, int table);

	public abstract void msgHeresMyCheck(Customer c, Check ch, double debt);

	public abstract void msgHereIsDeliveryCheck(Check ch, Market m);

}