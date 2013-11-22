package simcity.Restaurant4;
import agent.Agent;
import agent.Role;

import java.util.*;

import simcity.PersonAgent;
import simcity.Restaurant4.Restaurant4Gui.Restaurant4CookGui;
import simcity.Restaurant4.interfaces.Restaurant4Cook;
import simcity.Restaurant4.interfaces.Restaurant4Waiter;


public class Restaurant4CookRole extends Role implements Restaurant4Cook{
	private String name;
	private List<Order> orders = new ArrayList<Order>();
	private Timer timer = new Timer();
	public Map<String, Food> foods = new HashMap<String, Food>();
	Food chicken = new Food("Chicken", 4);
	Food steak = new Food("Steak", 4);
	Food pizza = new Food("Pizza", 4);
	Food salad = new Food("Salad", 4);
	private PersonAgent person;
	private Restaurant4CookGui cg;
	
	public Restaurant4CookRole(PersonAgent p){
		super(p);
		
		foods.put("Chicken",chicken);
		
		foods.put("Steak",steak);
		
		foods.put("Pizza",pizza);
		
		foods.put("Salad",salad);
	}
	
	public String getName(){
		return name;
	}
	
	public void gotFood(String s) {
		cg.DoRemoveFood(s);
	}
	
	public void setGui(Restaurant4CookGui c){
		cg = c;
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
	
	public void HereIsOrder(Restaurant4Waiter waiter, String choice, int table){
		person.Do("Got your order " + choice);
		orders.add(new Order(waiter, choice, table));
		stateChanged();
	}
	  
	public void hereIsYourOrder(String s, int q){ //market
		Food f = foods.get(s);
		f.amount = f.amount + q;
		person.Do("Now i have " + f.amount + " "+ s);
		foods.put(s, f);
		f.state = Food.FoodState.enough;
		if (f.amount<f.capacity){
			person.Do("Looking for more " + s);
			orderFoodThatIsLow(f);
		}
		stateChanged();
	}
	
	public void setSalad(){
		salad.amount = 0;
	}
	//scheduler
	
	public boolean pickAndExecuteAnAction(){
		
		//check food amount here too
		for (Food value: foods.values()){
			if (value.state == Food.FoodState.low){
				value.state = Food.FoodState.ordering;
				orderFoodThatIsLow(value);
				return true;
			}
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
				tryToCookFood(order);
				return true;
			}
		}
	
		return false;
	}
	

	//actions 
	
	public void tryToCookFood(Order o){
		Food f = foods.get(o.choice);
		if (f.amount == 0){
			person.Do("No " + o.choice);
			o.waiter.outOf(o.table, o.choice);
			orderFoodThatIsLow(foods.get(o.choice));
			orders.remove(o);
			return;
		}
		else{
			person.Do("Cooking " + o.choice);
			cg.DoCookFood(o.choice);
			o.s = Order.OrderState.cooking;
			
			timer.schedule(new DoTask(o), f.cookTime.get(o.choice)+1000);
			f.amount = f.amount -1;
		}
		if (f.amount <= f.low){
			orderFoodThatIsLow(f);
		}
	}
	
	public void orderFoodThatIsLow(Food food){
		
		
	}
//}
	
/*	public MarketAgent pickAMarket(Food f){
		for (int i=0; i<markets.size(); i++){
			if (markets.get(i).inventory.get(f.type) > 0){
				Do("chose " + markets.get(i).getName() + " for " + f.type);
				return markets.get(i);
			}
		}
		return null;
	}*/
	
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
		person.Do("Done cooking " + o.choice);
		//o.s = OrderState.done;
		cg.DoPlateOrder(o.choice);
		//stateChanged();
	}
	public void NotifyWaiter(Order o){
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
	
	static class Food{
		private String type;
		private int amount;
		private int low=2;
		private int capacity=6;
		private FoodState state;
		private enum FoodState {enough, ordering, low};
		private Map<String, Integer> cookTime = new HashMap<String,Integer>();
		
		
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
