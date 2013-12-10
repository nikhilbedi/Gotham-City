package simcity.Market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map;


import Gui.ScreenFactory;

import agent.Role;
import simcity.Building;
import simcity.Market.MarketGui.MarketCashierGui;
import simcity.Market.MarketGui.MarketWorkerGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketWorker;

public class Market extends Building {
		private String CustomerRole;
	private List <Item> items = new ArrayList<Item>();
	MarketCashierRole marketCashier = new MarketCashierRole();
    MarketWorkerRole marketWorker = new MarketWorkerRole(); 
	    Item beef = new Item("Beef", 10.99, 10);
		Item chicken = new Item("Chicken", 8.99, 10);
		Item rice = new Item("Rice", 6.99, 10);
		Item potato = new Item("Potato", 5.99, 10);
	MarketCashierGui cashierGui = new MarketCashierGui(marketCashier,ScreenFactory.getMeScreen(this.getName()));
	MarketWorkerGui workerGui = new MarketWorkerGui(marketWorker,ScreenFactory.getMeScreen(this.getName()));
	
	
	public Market(String type, int entranceX, int entranceY, int guiX,
				int guiY) {
			super(type, entranceX, entranceY, guiX, guiY);
			marketCashier.setWorker(marketWorker);
			marketWorker.setCashier(marketCashier);
			setWeekdayHours(8, 24);
			setWeekendHours(0, 0);
			marketCashier.setGui(cashierGui);
			marketWorker.setGui(workerGui);
			// TODO Auto-generated constructor stub
			items.add(beef);
			items.add(chicken);
			items.add(rice);
			items.add(potato);
			Map<String, Role> jobs = Collections.synchronizedMap(new HashMap<String, Role>());
			jobs.put("marketCashier", marketCashier);
			jobs.put("marketWorker", marketWorker);
			setJobRoles(jobs);
			Item beef = new Item("Beef", 10.99, 100);
			Item chicken = new Item("Chicken", 8.99, 100);
			Item rice = new Item("Rice", 6.99, 100);
			Item potato = new Item("Potato", 5.99, 100);
			Item pizza = new Item("Pizza", 5.99, 100);
			Item salad = new Item("Salad", 3.99, 100);
			Item steak = new Item("Steak", 10.99, 100);
			marketCashier.getInventory().put("Beef", beef);
			marketCashier.getInventory().put("Chicken", chicken);
			marketCashier.getInventory().put("Rice", rice);
			marketCashier.getInventory().put("Potato", potato);
			marketCashier.getInventory().put("Pizza", pizza);
			marketCashier.getInventory().put("Salad", salad);
			marketCashier.getInventory().put("Steak", steak);
			marketWorker.getInventory().put("Beef", beef);
			marketWorker.getInventory().put("Chicken", chicken);
			marketWorker.getInventory().put("Rice", rice);
			marketWorker.getInventory().put("Potato", potato);
			marketWorker.getInventory().put("Pizza", pizza);
			marketWorker.getInventory().put("Salad", salad);
			marketWorker.getInventory().put("Steak", steak);
		}	
	
	public void setCashier(MarketCashierRole c){
		System.out.println("setting cashier");
		marketCashier = c;
	}
	
	public void setWorker(MarketWorkerRole w){
		marketWorker = w;
	}
	
	public MarketCashier getCashier(){
		return marketCashier ;
	}
	
	public MarketWorker getWorker(){
		return marketWorker;
	}
	public void addItems(){
		Item beef = new Item("Beef", 10.99, 100);
		Item chicken = new Item("Chicken", 8.99, 100);
		Item rice = new Item("Rice", 6.99, 100);
		Item potato = new Item("Potato", 5.99, 100);
		Item pizza = new Item("Pizza", 5.99, 100);
		Item salad = new Item("Salad", 3.99, 100);
		Item steak = new Item("Steak", 10.99, 100);
		marketCashier.getInventory().put("Beef", beef);
		marketCashier.getInventory().put("Chicken", chicken);
		marketCashier.getInventory().put("Rice", rice);
		marketCashier.getInventory().put("Potato", potato);
		marketCashier.getInventory().put("Pizza", pizza);
		marketCashier.getInventory().put("Salad", salad);
		marketCashier.getInventory().put("Steak", steak);
		marketWorker.getInventory().put("Beef", beef);
		marketWorker.getInventory().put("Chicken", chicken);
		marketWorker.getInventory().put("Rice", rice);
		marketWorker.getInventory().put("Potato", potato);
		marketWorker.getInventory().put("Pizza", pizza);
		marketWorker.getInventory().put("Salad", salad);
		marketWorker.getInventory().put("Steak", steak);

	}
	
	public Vector<String> getBuildingInfo(){
		Vector<String> info = new Vector<String>();
		info.add("Market");
		info.add("Created by: Miiiiiiikkkkkkaaaa!");
		info.add("this is even more info");
		return info;
	}
	
}
