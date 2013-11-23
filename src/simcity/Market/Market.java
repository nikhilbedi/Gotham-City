package simcity.Market;

import java.util.ArrayList;
import java.util.List;

import simcity.Building;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketWorker;

public class Market extends Building {
	
	private int locX;
	private int locY;
	private MarketCashier cashier;
	private String CustomerRole;
	private List <Item> items = new ArrayList<Item>();
	private MarketWorker worker; //later will be a list
	    Item beef = new Item("Beef", 10.99, 10);
		Item chicken = new Item("Chicken", 8.99, 10);
		Item rice = new Item("Rice", 6.99, 10);
		Item potato = new Item("Potato", 5.99, 10);
	
	public Market(String type, int entranceX, int entranceY, int guiX,
				int guiY) {
			super(type, entranceX, entranceY, guiX, guiY);
			// TODO Auto-generated constructor stub
			items.add(beef);
			items.add(chicken);
			items.add(rice);
			items.add(potato);
		}	
		
/*	public Market(){
		items.add(beef);
		items.add(chicken);
		items.add(rice);
		items.add(potato);
		//setLocations
	}*/
	
	public void setCashier(MarketCashier c){
		cashier = c;
	}
	
	public void addWorker(MarketWorker w){
		worker = w;
	}
	
	public void addItems(){
		cashier.getInventory().put("Beef", beef);
		cashier.getInventory().put("Chicken", chicken);
		cashier.getInventory().put("Rice", rice);
		cashier.getInventory().put("Potato", potato);
		worker.getInventory().put("Beef", beef);
		worker.getInventory().put("Chicken", chicken);
		worker.getInventory().put("Rice", rice);
		worker.getInventory().put("Potato", potato);
	}
	
}
