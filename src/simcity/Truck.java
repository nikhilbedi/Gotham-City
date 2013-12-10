package simcity;

import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import agent.Role;
import simcity.Market.MarketWorkerRole;
import simcity.Market.interfaces.MarketWorker;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant3.Restaurant3;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant5.CookRole;
import simcity.restaurants.restaurant5.Restaurant5;
import Gui.RoleGui;
import Gui.ScreenFactory;


public class Truck extends RoleGui{
	ImageIcon currentImage;
	ImageIcon truckLeft = new ImageIcon(this.getClass().getResource("/resources/mika/truckLeft.jpg"));
	ImageIcon truckRight = new ImageIcon(this.getClass().getResource("/resources/mika/truckRight.jpg"));
	ImageIcon truckUp = new ImageIcon(this.getClass().getResource("/resources/mika/truckUp.jpg"));
	ImageIcon truckDown = new ImageIcon(this.getClass().getResource("/resources/mika/truckDown.jpg"));
	Restaurant1 r1 = (Restaurant1) TheCity.getBuildingFromString("Restaurant 1");
	Restaurant2 r2 = (Restaurant2) TheCity.getBuildingFromString("Restaurant 2");
	Restaurant3 r3  = (Restaurant3) TheCity.getBuildingFromString("Restaurant 3");
	Restaurant4 r4  = (Restaurant4) TheCity.getBuildingFromString("Restaurant 4");
	Restaurant5 r5  = (Restaurant5) TheCity.getBuildingFromString("Restaurant 5");
	Restaurant restaurant;
	Map<String, Integer> delivery = new HashMap<String, Integer>();
	int returnX, returnY;
	private MarketWorkerRole worker;
	private Role role;
	public String command = "";
	
	//Transportation - Brice
	int finalX, finalY;
	boolean reachedDest = false;
	Character[][] grid;
	char prevTile = 'I';
	Timer moveTimer = new Timer();
	
	public Truck(int x, int y, int destX, int destY){
		xPos = x;
		yPos = y;
		xDestination = destX;
		yDestination = destY;
		//finalX = destX/20 + 1;
		//finalY = destY/20;
		grid = TheCity.getGrid();
		prevTile = 'S';
		currentImage = truckDown;
		//guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
	}
	
	
	public void setData(MarketWorker w, Restaurant r, Map<String, Integer> m){
		worker = (MarketWorkerRole) w;
		restaurant = r;
		grid = TheCity.getGrid();
		//this.role = role;
		delivery = m;
	}
	
	public void setReturnCoordinates(int x, int y){
		returnX = x;
		returnY = y;
	}
	
	public boolean checkIfOpen(){
		boolean open = false; // change it to false later !!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (restaurant== r1){
			open =  r1.isOpen();
		}
		else if (restaurant == r2){
			open = r2.isOpen();
		}
		else if (restaurant == r3){
			open = r3.isOpen();
		}
		else if (restaurant == r4){
			open = r4.isOpen();
		}
		else if (restaurant == r5){
			open = r5.isOpen();
		}
		return open;
	}
	
	
	@Override
	public void updatePosition(){
		if (xPos < xDestination){
			xPos+=speed;
			currentImage = truckRight;
		}
		else if (xPos > xDestination){
			xPos-=speed;
			currentImage = truckLeft;
		}
		if (yPos < yDestination){
			yPos+=speed;
			currentImage = truckDown;
		}

		else if (yPos > yDestination){
			yPos-=speed;
			currentImage = truckUp;
		}
		
		if (xPos == xDestination && yPos == yDestination && command == "delivering"){ // && and restaurant is open
			command = "";
			reachedDest = false;
			if (checkIfOpen()){
				if (restaurant== r1){
					System.err.println("Restaurant 1 geting food");
					r1.getCook().HereIsYourFood(delivery, worker);
				
				}
				else if (restaurant == r2){
					r2.getCook().HereIsYourFood(delivery, worker);
	
				}
				else if (restaurant == r3){
					 r3.getCook().HereIsYourFood(delivery, worker);
	
				}
				else if (restaurant == r4){
					r4.getCook().HereIsYourFood(delivery, worker);

				}
				else if (restaurant == r5){
					r5.getCook().HereIsYourFood(delivery, worker);

				}
			}
			else{
				System.err.println("Restaurant is closed " + restaurant.getName());
				worker.RestaurantIsClosed(restaurant);
			}
			goBackToMarket();
			//guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
		}
		if (xPos == xDestination  && yPos == yDestination && command == "GoingBack"){
			command = "";
			ScreenFactory.main.removeGui(this);
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
	
	public void goBackToMarket(){
		xDestination = returnX;
		yDestination = returnY;
		//finalX = returnX/20;
		//finalY= returnY/20;
		command = "GoingBack";
	}
	
	public void DoGoToLocation(Location destination) {
		xDestination = destination.getX();
		yDestination = destination.getY();
	}
	
	void guiMoveFromCurrentPositionTo(final int x, final int y){ // Brice - Method for traveling along the grid within the City Screen
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
	 		grid[x][y] = prevTile;
	 		//reachedDest = true;
	 		return;
	 	}
	 	
	 	//Move RIGHT
	 	if(finalX - (x + 1)  >= 0 /*<= deltaX*/ && (grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) {
	 		try {
	 			char temp = grid[x+1][y];
	 			grid[x+1][y] = 'B';
	 			grid[x][y] = prevTile;
	 			prevTile = temp;
	 			DoGoToLocation(new Location((x+1)*20, y*20)); //Temporary timer or semaphore?
	 			moveTimer.schedule(new TimerTask() {
	 				Object cookie = 1;
	 				public void run() {
	 					guiMoveFromCurrentPositionTo(x+1, y);
	 				}
	 			},
	 			240);
	 			
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move LEFT
	 	else if((x - 1) - finalX >= 0 && (grid[x-1][y] == 'R' || grid[x-1][y] == 'I')) {
	 		try {
	 			char temp = grid[x-1][y];
	 			grid[x-1][y] = 'B';
	 			grid[x][y] = prevTile;
	 			prevTile = temp;
	 			DoGoToLocation(new Location((x-1)*20, y*20)); //Temporary timer or semaphore?
	 			moveTimer.schedule(new TimerTask() {
	 				Object cookie = 1;
	 				public void run() {
	 					guiMoveFromCurrentPositionTo(x-1, y);
	 				}
	 			},
	 			240);
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move UP
	 	else if((y - 1) - finalY  >= 0 && (grid[x][y-1] == 'R' || grid[x][y-1] == 'I')) {
	 		try {
	 			char temp = grid[x][y-1];
	 			grid[x][y-1] = 'B';
	 			grid[x][y] = prevTile;
	 			prevTile = temp;
	 			DoGoToLocation(new Location(x*20, (y-1)*20)); //Temporary timer or semaphore?
	 			moveTimer.schedule(new TimerTask() {
	 				Object cookie = 1;
	 				public void run() {
	 					guiMoveFromCurrentPositionTo(x, y-1);
	 				}
	 			},
	 			240);
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move DOWN
	 	else if(finalY - (y + 1) >= 0 && (grid[x][y+1] == 'R' || grid[x][y+1] == 'I')) {
	 		try {
	 			char temp = grid[x][y+1];
	 			grid[x][y+1] = 'B';
	 			grid[x][y] = prevTile;
	 			prevTile = temp;
	 			DoGoToLocation(new Location(x*20, (y+1)*20)); //Temporary timer or semaphore?
	 			moveTimer.schedule(new TimerTask() {
	 				Object cookie = 1;
	 				public void run() {
	 					guiMoveFromCurrentPositionTo(x, y+1);
	 				}
	 			},
	 			240);
	 			
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	else {
	 		moveTimer.schedule(new TimerTask() {
 				Object cookie = 1;
 				public void run() {
 					guiMoveFromCurrentPositionTo(x, y);
 				}
 			},
 			500);
	 	}
    }
	
}

