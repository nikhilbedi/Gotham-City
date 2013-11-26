package Gui;


import java.util.*;

import simcity.bank.bankAnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;
import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.restaurants.restaurant1.gui.RestaurantNikhilAnimationPanel;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4AnimationPanel;
import simcity.Home.gui.HomeAnimationPanel;
import simcity.bank.bankAnimationPanel;


import simcity.Home.gui.HomeAnimationPanel;

public class ScreenFactory {

        List<Screen> screenList = Collections.synchronizedList(new ArrayList<Screen>());

    	public static MainScreen main = new MainScreen();


	//public static Screen main = new Screen(1);
	public static Screen rest = new Restaurant4AnimationPanel();
	//public static Screen market = new Screen(3);
	//public static Screen bank = new Screen(4);
	//public static Screen home = new Screen(5);
	
	//public static Screen rest = new RestaurantNikhilAnimationPanel();

	//public static Screen market = new MarketAnimationPanel();
	public static Screen market = new MarketAnimationPanel();
	public static Screen home = new HomeAnimationPanel();

	public static Screen bank = new bankAnimationPanel();
	public ScreenFactory(){
		synchronized(screenList) {
		screenList.add(main);
		screenList.add(rest);
		screenList.add(market);
		screenList.add(bank);
		screenList.add(home);
		}


		//These are testing hacks
		/*gui1 = new RoleGuiTest("red");

>>>>>>> master
                gui2 = new RoleGuiTest("notred");
                main.addGui(gui1);
                swap.addGui(gui2);*/
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
        public static MainScreen getMainScreen(){
                return main;
        }
        
        public static Screen getMeScreen(String s){
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
