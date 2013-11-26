package simcity;

import java.util.*;

import simcity.Home.Home;
import simcity.Market.Market;
import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant4.Restaurant4;

public class TheCity {
	static List<String> homeList = new ArrayList<String>();
	static List<String> jobLocationList = new ArrayList<String>();
	static List<String> jobPositionList = new ArrayList<String>();
	static List<String> transportationList = new ArrayList<String>();
	static List<String> homeOwnerList = new ArrayList<String>();
	
	static ArrayList<Building> buildings = new ArrayList<Building>();
	static Building rest1;
	static Building house;
	static Building market;
	static Building bank;
	
	
	static{//populate person-specific elements
	homeList.add("None (Homeless Shelter)");
	homeList.add("Home 1");
	
	jobLocationList.add("No job");
	jobLocationList.add("Market");
	jobLocationList.add("Bank");
	jobLocationList.add("Restaurant 1");
	
	jobPositionList.add("This is a temp variable I still need to figure out how to make this list dynamic");
	
	transportationList.add("Walker");
	
	homeOwnerList.add("none");
	homeOwnerList.add("Home 1");
	}
	
	static{//populate Buildings
		house = new Home("House", 390, 590, 400, 600);
		house.setImagePath("/resources/Buildings/House.png");
		market = new Market("Market", 210, 110, 200, 100);
		market.setImagePath("/resources/Buildings/Market.png");
		bank = new Bank("Bank", 410, 110, 400, 100);
		bank.setImagePath("/resources/Buildings/Bank.png");
		rest1 = new Restaurant4("Restauarant 1", 610, 110, 600, 100);
		rest1.setImagePath("/resources/Buildings/Restaurant.png");

		buildings.add(house);
		buildings.add(market);
		buildings.add(bank);
		buildings.add(rest1);
		
		
		
	}
	public static List<String> getJobLocs(){
		return jobLocationList;
	}
	public static List<String> getPos(){
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
		if(s.equalsIgnoreCase("home")){
		return house;	
		}
		if(s.equalsIgnoreCase("market")){
		return market;	
		}
		if(s.equalsIgnoreCase("bank")){
		return bank;	
		}
		if(s.equalsIgnoreCase("rest1")){
		return rest1;	
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
		return (Home)house;
	}
	
}

