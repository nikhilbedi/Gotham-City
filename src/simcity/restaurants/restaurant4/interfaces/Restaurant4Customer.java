package simcity.restaurants.restaurant4.interfaces;

import simcity.restaurants.restaurant4.Menu;
import simcity.restaurants.restaurant4.Restaurant4WaiterRole;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CustomerGui;

public interface Restaurant4Customer {

	abstract void leaveIfYouWant();

	abstract void whatDoYouWant();

	abstract String getName();

	abstract double getMoney();

	abstract void outOfChoice(Menu menu);

	abstract void HereIsFood();

	abstract String getChoice();

	abstract void PayForFood(double amountDue);

	abstract void followMe(Restaurant4Waiter restaurant4WaiterRole,
			Menu menu);

	abstract double getAmountDue();

	abstract void HereIsYourChange(double value);

	abstract void arrivedToCashier();

	abstract void AtRestaurant();

	abstract void msgAnimationFinishedGoToSeat();

	abstract void msgAnimationFinishedLeaveRestaurant();

	abstract void gotHungry();

	abstract Restaurant4CustomerGui getGui();

	abstract void msgOutOfRestaurant4();

	abstract Restaurant4Host getHost();


}
