package simcity.restaurants.restaurant5;

import agent.Agent;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Collections;
import java.util.Timer;
import java.util.Map;
import java.util.TimerTask;

import simcity.restaurants.restaurant5.interfaces.*;
import simcity.restaurants.restaurant5.gui.*;
/**
 * Restaurant Cook Agent
 */
public class MarketRole extends Role implements Market {

	private List<Food> stock = new ArrayList<Food>();
	private String name;

	Timer timer = new Timer();


	private Cook cook;
	private Cashier cashier;

	private MyCook mc;
	//private CookGui cookGui = null;

	public MarketRole(String n, Cook c, Cashier cash) {
		Food steak = new Food("Steak");
		Food chicken = new Food("Chicken");
		Food salad = new Food("Salad");
		Food pizza = new Food("Pizza");

		stock.add(steak);
		stock.add(chicken);
		stock.add(salad);
		stock.add(pizza);

		cashier = cash;
		cook = c;
		mc = new MyCook(c);
		name = n;
	}

	// Messages

	public void iNeedFood(Cook ref, String s, int amount){
		print("Received request for " + amount + " " + s + " from " + ref.getName());
		mc.foodNeeded = s;
		mc.foodAmountWanted = amount;
		mc.cs = CookState.needFood;
		stateChanged();
	}
	
	//v2.2

	public void hereIsPayment(Cashier c, double payment) {
		print("Recieved payment of $" + payment + " from " + c.getName());
		
	}

	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	public boolean pickAndExecuteAnAction() {
		if(mc.cs == CookState.needFood){
			mc.cs = CookState.ordered;
			//print(mc.neededFoods.toString());
			processFoodOrder(mc);
			return true;
		}
		return false;

		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}


	// Actions
	private void processFoodOrder(final MyCook mc){
		timer.schedule(new TimerTask() {
			public void run() {
				tryFillOrder(mc);
			}
		},(5000));

	}


	private void tryFillOrder(MyCook mc){//used in single item order implementation
		double bill = 0.0;
		for(Food food: stock){
			if(food.type.equals(mc.foodNeeded)){
				if(food.amount >= mc.foodAmountWanted){
					mc.c.hereIsFood(this, mc.foodNeeded, mc.foodAmountWanted);
					bill += food.price*mc.foodAmountWanted;
					mc.cs = CookState.waiting;
					food.amount -= mc.foodAmountWanted;
				}
				else{
					bill += food.amount * food.price;
					mc.c.hereIsSomeFood(this, mc.foodNeeded, food.amount);
					mc.cs = CookState.waiting;
					food.amount = 0;
				}
			}
		}
		cashier.hereIsMarketBill(this, bill);
	}

	//utilites


	public String getFoodAmounts(){
		String result = name + ": ";
		for(Food f: stock)
		{
			String temp = "error";
			if(f.type.equalsIgnoreCase("Steak")){
				temp = "ST";
			}
			else if(f.type.equalsIgnoreCase("Chicken")){
				temp = "CK";
			}
			else if(f.type.equalsIgnoreCase("Pizza")){
				temp = "PZ";
			}
			else if(f.type.equalsIgnoreCase("Salad")){
				temp = "SL";
			}
			else{
				temp = "failed";
			}
			result += temp + ":" + f.amount + " ";
		}
		return result;
	}

	public String getName(){
		return name;
	}

	private enum CookState
	{waiting, needFood, ordered};

	private class MyCook{
		Cook c;
		CookState cs = CookState.waiting;
		List<String> neededFoods;
		List<Integer> neededAmounts;
		List<Integer> givenAmounts;
		String foodNeeded;
		int foodAmountWanted;
		public MyCook(Cook cook){
			c = cook;
		}
	}

	private class Food{
		String type;
		double price;
		public Food(String n){
			type = n;
			if(type.equals("Steak")){
				price = 5.50;
			}
			if(type.equals("Chicken")){
				price = 3.50;
			}
			if(type.equals("Salad")){
				price = 2.00;
			}
			if(type.equals("Pizza")){
				price = 3.00;
			}
		}
		int amount = 13;


	}

}

