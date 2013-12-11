package Gui;


import java.util.*;

import simcity.TheCity;
import simcity.bank.bankAnimationPanel;
import simcity.Home.gui.ApartmentAnimationPanel;
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
	public static Screen market1;

	
	public static Screen rest1; 
	public static Screen rest2; 
	public static Screen rest3; 
	public static Screen rest4; 
	public static Screen rest5; 

	public static Screen bank;
	public static Screen bank1;
	public static Screen home;
	public static Screen home1;
	public static Screen home2;
	public static Screen home3;
	public static Screen home4;
	public static Screen home5;
	public static Screen home6;
	public static Screen home7;
	public static Screen home8;
	public static Screen home9;
	public static Screen home10;
	public static Screen home11;
	public static Screen home12;
	public static Screen home13;
	public static Screen home14;
	public static Screen home15;
	public static Screen home16;
	public static Screen home17;
	public static Screen home18;
	public static Screen home19;
	public static Screen home20;
	public static Screen home21;
	public static Screen home22;
	public static Screen home23;
	public static Screen home24;
	public static Screen home25;
	public static Screen home26;
	public static Screen home27;
	public static Screen home28;
	public static Screen home29;
	public static Screen home30;
	public static Screen home31;
	public static Screen home32;
	public static Screen apart;
	public static Screen apart1;
	public static Screen apart2;
	public static Screen apart3;
//	public static Screen apart4;




	static{
		bank = new bankAnimationPanel();
		bank1 = new bankAnimationPanel();
		rest1 = new RestaurantNikhilAnimationPanel();
 		market = new MarketAnimationPanel();
 		market1 = new MarketAnimationPanel();
 		rest4 = new Restaurant4AnimationPanel();
 		rest5 = new RestaurantHunterAnimationPanel();		
 		rest2 = new Restaurant2AnimationPanel();
		home = new HomeAnimationPanel();
		home1 = new HomeAnimationPanel();
		home2 = new HomeAnimationPanel();
		home3 = new HomeAnimationPanel();
		home4 = new HomeAnimationPanel();
		home5 = new HomeAnimationPanel();
		home6 = new HomeAnimationPanel();
		home7 = new HomeAnimationPanel();
		home8 = new HomeAnimationPanel();
		home9 = new HomeAnimationPanel();
		home10 = new HomeAnimationPanel();
		home11 = new HomeAnimationPanel();
		home12 = new HomeAnimationPanel();
		home13 = new HomeAnimationPanel();
		home14 = new HomeAnimationPanel();
		home15 = new HomeAnimationPanel();
		home16 = new HomeAnimationPanel();
		home17 = new HomeAnimationPanel();
		home18 = new HomeAnimationPanel();
		home19 = new HomeAnimationPanel();
		home20 = new HomeAnimationPanel();
		home21 = new HomeAnimationPanel();
		home22 = new HomeAnimationPanel();
		home23 = new HomeAnimationPanel();
		home24 = new HomeAnimationPanel();
		home25 = new HomeAnimationPanel();
		home26 = new HomeAnimationPanel();
		home27 = new HomeAnimationPanel();
		home28 = new HomeAnimationPanel();
		home29 = new HomeAnimationPanel();
		home30 = new HomeAnimationPanel();
		home31 = new HomeAnimationPanel();
		home32 = new HomeAnimationPanel();
 		apart = new ApartmentAnimationPanel();
 		apart1 = new ApartmentAnimationPanel();
 		apart2 = new ApartmentAnimationPanel();
 		apart3 = new ApartmentAnimationPanel();
// 		apart4 = new ApartmentAnimationPanel();
		rest3 = new RestaurantEvanAnimationPanel();
	
	
		screenList.add(rest1);
		screenList.add(market);
		screenList.add(bank);
		screenList.add(market1);
		screenList.add(bank1);
		screenList.add(rest2);
		screenList.add(rest4);
		screenList.add(home);
		screenList.add(home1);
		screenList.add(home2);
		screenList.add(home3);
		screenList.add(home4);
		screenList.add(home5);
		screenList.add(home6);
		screenList.add(home7);
		screenList.add(home8);
		screenList.add(home9);
		screenList.add(home10);
		screenList.add(home11);
		screenList.add(home12);
		screenList.add(home13);
		screenList.add(home14);
		screenList.add(home15);
		screenList.add(home16);
		screenList.add(home17);
		screenList.add(home18);
		screenList.add(home19);
		screenList.add(home20);
		screenList.add(home21);
		screenList.add(home22);
		screenList.add(home23);
		screenList.add(home24);
		screenList.add(home25);
		screenList.add(home26);
		screenList.add(home27);
		screenList.add(home28);
		screenList.add(home29);
		screenList.add(home30);
		screenList.add(home31);
		screenList.add(home32);

		screenList.add(rest5);
		screenList.add(rest3);
		screenList.add(rest4);
		
		screenList.add(apart);
		screenList.add(apart1);
		screenList.add(apart2);
		screenList.add(apart3);
		/*
		screenList.add(apart);//can only be accessed statically for now

		
		
		*/
		
		TheCity.populate();//this ensures that all the buildings are allocataed before the mainScreen is created
		
		main = new MainScreen();		
		screenList.add(main);

	}

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
		if(s.equalsIgnoreCase("Market1")){
			return market1;
		}
		if(s.equalsIgnoreCase("Bank1")){
			return bank1;
		}
		if(s.equalsIgnoreCase("Home")){
			return home;
		}
		if(s.equalsIgnoreCase("Home1")){
			return home1;
		}
		if(s.equalsIgnoreCase("Home2")){
			return home2;
		}
		if(s.equalsIgnoreCase("Home3")){
			return home3;
		}
		if(s.equalsIgnoreCase("Home4")){
			return home4;
		}
		if(s.equalsIgnoreCase("Home5")){
			return home5;
		}
		if(s.equalsIgnoreCase("Home6")){
			return home6;
		}
		if(s.equalsIgnoreCase("Home7")){
			return home7;
		}
		if(s.equalsIgnoreCase("Home8")){
			return home8;
		}
		if(s.equalsIgnoreCase("Home9")){
			return home9;
		}
		if(s.equalsIgnoreCase("Home10")){
			return home10;
		}
		if(s.equalsIgnoreCase("Home11")){
			return home11;
		}
		if(s.equalsIgnoreCase("Home12")){
			return home12;
		}
		if(s.equalsIgnoreCase("Home13")){
			return home13;
		}
		if(s.equalsIgnoreCase("Home14")){
			return home14;
		}
		if(s.equalsIgnoreCase("Home15")){
			return home15;
		}
//		if(s.equalsIgnoreCase("Home16")){
//			return home;
//		}
		if(s.equalsIgnoreCase("Home17")){
			return home17;
		}
		if(s.equalsIgnoreCase("Home18")){
			return home18;
		}
		if(s.equalsIgnoreCase("Home19")){
			return home19;
		}
		if(s.equalsIgnoreCase("Home20")){
			return home20;
		}
		if(s.equalsIgnoreCase("Home21")){
			return home21;
		}
//		if(s.equalsIgnoreCase("Home22")){
//			return home;
//		}
//		if(s.equalsIgnoreCase("Home23")){
//			return home;
//		}
		if(s.equalsIgnoreCase("Home24")){
			return home24;
		}
		if(s.equalsIgnoreCase("Home25")){
			return home25;
		}
		if(s.equalsIgnoreCase("Home26")){
			return home26;
		}
		if(s.equalsIgnoreCase("Home27")){
			return home27;
		}
		if(s.equalsIgnoreCase("Home28")){
			return home28;
		}
		if(s.equalsIgnoreCase("Home29")){
			return home29;
		}
		if(s.equalsIgnoreCase("Home30")){
			return home30;
		}
		if(s.equalsIgnoreCase("Home31")){
			return home31;
		}
		if(s.equalsIgnoreCase("Home32")){
			return home32;
		}
//		if(s.equalsIgnoreCase("Home33")){
//			return home;
//		}
//		if(s.equalsIgnoreCase("Home34")){
//			return home;
//		}
		
		if(s.equalsIgnoreCase("Apartment 1")){
			return apart;
		}
		if(s.equalsIgnoreCase("Apartment 2")){
			return apart2;
		}
		if(s.equalsIgnoreCase("Apartment 3")){
			return apart3;
		}
//		if(s.equalsIgnoreCase("Apartment 4")){
//			return apart4;
//		}

		return null;
	}

		
}
