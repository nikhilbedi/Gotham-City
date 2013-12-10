package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Role;
import Gui.MainScreen;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;
import simcity.Location;
import simcity.TheCity;
import simcity.Truck;
import simcity.Market.Market;
import simcity.Market.MarketWorkerRole;
import simcity.Market.Order;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant5.Restaurant5;

public class MarketWorkerGui extends RoleGui{
	
	private MarketWorker worker;
	private MarketCustomer customer;
	private Command command;
	private enum Command {none, getting, delivering, restGetting, truck};
	private List <Order> orders;
	private boolean drawString = false;
	private Role role;
	boolean d = false;
	Restaurant1 r1;
	Restaurant2 r2;
	Restaurant3 r3;
	Restaurant4 r4;
	Restaurant5 r5;
	Restaurant r;
	Location loc;
	Map<String, Integer> delivery = new HashMap<String, Integer>();
	
	public MarketWorkerGui(MarketWorker mw){
		worker = mw;
		xPos = 430;
		yPos = 360;
		xDestination = 430;
		yDestination = 360;
	}

	public MarketWorkerGui(MarketWorkerRole mw, Screen s){
		super(mw, s);
		worker = mw;
		xPos = 430;
		yPos = 360;
		xDestination = 430;
		yDestination = 360;
	}
	
	/*public void setRestaurants(){
		restaurants = TheCity.getRestaurantList();
	}*/

	public void updatePosition() {
		super.updatePosition();
		
	        if (xPos == 550 && yPos == 250   && command == Command.getting){
	        	command = Command.none;
	        	Deliver();
	        }
	        if (xPos == 580 && yPos == 410 && command == Command.delivering){
	        	command = Command.none;
	        	worker.Brought(customer);
	        }
	        if (xPos == 130 && yPos == 250 && command == Command.restGetting){
	        	System.out.println("Got things");
	        	command = Command.none;
	        	LoadToTruck();
	        }
	        if (xPos == 100 && yPos == 250 && command == Command.truck){
	        	command = Command.none;
	        	System.out.println("Loaded to truck");
	        	worker.Sent(role);
	        }
	}

	
	public void draw(Graphics g) {
		super.draw(g);
	}

	public boolean isPresent() {
		return true;
	}

	
	public void DoSend(Map<String, Integer> m, Restaurant restaurant){
		r = restaurant;//restaurant
		delivery = m;
		r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
		r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
		r3 = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
		r4 = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
		r5 = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
		if (restaurant == r1){
			loc = r1.getGuiLocation();
		}
		else if (restaurant == r2){
			loc = r2.getGuiLocation();
		}
		else if (restaurant == r3){
			loc = r3.getGuiLocation();
		}
		else if (restaurant == r4){
			loc = r4.getGuiLocation();
		}
		else if (restaurant == r5){
			loc = r5.getGuiLocation();
		}
		System.out.println("Going to truck");
		//this.role = role;
		xDestination = 130;
		yDestination = 250;
		command = Command.restGetting;
	}

	public void LoadToTruck(){ //restaurant
		xDestination = 100;
		yDestination = 250;
		command = Command.truck;
		Market m1  = (Market) TheCity.getBuildingFromString("Market");
		Truck truck = new Truck(m1.getGuiLocation().getX(),m1.getGuiLocation().getY(), loc.getX(), loc.getY());
		truck.setReturnCoordinates(m1.getGuiLocation().getX(), m1.getGuiLocation().getY());
		truck.command = "delivering";
		if (r == r1){
			truck.setData(worker,  r1, delivery);
		}
		else if (r == r2){
			truck.setData(worker, r2, delivery);
		}
		else if (r == r3){
			truck.setData(worker, r3, delivery);
		}
		else if (r == r4){
			truck.setData(worker, r4, delivery);
		}
		else if (r == r5){
			truck.setData(worker, r5, delivery);
		}
		ScreenFactory.main.addGui(truck);
		
	}
	public void drawOrder(Graphics g){
		int x = xPos;
		
	}
	
	
	public void DoBring(MarketCustomer m){ //customer
		customer = m;
		xDestination = 550;
		yDestination = 250;
		command = Command.getting;
	}
	
	public void Deliver(){ //customer
		drawString = true; 
		xDestination = 580;
		yDestination = 410;
		command = Command.delivering;
	}
	
	public void DefaultPos(){
		xDestination = 430;
		yDestination = 360;
	}
}
