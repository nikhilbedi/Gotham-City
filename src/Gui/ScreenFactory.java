package Gui;

import java.util.*;

//import simcity.Market.MarketGui.MarketAnimationPanel;
//import simcity.restaurants.restaurant1.gui.RestaurantNikhilAnimationPanel;
//import simcity.Home.gui.HomeAnimationPanel;
import bank.bankAnimationPanel;

public class ScreenFactory {
	List<Screen> screenList = new ArrayList<Screen>();



	Screen main = new Screen(1);

	Screen rest = new Screen(2);
	Screen market = new Screen(3);
	Screen bank = new Screen(4);
	Screen home = new Screen(5);
	//Screen rest = new RestaurantNikhilAnimationPanel();
	//Screen market = new MarketAnimationPanel();
	//Screen bank = new bankAnimationPanel();
	//Screen home = new HomeAnimationPanel();

	public ScreenFactory(){
		screenList.add(main);
		screenList.add(rest);
		screenList.add(market);
		screenList.add(bank);
		screenList.add(home);


		RoleGui gui1, gui2;


		//These are testing hacks
		/*gui1 = new RoleGuiTest("red");
                gui2 = new RoleGuiTest("notred");
                main.addGui(gui1);
                swap.addGui(gui2);*/
	}

	public Screen getCity(){
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
		for (Screen s : screenList) {
			s.updateAgents();
		}
	}
	public Screen getScreen(String s){
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

}

