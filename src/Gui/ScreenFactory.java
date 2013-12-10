package Gui;


import java.util.*;

import simcity.TheCity;
import simcity.bank.bankAnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;
import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.restaurants.restaurant1.gui.RestaurantNikhilAnimationPanel;

import simcity.restaurants.restaurant5.gui.RestaurantHunterAnimationPanel;
import simcity.restaurants.restaurant3.gui.RestaurantEvanAnimationPanel;

import simcity.restaurants.restaurant2.gui.Restaurant2AnimationPanel;

import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4AnimationPanel;

import simcity.Home.gui.HomeAnimationPanel;
import simcity.bank.bankAnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;

public class ScreenFactory {

	static List<Screen> screenList = Collections.synchronizedList(new ArrayList<Screen>());

	public static MainScreen main;
	public static Screen market;
	
	public static Screen rest1; 
	public static Screen rest2; 
	public static Screen rest3; 
	public static Screen rest4; 
	public static Screen rest5; 

	public static Screen bank;
	public static Screen home;



	static{
		
		rest1 = new RestaurantNikhilAnimationPanel();
		
 		market = new MarketAnimationPanel();
 		
 		//FOR SOME WEIRD RESASON BANK MUST COME AFTER REST1?
 		bank = new bankAnimationPanel();
 		
		//home = new HomeAnimationPanel();
/*		rest2 = new Restaurant2AnimationPanel();
		rest3 = new RestaurantEvanAnimationPanel();
		rest4 = new Restaurant4AnimationPanel();
		rest5 = new RestaurantHunterAnimationPanel();*/

		
		
		
	
		screenList.add(rest1);
		screenList.add(market);
		screenList.add(bank);
		//screenList.add(home);
		/*screenList.add(rest2);
		screenList.add(rest3);
		screenList.add(rest4);
		screenList.add(rest5);

		
		
		*/
		
		TheCity.populate();//this ensures that all the buildings are allocataed before the mainScren is created

		main = new MainScreen();		
		screenList.add(main);

	}

	/*public ScreenFactory(){
		synchronized(screenList) {
			screenList.add(main);
			

			screenList.add(rest1);
			screenList.add(rest2);
			screenList.add(rest3);
			screenList.add(rest4);
			screenList.add(rest5);

			screenList.add(market);
			screenList.add(bank);
			screenList.add(home);

		}
	}*/

	public static MainScreen getMainScreen(){
		return main;
	}

	public static Screen getRestaurant(int i){//pass number of restaurant as parameter and get according Screen
		return screenList.get(i);

	}
	public static Screen getMarket(){
		return market;
	}
	public static Screen getBank(){
		return bank;
	}
	public static Screen getHome(){
		return home;
	}

	public static void updateScreens(){
		synchronized(screenList) {
			for (Screen s : screenList) {
				s.updateAgents();
			}
		}
	}

	
	public static Screen getMeScreen(String s){
		if(s.equalsIgnoreCase("City")){
			return main;
		}

		if(s.equalsIgnoreCase("Restaurant 1")){
			return rest1;
		}
		if(s.equalsIgnoreCase("Restaurant 2")){
			return rest2;
		}
		if(s.equalsIgnoreCase("Restaurant 3")){
			return rest3;
		}
		if(s.equalsIgnoreCase("Restaurant 4")){
			return rest4;
		}
		if(s.equalsIgnoreCase("Restaurant 5")){
			return rest5;

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
