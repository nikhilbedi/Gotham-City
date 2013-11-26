package simcity.restaurants.restaurant4.test.mock;

import simcity.restaurants.restaurant4.Menu;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CustomerGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4CustomerMock extends Mock implements Restaurant4Customer{

	public Restaurant4CustomerMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void leaveIfYouWant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void whatDoYouWant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getMoney() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void outOfChoice(Menu menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsFood() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getChoice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void PayForFood(double amountDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void followMe(Restaurant4Waiter restaurant4WaiterRole, Menu menu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getAmountDue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void HereIsYourChange(double value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrivedToCashier() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AtRestaurant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAnimationFinishedGoToSeat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAnimationFinishedLeaveRestaurant() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gotHungry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Restaurant4CustomerGui getGui() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void msgOutOfRestaurant4() {
		// TODO Auto-generated method stub
		
	}



}
