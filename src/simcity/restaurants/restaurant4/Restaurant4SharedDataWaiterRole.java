package simcity.restaurants.restaurant4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import agent.Role;
import simcity.PersonAgent;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4WaiterGui;
import simcity.restaurants.restaurant4.Restaurant4WaiterRole.WaiterState;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4SharedDataWaiterRole extends Restaurant4WaiterAgent implements Restaurant4Waiter{

	private Restaurant4HostRole host = null;
	private Restaurant4CashierRole cashier = null;
	private String name;
	public  List<MyCustomer> customers = Collections.synchronizedList(new ArrayList<MyCustomer>());
	public Restaurant4WaiterGui waiterGui = null;
	private Semaphore atTable = new Semaphore(0,true);
	private Restaurant4Cook cook;
	public WaiterState state;
	public enum WaiterState {available, onBreak};
	private Timer timer = new Timer();
	private boolean Break = false;
	private PersonAgent person;
	
	public Restaurant4SharedDataWaiterRole(PersonAgent p){
		super(p);
		this.person = p;
	}
	@Override
	public void setGui(Restaurant4WaiterGui gui){
		waiterGui = gui;
	}
	
	public int getSize(){
		return customers.size();
	}
	
	@Override
	public Restaurant4WaiterGui getGui(){
		return waiterGui;
	}
	
	@Override
	public void setCook(Restaurant4Cook c){
		cook = c;
	}
	@Override
	public void setHost(Restaurant4HostRole h){
		host = h;
	}
	@Override
	public void setCashier(Restaurant4CashierRole c){
		cashier = c;
	}
	
	//messages
	@Override
	public void atDefaultPosition(){
		atTable.release();
		
		stateChanged();
	}
	
	@Override
	public void PleaseSeatCustomer(Restaurant4Customer c, int table){
		person.Do("seating");
		customers.add(new MyCustomer(c, table));
		stateChanged();
		
	}
	@Override
	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true	
	}
	
	@Override
	public void ReadyToOrder(Restaurant4Customer c){
		synchronized(customers){
		for (MyCustomer customer: customers) {
			if (customer.myCustomer==c) {
				customer.s = MyCustomer.CustomerState.ReadytoOrder;
				stateChanged();
			}
		}		
		}
	}
	
	@Override
	public void arrivedAtTable(Restaurant4Customer c){ //from animation
		atTable.release();
		c.whatDoYouWant();
		person.Do("What do you want?");
	}
	
	@Override
	public void HereIsMyChoice(Restaurant4Customer c, String choice){
		synchronized(customers){
		for (MyCustomer customer: customers) {
			if (customer.myCustomer==c) {
				customer.choice = choice;
				person.Do(c.getName() + " ordered " + choice);
				customer.s = MyCustomer.CustomerState.Ordered;
				stateChanged();
			}
		}	
		}
	}
	
	@Override
	public void startBreak(){
		atTable.release();
		person.Do("Starting a break");
		timer.schedule(new TimerTask() {
			public void run() {
				state = WaiterState.available;
				person.Do("Break is over");
				breakOver();
			}
		},
		10000);
		
	}
	
	@Override
	public void breakOver(){
		host.BreakIsOver(this);
		stateChanged();
		}
	@Override
	public void youMayGoToABreak(){
		state = WaiterState.onBreak;
		stateChanged();
		
	}
	@Override
	public void arrivedToCook(Restaurant4Customer c){ //from animation
		atTable.release();
		synchronized(customers){
		for(MyCustomer customer: customers){
			if (customer.myCustomer==c){
				person.Do("Ordering "+customer.choice+ " for "+customer.myCustomer.getName());
				if (c.getMoney()>=5.99 && c.getMoney()<8.99 && c.getName().equals("cheapest")){
					cook.setSalad();
				}
				cook.HereIsOrder(this, customer.choice, customer.table);
				}
			}
		}
		}
	
	@Override
	public void outOf(int table, String choice){
		synchronized(customers){
		for(MyCustomer customer: customers){
			if(customer.table==table){
				customer.s = MyCustomer.CustomerState.outOf;
		
		}}
		}
	}
	@Override
	public void arrivedToNotifyNoFood(Restaurant4Customer c, String choice){
		atTable.release();
		synchronized(customers){
		for(MyCustomer customer: customers){
			if (customer.myCustomer == c){
				person.Do("Sorry " +c.getName() + " we have no " + choice);
				//customer.s = MyCustomer.CustomerState.Seated;
				Menu menu = new Menu();
				menu.choice.remove(choice);
				c.outOfChoice(menu);
				stateChanged();
			}
		}	
		}
	}
	
	@Override
	public void OrderDone(String c, int table ){ //cook
		synchronized(customers){
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				customer.s = MyCustomer.CustomerState.OrderDone;
				stateChanged();
			}
			}
		}
		}
	
	@Override
	public void HereIsYourFood(int table){//from animation
		atTable.release();
		synchronized(customers){
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				person.Do("Here is your food");
				customer.myCustomer.HereIsFood();
				customer.s = MyCustomer.CustomerState.Eating;
				stateChanged();
			}
		}
		}
	}
	
	@Override
	public void DoneAndLeaving(Restaurant4Customer c){
		synchronized(customers){
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.s = MyCustomer.CustomerState.DoneEating;
				stateChanged();
			}
		}
		}
	} 
	@Override
	public void computeCheck(Restaurant4Customer c){
		synchronized(customers){
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.s = MyCustomer.CustomerState.ComputeCheck;
				stateChanged();
			}
		}
		}
	}
	@Override
	public void arrivedAtCashier(Restaurant4Customer c){
		atTable.release();
		person.Do("Arrived to the cashier");
		cashier.giveCheck(this, c, c.getChoice());
	}
	@Override
	public void HereIsCheck(Restaurant4Customer c, double price){ //cashier
		synchronized(customers){
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.amountDue = price;
				customer.s = MyCustomer.CustomerState.Computed;
				stateChanged();
			}
		}
		}
	}
	@Override
	public void HereIsYourCheck(int table){ // from animation
		atTable.release();
		synchronized(customers){
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				person.Do("Here is your check");
				customer.myCustomer.PayForFood(customer.amountDue);
				//removeCustomer(customer);
				stateChanged();
				
				
			}
		}
		}
	}
	
	public void atRevolvingStand(Restaurant4Customer customer){
		atTable.release();
		synchronized(customers){
		for (MyCustomer c: customers){
			if (c.myCustomer == customer){
				myPerson.Do("at revolving stand");
				RevolvingStand.addOrder(cook, this, c.choice, c.table);
			}
		}
		}
	}
	
//	scheduler
	@Override
	public boolean pickAndExecuteAnAction() {
		try{
		for (MyCustomer customer: customers){
			if (customer.s == MyCustomer.CustomerState.DoneEating){
				removeCustomer(customer);
				return true;
			}
		
			if (customer.s == MyCustomer.CustomerState.OrderDone){
				customer.s = MyCustomer.CustomerState.DeliveringFood;
				DeliverFood(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.ReadytoOrder){
			customer.s = MyCustomer.CustomerState.WaitingWaiter;
				TakeOrder(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.Ordered){
				customer.s = MyCustomer.CustomerState.WaitingFood;
				person.Do("Go to cook");
				GoToCook(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.Computed){
				customer.s = MyCustomer.CustomerState.deliveringCheck;
				person.Do("Going to get a check");
				doPickUpCheck(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.outOf){
				customer.s = MyCustomer.CustomerState.Seated;
				person.Do("Notify no food");
				notifyNoFood(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.ComputeCheck){
				customer.s = MyCustomer.CustomerState.WaitingCheck;
				person.Do("About to go to the cashier");
				GoToCashier(customer);
				return true;
			}
			
			if (customer.s == MyCustomer.CustomerState.Waiting ){
				customer.s = MyCustomer.CustomerState.Seated; 
				SeatCustomer(customer);
				return true;
			}
			
		}
		}
		catch(ConcurrentModificationException e){
			return false;
		}
		if (state == WaiterState.onBreak && customers.size()== 0 && Break == true){
			person.Do("About to go to the break");
			goToABreak();
			return true;
		}
		doLeaveCustomer();
		
		return false;
	}
	
	//actions

	
	@Override
	public void SeatCustomer(MyCustomer c){   
		c.myCustomer.followMe(this, new Menu());
		person.Do("Seating " + c.myCustomer.getName()+ " Table: " + c.table);
		waiterGui.DoBringToTable(c.myCustomer,c.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Hello Mika it's Hunter fixing your github!

	@Override
	public void TakeOrder(MyCustomer c){
		person.Do("Going to take " + c.myCustomer.getName()+ "'s order");
		waiterGui.DoGoToTable(c.myCustomer, c.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void GoToCook(MyCustomer customer){
		person.Do("Going to revolving stand");
		waiterGui.GoToRevolvingStand(customer.myCustomer);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void DeliverFood(MyCustomer customer){
		waiterGui.PickUpOrder(customer.table);
		waiterGui.setFood(customer.choice);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void doPickUpCheck(MyCustomer customer){
		waiterGui.DoPickUpCheck(customer.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	@Override
	public void GoToCashier(MyCustomer customer){
		waiterGui.DoGoToCashier(customer.myCustomer);
		person.Do("Going to cashier");
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void notifyNoFood(MyCustomer c){
		waiterGui.notifyNoFood(c.myCustomer, c.table);
	
		try {
		atTable.acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	@Override
	public void removeCustomer(MyCustomer customer){
		customers.remove(customer);
		host.msgLeavingTable(customer.myCustomer);
		if(customers.size() == 0){
		Break = true;}
		stateChanged();
		}
	@Override
	public void wantToGoOnBreak(){
		person.Do("I want to go on a break");
		host.wantABreak(this);
	}
	@Override
	public void goToABreak(){
		Break = false;
		waiterGui.DoGoToABreak();
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void doLeaveCustomer(){
		waiterGui.DoLeaveCustomer();
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void GotFood(String food) {
		cook.gotFood(food);
		
	}
	
}
