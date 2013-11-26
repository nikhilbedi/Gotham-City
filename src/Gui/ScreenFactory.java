package Gui;


import java.util.*;

import simcity.bank.bankAnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;
import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.restaurants.restaurant1.gui.RestaurantNikhilAnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;
import simcity.bank.bankAnimationPanel;


import simcity.Home.gui.HomeAnimationPanel;

public class ScreenFactory {
	static List<Screen> screenList = Collections.synchronizedList(new ArrayList<Screen>());

	//public static Screen main = new Screen(1);
	//public static Screen rest = new Screen(2);
	//public static Screen market = new Screen(3);
	//public static Screen bank = new Screen(4);
	//public static Screen home = new Screen(5);

	public static MainScreen main = new MainScreen();

	public static Screen rest = new RestaurantNikhilAnimationPanel();

	//public static Screen rest = new RestaurantNikhilAnimationPanel();
	//public static Screen market = new MarketAnimationPanel();
	public static Screen market = new MarketAnimationPanel();
	public static Screen home = new HomeAnimationPanel();

	public static Screen bank = new bankAnimationPanel();


	static{
		screenList.add(main);
		screenList.add(rest);
		screenList.add(market);
		screenList.add(bank);
		screenList.add(home);
	}

	public ScreenFactory(){
		synchronized(screenList) {
			screenList.add(main);
			screenList.add(rest);
			screenList.add(market);
			screenList.add(bank);
			screenList.add(home);

		}
	}



	public MainScreen getCity(){
		return main;
	}
	public Screen getRestaurant(){
		return rest;
	}
	public Screen getMarket(){
		return market;
	}
	public Screen getBank(){
		return bank;
	}
	public Screen getHome(){
		return home;
	}

	public void updateAllPositions(){
		synchronized(screenList) {
			for (Screen s : screenList) {
				s.updateAgents();
			}
		}
	}
	public static void updateScreens(){
		synchronized(screenList) {
			for (Screen s : screenList) {
				s.updateAgents();
			}
		}
	}
	public Screen getScreen(String s){//requires instance of screenFactory to use
		if(s.equalsIgnoreCase("City")){
			return main;
		}
		if(s.equalsIgnoreCase("Restaurant")){
			return rest;
		}
		if(s.equalsIgnoreCase("Market")){
			return market;
		}
		if(s.equalsIgnoreCase("Bank")){
			return bank;
		}
		if(s.equalsIgnoreCase("Home")){
			return home;
		}

		return null;
	}

	public static MainScreen getMainScreen(){
		return main;
	}

	public static Screen getMeScreen(String s){
		if(s.equalsIgnoreCase("City")){
			return main;
		}
		if(s.equalsIgnoreCase("Restaurant 1")){
			System.out.println("Here");
			return rest;
		}
		if(s.equalsIgnoreCase("Market")){
			return market;
		}
		if(s.equalsIgnoreCase("Bank")){
			return bank;
		}
		if(s.equalsIgnoreCase("Home")){
			return home;
		}
		return null;
	}
}
