package simcity.Restaurant4.test.mock;

import simcity.Restaurant4.interfaces.Restaurant4Cook;
import simcity.Restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4CookMock extends Mock implements Restaurant4Cook{

	public Restaurant4CookMock(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean getPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Ready(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSalad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void HereIsOrder(Restaurant4Waiter restaurant4WaiterRole,
			String choice, int table) {
		// TODO Auto-generated method stub
		
	}

}
