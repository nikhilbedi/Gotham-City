package restaurant2.interfaces;

import java.util.List;
import java.util.Map;

public interface Cook {

	public abstract void msgHereIsOrder(Waiter w, String choice, int table);

	public abstract void msgHereIsInventoryReport(
			List<Integer> inventoryDifference);

	public abstract void msgHereIsDelivery(Map<String, Integer> foodsToDeliver);

	public abstract void msgPickedUpOrder(int currentOrderTableNumber);

}