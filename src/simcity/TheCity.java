package simcity;

import java.util.*;

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
	
	
	static{//populate Buildings
		//Changing from Home to Home 1 because now we have multiple buildings
		home = new Home("Home 1", 390, 590, 400, 600);
		home.setImagePath("/resources/Buildings/HouseDark2.png");
	
		
		market = new Market("Market", 500, 100, 500, 100);
		market.setImagePath("/resources/Buildings/MarketDark2.png");
		bank = new Bank("Bank", 350, 100, 350, 100);
		bank.setImagePath("/resources/Buildings/BankDark2.png");
		
		rest1 = new Restaurant1("Restaurant 1", 200, 100, 200, 100);
		rest1.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest2 = new Restaurant2("Restaurant 2", 50, 200, 50, 200);
		rest2.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest3 = new Restaurant3("Restaurant 3", 650, 200, 650, 200);
		rest3.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest4 = new Restaurant4("Restaurant 4", 50, 400, 50, 400);
		rest4.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest5 = new Restaurant5("Restaurant 5", 650, 400, 650, 400);
		rest5.setImagePath("/resources/Buildings/RestaurantDark2.png");

		buildings.add(home);
		buildings.add(market);
		buildings.add(bank);
		buildings.add(rest1);
		buildings.add(rest2);
		buildings.add(rest3);
		buildings.add(rest4);
		buildings.add(rest5);
		
		
		
	}
	
	static{//populate person-specific elements
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
		if(s.equalsIgnoreCase("Home 1")){
			return home;
		}

		return null;
	}
	
	
	
	public List<Restaurant> getRestaurantList(){
		List<Restaurant> tempcast = new ArrayList<Restaurant>();
		tempcast.add((Restaurant)rest1);
		return tempcast;
	}

	public List<Market> getMarketList(){
		List<Market> tempcast = new ArrayList<Market>();
		tempcast.add((Market)market);
		return tempcast;
	}

	public Bank getBank(){
		return (Bank)bank;
	}

	public Home getHomeHack(){
		//this is labeled as a Hack because technically there should be multiple homes
		return (Home)home;
	}
	
}

