package restaurant.test.mock;

import restaurant.Menu;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Cook;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Host;
import restaurant.interfaces.Waiter;

public class MockWaiter extends Mock implements Waiter{
	public MockWaiter(String s){
		super(s);
	}
	public  String getName(){
		return super.getName();
	}
    public  void doneMoving(){}
	// Messages
	//Messages added from v2

	public  void sitAtTable(Host h, Customer c, int table){}

	public  void imReadyToOrder(Customer c){}

	public  void hereIsMyChoice(Customer c, String choice){}

	public  void hereIsAnOrder(Cook ck, String choice, int table){}

	public  void doneEatingAndLeaving(Customer c){}

	//Messages added from v2.1
	public  void outOfOrder(String choice, int i, Menu m){}

	public  void doneEating(Customer c){}

	public  void gotCheckAndLeaving(Customer c){}

	public  void cantOrderLeaving(Customer c) {}
	public  void hereIsCheck(Cashier ch, Customer c, Double bill){
		log.add(new LoggedEvent("Recieved hereIsCheck from " + ch.getName() + " for " + c.getName() + "for a total of " + bill));
	}
	public  void breakApproved(){}
	public  void cantBreak(){}
}
