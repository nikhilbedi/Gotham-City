package simcity.Market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.MarketGui.MarketCashierGui;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.*;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CashierMock;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CookMock;
import Gui.RoleGui;
import simcity.restaurants.restaurant5.Restaurant5;
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
	Restaurant1 r1;
	Restaurant2 r2;
	Restaurant3 r3;
	Restaurant4 r4;
	Restaurant5 r5;
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
	
	public void SendFood(Map<String, Integer> things, Restaurant r){
		myPerson.Do("Got new order from restaurant");
		restDeliveries.add(new RestaurantDelivery(things, r));
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
	public void Sent(Role role){  //gui sends this
		delivering.release();
		myPerson.Do("sent things to restaurant");
	}
	
	public void Delivered(Restaurant r){ //truck sends this
		r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
		r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
		r3 = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
		r4 = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
		r5 = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
		for (int i=0; i<restDeliveries.size(); i++){
		if (restDeliveries.get(i).rest == r){
			myPerson.Do("Telling cashier that truck delivered food");
			if (r4 == r){
				 cashier.foodIsDelivered(r4);
			     restDeliveries.remove(restDeliveries.get(i));
			}
			else if (r1 == r){
				 cashier.foodIsDelivered(r1);
			     restDeliveries.remove(restDeliveries.get(i));
			}
			else if (r2 == r){
				 cashier.foodIsDelivered(r2);
			     restDeliveries.remove(restDeliveries.get(i));
			}
			else if (r3 == r){
				 cashier.foodIsDelivered(r3);
			     restDeliveries.remove(restDeliveries.get(i));
			}
			else if (r5 == r){
				 cashier.foodIsDelivered(r5);
			     restDeliveries.remove(restDeliveries.get(i));
			}
			  
			stateChanged();
		}
	}
	}
	
	public void RestaurantIsClosed(Restaurant r){
		myPerson.Do(r + " is closed");
		//after some time send it again or check when it is open 
		
		
		
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
		workerGui.DoSend(d.foods, d.rest);
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
		
		public RestaurantDelivery(Map<String, Integer> f,  Restaurant r){
			//cookRole = role;
			foods = f;
			rest = r;
			state = DeliveryState.pending;
		}	
	}

	public void setGui(RoleGui gui){
		super.setGui(gui);
		workerGui = (MarketWorkerGui)gui;
	}


	

}
