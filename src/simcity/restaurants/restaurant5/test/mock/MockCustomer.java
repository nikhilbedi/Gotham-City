package restaurant.test.mock;


import restaurant.Menu;
import restaurant.gui.CustomerGui;
import restaurant.interfaces.Cashier;
import restaurant.interfaces.Cook;
import restaurant.interfaces.Customer;
import restaurant.interfaces.Host;
import restaurant.interfaces.Waiter;

public class MockCustomer extends Mock implements Customer{
	
	public MockCustomer(String name){
		super(name);
	}
	public  String getName(){
	return super.getName();
}
	public  Restaurant5CustomerGui getGui(){
		return null;
	}//needed for semaphore animation
	
	public  void becomesHungry(){}

	public  void msgSitAtTable(int table){}

	//Messages added for v2
	public  void followMeToTable(Waiter  w, Menu m){}

	public  void whatWouldYouLike(Waiter  w){}
	public  void whatWouldYouLike(Waiter  w, Menu m){}
	public  void hereIsYourFood(Waiter  w){}
	
	//v2.1
	public  void giveChange(Cashier  ca, double cash){
		log.add(new LoggedEvent("Recieved giveChange from " + ca.getName() + " for a total of " + cash));
	}
	public  void giveDebt(Cashier  ca, double cash){
		log.add(new LoggedEvent("Recieved giveDebt from " + ca.getName() + " for a total of " + cash));
	}
	public  void hereIsCheck(double check){}
	
	public  void restIsFull(Host  h){}	

}




