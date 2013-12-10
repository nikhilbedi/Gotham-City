package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import simcity.Building;
import simcity.TheCity;

import simcity.Home.Apartment;

import simcity.Bus;

import simcity.Home.Home;
import simcity.Market.Market;
import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant5.Restaurant5;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant4.Restaurant4;


public class MainScreen extends Screen{

	ArrayList<Building> buildings = new ArrayList<Building>();
	Character[][] grid;
	public Bus bus = new Bus("Clockwise");
	
	public MainScreen()
	{

		addGui(bus);
		//so much cleaner
		buildings = TheCity.getBuildings();
		//grid = TheCity.getGrid();
		
		//addGui(bus2);
	}

	public void paintBackground(Graphics g)
	{
		g.setColor(Color.orange);
		g.fillRect(0,0,1200, 1000);
		java.net.URL image1 = this.getClass().getResource("/resources/Backgrounds/SimCityBackground.png");
		ImageIcon current1 = new ImageIcon(image1);
		g.drawImage(current1.getImage(), 0, 0, null);
		
		for(int x = 0; x < grid.length; x++)
 			for(int y = 0; y < grid[0].length; y++) {
 				/*if(grid[x][y] == 'R') {
 					java.net.URL im = this.getClass().getResource("/resources/Backgrounds/roadTile.png");
 					ImageIcon currentIm = new ImageIcon(im);
 					g.drawImage(currentIm.getImage(), x*20, y*20, null);
 				}*/
 				//else if(grid[x][y] == 'E') {
 				//	java.net.URL im2 = this.getClass().getResource("/resources/Backgrounds/stoneTile.png");
 				//	ImageIcon currentIm2 = new ImageIcon(im2);
 				//	g.drawImage(currentIm2.getImage(), x*20, y*20, null);
 				//}

 					if(grid[x][y] == 'E')
 						g.setColor(Color.white);
 					else if(grid[x][y] == 'R') {
 						g.setColor(Color.red);
 					}
 					//g.setColor(Color.red);
 					else if(grid[x][y] == 'P')
 						g.setColor(Color.green);
 					else if(grid[x][y] == 'S')
 						g.setColor(Color.gray);
 					else if(grid[x][y] == 'I')
 						g.setColor(Color.magenta);
 					else if(grid[x][y] == 'B')
 						g.setColor(Color.cyan);

 					g.fillRect(x*20, y*20, 40, 40);
 				}
		
		for(Building b : buildings){
			java.net.URL image = this.getClass().getResource(b.getImagePath());
			b.icon = new ImageIcon(image);
			g.drawImage(b.icon.getImage(), b.getGuiLocation().getX(), b.getGuiLocation().getY(), null);
		}
		
		
	}


	@Override
	public String checkSwap(int x, int y){
		for(Building b: buildings){
			int tempX = b.getGuiLocation().getX();
			int tempY = b.getGuiLocation().getY();
			if( (x>tempX) && (x<tempX + b.icon.getIconWidth()) && (y>tempY) && (y<tempY+b.icon.getIconHeight())){
				System.err.println(b.getName());
				return b.getName();
				
			}
		}

		return "This is not a building";

	}

	public void setGrid(Character[][] grid) {
		this.grid = TheCity.getGrid();
		bus = new Bus("clockWise", grid);
		bus.setGrid(TheCity.getGrid());
		addGui(bus);
	}
	
	public Character[][] getGrid() {
		return grid;
	}
}


