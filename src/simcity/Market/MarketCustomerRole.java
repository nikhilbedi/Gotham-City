package simcity.Market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simcity.PersonAgent;
import simcity.PersonAgent.MarketState;
import simcity.Market.MarketGui.MarketCustomerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import Gui.RoleGui;
import agent.Role;;

public class MarketCustomerRole extends Role implements MarketCustomer{

	private MarketCashier cashier;
	private List<Order> orders = new ArrayList<Order>();
	public CustomerState state;
	private double amountDue;
	private String name;
	private double money;
	//public PersonAgent person;
	public enum CustomerState {needFood, choseGroceries, movingToCashier, atCashier, ordering, inALine, paying, amountDue, gotChange, leaving, moving, gettingItems, gotItems };
	private MarketCustomerGui customerGui;

	public MarketCustomerRole(PersonAgent p){
		super(p);
		//person = p;	
		name = p.name;
		customerGui  = (MarketCustomerGui)super.gui;
	}
	
	public MarketCustomerRole(){
		super();
		customerGui  = (MarketCustomerGui)super.gui;
		
	}
	
	public void setCashier(MarketCashier m){
		cashier = m;
	}

	public String getName(){
		return name;
	}
	public void getGroceries(){
		 //person.groceryList.put("Chicken", 3);
		 //person.groceryList.put("Rice", 2);

		System.out.println("Got order to get groceries");
		for (Map.Entry<String, Integer> entry: myPerson.groceryList.entrySet() ){
			orders.add(new Order(this, entry.getKey(), entry.getValue(), false));
		}
		state = CustomerState.needFood;
		stateChanged();
	}
	
	@Override
	public void startBuildingMessaging(){
		getGroceries();
		System.out.println("start build");
		//System.out.println(person);
		//print("maket" +myperson.markets.size());
		cashier = myPerson.markets.get(0).getCashier();
	}
	
	public void AtCashier(){//from gui
		System.out.println("At cashier");
		state = CustomerState.atCashier;
		stateChanged();
	} 
	
	public void ArrivedToGetItem(){
		System.out.println(myPerson.name+ " " +"Getting things");
		state = CustomerState.gettingItems;
		stateChanged();
	}
	
	public void outOf(Order order){
		//the order that cannot be fulfilled
	}
	
	public void amountDue(double amount){
		System.out.println(myPerson.name+ " " +"Amount due " + amount);
		amountDue = amount;
		state = CustomerState.amountDue;
		stateChanged();
	}
	
	public void HereIsChange(double change){
		System.out.println(myPerson.name+ " " + "Got change " + change);
		money = money+change;
		state = CustomerState.gotChange;
		stateChanged();
	}
	
	public void HereIsYourStuff(Map<String, Integer> map){
		
		System.out.println(myPerson.name+ " " +"Got my stuff");
		myPerson.groceryBag = map;
		myPerson.groceryList.clear();
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
		System.out.println(myPerson.name+ " " + "Leaving");
		customerGui.DoLeave();
		//testing
		myPerson.marketState = MarketState.TakeGroceriesHome;
	}
	
	public void Waiting(){
		
		
	}
	
	public void GetItems(){
		customerGui.DoGetItems();
		
	}
	
	public void DoPay(){
		System.out.println(myPerson.name+ " " +"Paying");
		double payment = round(amountDue);
		myPerson.setMoney((float) (myPerson.getMoney() - payment)) ;
		cashier.hereIsMoney(this, payment);
	}
	
	public void tellCashier(){
		System.out.println(myPerson.name+ " " + "Cashier, I need food");
		cashier.needFood(this);
	}
	
	public void MoveToCashier(){
		customerGui.DoMoveToCashier();
	}
	
	public void makePurchase(){
		System.out.println(myPerson.name+ " " +"Ordering things");
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

	/*public void setGui(MarketCustomerGui customerGui) {
		this.customerGui = customerGui;
	}*/
	
	public void setGui(RoleGui g){
		super.setGui(g);
		customerGui = (MarketCustomerGui)g;
	}
	
	public void msgOutOfMarket(){
		
		myPerson.leftBuilding(this);
	}
	
	public void Do(String msg){
	//	person.Do(msg);
	}


}
