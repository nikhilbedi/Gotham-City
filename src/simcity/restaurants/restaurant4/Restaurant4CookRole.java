package simcity.restaurants.restaurant4;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import agent.Agent;
import agent.Role;

import java.util.*;

import simcity.Item;
import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketWorkerRole;
import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CookGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;
import trace.AlertLog;
import trace.AlertTag;


public class Restaurant4CookRole extends Role implements Restaurant4Cook{
        private String name;
        private List<Order> orders = new ArrayList<Order>();
        private Timer timer = new Timer();
        public Map<String, Food> foods = new HashMap<String, Food>();
        Food chicken = new Food("Chicken", 3);
        Food steak = new Food("Steak", 3);
        Food pizza = new Food("Pizza", 3);
        Food salad = new Food("Salad", 3);

	private Restaurant4CookGui cg;
	public Map<String, Integer> neededFood = new HashMap<String, Integer>();
	private MarketCashier cashier;
	private Restaurant4CashierRole restCashier;
	private boolean order = false;
	private boolean needFood = false;
	Restaurant4 r4;
	public Restaurant4CookRole(PersonAgent p){
		super(p);
		foods.put("Chicken",chicken);
		foods.put("Steak",steak);
		foods.put("Pizza",pizza);
		foods.put("Salad",salad);
	}

	public Restaurant4CookRole(){
		super();
		foods.put("Chicken",chicken);
		foods.put("Steak",steak);
		foods.put("Pizza",pizza);
		foods.put("Salad",salad);
	}
	
	public void setCashier(Restaurant4CashierRole mc){
		restCashier = mc;
	}
	public String getName(){
		return name;
	}
	
	public void gotFood(String s) {
		cg.DoRemoveFood(s);
	}
	
	/*public void setGui(Restaurant4CookGui c){ //This is messing things up
		cg = c;
	} 
	*/
	
	public void setGui(RoleGui gui) {
		super.setGui(gui);
		cg = (Restaurant4CookGui) gui;
	}
	
	public void resetInventory(){
		chicken.amount = 0;
		chicken.state = Food.FoodState.low;
		salad.amount = 0;
		salad.state = Food.FoodState.low;
		steak.amount = 0;
		steak.state = Food.FoodState.low;
		pizza.amount = 0;
		pizza.state = Food.FoodState.low;
		stateChanged();
	}
	//messages
	
	public Vector<Item> getInventory(){
		Vector<Item> inventory = new Vector<Item>();
		for (Map.Entry<String, Food> f : foods.entrySet())//check food amount
		{
			inventory.add(new Item(f.getKey(), f.getValue().amount));
			/*AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
					"Key: " + f.getKey() + " Value: " + f.getValue());*/
		}
		AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
				inventory.toString());
		return inventory;
	}
	
	public void updateItem(String s, int hashCode) {
		// TODO Auto-generated method stub
		Food f = foods.get(s);
		foods.get(s).amount = hashCode;
	}
	
	public void HereIsOrder(Restaurant4Waiter waiter, String choice, int table){
		myPerson.Do("Got your order " + choice);
		orders.add(new Order(waiter, choice, table));
		stateChanged();
	}
	
	public void OrderOnTheStand(){
		orders.add(new Order(RevolvingStand.orders.get(0).waiter,RevolvingStand.orders.get(0).choice, RevolvingStand.orders.get(0).table ));
		myPerson.Do("There is something on the stand");
		int index = orders.size()-1;
		Order o = orders.get(index);
		//RevolvingStand.removeOrder(this, o.waiter, o.choice, o.table);
		RevolvingStand.orders.remove(0);
		stateChanged();
	}

	
	public void setSalad(){
		salad.amount = 0;
	}
	
	public void HereIsYourFood(Map<String, Integer> m, MarketWorkerRole worker){ //from market
		needFood = false;
		order = false;
		myPerson.Do("Sending market worker message that I got food");
		worker.Delivered(r4);
		for (Map.Entry<String, Integer> entry: m.entrySet()){
			Food f = foods.get(entry.getKey());
			f.amount = f.amount + entry.getValue();
			foods.put(entry.getKey(), f);
			myPerson.Do("Got order from market, now I have " + f.type + " " + f.amount);
		}
		
	}
	//scheduler
	
	public boolean pickAndExecuteAnAction(){
		if(theManLeavingMe != null && orders.isEmpty()) {
			leaveWork();
			return true;
		}
		
		for (Order order:orders){
			if (order.s == Order.OrderState.done){
				order.s = Order.OrderState.finished;
				NotifyWaiter(order);
				return true;
			}
		}
		for (Order order:orders){
			if (order.s == Order.OrderState.pending){
				myPerson.Do("state pending cook, in scheduler");
				tryToCookFood(order);
				return true;
			}
		}	
		
		if (order == false){
		for (Map.Entry<String, Food> entry: foods.entrySet()){
			if (entry.getValue().amount<=2){
				myPerson.Do("Need " + entry.getKey());
				int needed = entry.getValue().capacity - entry.getValue().amount;
				neededFood.put(entry.getKey(), needed);
				needFood = true;
			}
		}
		}
		if ((needFood == true)&&(order == false)){
			order = true;
			myPerson.Do("Ordering food");
			orderFoodThatIsLow();
			return true;
		}
	
		return false;
	}
	

	//actions 
	
	public void tryToCookFood(Order o){
		Food f = foods.get(o.choice);
		if (f.amount == 0){
			myPerson.Do("No " + o.choice);
			o.waiter.outOf(o.table, o.choice);
			neededFood.put(f.type, 6);
			orderFoodThatIsLow();
			orders.remove(o);
			return;
		}
		else{
			myPerson.Do("Cooking " + o.choice);
			cg.DoCookFood(o.choice);
			o.s = Order.OrderState.cooking;
			
			timer.schedule(new DoTask(o), f.cookTime.get(o.choice)+1000);
			f.amount = f.amount -1;
		}
		if (f.amount <= f.low){
			int i = f.capacity - f.amount;
			neededFood.put(f.type, i);
			orderFoodThatIsLow();
		}
	}
	
	
	
	public void orderFoodThatIsLow(){
		//order = false;
		myPerson.Do("Ordering food from market");
		Market m = (Market) TheCity.getBuildingFromString("Market"); // add one more market later
    	cashier = m.getCashier();
    	r4 = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
		cashier.INeedFood(neededFood, r4);
	}


        public void setMarketCashier(MarketCashier m){
                
                cashier = m;
        }
        
        private class DoTask extends TimerTask{
                Order order;
                public DoTask(Order o){
                        order = o;
                }
                public void run() {
                        // TODO Auto-generated method stub
                        TimerDone(order);
                }
        }
        
        public void TimerDone(Order o){
                myPerson.Do("Done cooking " + o.choice);
                o.s = Order.OrderState.done;
                cg.DoPlateOrder(o.choice);
                stateChanged();
        }
        public void NotifyWaiter(Order o){
                orders.remove(o);
                //cg.DoRemoveFood(o.choice);
                o.waiter.OrderDone(o.choice, o.table);
        }
        
        
        static class Order{
                private Restaurant4Waiter waiter;
                private String choice;
                private int table;
                private OrderState s;
                public enum OrderState {pending, cooking, done, finished};
                
                public Order(Restaurant4Waiter waiter2, String c, int t){
                        waiter = waiter2;
                        choice = c;
                        table = t;
                        s = OrderState.pending;
                }
        }
        
        public static class Food{
                public String type;
                public int amount;
                public int low=2;
                public int capacity=6;
                public FoodState state;
                public enum FoodState {enough, ordering, low};
                public Map<String, Integer> cookTime = new HashMap<String,Integer>();
                
                
                public Food(String type, int amount){
                        this.type = type;
                        this.amount = amount;
                        cookTime.put("Steak", new Integer(6000));
                        cookTime.put("Chicken", new Integer(5000));
                        cookTime.put("Pizza", new Integer(4500));
                        cookTime.put("Salad", new Integer(4000));
                        if (amount <= low){
                                state = FoodState.low;
                                                }
                        else{
                                state = FoodState.enough;
                        }
                }
                
        }

        @Override
        public boolean getPause() {
                // TODO Auto-generated method stub
                return false;
        }

        @Override
        public void Ready(String string) {
                // TODO Auto-generated method stub
                
        }


        
}
