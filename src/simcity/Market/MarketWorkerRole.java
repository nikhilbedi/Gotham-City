package simcity.Market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant4.*;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CashierMock;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CookMock;
import agent.Role;

public class MarketWorkerRole extends Role implements MarketWorker{
	private List<CustomerDelivery> deliveries = Collections.synchronizedList(new ArrayList<CustomerDelivery>());
	private List<RestaurantDelivery> restDeliveries = new ArrayList<RestaurantDelivery>();
	public Map<String, Item> inventory = new HashMap<String, Item>();
	private MarketCashier cashier;
	private MarketWorkerGui workerGui;
//	private PersonAgent person;
	public String name;
	public Semaphore delivering = new Semaphore(0,true);
	private List<MarketCustomer> waitingCustomers  = Collections.synchronizedList(new ArrayList<MarketCustomer>());
	String restaurantType;
	private Restaurant4CookRole restaurant4Cook;
//	private Restaurant1CookRole restaurant1Cook;
//	private Restaurant2CookRole restaurant2Cook;
//	private Restaurant3CookRole restaurant3Cook;
//	private Restaurant5CookRole restaurant5Cook;
	public MarketWorkerRole(PersonAgent p){
		super(p);
		name = p.name;
	}
	
	public MarketWorkerRole(){
		super();
	}
	
	public void setCashier(MarketCashier c){
		cashier = c;
	}
	
	public List<MarketCustomer> getWaitingCustomers(){
		return waitingCustomers;	
	}
	
	public void setCooks(){
		Restaurant4 r4 = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
		restaurant4Cook = (Restaurant4CookRole) r4.getCook();
		/*Restaurant4 r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
		restaurant4Cook = (Restaurant1CookRole) r1.getCook();
		Restaurant4 r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
		restaurant4Cook = (Restaurant2CookRole) r2.getCook();
		Restaurant4 r3 = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
		restaurant4Cook = (Restaurant4CookRole) r3.getCook();
		Restaurant4 r5 = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
		restaurant4Cook = (Restaurant5CookRole) r5.getCook();*/
	}
	
	public void Bring(List<Order> o){ //for customers
		System.out.println(myPerson.name+ " " +"Got new order from " + o.get(0).customer.getName());
		deliveries.add(new CustomerDelivery(o));
		waitingCustomers.add(o.get(0).customer); //hope will work, not tested
		stateChanged();
	}
	
	public void updateCustomerPositions(){
		synchronized(waitingCustomers){
		for(int i=0; i<waitingCustomers.size(); i++){
			int destination = 580;
			waitingCustomers.get(i).getCustomerGui().setDestination(destination);
			destination = destination - 30;
		}
		}
	}
	
	public List<CustomerDelivery> getCustomerDeliveries(){
		return deliveries;
	}
	
	public List<RestaurantDelivery> getRestaurantDeliveries(){
		return restDeliveries;
	}
	
	public Map<String, Item> getInventory(){
		return inventory;
	}
	
	public void SendFood(Map<String, Integer> things, Role role, Restaurant r){
		myPerson.Do("Got new order from restaurant");
		restDeliveries.add(new RestaurantDelivery(things, role, r));
		stateChanged();
	}
	
	public void Brought(MarketCustomer c){
		delivering.release();
		System.out.println("Delivered for " );
		for (int i=0; i<deliveries.size(); i++){
			if (deliveries.get(i).customer == c){
				HandIn(deliveries.get(i));
				waitingCustomers.remove(c); 
				if (!waitingCustomers.isEmpty()){
					updateCustomerPositions(); //hope will work
				}
				stateChanged();
			}
		}
	}
	
	//food is delivered
	public void Sent(Role role){
		setCooks();
		delivering.release();
		myPerson.Do("sent things to restaurant");
		for (int i=0; i<restDeliveries.size(); i++){
			if (restDeliveries.get(i).cookRole == role){
				//if else block with checking if the cook role equal to any of set restaurantcookroles
				   ((Restaurant4CookRole) restDeliveries.get(0).cookRole).HereIsYourFood(restDeliveries.get(i).foods);
				   
				   cashier.foodIsDelivered(restDeliveries.get(i).cookRole);
				restDeliveries.remove(restDeliveries.get(i));
				stateChanged();
			}
		}
	}
	
	public boolean pickAndExecuteAnAction(){
			for (CustomerDelivery delivery: deliveries){	
			 if (delivery.state == CustomerDelivery.DeliveryState.pending){
				delivery.state = CustomerDelivery.DeliveryState.getting;
				System.out.println(myPerson.name + ": " +"pending state " + delivery.customer.getName());
					Bring(delivery);
					return true;
			}	
		}
			
			for (RestaurantDelivery delivery: restDeliveries){
				if (delivery.state == RestaurantDelivery.DeliveryState.pending){
					delivery.state = RestaurantDelivery.DeliveryState.getting;
					Send(delivery);
					return true;
				}
			}
		workerGui.DefaultPos();
		return false;
	}
	//customer
	public void Bring(CustomerDelivery d){  // bring to customers
		workerGui.DoBring(d.customer);
		System.out.println(myPerson.name+ ": " +"Bringing things for " + d.customer.getName());
		try {
			delivering.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void HandIn(CustomerDelivery d){
		System.out.println(myPerson.name+ " " +"Giving stuff to " + d.customer.getName());
		Map<String, Integer> f= new HashMap<String, Integer>();
		for (int i=0; i<d.orders.size(); i++){
			f.put(d.orders.get(i).getChoice(), d.orders.get(i).getQuantity());
		}
		d.customer.HereIsYourStuff(f);
		deliveries.remove(d);
		stateChanged();
	}
	//restaurant
	public void Send(RestaurantDelivery d){
		
		myPerson.Do("Sending food to restaurant");
		workerGui.DoSend(d.foods, d.cookRole, d.rest);
		try {
			delivering.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static class CustomerDelivery{
		List<Order> orders = new ArrayList<Order>();
		MarketCustomer customer;
		public DeliveryState state;
		boolean d;
		public enum DeliveryState {pending, getting, delivering, delivered};
		
		
		public CustomerDelivery(List<Order> o){
			d = o.get(0).delivery;
			customer = o.get(0).customer;
			orders = o;
			state = DeliveryState.pending;
		}
	}

	public static class RestaurantDelivery{
		private Role cookRole;
		private Map<String, Integer> foods;
		private Restaurant rest;
		//Location
		public DeliveryState state;
		public enum DeliveryState {pending, getting, delivering, delivered};
		
		public RestaurantDelivery(Map<String, Integer> f, Role role, Restaurant r){
			cookRole = role;
			foods = f;
			rest = r;
			state = DeliveryState.pending;
		}	
	}

	public void setGui(MarketWorkerGui Gui) {
		workerGui = Gui;
	}


}
