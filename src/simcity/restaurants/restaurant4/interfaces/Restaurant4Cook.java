package simcity.restaurants.restaurant4.interfaces;

import java.util.Map;

public interface Restaurant4Cook {

	abstract boolean getPause();

	abstract void Ready(String string);

	abstract void setSalad();

	abstract void HereIsOrder(Restaurant4Waiter restaurant4WaiterRole,
			String choice, int table);
	abstract void HereIsYourFood(Map<String, Integer> k);

	abstract void gotFood(String food);
}
