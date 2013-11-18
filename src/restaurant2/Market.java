package restaurant2;

import java.util.HashMap;

import agent.Agent;
import agent.Role;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import restaurant2.interfaces.Cashier;
import restaurant2.interfaces.Cook;

public class Market extends Role{
List<DeliveryOrder> deliveries = Collections.synchronizedList(new LinkedList<DeliveryOrder>());
	
	//Map <String, Food> foods = new HashMap<String, Food>();
	Inventory inventory;
	private String name;
	//Timer timer = new Timer();
	//public HostGui hostGui = null;
	public Cook cook = null;
	public Cashier cashier = null;
	Timer deliveryTimer = new Timer();
	
	public Market(String name) {
		super();
		
		this.name = name;
	}
	
	public void setCook(Cook cook2) {
		cook = cook2;
	}
	
	public void setCashier(Cashier c) {
		cashier = c;
	}
	
	public void setInventory(int stNum, int piNum, int chNum, int saNum) {
		inventory = new Inventory(stNum, piNum, chNum, saNum);
	}
	
	public String getName() {
		return name;
	}
	
	// Messages
	
	public void msgNeedDelivery(List<Integer> order) {
		deliveries.add(new DeliveryOrder(order));
		print("Recieved order for delivery.");
		stateChanged();
	}
	
	/**
	 * Scheduler.  Determine what action is called for, and do it.
	 */
	protected boolean pickAndExecuteAnAction() {
		/* Think of this next rule as:
            Does there exist a table and customer,
            so that table is unoccupied and customer is waiting.
            If so seat him at the table.
		 */
		
		synchronized(deliveries) {
			for (int x = 0; x < deliveries.size(); x++) {
				if (deliveries.get(x).s == DeliveryState.pending) {
					CheckForItems(x);
				}
			}
		}
		
		synchronized(deliveries) {
			for (int x = 0; x < deliveries.size(); x++) {
				if (deliveries.get(x).s == DeliveryState.readyToDeliver) {
					DeliverOrder(x);
				}
			}
		}
		
		synchronized(deliveries) {
			for (int x = 0; x < deliveries.size(); x++) {
				if (deliveries.get(x).s == DeliveryState.lowInventory) {
					SendInventoryReport(x);
				}
			}
		}
		
		//synchronized(deliveries) {
			//for (int x = 0; x < deliveries.size(); x++) {
				//if (deliveries.get(x).s == DeliveryState.delivered) {
				//	deliveries.remove(x);
				//}
		//	}
		//}
		
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
	}
	
	// Actions
	
	private void DeliverOrder(int x) {
		final int index = x;
		deliveries.get(index).s = DeliveryState.delivering;
		cashier.msgHereIsDeliveryCheck(new Check(calculateCheckBill(x)), this);
		deliveryTimer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				print("Delivering order!, cookie=" + cookie);
				cook.msgHereIsDelivery(deliveries.get(index).foodsToDeliver);
				deliveries.get(index).s = DeliveryState.delivered;
				//deliveries.remove(index);
				stateChanged();
			}
		},
		5000);
		
		//stateChanged();
	}

	private double calculateCheckBill(int index) {
		double total = 0;
		
		total += inventory.foodPrices.get("Steak") * deliveries.get(index).foodsToDeliver.get("Steak");
		total += inventory.foodPrices.get("Pizza") * deliveries.get(index).foodsToDeliver.get("Pizza");
		total += inventory.foodPrices.get("Chicken") * deliveries.get(index).foodsToDeliver.get("Chicken");
		total += inventory.foodPrices.get("Salad") * deliveries.get(index).foodsToDeliver.get("Salad");
		
		return total;
	}

	private void SendInventoryReport(int deliveryIndex) {
		print("We cannot fulfill the entire order.");
		print("Missing the following: ");
		if(deliveries.get(deliveryIndex).getInventoryDifference().get(0) < 0) { //Can be shortened******************
			print("Steak: " + Math.abs(deliveries.get(deliveryIndex).getInventoryDifference().get(0)));
		}
		
		if(deliveries.get(deliveryIndex).getInventoryDifference().get(1) < 0) {
			print("Pizza: " + Math.abs(deliveries.get(deliveryIndex).getInventoryDifference().get(1)));
			
		}
		
		if(deliveries.get(deliveryIndex).getInventoryDifference().get(2) < 0) {
			print("Chicken: " + Math.abs(deliveries.get(deliveryIndex).getInventoryDifference().get(2)));
			
		}
		
		if(deliveries.get(deliveryIndex).getInventoryDifference().get(3) < 0) {
			print("Salad: " + Math.abs(deliveries.get(deliveryIndex).getInventoryDifference().get(3)));
			
		}
		
		cook.msgHereIsInventoryReport(deliveries.get(deliveryIndex).getInventoryDifference());
		deliveries.get(deliveryIndex).s = DeliveryState.readyToDeliver;
	}
	
	private void CheckForItems(int deliveryIndex) {
		List<Integer> delivery = deliveries.get(deliveryIndex).deliveryFoods;
		
		deliveries.get(deliveryIndex).setInventoryDifference();
		deliveries.get(deliveryIndex).setFoodsToDeliver();
		
		if(delivery.get(0) <= inventory.getAmount("Steak") && //Can be shortened******************************
			delivery.get(1) <= inventory.getAmount("Pizza") &&
			delivery.get(2) <= inventory.getAmount("Chicken") &&
			delivery.get(3) <= inventory.getAmount("Salad")) {
				deliveries.get(deliveryIndex).s = DeliveryState.readyToDeliver;
		}
		else {
			deliveries.get(deliveryIndex).s = DeliveryState.lowInventory;
		}
		
		stateChanged();
	}
	
	//utilities
	
	class DeliveryOrder {
		Map<String, Integer> foodsToDeliver = Collections.synchronizedMap(new HashMap<String, Integer>());
		List<Integer> deliveryFoods = Collections.synchronizedList(new LinkedList<Integer>());
		List<Integer> InventoryDifference = Collections.synchronizedList(new LinkedList<Integer>());
		DeliveryState s;
		
		public DeliveryOrder(List<Integer> deliveryOrder) {
			deliveryFoods = deliveryOrder;
			
			//waiter = w;
			//choice = c;
			//table = t;
			
			s = DeliveryState.pending;
		}
		
		public void setInventoryDifference() {
			InventoryDifference.add(new Integer(inventory.getAmount("Steak") - deliveryFoods.get(0)));
			InventoryDifference.add(new Integer(inventory.getAmount("Pizza") - deliveryFoods.get(1)));
			InventoryDifference.add(new Integer(inventory.getAmount("Chicken") - deliveryFoods.get(2)));
			InventoryDifference.add(new Integer(inventory.getAmount("Salad") - deliveryFoods.get(3)));
		}
		
		List<Integer> getInventoryDifference() {
			return InventoryDifference;
		}
		
		void setFoodsToDeliver() {
			if(InventoryDifference.get(0) >= 0)
				foodsToDeliver.put("Steak", deliveryFoods.get(0));
			else
				foodsToDeliver.put("Steak", 0);
				//foodsToDeliver.put("Steak", deliveryFoods.get(0) - inventory.getAmount("Steak"));
			
			if(InventoryDifference.get(1) >= 0)
				foodsToDeliver.put("Pizza", deliveryFoods.get(1));
			else
				foodsToDeliver.put("Pizza", 0);
				//foodsToDeliver.put("Pizza", deliveryFoods.get(1) - inventory.getAmount("Pizza"));
			
			if(InventoryDifference.get(2) >= 0)
				foodsToDeliver.put("Chicken", deliveryFoods.get(2));
			else
				foodsToDeliver.put("Chicken", 0);
				//foodsToDeliver.put("Chicken", deliveryFoods.get(2) - inventory.getAmount("Chicken"));
			
			if(InventoryDifference.get(3) >= 0)
				foodsToDeliver.put("Salad", deliveryFoods.get(3));
			else
				foodsToDeliver.put("Salad", 0);
				//foodsToDeliver.put("Salad", deliveryFoods.get(3) - inventory.getAmount("Salad"));
		}
	}
	
	public enum DeliveryState {pending, delivering, readyToDeliver, lowInventory, delivered}; 
	
	class Inventory {
		String type;
		Map<String, Integer> foods = Collections.synchronizedMap(new HashMap<String, Integer>());
		Map<String, Double> foodPrices = Collections.synchronizedMap(new HashMap<String, Double>());
		FoodState s;
		
		public Inventory(int steakNum, int pizzaNum, int chickenNum, int saladNum) {
			
			foods.put("Steak", steakNum);
			foods.put("Pizza", pizzaNum);
			foods.put("Chicken", chickenNum);
			foods.put("Salad", saladNum);
			
			foodPrices.put("Steak", 10.99);
			foodPrices.put("Pizza", 6.99);
			foodPrices.put("Chicken", 8.99);
			foodPrices.put("Salad", 3.99);
			
			s = FoodState.notOrdered;
		}
		
		public int getAmount(String type) {
				return foods.get(type);
		}
	}
	public enum FoodState {notOrdered, ordered};
}
