package simcity.Market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import simcity.PersonAgent;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.restaurant4.*;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CashierMock;
import simcity.restaurants.restaurant4.test.mock.Restaurant4CookMock;
import agent.Role;

public class MarketWorkerRole extends Role implements MarketWorker{
	private List<CustomerDelivery> deliveries = new ArrayList<CustomerDelivery>();
	private List<RestaurantDelivery> restDeliveries = new ArrayList<RestaurantDelivery>();
	public Map<String, Item> inventory = new HashMap<String, Item>();
	private MarketCashier cashier;
	private MarketWorkerGui workerGui;
//	private PersonAgent person;
	public String name;
	public Semaphore delivering = new Semaphore(0,true);
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
	
	public void Bring(List<Order> o){ //for customers
		System.out.println(myPerson.name+ " " +"Got new order from " + o.get(0).customer.getName());
		deliveries.add(new CustomerDelivery(o));
		stateChanged();
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
	
	public void SendFood(Map<String, Integer> things, Role role){
		myPerson.Do("Got new order from restaurant");
		restDeliveries.add(new RestaurantDelivery(things, role));
		stateChanged();
	}
	
	public void Brought(MarketCustomer c){
		delivering.release();
		System.out.println("Delivered for " );
		for (int i=0; i<deliveries.size(); i++){
			if (deliveries.get(i).customer == c){
				HandIn(deliveries.get(i));
				//delivery.state = Delivery.DeliveryState.delivering;
				stateChanged();
			}
		}
	}
	
	public void Sent(Role role){
		delivering.release();
		myPerson.Do("sent things to restaurant");
		for (int i=0; i<restDeliveries.size(); i++){
			if (restDeliveries.get(i).cookRole == role){
				   ((Restaurant4CookRole) restDeliveries.get(0).cookRole).HereIsYourFood(restDeliveries.get(i).foods);
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
	public void Bring(CustomerDelivery d){
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
		workerGui.DoSend(d.foods, d.cookRole);
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
		//Location
		public DeliveryState state;
		public enum DeliveryState {pending, getting, delivering, delivered};
		
		public RestaurantDelivery(Map<String, Integer> f, Role role){
			cookRole = role;
			foods = f;
			state = DeliveryState.pending;
		}
		
	}

	public void setGui(MarketWorkerGui Gui) {
		workerGui = Gui;
		
	}

}
