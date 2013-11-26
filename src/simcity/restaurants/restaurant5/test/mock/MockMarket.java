package restaurant.test.mock;

import restaurant.CookAgent;
import restaurant.interfaces.*;

public class MockMarket extends Mock implements Market {
	public MockMarket(String name){
		super(name);
	}
	public String getName(){
		return super.getName();
	}
	public void hereIsPayment(Cashier c, double payment){
	log.add(new LoggedEvent("Recieved hereIsPayment from " + c.getName() + " for a total of " + payment));
	}
	
	
	public void iNeedFood(Cook ref, String s, int amount){
		log.add(new LoggedEvent("Recieved message from cook " + ref.getName() + " that he needs " + amount + " of " + s ));
	}
	
}
