package simcity.restaurants.restaurant3;

import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import agent.Role;
import simcity.restaurants.restaurant3.Order.OrderState;
import simcity.restaurants.restaurant3.gui.*;
import simcity.restaurants.restaurant3.interfaces.*;
import agent.Agent;

/**
 * Restaurant WaiterRole
 * 
 */

public class WaiterRole extends Role implements Waiter{
	
	public List<myCustomer> customers= Collections.synchronizedList(new CopyOnWriteArrayList<myCustomer>());
	public Collection<Table> tables;
	public boolean isOnBreak = false;
	//note that tables is typed with Collection semantics.
	//Later we will see how it is implemented
	//private boolean waiterBusy= false;
	private String name;
	private Semaphore atTable = new Semaphore(0,true);
	private Semaphore atHost = new Semaphore(0, true);
	private Semaphore atCook = new Semaphore(1, true);
	private Semaphore atCashier = new Semaphore(1, true);
	

	public HostRole host = null;
	private CookRole cook = null;
	private CashierRole cashier = null;
	public WaiterGui waiterGui = null;
	public HostGui hostGui = null;
	public Restaurant3CustomerGui customerGui = null;
	public Menu menu = new Menu();
	
	
	public class myCustomer {
		Customer c;
		int table;
		String choice;
		public customerState cs;
		Order order;
		int initialX;
        int initialY;
		
		public myCustomer(Customer c, int table, customerState cs, Order order) {
			this.c = c;
			this.table = table;
			this.cs = cs;
			this.order = order;
		}
		/*
		public myCustomer(Customer c, int table, customerState cs, Order order, int x, int y) {
			this.c = c;
			this.table = table;
			this.cs = cs;
			this.order = order;
			initialX = x;
            initialY = y;
		}
		*/
	}
	private waiterState ws;
	
	public enum customerState {waiting, seated, readyToOrder, askedToOrder, ordered, reordering, 
		waitingForFoodToCook, waitingForFoodToBeDelivered, eating, waitingForCheckToCalculate, 
		waitingForCheckToBeDelivered, payingCheck,leaving}
	
	public enum waiterState {available, notAvailable }
	
	public WaiterRole(PersonAgent p) {
		super(p);
		this.name = name;
	}
	

	public String getMaitreDName() {
		return name;
	}

	public String getName() {
		return name;
	}

	public List getCustomers() {
		return customers;
	}

	public Collection getTables() {
		return tables;
	}
	
	// Messages

	/*public void msgIWantFood(CustomerAgent cust) {
		waitingCustomers.add(cust);
		stateChanged();
	}*/
	
	public void msgSitAtTable(Customer c, int table) {
		System.out.println("The host tells " + this.getName() + " to seat the customer at table " + table);
		customers.add(new myCustomer(c, table, customerState.waiting, null));//potential null pointer
		stateChanged();
	}
	public void msgReadyToOrder(Customer cust) {
		System.out.println("customer tells " + this.getName() + " he/she is ready to order;");
		for (myCustomer c: customers) {
			if(c.c.equals(cust)) {
				c.cs = customerState.readyToOrder;
				stateChanged();
				break;
			}
		}
	}
	public void msgHereIsMyChoice(Customer cust, String choice) {
		for (myCustomer c: customers) {
			if(c.c.equals(cust)) {
				c.cs = customerState.ordered;
				c.choice = choice;
				waiterGui.order=choice;
				//waiterGui.choice = choice;
				//customerGui.choice = choice;
				//waiterGui.createOrderGui(choice);
				stateChanged();
				break;
			}
		}
	}
	public void msgWaiterOutOfFood(Order o){
		System.out.println("Waiter received message that it is out of that food");
		for(myCustomer c: customers) {
			if(c.order == o) {
				c.cs = customerState.reordering;
				//stateChanged();
				break;
			}
		}
	}
	public void msgOrderIsReady(Order order) { //message from cook
		System.out.println(cook.getName() + " tells " + this.getName() + " that the food is ready for table " + order.tableNumber);
		CookGui.plating = false;
		for(myCustomer c: customers) {
			if(c.order == order) {
				
			c.cs = customerState.waitingForFoodToBeDelivered;
			stateChanged();
			break;
			}
		}
		
		//myCustomer mc = customer.find(c);
		//mc.cs = waitingForFoodToBeDelivered;
	}
	public void msgIWantTheCheck(Customer cust) {
		//System.out.println(cust.getName() + " tells " + this.getName() + " he/she is ready for the check;");
		for(myCustomer c: customers) {
			if(c.c == cust){
				c.cs = customerState.waitingForCheckToCalculate;
				stateChanged();
			}
		}
	}
	public void msgWaiterHereIsCheck(Order o){ //message from cashier
		System.out.println("Here is your check");
		for(myCustomer mc: customers) {
			if(o.customer == mc.c){//
				mc.cs = customerState.waitingForCheckToBeDelivered;
				stateChanged();
			}
		}
	}
	
	public void msgDoneEatingAndLeavingTable(Customer cust) {
		for(myCustomer c: customers) {
			if(c.c == cust){
				
				c.cs = customerState.leaving;
				stateChanged();
				host.msgTableIsFree(cust);
			}
		}
	}

	public void msgAtTable() {//from animation
		System.out.println("msgAtTable() called");
		atTable.release();// = true;
		stateChanged();
	}
	public void msgAtHost() {
//		System.out.println("msgAtHost");
		atHost.release();
		stateChanged();
	}
	public void msgAtCook() {
		atCook.release();
		stateChanged();
	}
	
	public void msgAtCashier() {
		atCashier.release();
		stateChanged();
	}
	

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	  //if there is c in customers where c.state == waiting, then seatCustomer(c);
	public boolean pickAndExecuteAnAction() {
		
		//if (ws == waiterState.available) {
		try {
			
		
		for (myCustomer c: customers) {
			if(c.cs == customerState.waiting){
				seatCustomer(c);
				return true;
			}
		}
		for (myCustomer c: customers) {
			if(c.cs != customerState.leaving && c.cs == customerState.readyToOrder || c.cs == customerState.reordering) {
				takeOrder(c);
				return true;
			}
		}
		for (myCustomer c: customers) {
			if(c.cs == customerState.ordered) {
				deliverOrderToCook(c);
				return true;
			}
		}
		/*for (myCustomer c: customers) {
			if(c.cs == customerState.reordering) {
				takeOrder(c);
				return true;
			}
		}
		*/
		for (myCustomer c: customers) {
			if(c.cs == customerState.waitingForFoodToBeDelivered) {
				deliverOrderToCustomer(c);
				System.out.println(c.order);
				return true;
			}
		}
		/*
		for (myCustomer c: customers) {
			if(c.cs == customerState.eating) {
				if(host.msgAskToGoOnBreak(this)) {
					System.out.println("GO ON BREAK" + this.name);
					try {
						this.isOnBreak = true;
						goOnBreak();
						Thread.currentThread().sleep(10000);
						this.isOnBreak = false;
					}
					catch (Exception e) {
				}
				System.out.println("BACK FROM BREAK: " + this.name);
				}
				
				return true;
			}
		}*/
		
		for (myCustomer c: customers) {
			if(c.cs == customerState.waitingForCheckToCalculate) {
				obtainCheck(c);
				return true;
			}
		}
		for (myCustomer c: customers) {
			if(c.cs == customerState.waitingForCheckToBeDelivered) {
				deliverCheckToCustomer(c);
				return true;
			}
		}
		
		for (myCustomer c: customers) {
			if(c.cs == customerState.payingCheck) {
				alertHostTableIsFree(c);
				
				//customers.remove(c);
				return true;
			}
		}
		for (myCustomer c: customers) {
			if(c.cs == customerState.leaving) {
				alertHostTableIsFree(c);
				return true;
			}
		}
		//if(waiterGui.getXPos() != -20 && waiterGui.getYPos() != -20){
			
			//return true;
		//}
		//waiterGui.DoGoToHost();
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
		
		}
		
	// Actions
	

	private void seatCustomer(myCustomer c) {
		System.out.println("seatCustomer action");
		waiterGui.DoGoToHost();
		//customer.msgSitAtTable(table.tableNumber);
		//DoSeatCustomer(customer, table);
		try {
			atHost.acquire();
			//System.out.println("atHost.acquire");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.c.msgFollowMeToTable(this.menu, c.table);//new Menu());
		System.out.println("Follow Me to your table.");
		DoSeatCustomer(this, c.c, c.table);
	
		//table.setOccupant(customer);
		//customers.remove(customer);
		//hostGui.DoLeaveCustomer();
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.cs = customerState.seated;  
	
		c.cs = customerState.readyToOrder;
	}
	
	private void takeOrder(myCustomer cust) {
		System.out.println("Taking order");
		waiterGui.DoGoToTable(cust.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//if(cust.cs == customerState.readyToOrder) {
			cust.c.msgWhatWouldYouLike();
			cust.cs = customerState.askedToOrder;
		//}
	} 

	
	private void deliverOrderToCook(myCustomer cust) {
		System.out.println("Delivering order to cook");
		waiterGui.DoGoToCook();
		Order myOrder = new Order(this, cust.choice, cust.table, OrderState.pending, cust.c);
		//cust.c.order = myOrder;
		cust.order = myOrder;
		//waiterGui.foodOrdered(cust.choice);
		//waiterGui.OrderGoToCook(cust.choice);
		try {
			atCook.acquire();
			//System.out.println ("acuiqred atCook  # 1*****");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cook.msgHereIsOrder(myOrder);
		cust.cs = customerState.waitingForFoodToCook;
	}
	private void deliverOrderToCustomer(myCustomer cust) {
		System.out.println("Delivering order to customer");
		
		waiterGui.DoGoToCook();
		try {
			atCook.acquire();
			//System.out.println ("acuiqred atCook  # 2*****");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waiterGui.deliveringFood=true;
		waiterGui.DoGoToTable(cust.table);
		//waiterGui.doneCooking(cust.choice);
		//waiterGui.OrderGoToCustomer(cust.choice, cust.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cust.c.msgHereIsYourFood();
		waiterGui.deliveringFood=false;
		//waiterGui.DoGoToHost();
		cust.cs = customerState.eating;
		stateChanged();
		waiterGui.DoLeaveCustomer();
		//atTable.release();
	}
	private void obtainCheck(myCustomer mc) {
		System.out.println("obtaining check from cashier");
		waiterGui.DoGoToCashier();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("******************message received");
		cashier.msgRequestCheck(mc.c, mc.order);
		mc.cs = customerState.waitingForCheckToBeDelivered;
		stateChanged();
	}
		
	private void deliverCheckToCustomer(myCustomer mc) {
		System.out.println("delivering check to customer");
		waiterGui.DoGoToCashier();
		try {
			atCashier.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		waiterGui.DoGoToTable(mc.table);
		try {
			atTable.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mc.c.msgHereIsYourCheck(mc.order);
		//mc.c.msgHereIsYourCheck();
		mc.cs = customerState.payingCheck;
		waiterGui.DoLeaveCustomer();
		
		
		
	}
	
	public void alertHostTableIsFree(myCustomer mc) {
		System.out.println("Alerting host table is free");
		//waiterGui.doneEating(mc.choice);
		//waiterGui.DoGoToHost();
		host.msgTableIsFree(mc.c);
		customers.remove(mc);
		/*if (host.msgAskToGoOnBreak(this) ) {
			// go on break
			System.out.System.out.printlnln("**** GO ON BREAK");
			try {
				Thread.sleep(3000);
			}
			catch(Exception e) {
				
			}
			System.out.System.out.printlnln("**** BACK FROM BREAK");
			
		}
		*/
	}
	private void goOnBreak(WaiterRole w) {
		System.out.println(w.getName() + " is going on break.");
		waiterGui.DoGoToBreakSpot();
	}

	// The animation DoXYZ() routines
	private void DoSeatCustomer(WaiterRole w, Customer customer, int table) {
		//Notice how we System.out.println "customer" directly. It's toString method will do it.
		//Same with "table"
		System.out.println(w.getName() + " is seating " + customer + " at " + table);
		waiterGui.DoGoToTable(table); 

	}


	//utilities

	public void setGui(WaiterGui gui) {
		waiterGui = gui;
	}
	public void setGui(Restaurant3CustomerGui gui) {
		customerGui = gui;
	}
	public void setCustomerStateLeaving(Restaurant3CustomerRole cust) {
		for (myCustomer mc: customers) {
			mc.cs = customerState.leaving;
			stateChanged();
		}
	}
	public void setCustomerStateReadyToOrder(Restaurant3CustomerRole cust) {
		for (myCustomer mc: customers) {
			mc.cs = customerState.readyToOrder;
			stateChanged();
		}
	}
	

	public HostGui getGui() {
		return hostGui;
	}
	public int getCustomersCount() {
		return customers.size();
	}
	
	public void setHost(HostRole host) {
		this.host = host;
	}
	
	public void setCook(CookRole cook) {
		this.cook = cook;
	}
	public void setCashier(CashierRole cashier) {
		this.cashier = cashier;
	}

	private class Table {
		Restaurant3CustomerRole occupiedBy;
		int tableNumber;

		Table(int tableNumber) {
			this.tableNumber = tableNumber;
		}

		void setOccupant(Restaurant3CustomerRole cust) {
			occupiedBy = cust;
		}

		void setUnoccupied() {
			occupiedBy = null;
		}

		Restaurant3CustomerRole getOccupant() {
			return occupiedBy;
		}

		boolean isOccupied() {
			return occupiedBy != null;
		}

		public String toString() {
			return "table " + tableNumber;
		}
		
	}

	
}



