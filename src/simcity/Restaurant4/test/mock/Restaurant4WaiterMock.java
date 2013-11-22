package simcity.Restaurant4.test.mock;

import simcity.Restaurant4.interfaces.Restaurant4Customer;
import simcity.Restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4WaiterMock extends Mock implements Restaurant4Waiter {

	public Restaurant4WaiterMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void youMayGoToABreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void PleaseSeatCustomer(Restaurant4Customer restaurant4Customer,
			int table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void ReadyToOrder(Restaurant4Customer restaurant4CustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsMyChoice(Restaurant4Customer restaurant4CustomerRole,
			String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computeCheck(Restaurant4Customer restaurant4CustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void DoneAndLeaving(Restaurant4Customer restaurant4CustomerRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsCheck(Restaurant4Customer c, double amountDue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OrderDone(String choice, int table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outOf(int table, String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgAtTable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrivedAtTable(Restaurant4Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrivedToNotifyNoFood(Restaurant4Customer customer,
			String choice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrivedToCook(Restaurant4Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void GotFood(String food) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startBreak() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsYourFood(int table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arrivedAtCashier(Restaurant4Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsYourCheck(int table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void atDefaultPosition() {
		// TODO Auto-generated method stub
		
	}

}
