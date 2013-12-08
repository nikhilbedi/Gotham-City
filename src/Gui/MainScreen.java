package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import simcity.Building;
import simcity.Home.Home;
import simcity.Market.Market;
import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant4.Restaurant4;

public class MainScreen extends Screen{

	/**For reference
	 * obst.add(new Obstacle(200, 100, 50, 50));
		obst.add(new Obstacle(400, 100, 50, 50));
		obst.add(new Obstacle(600, 100, 50, 50));
		obst.add(new Obstacle(400, 700, 50, 50));
	 */

	ArrayList<Building> buildings = new ArrayList<Building>();
	Building house;
	Building house2,house3,house4,house5,house6;
	Building market;
	Building bank;
	Building rest1;
	Building rest2;
	Building rest3;
	Building rest4;
	Building rest5;
	Character[][] grid;



	public MainScreen()
	{
		temp = 1;
		
		//TODO Need to update the entrance positions for each location in order to match the new gui movement
		
		house = new Home("Home", 420, 590, 420, 600);
		house.setImagePath("/resources/Buildings/HouseDark2.png");
		
		//These are just added to make Evan happy
		house2 = new Home("Home2", 90, 590, 100, 600);
		house2.setImagePath("/resources/Buildings/HouseDark2.png");
		house3 = new Home("Home3", 190, 590, 200, 600);
		house3.setImagePath("/resources/Buildings/HouseDark2.png");
		house4 = new Home("Home4", 290, 590, 300, 600);
		house4.setImagePath("/resources/Buildings/HouseDark2.png");
		house5 = new Home("Home5", 490, 590, 500, 600);
		house5.setImagePath("/resources/Buildings/HouseDark2.png");
		house6 = new Home("Home6", 590, 590, 600, 600);
		house6.setImagePath("/resources/Buildings/HouseDark2.png");
		
		
		market = new Market("Market", 630, 130, 600, 100);
		market.setImagePath("/resources/Buildings/MarketDark2.png");
		bank = new Bank("Bank", 400, 100, 400, 100);
		bank.setImagePath("/resources/Buildings/BankDark2.png");
		
		rest1 = new Restaurant1("Restaurant 1", 200, 100, 200, 100);
		rest1.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest2 = new Restaurant2("Restaurant 2", 50, 210, 50, 210);
		rest2.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest3 = new Restaurant1("Restaurant 3", 730, 210, 730, 210);
		rest3.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest4 = new Restaurant4("Restaurant 4", 50, 410, 50, 410);
		rest4.setImagePath("/resources/Buildings/RestaurantDark2.png");
		rest5 = new Restaurant1("Restaurant 5", 730, 410, 730, 410);
		rest5.setImagePath("/resources/Buildings/RestaurantDark2.png");

		buildings.add(house);
		buildings.add(market);
		buildings.add(bank);
		buildings.add(rest1);
		buildings.add(rest2);
		buildings.add(rest3);
		buildings.add(rest4);
		buildings.add(rest5);
		
		//just for looks
		buildings.add(house2);
		buildings.add(house3);
		buildings.add(house4);
		buildings.add(house5);
		buildings.add(house6);
		
		
	}

	public void paintBackground(Graphics g)
	{
		g.setColor(Color.white);
        //g.fillRect(0,0,1000, 800);
		java.net.URL image1 = this.getClass().getResource("/resources/Backgrounds/SimCityBackground.png");
 		ImageIcon current1 = new ImageIcon(image1);
 		g.drawImage(current1.getImage(), 0, 0, null);
         
 		//for(int x = 0; x < )
 		for(int x = 0; x < grid.length; x++)
 			for(int y = 0; y < grid[0].length; y++) {
 				if(grid[x][y] == 'E')
 					g.setColor(Color.white);
 				else if(grid[x][y] == 'R')
 					g.setColor(Color.red);
 				else if(grid[x][y] == 'P')
 					g.setColor(Color.green);
 				else if(grid[x][y] == 'S')
 					g.setColor(Color.gray);
 				else if(grid[x][y] == 'I')
 					g.setColor(Color.magenta);
 				
 				g.fillRect(x*20, y*20, 40, 40);
 			}
 		
 		for(Building b : buildings){
			java.net.URL image = this.getClass().getResource(b.getImagePath());
			b.icon = new ImageIcon(image);
			g.drawImage(b.icon.getImage(), b.getGuiLocation().getX(), b.getGuiLocation().getY(), null);
		}
		
		/*for(int x = 0; x < grid.length; x++)
 			for(int y = 0; y < grid[0].length; y++) {
 				if(grid[x][y] == 'E')
 					g.setColor(Color.white);
 				else if(grid[x][y] == 'R')
 					g.setColor(Color.red);
 				else if(grid[x][y] == 'P')
 					g.setColor(Color.green);
 				
 				g.fillRect(x*20, y*20, 40, 40);
 			}*/
	}
	
	public List<Restaurant> getRestaurantList(){
		List<Restaurant> tempcast = new ArrayList<Restaurant>();
		tempcast.add((Restaurant)rest1);
		tempcast.add((Restaurant)rest2);
		tempcast.add((Restaurant)rest3);
		tempcast.add((Restaurant)rest4);
		tempcast.add((Restaurant)rest5);
		
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
	
	@Override
	public String checkSwap(int x, int y){
		for(Building b: buildings){
			int tempX = b.getGuiLocation().getX();
			int tempY = b.getGuiLocation().getY();
			if( (x>tempX) && (x<tempX + b.icon.getIconWidth()) && (y>tempY) && (y<tempY+b.icon.getIconHeight())){
				return b.getName();
			}
		}
		return "This is not a building";
	}

	public void setGrid(Character[][] grid) {
		this.grid = grid;
		
	}
}


