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
import agent.Role;

public class MarketWorkerRole extends Role implements MarketWorker{
	private List<Delivery> deliveries = new ArrayList<Delivery>();
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
	
	public void Bring(List<Order> o){
		System.out.println(person.name+ " " +"Got new order from " + o.get(0).customer.getName());
		deliveries.add(new Delivery(o));
		stateChanged();
	}
	
	public Map<String, Item> getInventory(){
		return inventory;
	}
	
	public void Brought(MarketCustomer c){
		delivering.release();
		System.out.println(person.name+ " " +"Delivered for " + c.getName());
		for (int i=0; i<deliveries.size(); i++){
			if (deliveries.get(i).customer == c){
				HandIn(deliveries.get(i));
				//delivery.state = Delivery.DeliveryState.delivering;
				stateChanged();
			}
		}
	}
	
	public boolean pickAndExecuteAnAction(){
			for (Delivery delivery: deliveries){	
			 if (delivery.state == Delivery.DeliveryState.pending){
				delivery.state = Delivery.DeliveryState.getting;
				System.out.println(person.name + ": " +"pending state " + delivery.customer.getName());
				Bring(delivery);
				return true;
			}	
		}
		workerGui.DefaultPos();
		return false;
	}
	
	public void Bring(Delivery d){
		workerGui.DoBring(d.customer);
		System.out.println(person.name+ ": " +"Bringing things for " + d.customer.getName());
		try {
			delivering.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void HandIn(Delivery d){
		System.out.println(person.name+ " " +"Giving stuff to " + d.customer.getName());
		d.customer.HereIsYourStuff(d.orders);
		deliveries.remove(d);
		stateChanged();
	}
	
	static class Delivery{
		List<Order> orders = new ArrayList<Order>();
		MarketCustomer customer;
		DeliveryState state;
		enum DeliveryState {pending, getting, delivering, delivered};
		
		
		public Delivery(List<Order> o){
			customer = o.get(0).customer;
			orders = o;
			state = DeliveryState.pending;
		}
	}

	public void setGui(MarketWorkerGui Gui) {
		workerGui = Gui;
		
	}
}
