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

public class MainScreen extends Screen{

	/**For reference
	 * obst.add(new Obstacle(200, 100, 50, 50));
		obst.add(new Obstacle(400, 100, 50, 50));
		obst.add(new Obstacle(600, 100, 50, 50));
		obst.add(new Obstacle(400, 700, 50, 50));
	 */

	ArrayList<Building> buildings = new ArrayList<Building>();
	Building house;
	Building market;
	Building bank;
	Building rest1;



	public MainScreen()
	{
		temp = 1;
		house = new Home("Home", 390, 590, 400, 600);
		house.setImagePath("/resources/Buildings/HouseDark.png");
		market = new Market("Market", 210, 110, 200, 100);
		market.setImagePath("/resources/Buildings/MarketDark.png");
		bank = new Bank("Bank", 410, 110, 400, 100);
		bank.setImagePath("/resources/Buildings/BankDark.png");
		rest1 = new Restaurant1("Restauarant 1", 610, 110, 600, 100);
		rest1.setImagePath("/resources/Buildings/RestaurantDark.png");

		buildings.add(house);
		buildings.add(market);
		buildings.add(bank);
		buildings.add(rest1);
	}

	public  void paintBackground(Graphics g)
	{
		 g.setColor(Color.white);
         //g.fillRect(0,0,1000, 800);
		 java.net.URL image1 = this.getClass().getResource("/resources/Backgrounds/SimCityBackground.png");
 		ImageIcon current1 = new ImageIcon(image1);
 		g.drawImage(current1.getImage(), 0, 0, null);
         
		for(Building b : buildings){
			java.net.URL image = this.getClass().getResource(b.getImagePath());
			b.icon = new ImageIcon(image);
			g.drawImage(b.icon.getImage(), b.getGuiLocation().getX(), b.getGuiLocation().getY(), null);
		}

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
	
	@Override
	public String checkSwap(int x, int y){
		for(Building b: buildings){
			int tempX = b.getGuiLocation().getX();
			int tempY = b.getGuiLocation().getY();
			if( (x>tempX) && (x<tempX + b.icon.getIconWidth()) && (y>tempY) && (y<tempY+b.icon.getIconHeight())){
				return b.getName();
			}
		}
		return null;
	}
}


