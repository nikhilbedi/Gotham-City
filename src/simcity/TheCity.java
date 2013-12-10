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
	static HashMap<String, Vector<String>> buildingJobsMap = new HashMap<String,Vector<String>>();
	static Vector<Vector<String>> jobPositionList = new Vector<Vector<String>>();
	static List<String> transportationList = new ArrayList<String>();
	static List<String> homeOwnerList = new ArrayList<String>();

	static ArrayList<Building> buildings = new ArrayList<Building>();
	static Building rest1, rest2, rest3, rest4, rest5;
	static Building home, home1, home2, home3, home4;
	static Building market;
	static Building bank;

	static Building apart;

	static final int GRIDWIDTH = 40;
	static final int GRIDHEIGHT = 40;

	static Character[][] grid  = new Character[GRIDWIDTH][GRIDHEIGHT];

	//Making timebar static so it can be updated by static city clock is this bad?
	public static TimeBar bar;


	public static void populate(){//populate Buildings


		home = new Home("Home", 360, 700, 350, 720);
		home.setImagePath("/resources/Buildings/HouseDark2.png");

		home1 = new Home("Home1", 260, 700, 250, 720);
		home1.setImagePath("/resources/Buildings/HouseDark2.png");
		home2 = new Home("Home2", 160, 700, 150, 720);
		home2.setImagePath("/resources/Buildings/HouseDark2.png");
		home3 = new Home("Home3", 460, 700, 450, 720);
		home3.setImagePath("/resources/Buildings/HouseDark2.png");
		home4 = new Home("Home4", 560, 700, 550, 720);
		home4.setImagePath("/resources/Buildings/HouseDark2.png");

		/*apart = new Apartment("Apartment 1", 620, 700, 610, 720);
		apart.setImagePath("/resources/Buildings/HouseDark.png");
*/
		market = new Market("Market", 620, 120, 610, 60);
		market.setImagePath("/resources/Buildings/MarketDark2.png");
		bank = new Bank("Bank", 360, 120, 360, 60, 420, 80);
		bank.setImagePath("/resources/Buildings/BankDark2.png");

		rest1 = new Restaurant1("Restaurant 1", 100, 120, 100, 40, 100, 120);
		rest1.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest2 = new Restaurant2("Restaurant 2", 100, 120, 0, 120, 100, 140);
		rest2.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest3 = new Restaurant3("Restaurant 3", 680, 120, 700, 120, 680, 120);
		rest3.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest4 = new Restaurant4("Restaurant 4", 100, 380, 0, 380, 100, 420);
		rest4.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest5 = new Restaurant5("Restaurant 5", 680, 380, 700, 380, 680, 400);
		rest5.setImagePath("/resources/Buildings/RestaurantDark2.png");

		buildings.add(home);
		buildings.add(home1);
		buildings.add(home2);
		buildings.add(home3);
		buildings.add(home4);
		buildings.add(bank);
		buildings.add(rest1);
		buildings.add(market);
		buildings.add(rest4);
		buildings.add(rest5);
		//buildings.add(apart);

		buildings.add(rest3);
		buildings.add(rest2);


		jobLocationList.add("No job");
		for(Building b: buildings){
			if(!(b.getName().contains("ome")||b.getName().contains("artment"))){
				jobLocationList.add(b.getName());
			}
		}
		//populate building jobs map
		for(Building b: buildings){
			jobPositionList.add(b.getJobCollec());
			buildingJobsMap.put(b.getName(), b.getJobCollec());
		}

		//Setup Transportation Grid

		//Initially set grid to empty ('E') entirely
		for(int x = 0; x < GRIDWIDTH; x++)
			for(int y = 0; y < GRIDHEIGHT; y++) {
				grid[x][y] = 'E';
			}

		//Sidewalks ('S')
		for(int x = 4; x < GRIDWIDTH - 4; x++) //Middle Horizontal Sidewalks
			grid[x][19] = 'S';

		for(int x = 4; x < GRIDWIDTH - 4; x++)
			grid[x][22] = 'S';


		for(int y = 5; y < GRIDHEIGHT - 3; y++) //Middle Vertical Sidewalks
			grid[18][y] = 'S';

		for(int y = 5; y < GRIDHEIGHT - 3; y++)
			grid[21][y] = 'S';

		//Put in roads ('R')
		for(int x = 4; x < GRIDWIDTH - 4; x++) //Top road
			for(int y = 7; y < 9; y++)
				grid[x][y] = 'R';

		for(int x = 4; x < GRIDWIDTH - 4; x++) //Top Sidewalks
			grid[x][6] = 'S';

		for(int x = 4; x < GRIDWIDTH - 4; x++)
			grid[x][9] = 'S';

		for(int x = 4; x < GRIDWIDTH - 4; x++) //Bottom road
			for(int y = 33; y < 35; y++)
				grid[x][y] = 'R';

		for(int x = 4; x < GRIDWIDTH - 4; x++) //Bottom Sidewalks
			grid[x][35] = 'S';

		for(int x = 4; x < GRIDWIDTH - 4; x++)
			grid[x][32] = 'S';

		for(int x = 6; x < 8; x++) //Left road
			for(int y = 5; y < GRIDHEIGHT - 3; y++)
				grid[x][y] = 'R';

		for(int y = 5; y < GRIDHEIGHT - 3; y++) //Left Sidewalks
			grid[5][y] = 'S';

		for(int y = 5; y < GRIDHEIGHT - 3; y++)
			grid[8][y] = 'S';

		for(int x = 32; x < 34; x++) //Right road
			for(int y = 5; y < GRIDHEIGHT - 3; y++)
				grid[x][y] = 'R';

		for(int y = 5; y < GRIDHEIGHT - 3; y++) //Right Sidewalks
			grid[34][y] = 'S';

		for(int y = 5; y < GRIDHEIGHT - 3; y++)
			grid[31][y] = 'S';

		for(int x = 4; x < GRIDWIDTH - 4; x++) //Middle roads
			for(int y = 20; y < 22; y++)
				grid[x][y] = 'R';

		for(int x = 19; x < 21; x++)
			for(int y = 5; y < GRIDHEIGHT - 3; y++)
				grid[x][y] = 'R';

		//Setup crosswalks for intersections
		setupIntersectionFromPosition(5, 6);  //Top-Left Intersection
		setupIntersectionFromPosition(18, 6); //Top-Middle Intersection
		setupIntersectionFromPosition(31, 6); //Top-Right Intersection
		setupIntersectionFromPosition(5, 19); //Mid-Left Intersection
		setupIntersectionFromPosition(18, 19);//Middle Intersection
		setupIntersectionFromPosition(31, 19);//Mid-Right Intersection
		setupIntersectionFromPosition(5, 32); //Bot-Left Intersection
		setupIntersectionFromPosition(18, 32);//Bottom Intersection
		setupIntersectionFromPosition(31, 32);//Bot-Right Intersection

	}

	private static void setupIntersectionFromPosition(int i, int j) {
		grid[i+1][j+3] = 'I';
		grid[i+2][j+3] = 'I';
		grid[i][j+1] = 'I';
		grid[i][j+2] = 'I';
		grid[i+1][j] = 'I';
		grid[i+2][j] = 'I';
		grid[i+3][j+1] = 'I';
		grid[i+3][j+2] = 'I';
	}

	static{//populate person-specific elements
		bar = new TimeBar();
		bar.setVisible(true);


	

		homeList.add("None (Homeless Shelter)");
		homeList.add("Home");
		homeList.add("Home1");
		homeList.add("Home2");
		homeList.add("Home3");
		homeList.add("Home4");
		homeList.add("Apartment 1");


		//populate lists
		/*jobLocationList.add("No job");
	for(Building b: buildings){
		jobLocationList.add(b.getName());
	}*/
		//populate building jobs map
		for(Building b: buildings){
			jobPositionList.add(b.getJobCollec());
			buildingJobsMap.put(b.getName(), b.getJobCollec());
		}

	}

	static{//populate person-specific elements
		bar = new TimeBar();
		bar.setVisible(true);




		//populate lists
/*		jobLocationList.add("No job");
		for(Building b: buildings){
			jobLocationList.add(b.getName());
		}
		//populate building jobs map
		for(Building b: buildings){
			jobPositionList.add(b.getJobCollec());
			buildingJobsMap.put(b.getName(), b.getJobCollec());
		}*/

		//jobPositionList.add("This is a temp variable I still need to figure out how to make this list dynamic");

		transportationList.add("Walker");
		transportationList.add("Car");
		transportationList.add("Bus");

		homeOwnerList.add("none");
		homeOwnerList.add("Home");
		homeOwnerList.add("Home1");
		homeOwnerList.add("Home2");
		homeOwnerList.add("Home3");
		homeOwnerList.add("Home4");
		
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
		tempcast.add((Restaurant)rest2);
		tempcast.add((Restaurant)rest3);
		tempcast.add((Restaurant)rest4);
		tempcast.add((Restaurant)rest5);
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

	public static Character[][] getGrid() {
		return grid;
	}
	public static HashMap<String, Vector<String>> getJobMap(){
		return buildingJobsMap;
	}
}

