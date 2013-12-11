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
	static Building home;
	static Building home1,home2, home3, home4, home5, home6,home7,home8,home9, home10
	, home11, home12, home13, home14, home15, home16, home17,home18,home19, home20, home21, 
	home22, home23,home24,home25,home26, home27
	, home28, home29, home30, home31, home32, home33, home34; 
	static Building market, market1;
	static Building bank, bank1;

	static Building apart, apart1, apart2, apart3, apart4;

	static final int GRIDWIDTH = 40;
	static final int GRIDHEIGHT = 40;

	static Character[][] grid  = new Character[GRIDWIDTH][GRIDHEIGHT];

	//Making timebar static so it can be updated by static city clock is this bad?
	public static TimeBar bar;


	public static void populate(){//populate Buildings

		//These are just added to make Evan happy
		//For reference, not putting them in anymore


		home = new Home("Home", 100, 540, 60, 540);
		home.setImagePath("/resources/Buildings/HouseDark2_small.png");
		home1 = new Home("Home1", 100, 500, 60, 500);
        home1.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home2 = new Home("Home2", 100, 580, 60, 580);
        home2.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home3 = new Home("Home3", 100, 620, 60, 620);
        home3.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home4 = new Home("Home4", 100, 660, 60, 660);
        home4.setImagePath("/resources/Buildings/HouseDark2_small.png");
        
        apart2 = new Home("Apartment 2", 100, 700, 60, 700);
		apart2.setImagePath("/resources/Buildings/HouseDark_small.png");
		apart3 = new Home("Apartment 3", 160, 700, 180, 700);
        apart3.setImagePath("/resources/Buildings/HouseDark_small.png");
        
        home7 = new Home("Home7", 160, 600, 180, 600);
        home7.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home8 = new Home("Home8", 160, 560, 180, 560);
        home8.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home9 = new Home("Home9", 160, 520, 180, 520);
        home9.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home10 = new Home("Home10", 160, 480, 180, 480);
        home10.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home11 = new Home("Home11", 360, 700, 320, 700);
		home11.setImagePath("/resources/Buildings/HouseDark2_small.png");
		home12 = new Home("Home12", 360, 600, 320, 600);
        home12.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home13 = new Home("Home13", 360, 560, 320, 560);
        home13.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home14 = new Home("Home14", 360, 520, 320, 520);
        home14.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home15 = new Home("Home15", 360, 480, 320, 480);
        home15.setImagePath("/resources/Buildings/HouseDark2_small.png");
       
		home17 = new Home("Home17", 420, 700, 440, 700);
        home17.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home18 = new Home("Home18", 420, 600, 440, 600);
        home18.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home19 = new Home("Home19", 420, 560, 440, 560);
        home19.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home20 = new Home("Home20", 420, 520, 440, 520);
        home20.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home21 = new Home("Home21", 420, 480, 440, 480);
        home21.setImagePath("/resources/Buildings/HouseDark2_small.png");
//        home22 = new Home("Home22", 680, 540, 700, 540);
//		home22.setImagePath("/resources/Buildings/HouseDark2_small.png");
//		home23 = new Home("Home23", 680, 500, 700, 500);
//        home23.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home24 = new Home("Home24", 680, 580, 700, 580);
        home24.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home25 = new Home("Home25", 680, 620, 700, 620);
        home25.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home26 = new Home("Home26", 680, 660, 700, 660);
        home26.setImagePath("/resources/Buildings/HouseDark2_small.png");
        
//        apart4 = new Home("Apartment 4", 680, 700, 700, 700);
//        apart4.setImagePath("/resources/Buildings/HouseDark_small.png");
		
		home28 = new Home("Home28", 620, 700, 580, 700);
        home28.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home29 = new Home("Home29", 620, 600, 580, 600);
        home29.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home30 = new Home("Home30", 620, 560, 580, 560);
        home30.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home31 = new Home("Home31", 620, 520, 580, 520);
        home31.setImagePath("/resources/Buildings/HouseDark2_small.png");
        home32 = new Home("Home32", 620, 480, 580, 480);
        home32.setImagePath("/resources/Buildings/HouseDark2_small.png");
        
        apart = new Apartment("Apartment 1", 680, 700, 700, 700);
        apart.setImagePath("/resources/Buildings/HouseDark_small.png");
        
        
        


		//apart = new Apartment("Apartment 1", 620, 700, 610, 720);
		//apart.setImagePath("/resources/Buildings/HouseDark.png");

		market = new Market("Market", 620, 120, 610, 60);
		market.setImagePath("/resources/Buildings/MarketDark2.png");
		bank = new Bank("Bank", 360, 120, 360, 60, 420, 80);
		bank.setImagePath("/resources/Buildings/BankDark2.png");
		market1 = new Market("Market1", 620, 380, 520, 310);//520 310
		market1.setImagePath("/resources/Buildings/MarketDark2.png");
		bank1 = new Bank("Bank1", 360, 380, 260, 310, 420, 80);
		bank1.setImagePath("/resources/Buildings/BankDark2.png");

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
//		buildings.add(home5);
//		buildings.add(home6);
		buildings.add(home7);
		buildings.add(home8);
		buildings.add(home9);
		buildings.add(home10);
		buildings.add(home11);
		buildings.add(home12);
		buildings.add(home13);
		buildings.add(home14);
		buildings.add(home15);
//		buildings.add(home16);
		buildings.add(home17);
		buildings.add(home18);
		buildings.add(home19);
		buildings.add(home20);
		buildings.add(home21);
//		buildings.add(home22);
//		buildings.add(home23);
		buildings.add(home24);
		buildings.add(home25);
		buildings.add(home26);
//		buildings.add(home27);
		buildings.add(home28);
		buildings.add(home29);
		buildings.add(home30);
		buildings.add(home31);
		buildings.add(home32);
		//buildings.add(home33);
		//buildings.add(home34);
		//buildings.add(home35);
		
		//buildings.add(house1);
		buildings.add(bank);
		buildings.add(bank1);
		buildings.add(rest1);
		buildings.add(market);
		buildings.add(market1);
		buildings.add(rest4);
		buildings.add(rest5);
		buildings.add(apart);
		buildings.add(apart2);
		buildings.add(apart3);
//		buildings.add(apart4);

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
		grid[i][j] = 'I';
		grid[i+1][j+3] = 'I';
		grid[i+2][j+3] = 'I';
		grid[i][j+1] = 'I';
		grid[i][j+2] = 'I';
		grid[i+1][j] = 'I';
		grid[i+2][j] = 'I';
		grid[i][j+3] = 'I';
		grid[i+3][j] = 'I';
		grid[i+3][j+1] = 'I';
		grid[i+3][j+2] = 'I';
		grid[i+3][j+3] = 'I';
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
		homeList.add("Home5");
		homeList.add("Home6");
		homeList.add("Home7");
		homeList.add("Home8");
		homeList.add("Home9");
		homeList.add("Home10");
		homeList.add("Home11");
		homeList.add("Home12");
		homeList.add("Home13");
		homeList.add("Home14");
		homeList.add("Home15");
//		homeList.add("Home16");
		homeList.add("Home17");
		homeList.add("Home18");
		homeList.add("Home19");
		homeList.add("Home20");
		homeList.add("Home21");
//		homeList.add("Home22");
//		homeList.add("Home23");
		homeList.add("Home24");
		homeList.add("Home25");
		homeList.add("Home26");
		homeList.add("Home27");
		homeList.add("Home28");
		homeList.add("Home29");
		homeList.add("Home30");
		homeList.add("Home31");
		homeList.add("Home32");
		//homeList.add("Home33");
		//homeList.add("Home34");
		
		homeList.add("Apartment 1");
		homeList.add("Apartment 2");
		homeList.add("Apartment 3");
//		homeList.add("Apartment 4");


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
		jobLocationList.add("Market");
		jobLocationList.add("Market1");
		jobLocationList.add("Bank");
		jobLocationList.add("Bank1");
		jobLocationList.add("Restaurant 1");

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
		homeOwnerList.add("Home5");
		homeOwnerList.add("Home6");
		homeOwnerList.add("Home7");
		homeOwnerList.add("Home8");
		homeOwnerList.add("Home9");
		homeOwnerList.add("Home10");
		homeOwnerList.add("Home11");
		homeOwnerList.add("Home12");
		homeOwnerList.add("Home13");
		homeOwnerList.add("Home14");
		homeOwnerList.add("Home15");
//		homeOwnerList.add("Home16");
		homeOwnerList.add("Home17");
		homeOwnerList.add("Home18");
		homeOwnerList.add("Home19");
		homeOwnerList.add("Home20");
		homeOwnerList.add("Home21");
//		homeOwnerList.add("Home22");
//		homeOwnerList.add("Home23");
		homeOwnerList.add("Home24");
		homeOwnerList.add("Home25");
		homeOwnerList.add("Home26");
		homeOwnerList.add("Home27");
		homeOwnerList.add("Home28");
		homeOwnerList.add("Home29");
		homeOwnerList.add("Home30");
		homeOwnerList.add("Home31");
		homeOwnerList.add("Home32");
		//homeOwnerList.add("Home33");
		//homeOwnerList.add("Home34");
		homeOwnerList.add("Apartment 1");
		homeOwnerList.add("Apartment 2");
		homeOwnerList.add("Apartment 3");
//		homeOwnerList.add("Apartment 4");
		
		
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



	public static List<Market> getMarketList(){
		List<Market> tempcast = new ArrayList<Market>();
		tempcast.add((Market)market);
		tempcast.add((Market)market1);
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

