package simcity.Market.interfaces;

import java.util.List;
import java.util.Map;

import simcity.Market.Item;
import simcity.Market.MarketCustomerRole;
import simcity.Market.Order;

public interface MarketCashier {

	abstract void hereIsMoney(MarketCustomer marketCustomerRole, double payment);

	abstract void INeed(List<Order> orders);

	abstract Map<String, Item> getInventory();

	abstract void needFood(MarketCustomerRole marketCustomerRole);

	

}
