package simcity.Market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.Market.MarketGui.MarketCashierGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4AnimationPanel;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant5.Restaurant5;
import simcity.PersonAgent;
import simcity.TheCity;
import Gui.RoleGui;
import Gui.ScreenFactory;
import agent.Role;

public class MarketCashierRole extends Role implements MarketCashier{

	private List<Check> checks= new ArrayList<Check>();
	public Map<String, Item> inventory = new HashMap<String, Item>();
	private MarketWorker worker;
	public String name;
	private MarketCashierGui cashierGui;
	private List<MarketCustomer> waitingCustomers = new ArrayList<MarketCustomer>();
	private List<RestaurantOrder> restaurantOrders = new ArrayList<RestaurantOrder>();
	public MarketCustomer currentCustomer;
	public List<MarketCustomer> customersInMarket = new ArrayList<MarketCustomer>();

	Restaurant1 r1;
	Restaurant2 r2;
	Restaurant3 r3;
	Restaurant4 r4;
	Restaurant5 r5;
	
	//private PersonAgent person;
	public MarketCashierRole(PersonAgent p){
		super(p);
		//person = p;
		name = p.name;
	}
	
	public MarketCashierRole(){
		super();
	}
	
	public void setRest4Cashier(Restaurant4CashierRole r){
		//cashier4 = r;
	}

	public List<RestaurantOrder> getRestaurantOrders(){
		return restaurantOrders;
	}
	
	
	public List<MarketCustomer> getCustomers(){
		return waitingCustomers;
	}
	
	public MarketCustomer getCurrentCustomer(){
		return currentCustomer;
	}
	
	public List<Check> getChecks(){
		return checks;
	}
	
	
	public void setCashiers(){
	/*	Restaurant4 r4 = (Restaurant4)TheCity.getBuildingFromString("Restaurant 4");
    	cashier4 = (Restaurant4CashierRole) r4.getCashier();
    	Restaurant1 r1 = (Restaurant1)TheCity.getBuildingFromString("Restaurant 1");
    	//cashier1 = (Restaurant1CashierRole) r1.getCashier();
    	Restaurant2 r2 = (Restaurant2)TheCity.getBuildingFromString("Restaurant 2");
    //	cashier2 = (Restaurant2CashierRole) r2.getCashier();
    	Restaurant3 r3 = (Restaurant3)TheCity.getBuildingFromString("Restaurant 3");
    //	cashier3 = (Restaurant3CashierRole) r3.getCashier();
    	Restaurant5 r5 = (Restaurant5)TheCity.getBuildingFromString("Restaurant 5");
    //	cashier5 = (Restaurant5CashierRole) r5.getCashier();
    	*/
	}

	
	public void setGui(RoleGui gui){
		super.setGui(gui);
		cashierGui = (MarketCashierGui)gui;
	}

	public void needFood(MarketCustomer mcr){ //when customer just arrives to the market and in a wating line 
		System.out.println(myPerson.name+ ": "+ "New customer arrived " );
		waitingCustomers.add(mcr);
		if (waitingCustomers.size()==1 ){
			currentCustomer = mcr;
			System.out.println("Current customer " + currentCustomer.getName());
			stateChanged();	
		}
	}
	
    public void hereIsMoneyRestaurant(Restaurant r, double money){ //restaurant paying
    	for(RestaurantOrder order: restaurantOrders){
    		if (order.rest== r){
    			order.moneyGiven = money;
    			order.state = RestaurantOrder.State.paying;
    			stateChanged();
    		}
    	}
    }
	 
	public void INeed(List<Order> o){ //here customer passes what he needs
		System.out.println(myPerson.name+ ": "+ "Got new order from "+ o.get(0).customer.getName());
		checks.add(new Check(o));
		customersInMarket.add(o.get(0).customer);
		stateChanged();
	}
	
	public void INeedFood(Map<String, Integer> food, Restaurant rest){ //order from restaurant
		System.out.println("Got new order from restaurant cook" + rest);
		r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
		r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
		r3 = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
		r4 = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
		r5 = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
		restaurantOrders.add(new RestaurantOrder(food, rest));
		if(myPerson != null) //adding this because it causes null pointer
			stateChanged();
	}
	
	public void setWorker(MarketWorker w){
		worker = w;
	}
	
	public Map<String, Item> getInventory(){
		return inventory;
	}
	
	public void hereIsMoney(MarketCustomer c, double money){ //customer paying
		System.out.println("Received money from customer "+ money);
		for (Check check: checks){
			if (check.c == c){
				check.moneyGiven = money;
				check.state = Check.CheckState.gotMoney;
				stateChanged();
			}
		}
	}
	
	public void foodIsDelivered(Restaurant r){  //who will send this? maybe truck will send it to the worker and worker will send it to the cashier
		myPerson.Do("food is delivered, sending check");
		for (RestaurantOrder order: restaurantOrders){
			if (order.rest == r){
				if (order.rest == r1){
					r1.getCashier().amountDue(order.amountDue, this);
				}
				else if (order.rest == r2){
					System.out.println(r2.getName() + " Here is amount due " + order.amountDue);
					r2.getCashier().amountDue(order.amountDue, this);
				}
				else if (order.rest == r3){
					r3.getCashier().amountDue(order.amountDue, this);
				}
				else if (order.rest == r4){
					System.out.println(r4.getName() + " Here is amount due " + order.amountDue);
					r4.getCashier().amountDue(order.amountDue, this);
				}
				else if (order.rest == r5){
					r5.getCashier().amountDue(order.amountDue, this);
				}
			}
		}
	} 
	
	public boolean pickAndExecuteAnAction(){
		if(theManLeavingMe != null && waitingCustomers.isEmpty()) {
			leaveWork();
			return true;
		}
		
		for (Check check: checks){
			if (check.state == Check.CheckState.gotMoney && check.c == currentCustomer){
				check.state = Check.CheckState.paid;
				GiveChangeCustomer(check);
				return true;
			}
			
			if (check.state == Check.CheckState.pending && check.c == currentCustomer){
				check.state = Check.CheckState.checkingAmount;
				checkQuantity(check);
				return true;
			}
		}
		if (currentCustomer != null){
			System.out.println("there is a customer" + currentCustomer.getName());
			Ask(currentCustomer);
			return true;
		}
		for (RestaurantOrder restaurantOrder: restaurantOrders){
			
			if (restaurantOrder.state == RestaurantOrder.State.paying){
				restaurantOrder.state = RestaurantOrder.State.paid;
				GiveChangeRestaurant(restaurantOrder);
				return true;
			}
			if (restaurantOrder.state == RestaurantOrder.State.pending){
				restaurantOrder.state = RestaurantOrder.State.processing;
				ProcessRestaurantOrder(restaurantOrder);
				return true;
			}
			
		}
		
		return false;
	}
	
	public void Ask(MarketCustomer c){
		System.out.println("What Do You Want " + currentCustomer.getName());
		c.NextCustomerPlease();
	}
	
	public void checkQuantity(Check check){
		//System.out.println("Checking quantity"+ check.c.getName());
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
	
	public void ProcessRestaurantOrder(RestaurantOrder o){
             Map<String, Integer> temp = o.foodNeeded;
             for (Map.Entry<String, Integer> entry: temp.entrySet()){ //for now market has always enough
                     int quant = entry.getValue();
                     String choice = entry.getKey();
                     Item i = (inventory.get(choice));
                     System.out.println(i.type);
                     i.quantity = i.quantity - quant;
                     o.amountDue = o.amountDue + (i.price*quant);
             }
            /* myPerson.Do("Amount due " + o.amountDue);
             
             if (o.cashierRole == cashier4){
                     ((Restaurant4CashierRole) o.cashierRole).amountDue(o.amountDue, this);
             }*/
             worker.SendFood(temp, o.rest);
	}
	
	public void GiveChangeRestaurant(RestaurantOrder o){
		double i = o.moneyGiven - o.amountDue;
		for (RestaurantOrder order: restaurantOrders){
			if (order == o){
				if (order.rest == r1){
					System.out.println(r1.getName() + "Here is change");
					r1.getCashier().HereIsYourChange(i, this);
				}
				else if (order.rest == r2){
					System.out.println(r2.getName() + "Here is change");
					r2.getCashier().HereIsYourChange(i, this);
				}
				else if (order.rest == r3){
					System.out.println(r3.getName() + "Here is change");
					r3.getCashier().HereIsYourChange(i, this);
				}
				else if (order.rest == r4){
					System.out.println(r4.getName() + "Here is change");
					r4.getCashier().HereIsYourChange(i, this);
				}
				else if (order.rest == r5){
					System.out.println(r5.getName() + "Here is change");
					r5.getCashier().HereIsYourChange(i, this);
				}
			}
		}
		restaurantOrders.remove(o);
	}
	
	public void GiveChangeCustomer(Check check){
		System.out.println(myPerson.name+ ": "+"Giving change " + check.c.getName());
		check.c.HereIsChange(check.moneyGiven - check.amountDue);
		waitingCustomers.remove(currentCustomer);
		if (waitingCustomers.size()!=0){
			currentCustomer = waitingCustomers.get(0);
			stateChanged();
		}
		else{
			currentCustomer = null;
			stateChanged();
		}
		checks.remove(check);
	}
	
	
	public static class Check{
		private List<Order> orders = new ArrayList<Order>();
		private MarketCustomer c;
		public double amountDue;
		public double moneyGiven;
		public CheckState state;
		public  enum CheckState {pending,checkingAmount, computing, gotMoney, paid};
		
		
		public Check(List<Order> o){
			c = o.get(0).customer;
			orders = o;
			state = CheckState.pending;
		}
		
	}
	
	public static class RestaurantOrder{
		private Role cookRole;
		private double moneyGiven;
		private Role cashierRole;
		private Map<String, Integer> foodNeeded;
		public State state;
		public enum State {pending, processing, gotMoney, paying, paid};
		double amountDue;
		private Restaurant rest;
		public RestaurantOrder(Map<String,Integer> food, Restaurant r){
		/*	cashierRole = cashier;
			cookRole = role;*/
			foodNeeded = food;
			state = State.pending;
			rest = r;
		}
	}

	
}

