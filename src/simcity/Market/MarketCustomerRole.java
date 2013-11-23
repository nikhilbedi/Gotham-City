package simcity.Market;

import java.util.ArrayList;
import java.util.List;

import simcity.PersonAgent;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import agent.Role;;

public class MarketCustomerRole extends Role implements MarketCustomer{

	private MarketCashier cashier;
	private List<Order> orders = new ArrayList<Order>();
	private CustomerState state;
	private double amountDue;
	private String name;
	private double money;
	public PersonAgent person;
	private enum CustomerState {needFood, choseGroceries, movingToCashier, atCashier, ordering, inALine, paying, amountDue, gotChange, leaving, moving, gettingItems, gotItems };
	private MarketCustomerGui customerGui;
	
	public MarketCustomerRole(PersonAgent p){
		super(p);
		person = p;	
		name = person.name;
	}
	
	public void setCashier(MarketCashier m){
		cashier = m;
	}

	public String getName(){
		return name;
	}
	public void getGroceries(){
		List<String> foods = new ArrayList<String>();//poka tak 
		System.out.println(person.name+ " " + "Got order to get groceries");
		for (int i=0; i<foods.size(); i++){
			orders.add(new Order(this, foods.get(i), 2, false));
		}
		state = CustomerState.needFood;
		stateChanged();
	}
	
	public void AtCashier(){//from gui
		state = CustomerState.atCashier;
		stateChanged();
	} 
	
	public void ArrivedToGetItem(){
		System.out.println(person.name+ " " +"Getting things");
		state = CustomerState.gettingItems;
		stateChanged();
	}
	
	public void outOf(Order order){
		//the order that cannot be fulfilled
	}
	
	public void amountDue(double amount){
		System.out.println(person.name+ " " +"Amount due " + amount);
		amountDue = amount;
		state = CustomerState.amountDue;
		stateChanged();
	}
	
	public void HereIsChange(double change){
		System.out.println(person.name+ " " + "Got change " + change);
		money = money+change;
		state = CustomerState.gotChange;
		stateChanged();
	}
	
	public void HereIsYourStuff(List<Order> o){
		System.out.println(person.name+ " " +"Got my stuff");
		orders = o;
		state = CustomerState.gotItems;
		stateChanged();
	}
	
	
	public void NextCustomerPlease(){
		state = CustomerState.choseGroceries;
		stateChanged();
	}
	
	//scheduler
	@Override 
	public boolean pickAndExecuteAnAction(){
		if (state == CustomerState.needFood){
			state =CustomerState.inALine;
			tellCashier();
			return true;
		}
		
		if (state == CustomerState.choseGroceries){
			state = CustomerState.movingToCashier;
			MoveToCashier();
			return true;
		}
		
		if (state == CustomerState.atCashier){
			state = CustomerState.ordering;
			makePurchase();
			return true;
		}
		
		
		if (state == CustomerState.amountDue){
			state = CustomerState.paying;
			DoPay();
			return true;
		}
		if (state == CustomerState.gotChange){
			state = CustomerState.moving;
			GetItems();
			return true;
		}
		if (state == CustomerState.moving){
			state = CustomerState.gettingItems;
			Waiting();
			return true;
		}
		if (state == CustomerState.gotItems){
			state = CustomerState.leaving;
			Leave();
			return true;
		}
		
		return false;
	}
	
	//actions
	
	public void Leave(){
		System.out.println(person.name+ " " + "Leaving");
		customerGui.DoLeave();
		//add orders to persons inventory
		//gui stuff
	}
	
	public void Waiting(){
		
		
	}
	
	public void GetItems(){
		customerGui.DoGetItems();
		
	}
	
	public void DoPay(){
		System.out.println(person.name+ " " +"Paying");
		double payment = round(amountDue);
		money = money - payment;
		cashier.hereIsMoney(this, payment);
	}
	
	public void tellCashier(){
		System.out.println(person.name+ " " + "Cashier, I need food");
		cashier.needFood(this);
	}
	
	public void MoveToCashier(){
		customerGui.DoMoveToCashier();
	}
	
	public void makePurchase(){
		System.out.println(person.name+ " " +"Ordering things");
		 //gui will send this
		cashier.INeed(orders);
	}
	
	
	
	private int round(double money){
	    double d = Math.abs(money);
	    int i = (int) d;
	    double result = d - (double) i;
	    if(result<0.5){
	        return money<0 ? -i : i;            
	    }else{
	        return money<0 ? -(i+1) : i+1;          
	    }
	}

	public void setGui(MarketCustomerGui customerGui) {
		this.customerGui = customerGui;
	}
	
	public void Do(String msg){
		person.Do(msg);
	}

}
