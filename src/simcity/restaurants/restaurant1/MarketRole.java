package simcity.restaurants.restaurant1;

import simcity.PersonAgent;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant1.interfaces.*;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Restaurant Market Agent
 */
public class MarketRole extends Role implements Market {
    public List<RestaurantOrder> restOrders
	= Collections.synchronizedList(new ArrayList<RestaurantOrder>());
    private String name;
    private Timer packagingTimer = new Timer();
    private Map<String, FoodType> inventory = new HashMap<String, FoodType>() {{
    	    put("Pizza", new FoodType("Pizza", 2));
	    put("Steak", new FoodType("Steak", 2));
	    put("Salad", new FoodType("Salad", 2));
	    }};
    private Cook cook;
    private Cashier cashier;
    private boolean noOrdersForRestaurant = false;
    //creating states outside the RestaurantOrder class
    public enum OrderState {pending, preparing, done, finished};

    public MarketRole(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public List getCurrentOrders() {
	return restOrders;
    }

    public void addCook(Cook c) {
	cook = c;
    }
    
    public void addCashier(Cashier c) {
    	cashier = c;
    }

    // Messages

    //Market receives an order from the cook and stores it in a list
    public void needFoodItems(String choice, int amount) {
		print("Received order of " + amount + " " + choice + " from cook " + ((PersonAgent) cook).getName());
		restOrders.add(new RestaurantOrder(choice, amount));
		stateChanged();
    }

    public void packingDone(RestaurantOrder o) {
		o.state = OrderState.done;
		stateChanged();
    }
    
    public void hereIsPayment(double amount) {
    	if(amount >= 10.0) {
    		print("received full payment from cashier");
    	}
    	else {
    		print("cutting supply to this restaurant. No more orders for them");
    		
    	}
    }

    /**
     * Scheduler.  Determine what action is called for, and do it.
     */
    public boolean pickAndExecuteAnAction() {
	    if(!noOrdersForRestaurant) {
	    	synchronized(restOrders) {
				for (RestaurantOrder o : restOrders) {
				    if(o.state == OrderState.done){
						deliverOrder(o);		
						return true;
				    }
				    if(o.state == OrderState.pending) {
						processDelivery(o);
						return true;
				    }
				}
	    	}
	    }
	    else
	    	print("No orders will be provided to restaurant.");
		    
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
    }

    // Actions

    //Food order is cooked and is managed by a timer
    private void processDelivery(RestaurantOrder o) {
		FoodType item = inventory.get(o.type);
		if(item.stock == 0) {
		    print("This market is out of " + o.type);
		    ((CookRole) cook).canSupply(0, o.type);
		    restOrders.remove(o);
		    return;
		}
	
		if(o.amount > item.stock) {
		    print("This market can fulfill " + item.stock + " " + o.type);
		    o.amountFulfilled = item.stock;
		    item.stock = 0;
		    ((CookRole) cook).canSupply(item.stock, o.type);
		}
		else {
		    print("This market can fulfill " + o.amount + " " + o.type);
		    o.amountFulfilled = o.amount;
		    item.stock -= o.amount;
		    ((CookRole) cook).canSupply(o.amount, o.type);
		}
		o.state = OrderState.preparing;
		final RestaurantOrder order = o;
		packagingTimer.schedule(new TimerTask() {
			public void run() {
			    print("Packing and delivery done for " + order.type);
			    deliverOrder(order);
				}
		    },
		    20000);	
    }

    //Waiter is notifed of the cooked order after the timer has run, and the cook has changed the status of the order
    private void deliverOrder(RestaurantOrder o) {
		o.state = OrderState.finished;
		//The correct food order is given
		print("Delivering order for " + o.type);
		((CookRole) cook).foodDelivered(o.type, o.amountFulfilled);
		//send cashier bill
		cashier.payMarketBill(this, 10.0);
		restOrders.remove(o);
    }


    //Food and RestaurantOrder classes properly created and used
    class RestaurantOrder {
		Cook cook;
		String type;
		int amount;
		int amountFulfilled;
		public OrderState state = OrderState.pending;
		
		public RestaurantOrder(String choice, int amt) {
		    amount = amt;
		    type = choice;
		}
    }

    class FoodType {
		public String type;
		public int stock;
		//public int capacity;
		//public double price;
	
		public FoodType(String choice, int amt) {
		    stock = amt;
		    type = choice;
		}
    }
}