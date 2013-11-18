package simcity.Market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simcity.Market.MarketGui.MarketCashierGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.PersonAgent;
import agent.Role;

public class MarketCashierRole extends Role implements MarketCashier{

	private List<Check> checks= new ArrayList<Check>();
	public Map<String, Item> inventory = new HashMap<String, Item>();
	private MarketWorker worker;
	public String name;
	private MarketCashierGui cashierGui;
	private List<MarketCustomer> waitingCustomers = new ArrayList<MarketCustomer>();
	private MarketCustomer currentCustomer;
	private PersonAgent person;
	public MarketCashierRole(PersonAgent p){
		super(p);
		person = p;
		name = person.name;
	}
	
	public void setGui(MarketCashierGui gui){
		cashierGui = gui;
	}
	
	public void needFood(MarketCustomerRole mcr){
		System.out.println(person.name+ ": "+ "New customer arrived " + mcr.person.name);
		waitingCustomers.add(mcr);
		if (waitingCustomers.size()==1 ){
			currentCustomer = mcr;
			System.out.println("Current customer " + currentCustomer.getName());
			stateChanged();	
		}
	}
	
	public void INeed(List<Order> o){
		System.out.println(person.name+ ": "+ "Got new order from "+ o.get(0).customer.getName());
		checks.add(new Check(o));
		stateChanged();
	}
	
	public void setWorker(MarketWorker w){
		worker = w;
	}
	
	public Map<String, Item> getInventory(){
		return inventory;
	}
	
	public void hereIsMoney(MarketCustomer c, double money){
		System.out.println(person.name+ ": "+"Received money from customer "+ c.getName() + money);
		for (Check check: checks){
			if (check.c == c){
				check.moneyGiven = money;
				check.state = Check.CheckState.gotMoney;
				stateChanged();
			}
		}
	}
	
	public boolean pickAndExecuteAnAction(){
		
		for (Check check: checks){
			if (check.state == Check.CheckState.gotMoney && check.c == currentCustomer){
				Process(check);
				return true;
			}
			
			if (check.state == Check.CheckState.pending && check.c == currentCustomer){
				checkQuantity(check);
				return true;
			}
		}
		if (currentCustomer != null){
			System.out.println(person.name+ ": "+"there is a customer" + currentCustomer.getName());
			Ask(currentCustomer);
			return true;
		}
		
		return false;
	}
	
	public void Ask(MarketCustomer c){
		System.out.println(person.name+ ": "+"What Do You Want " + currentCustomer.getName());
		c.NextCustomerPlease();
	}
	
	public void checkQuantity(Check check){
		System.out.println(person.name+ ": "+"Checking quantity"+ check.c.getName());
		for (Order order: check.orders){
			Item i = (inventory.get(order.choice));
			if (i.quantity == 0){
				order.customer.outOf(order);
				check.orders.remove(order);
			}
		}
			worker.Bring(check.orders);
			for (Order o: check.orders){
				Item item = (inventory.get(o.choice));
				item.quantity = item.quantity - o.quantity;
				check.amountDue = check.amountDue + (item.price*o.quantity);
			}
			check.c.amountDue(check.amountDue);	
	}
	
	public void Process(Check check){
		System.out.println(person.name+ ": "+"Giving change " + check.c.getName());
		check.c.HereIsChange(check.moneyGiven - check.amountDue);
		waitingCustomers.remove(currentCustomer);
		if (waitingCustomers.size()!=0){
		currentCustomer = waitingCustomers.get(0);
		stateChanged();
		}
		checks.remove(check);
	}
	
	
	static class Check{
		private List<Order> orders = new ArrayList<Order>();
		private MarketCustomer c;
		private double amountDue;
		private double moneyGiven;
		private CheckState state;
		private enum CheckState {pending,checkingAmount, computing, gotMoney};
		
		
		public Check(List<Order> o){
			c = o.get(0).customer;
			orders = o;
			state = CheckState.pending;
		}
		
	}
}

