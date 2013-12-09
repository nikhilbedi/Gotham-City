package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import simcity.Building;
import simcity.Bus;
import simcity.TheCity;


public class MainScreen extends Screen{

	ArrayList<Building> buildings = new ArrayList<Building>();
	Character[][] grid;
	Bus bus;
	
	public MainScreen()
	{
		//so much cleaner
		buildings = TheCity.getBuildings();
		//grid = TheCity.getGrid();
		
		//addGui(bus2);
	}

	public void paintBackground(Graphics g)
	{
		g.setColor(Color.white);
		//g.fillRect(0,0,1000, 800);
		java.net.URL image1 = this.getClass().getResource("/resources/Backgrounds/SimCityBackground.png");
		ImageIcon current1 = new ImageIcon(image1);
		g.drawImage(current1.getImage(), 0, 0, null);
		
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
				return b.getName();
			}
		}

		return "This is not a building";

	}

	public void setGrid(Character[][] grid) {
		this.grid = grid;
		bus = new Bus("clockWise", grid);
		addGui(bus);
	}
}


