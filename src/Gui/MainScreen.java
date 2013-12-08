package Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import simcity.Building;
import simcity.TheCity;
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


	public MainScreen()
	{
		//so much cleaner
		buildings = TheCity.getBuildings();
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
}


