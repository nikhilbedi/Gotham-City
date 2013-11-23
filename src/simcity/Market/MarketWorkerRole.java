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
import simcity.Restaurant4.*;
import agent.Role;

public class MarketWorkerRole extends Role implements MarketWorker{
	private List<CustomerDelivery> deliveries = new ArrayList<CustomerDelivery>();
	private List<RestaurantDelivery> restDeliveries = new ArrayList<RestaurantDelivery>();
	public Map<String, Item> inventory = new HashMap<String, Item>();
	private MarketCashier cashier;
	private MarketWorkerGui workerGui;
	private PersonAgent person;
	public String name;
	private Semaphore delivering = new Semaphore(0,true);
	public MarketWorkerRole(PersonAgent p){
		super(p);
		person = p;
		name = person.name;
	}
	
	public void setCashier(MarketCashier c){
		cashier = c;
	}
	
	public void Bring(List<Order> o){ //for customers
		System.out.println(person.name+ " " +"Got new order from " + o.get(0).customer.getName());
		deliveries.add(new CustomerDelivery(o));
		stateChanged();
	}
	
	public Map<String, Item> getInventory(){
		return inventory;
	}
	
	public void SendFood(Map<String, Integer> things, Role role){
		person.Do("Got new order from restaurant");
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
		person.Do("sent things to restaurant");
		for (RestaurantDelivery delivery: restDeliveries){
			if (delivery.cookRole == role){
				   ((Restaurant4CookRole) delivery.cookRole).HereIsYourFood(delivery.foods);
				restDeliveries.remove(delivery);
				stateChanged();
			}
		}
	}
	
	public boolean pickAndExecuteAnAction(){
			for (CustomerDelivery delivery: deliveries){	
			 if (delivery.state == CustomerDelivery.DeliveryState.pending){
				delivery.state = CustomerDelivery.DeliveryState.getting;
				System.out.println(person.name + ": " +"pending state " + delivery.customer.getName());
				if (delivery.d ==false){
					Bring(delivery);
				}
				return true;
			}	
		}
			for (RestaurantDelivery delivery: restDeliveries){
				if (delivery.state == RestaurantDelivery.DeliveryState.pending){
					delivery.state = RestaurantDelivery.DeliveryState.getting;
					Send(delivery);
				}
			}
			
			
		workerGui.DefaultPos();
		return false;
	}
	//customer
	public void Bring(CustomerDelivery d){
		workerGui.DoBring(d.customer);
		System.out.println(person.name+ ": " +"Bringing things for " + d.customer.getName());
		try {
			delivering.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void HandIn(CustomerDelivery d){
		System.out.println(person.name+ " " +"Giving stuff to " + d.customer.getName());
		d.customer.HereIsYourStuff(d.orders);
		deliveries.remove(d);
		stateChanged();
	}
	//restaurant
	public void Send(RestaurantDelivery d){
		person.Do("Sending food to restaurant");
		workerGui.DoSend(d.foods, d.cookRole);
		try {
			delivering.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static class CustomerDelivery{
		List<Order> orders = new ArrayList<Order>();
		MarketCustomer customer;
		DeliveryState state;
		boolean d;
		enum DeliveryState {pending, getting, delivering, delivered};
		
		
		public CustomerDelivery(List<Order> o){
			d = o.get(0).delivery;
			customer = o.get(0).customer;
			orders = o;
			state = DeliveryState.pending;
		}
	}

	static class RestaurantDelivery{
		private Role cookRole;
		private Map<String, Integer> foods;
		//Location
		DeliveryState state;
		enum DeliveryState {pending, getting, delivering, delivered};
		
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
