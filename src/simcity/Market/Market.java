package simcity.Market;

import java.util.ArrayList;
import java.util.List;

import simcity.Building;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketWorker;

public class Market extends Building {
	
	private int locX;
	private int locY;
	private MarketCashierRole marketcashier;
	private String CustomerRole;
	private List <Item> items = new ArrayList<Item>();
	private MarketWorker marketworker; //later will be a list
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
	
	public void setCashier(MarketCashierRole c){
		System.out.println("setting cashier");
		marketcashier = c;
	}
	
	public void setWorker(MarketWorker w){
		marketworker = w;
	}
	
	public MarketCashier getCashier(){
		
		return marketcashier ;
	}
	
	public MarketWorker getWorker(){
		return marketworker;
	}
	public void addItems(){
		Item beef = new Item("Beef", 10.99, 100);
		Item chicken = new Item("Chicken", 8.99, 100);
		Item rice = new Item("Rice", 6.99, 100);
		Item potato = new Item("Potato", 5.99, 100);
		Item pizza = new Item("Pizza", 5.99, 100);
		Item salad = new Item("Salad", 3.99, 100);
		Item steak = new Item("Steak", 10.99, 100);
		marketcashier.getInventory().put("Beef", beef);
		marketcashier.getInventory().put("Chicken", chicken);
		marketcashier.getInventory().put("Rice", rice);
		marketcashier.getInventory().put("Potato", potato);
		marketcashier.getInventory().put("Pizza", pizza);
		marketcashier.getInventory().put("Salad", salad);
		marketcashier.getInventory().put("Steak", steak);
		marketworker.getInventory().put("Beef", beef);
		marketworker.getInventory().put("Chicken", chicken);
		marketworker.getInventory().put("Rice", rice);
		marketworker.getInventory().put("Potato", potato);
		marketworker.getInventory().put("Pizza", pizza);
		marketworker.getInventory().put("Salad", salad);
		marketworker.getInventory().put("Steak", steak);

	}
	
}
