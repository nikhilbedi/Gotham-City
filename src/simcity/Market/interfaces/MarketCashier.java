package simcity.Market.interfaces;

import java.util.List;
import java.util.Map;

import agent.Role;
import simcity.Market.Item;
import simcity.Market.MarketCustomerRole;
import simcity.Market.Order;
import simcity.Restaurant4.Restaurant4CashierRole;
import simcity.Restaurant4.interfaces.Restaurant4Cashier;

public interface MarketCashier {

	abstract void hereIsMoney(MarketCustomer marketCustomerRole, double payment);

	abstract void INeed(List<Order> orders);

	abstract Map<String, Item> getInventory();

	abstract void needFood(MarketCustomerRole marketCustomerRole);

	abstract void INeedFood(Map<String, Integer> food, Role role, Role cashier);

	abstract void hereIsMoneyRestaurant(Role role, double money);

}
