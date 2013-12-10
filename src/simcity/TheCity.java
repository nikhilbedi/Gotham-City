package simcity;
import simcity.*;

import java.util.*;


import simcity.Home.Apartment;

import Gui.TimeBar;


import simcity.Home.Home;
import simcity.Market.Market;
import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant5.Restaurant5;

public class TheCity {

	//private static Vector<PersonAgent> people = new Vector<PersonAgent>();

	static List<String> homeList = new ArrayList<String>();
	static List<String> jobLocationList = new ArrayList<String>();
	static HashMap<String, Collection<String>> buildingJobsMap = new HashMap<String,Collection<String>>();
	static Vector<Vector<String>> jobPositionList = new Vector<Vector<String>>();
	static List<String> transportationList = new ArrayList<String>();
	static List<String> homeOwnerList = new ArrayList<String>();

	static ArrayList<Building> buildings = new ArrayList<Building>();
	static Building rest1, rest2, rest3, rest4, rest5;
	static Building home;
	static Building market;
	static Building bank;

	static Building apart;
	

	//Making timebar static so it can be updated by static city clock is this bad?
	public static TimeBar bar;


	public static void populate(){//populate Buildings
		bar = new TimeBar();
		bar.setVisible(true);


		//These are just added to make Evan happy
		//For reference, not putting them in anymore
		/*house3 = new Home("Home3", 190, 590, 200, 600);
           house3.setImagePath("/resources/Buildings/HouseDark2.png");
           house4 = new Home("Home4", 290, 590, 300, 600);
           house4.setImagePath("/resources/Buildings/HouseDark2.png");
           house5 = new Home("Home5", 490, 590, 500, 600);
           house5.setImagePath("/resources/Buildings/HouseDark2.png");
           house6 = new Home("Home6", 590, 590, 600, 600);
           house6.setImagePath("/resources/Buildings/HouseDark2.png");*/ 
	/*	home = new Home("Home", 390, 590, 400, 600);
		home.setImagePath("/resources/Buildings/HouseDark2.png");
		apart = new Apartment("Apartment 1", 590, 590, 600, 600);
		apart.setImagePath("/resources/Buildings/HouseDark.png");*/

		bank = new Bank("Bank", 400, 100, 400, 100);
		bank.setImagePath("/resources/Buildings/BankDark2.png");

		market = new Market("Market", 600, 100, 600, 100);
		market.setImagePath("/resources/Buildings/MarketDark2.png");
		
		rest1 = new Restaurant1("Restaurant 1", 200, 100, 200, 100);
		rest1.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest4 = new Restaurant4("Restaurant 4", 50, 410, 50, 410);
		rest4.setImagePath("/resources/Buildings/RestaurantDark2.png");
		
		
		rest5 = new Restaurant5("Restaurant 5", 730, 410, 730, 410);
		rest5.setImagePath("/resources/Buildings/RestaurantDark2.png");
	
/*		rest2 = new Restaurant2("Restaurant 2", 50, 210, 50, 210);
		rest2.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest3 = new Restaurant3("Restaurant 3", 730, 210, 730, 210);
		rest3.setImagePath("/resources/Buildings/RestaurantDark2.png");
*/



/*		

		*/
	//	buildings.add(home);
		buildings.add(bank);
		buildings.add(rest1);
		buildings.add(market);
		buildings.add(rest4);
		buildings.add(rest5);
		//buildings.add(apart);
/*		buildings.add(rest2);
		buildings.add(rest3);
		buildings.add(rest5);*/

	}

	static{//populate person-specific elements

		
	homeList.add("None (Homeless Shelter)");
	homeList.add("Home 1");
	homeList.add("Apartment 1");
	
	
	//populate lists
	jobLocationList.add("No job");
	for(Building b: buildings){
		jobLocationList.add(b.getName());
	}
	//populate building jobs map
	for(Building b: buildings){
		jobPositionList.add(b.getJobCollec());
		buildingJobsMap.put(b.getName(), b.getJobCollec());
	}
	
	/*jobLocationList.add("Market");
=======
		homeList.add("None (Homeless Shelter)");
		homeList.add("Home 1");


		//populate lists
		jobLocationList.add("No job");
		for(Building b: buildings){
			jobLocationList.add(b.getName());
		}
		//populate building jobs map
		for(Building b: buildings){
			jobPositionList.add(b.getJobCollec());
			buildingJobsMap.put(b.getName(), b.getJobCollec());
		}

		/*jobLocationList.add("Market");
>>>>>>> master
	jobLocationList.add("Bank");
	jobLocationList.add("Restaurant 1");*/

		//jobPositionList.add("This is a temp variable I still need to figure out how to make this list dynamic");

		transportationList.add("Walker");

		homeOwnerList.add("none");
		homeOwnerList.add("Home 1");
	}
	public static List<String> getJobLocs(){
		return jobLocationList;
	}
	public static Vector<Vector<String>> getPos(){
		return jobPositionList;
	}
	public static List<String> getTransportation(){
		return transportationList;
	}
	public static List<String> getHomes(){
		return homeList;
	}
	public static List<String> getProperty(){
		return homeOwnerList;
	}

	public static Building getBuildingFromString(String s){
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
		if(s.equalsIgnoreCase("Apartment 1")){
			return apart;
		}

		return null;
	}



	public static List<Market> getMarketList(){
		List<Market> tempcast = new ArrayList<Market>();
		tempcast.add((Market)market);

		return tempcast;
	}


	public static List<Restaurant> getRestaurantList(){
		List<Restaurant> tempcast = new ArrayList<Restaurant>();
		//maybe this populate should be done in the static constructor
		tempcast.add((Restaurant)rest1);
	/*	tempcast.add((Restaurant)rest2);
		tempcast.add((Restaurant)rest3);
		tempcast.add((Restaurant)rest4);
		tempcast.add((Restaurant)rest5);*/
		return tempcast;
	}


	public static Bank getBank(){
		return (Bank)bank;
	}

	public static Home getHomeHack(){
		//this is labeled as a Hack because technically there should be multiple homes
		return (Home)home;
	}
	public static ArrayList<Building> getBuildings(){
		return buildings;
	}
}

