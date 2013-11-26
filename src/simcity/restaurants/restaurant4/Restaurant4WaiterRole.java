package simcity.restaurants.restaurant4;
import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4WaiterGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4WaiterRole extends Role implements Restaurant4Waiter {
	private Restaurant4HostRole host = null;
	private Restaurant4CashierRole cashier = null;
	private String name;
	public  List<MyCustomer> customers = new ArrayList<MyCustomer>();
	public Restaurant4WaiterGui waiterGui = null;
	private Semaphore atTable = new Semaphore(0,true);
	private Restaurant4Cook cook;
	public WaiterState state;
	public enum WaiterState {available, onBreak};
	private Timer timer = new Timer();
	private boolean Break = false;
	private PersonAgent person;
	public Restaurant4WaiterRole(PersonAgent p){
		super(p);
		this.person = p;
	}
	public String getName(){
		return name;
	}
	public void setGui(Restaurant4WaiterGui gui){
		waiterGui = gui;
	}
	
	public int getSize(){
		return customers.size();
	}
	
	public Restaurant4WaiterGui getGui(){
		return waiterGui;
	}
	
	public void setCook(Restaurant4Cook c){
		cook = c;
	}
	
	public void setHost(Restaurant4HostRole h){
		host = h;
	}
	
	public void setCashier(Restaurant4CashierRole c){
		cashier = c;
	}
	
	//messages
	
	public void atDefaultPosition(){
		atTable.release();
		
		stateChanged();
	}
	public void PleaseSeatCustomer(Restaurant4Customer c, int table){
		person.Do("seating");
		customers.add(new MyCustomer(c, table));
		stateChanged();
		
	}
	
	public void msgAtTable() {//from animation
		//print("msgAtTable() called");
		atTable.release();// = true	
	}
	
	public void ReadyToOrder(Restaurant4Customer c){
		for (MyCustomer customer: customers) {
			if (customer.myCustomer==c) {
				customer.s = MyCustomer.CustomerState.ReadytoOrder;
				stateChanged();
			}
		}		
	}
	
	public void arrivedAtTable(Restaurant4Customer c){ //from animation
		atTable.release();
		c.whatDoYouWant();
		person.Do("What do you want?");
	}
	
	public void HereIsMyChoice(Restaurant4Customer c, String choice){
		for (MyCustomer customer: customers) {
			if (customer.myCustomer==c) {
				customer.choice = choice;
				person.Do(c.getName() + " ordered " + choice);
				customer.s = MyCustomer.CustomerState.Ordered;
				stateChanged();
			}
		}	
	}
	
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
	
	public void breakOver(){
		host.BreakIsOver(this);
		stateChanged();
		}

	public void youMayGoToABreak(){
		state = WaiterState.onBreak;
		stateChanged();
		
	}
	public void arrivedToCook(Restaurant4Customer c){ //from animation
		atTable.release();
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
	
	public void outOf(int table, String choice){
		
		for(MyCustomer customer: customers){
			if(customer.table==table){
				customer.s = MyCustomer.CustomerState.outOf;
		
		}}
	
	}
	public void arrivedToNotifyNoFood(Restaurant4Customer c, String choice){
		atTable.release();
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
	
	
	public void OrderDone(String c, int table ){ //cook
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				customer.s = MyCustomer.CustomerState.OrderDone;
				stateChanged();
			}
			}
		}
	
	
	public void HereIsYourFood(int table){//from animation
		atTable.release();
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				person.Do("Here is your food");
				customer.myCustomer.HereIsFood();
				customer.s = MyCustomer.CustomerState.Eating;
				stateChanged();
			}
		}
	}
	
	public void DoneAndLeaving(Restaurant4Customer c){
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.s = MyCustomer.CustomerState.DoneEating;
				stateChanged();
			}
		}
	} 
	
	public void computeCheck(Restaurant4Customer c){
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.s = MyCustomer.CustomerState.ComputeCheck;
				stateChanged();
			}
		}
	}
	
	public void arrivedAtCashier(Restaurant4Customer c){
		atTable.release();
		person.Do("Arrived to the cashier");
		cashier.giveCheck(this, c, c.getChoice());
	}
	
	public void HereIsCheck(Restaurant4Customer c, double price){ //cashier
		for (MyCustomer customer: customers){
			if (customer.myCustomer==c){
				customer.amountDue = price;
				customer.s = MyCustomer.CustomerState.Computed;
				stateChanged();
			}
		}
	}
	
	public void HereIsYourCheck(int table){ // from animation
		atTable.release();
		for(MyCustomer customer: customers){
			if (customer.getTable()==table){
				person.Do("Here is your check");
				customer.myCustomer.PayForFood(customer.amountDue);
				//removeCustomer(customer);
				stateChanged();
				
				
			}
		}
	}
	
	
	
//	scheduler
	
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
	 
	public void GoToCook(MyCustomer customer){
		person.Do("Going to cook");
		waiterGui.GoToCook(customer.myCustomer);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public void doPickUpCheck(MyCustomer customer){
		waiterGui.DoPickUpCheck(customer.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
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
	
	public void notifyNoFood(MyCustomer c){
		waiterGui.notifyNoFood(c.myCustomer, c.table);
	
		try {
		atTable.acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void removeCustomer(MyCustomer customer){
		customers.remove(customer);
		host.msgLeavingTable(customer.myCustomer);
		if(customers.size() == 0){
		Break = true;}
		stateChanged();
		}
	
	public void wantToGoOnBreak(){
		person.Do("I want to go on a break");
		host.wantABreak(this);
	}
	
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
	public void doLeaveCustomer(){
		waiterGui.DoLeaveCustomer();
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static class MyCustomer{
		private Restaurant4Customer myCustomer;
		private int table;
		public CustomerState s;
		private String choice;
		private double amountDue;
		public enum CustomerState {Waiting, Seated, ReadytoOrder,WaitingWaiter, Ordered, DeliveringFood, WaitingFood, OrderDone, Eating, ComputeCheck, WaitingCheck, DoneEating, deliveringCheck,Computed, gotCheck, outOf,  Leaving};
	
		
	public int getTable(){
		return table;
	}	
	
	public Restaurant4Customer getCustomer(){
		return myCustomer;
	}
	
	public MyCustomer(Restaurant4Customer c, int t){
		myCustomer = c;
		table = t;
		s= CustomerState.Waiting;
	}
	}
	@Override
	public void GotFood(String food) {
		cook.gotFood(food);
		
	}
	
}
