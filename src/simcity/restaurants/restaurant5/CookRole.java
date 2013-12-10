package simcity.restaurants.restaurant5;

import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.Item;
import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.Market;
import simcity.Market.MarketWorkerRole;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant4.Restaurant4CookRole.Food;
import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;
import trace.AlertLog;
import trace.AlertTag;

/**
 * Restaurant Cook Agent
 */
public class CookRole extends Role implements Cook{

	public List<Order> orders =  Collections.synchronizedList(new ArrayList<Order>());
	Timer timer = new Timer();
	private Semaphore moving = new Semaphore(0,true);

	//private List<Food> stock = new ArrayList<Food>();
	private List<MyMarket> markets = Collections.synchronizedList(new ArrayList<MyMarket>());
	public Map<String, Food> foodMap = new HashMap<String, Food>();
	public Map<String, Integer> neededFood = new HashMap<String, Integer>();
	private String name;

	//private List<String> neededFoods = new ArrayList<String>();
	private List<Integer> neededAmounts = new ArrayList<Integer>();

	Restaurant5 r5;
	private enum DeliveryState{
		none, ordered, needOrder
	}
	
	DeliveryState ds = DeliveryState.none;
	//boolean orderFood = false;
	//MarketState order = MarketState.free;

	private CookGui cookGui = null;

	public CookRole(String n) {
		Food steak = new Food("Steak", 5);
		Food chicken = new Food("Chicken", 4);
		Food salad = new Food("Salad", 2);
		Food pizza = new Food("Pizza", 3);

		/*stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);
		 */

		foodMap.put("Steak", new Food("Steak", 5));
		foodMap.put("Chicken", new Food("Chicken", 4));
		foodMap.put("Salad", new Food("Salad", 2));
		foodMap.put("Pizza", new Food("Pizza", 3));

		name = n;

	}

	// Messages

	public CookRole(PersonAgent cookPerson) {
		super(cookPerson);
		Food steak = new Food("Steak", 5);
		Food chicken = new Food("Chicken", 4);
		Food salad = new Food("Salad", 2);
		Food pizza = new Food("Pizza", 3);

		/*stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);
		 */

		foodMap.put("Steak", new Food("Steak", 5));
		foodMap.put("Chicken", new Food("Chicken", 4));
		foodMap.put("Salad", new Food("Salad", 2));
		foodMap.put("Pizza", new Food("Pizza", 3));
	}

	public CookRole() {
		super();
		Food steak = new Food("Steak", 5);
		Food chicken = new Food("Chicken", 4);
		Food salad = new Food("Salad", 2);
		Food pizza = new Food("Pizza", 3);

		/*stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);
		 */

		foodMap.put("Steak", new Food("Steak", 5));
		foodMap.put("Chicken", new Food("Chicken", 4));
		foodMap.put("Salad", new Food("Salad", 2));
		foodMap.put("Pizza", new Food("Pizza", 3));
	}

	//v2
	public void hereIsAnOrder(Waiter w, String choice, int table){//(order)
		print("Received order for " + choice + " from " + w.getName() + " at table " + table);
		orders.add(new Order(w,choice,table));
		stateChanged();
	}

	public void foodDone(Order o){//(order o)
		print(o.choice + " is finished cooking!");
		o.s = OrderState.cooked;
		stateChanged();
	}

	//v2.1
	public void hereIsFood(Market m, String s, int amount){
		print("Received " + amount + " " + s + " from " + m.getName());
		for (Map.Entry<String, Food> food : foodMap.entrySet())
		{
			if(food.getValue().type.equals(s)){
				food.getValue().amount += amount;
				food.getValue().fs = FoodState.stocked;
				//print("" + food.getValue().amount);
			}
		}
		for(MyMarket market : markets){
			if(market.m == m){
				market.ms = MarketState.idle;
			}
		}
		stateChanged();
	}

	public void hereIsSomeFood(Market m, String s, int amount){
		print("Market " + m.getName() + " could not deliver all food. Recieved " + amount + " " + s + "from " + m.getName());
		for (Map.Entry<String, Food> food : foodMap.entrySet())
		{
			if(food.getValue().type.equals(s)){
				food.getValue().amount += amount;
				food.getValue().fs = FoodState.partialStock;
				//print("Total " + food.getValue().type + " is " + food.getValue().amount);
			}
		}
		for(MyMarket market : markets){
			if(market.m == m){
				market.ms = MarketState.outOfStock;
			}
		}

		stateChanged();
	}
	
	public void HereIsYourFood(Map<String, Integer> m, MarketWorkerRole worker){ //from market
		//needFood = false;
		ds = DeliveryState.none;
		myPerson.Do("Sending market worker message that I got food");
		worker.Delivered(r5);
		for (Map.Entry<String, Integer> entry: m.entrySet()){
			Food f = foodMap.get(entry.getKey());
			f.amount = f.amount + entry.getValue();
			foodMap.put(entry.getKey(), f);
			myPerson.Do("Got order from market, now I have " + f.type + " " + f.amount);
		}
		
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		//print("Picking and excuting");
		if(theManLeavingMe != null && orders.isEmpty()) {
			leaveWork();
			return true;
		}
		synchronized(orders){
			for(Order o: orders){
				if(o.s == OrderState.pending){
					tryCookIt(o);
					return true;
				}
				if(o.s == OrderState.cooked){
					plateIt(o);
					return true;
				}
			}
		}
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{

			if(f.getValue().amount <= f.getValue().low ){
				if(ds != DeliveryState.ordered){
					int needed = 5;
					neededFood.put(f.getKey(), needed);
					ds = DeliveryState.needOrder;
				}
			}
		}
		if(ds == DeliveryState.needOrder){
			orderFood();
			ds = DeliveryState.ordered;
		}
		if(Stand.hasOrders()){
			checkStand();
			return true;
		}
		return false;
	}

	// Actions

	private void checkStand() {
		DoGoToStand();
		orders.add(Stand.popOrder());
	}

	

	private void tryCookIt(final Order o){//final hack allows timer to accesss order
		//DoCooking(o);
		print("trying to cook it");
		if(o.f.amount == 0){
			o.s = OrderState.invalid;
			handleOutOfFood(o);
			return;
		}
		o.s = OrderState.cooking;
		o.f.amount--;
		print("time is" );
		DoGoToGrill();
		timer.schedule(new TimerTask() {
			String foodName = o.choice;
			public void run() {
				print("Done cooking " + foodName);
				foodDone(o);
			}
		},

		(foodMap.get(o.choice).cookingTime*1000));//getHungerLevel() * 1000);//how long to wait before running task*/
	}

	private void plateIt(Order o){//order o
		//DoPlating(o)
		DoGoToPlating();
		o.waiter.hereIsAnOrder(this, o.choice, o.table);
		o.s = OrderState.sent;
		//TODO I'm going to remove the order from the list for you - Nikhil
		orders.remove(o);
	}

	private void DoGoToPlating() {
		cookGui.DoGoToPlating();
		try {
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}

	private void DoGoToGrill(){
		print("this far");
		cookGui.DoGoToGrill();
		try {
			print("acquiring");
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
	}
	
	private void DoGoToStand() {
		cookGui.DoGoToStand();
		try{
			moving.acquire();
		}
		catch(InterruptedException e) {	
		}
		
	}

	private void handleOutOfFood(Order o){
		Menu temp = new Menu();
		int bugCounter = 0;
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{

			if(f.getValue().amount == 0){
				temp.remove(f.getKey());
				bugCounter++;
			}
		}
		if(bugCounter == 4){
			temp = null;
		}
		o.waiter.outOfOrder(o.choice, o.table, temp);
		orders.remove(o);
	}

	private void orderFood(){
		Market m = (Market) TheCity.getBuildingFromString("Market"); // add one more market late
		r5 = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
		m.getCashier().INeedFood(neededFood, r5);
	}
	//animation stuff
	public void doneMoving(){
		moving.release();
	}

	//utilities

	public String returnAmounts(){
		String result = "Cook: ";
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{
			String temp = "error";
			if(f.getKey().equalsIgnoreCase("Steak")){
				temp = "ST";
			}
			else if(f.getKey().equalsIgnoreCase("Chicken")){
				temp = "CK";
			}
			else if(f.getKey().equalsIgnoreCase("Pizza")){
				temp = "PZ";
			}
			else if(f.getKey().equalsIgnoreCase("Salad")){
				temp = "SL";
			}
			else{
				temp = "failed";
			}
			result += temp + ":" + f.getValue().amount + " ";
		}
		return result;

	}

	public void addMarket(Market m){
		MyMarket temp = new MyMarket(m);
		markets.add(temp);
	}

	public void setGui(RoleGui gui) {
		super.setGui(gui);
		cookGui = (CookGui) gui;
	}

	public CookGui getGui() {
		return cookGui;
	}

	public String getName(){
		return name;
	}

	public Vector<Item> getInventory(){
		Vector<Item> inventory = new Vector<Item>();
		for (Map.Entry<String, Food> f : foodMap.entrySet())//check food amount
		{
			inventory.add(new Item(f.getKey(), f.getValue().amount));
			/*AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
					"Key: " + f.getKey() + " Value: " + f.getValue());*/
		}
	/*	AlertLog.getInstance().logInfo(AlertTag.GUI, "CookRole",
				inventory.toString());*/
		return inventory;
	}

	private enum MarketState
	{idle, ordered, outOfStock, busy, free};

	private enum OrderState{
		pending,cooking,cooked,sent, invalid};

		private enum FoodState{
			stocked, ordering, partialStock};

			private class MyMarket{
				Market m;
				MarketState ms = MarketState.idle;
				public MyMarket(Market market){
					m = market;
				}

			}
			public class Order{
				Waiter waiter;
				String choice;
				int table;
				OrderState s;
				Food f;
				public Order(Waiter w, String c, int t){
					waiter = w;
					choice = c;
					table = t;
					f = foodMap.get(c);//maybe alter this
					s = OrderState.pending;
				}
			}
			private class Food{
				String type;
				int cookingTime;
				int amount;
				public Food(String n, int i){
					type = n;
					cookingTime = i;
					amount = 5;
				}
				int low = 2;
				FoodState fs = FoodState.stocked;

			}
			public void updateItem(String s, int hashCode) {
				foodMap.get(s).amount = hashCode;
			}

			public void notifyOrderAvailable() {
				// TODO Auto-generated method stub
				
			}
}

