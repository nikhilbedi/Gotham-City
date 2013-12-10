package simcity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import Gui.RoleGui;

public class Bus extends RoleGui{
	Timer timer = new Timer(), moveTimer = new Timer();
	private String type;
	String destination;
	ImageIcon currentImage = new ImageIcon();
	ImageIcon busLeft = new ImageIcon(this.getClass().getResource("/resources/mika/busleft.jpg"));
	ImageIcon busRight = new ImageIcon(this.getClass().getResource("/resources/mika/busright.jpg"));
	ImageIcon busUp = new ImageIcon(this.getClass().getResource("/resources/mika/busUp.jpg"));
	ImageIcon busDown = new ImageIcon(this.getClass().getResource("/resources/mika/busDown.jpg"));
	public static List<PersonGui> passengers = new ArrayList<PersonGui>();


	int currentDestinationX;
	int currentDestinationY;

	int finalX, finalY;
	
	Character[][] grid;
	char prevTile = 'R';
	
	static Location topLeftCorner = new Location(140, 160);
	static Location topRightCorner = new Location(640, 160);
	static Location botLeftCorner = new Location(140, 660);
	static Location botRightCorner = new Location(640, 660);
	
	public static boolean stoppedNorthRight = false; 
	public static boolean stoppedNorthLeft  = false;
	public static boolean stoppedSouthRight = false;
	public static boolean stoppedSouthLeft  = false;
	public static boolean stoppedEastRight = false; 
	public static boolean stoppedEastLeft  = false;
	public static boolean stoppedWestRight = false;
	public static boolean stoppedWestLeft  = false;
	
	boolean reachedDest = false;
	
	public void setGrid(Character[][] grid) {
		System.out.println("GRID SET");
		this.grid = grid;
	}
	
	public static Location northBusStop = new Location((topRightCorner.getX() + topLeftCorner.getX())/2 - 30, topRightCorner.getY(), "North busstop");
	public static Location southBusStop = new Location((topRightCorner.getX() + topLeftCorner.getX())/2 - 30, botRightCorner.getY(), "South busstop");
	public static Location eastBusStop = new Location(botRightCorner.getX(), (botRightCorner.getY() + topRightCorner.getY())/2 - 30, "East busstop");
	public static Location westBusStop = new Location(botLeftCorner.getX(), (botRightCorner.getY() + topRightCorner.getY())/2 - 30, "West busstop");
	
	public static Location northPickupStop = new Location((topRightCorner.getX() + topLeftCorner.getX())/2 - 30, topRightCorner.getY() + 20);
	public static Location southPickupStop = new Location((topRightCorner.getX() + topLeftCorner.getX())/2 - 30, botRightCorner.getY() - 20);
	public static Location eastPickupStop = new Location(botRightCorner.getX() - 20, (botRightCorner.getY() + topRightCorner.getY())/2 - 30);
	public static Location westPickupStop = new Location(botLeftCorner.getX() + 20, (botRightCorner.getY() + topRightCorner.getY())/2 - 30);
	
	public Bus(String type){ //goes east
		this.type = type;
		
		System.out.println("North Bus X: " + northBusStop.getX());
		System.out.println("North Bus Y: " + northBusStop.getY());
		System.out.println("West Bus X: " + westBusStop.getX());
		System.out.println("West Bus Y: " + westBusStop.getY());
		System.out.println("East Bus X: " + eastBusStop.getX());
		System.out.println("East Bus Y: " + eastBusStop.getY());
		System.out.println("South Bus X: " + southBusStop.getX());
		System.out.println("South Bus Y: " + southBusStop.getY());
		
		System.out.println("North Coor X: " + northPickupStop.getX());
		System.out.println("North Coor Y: " + northPickupStop.getY());
		System.out.println("West Coor X: " + westPickupStop.getX());
		System.out.println("West Coor Y: " + westPickupStop.getY());
		System.out.println("East Coor X: " + eastPickupStop.getX());
		System.out.println("East Coor Y: " + eastPickupStop.getY());
		System.out.println("South Coor X: " + southPickupStop.getX());
		System.out.println("South Coor Y: " + southPickupStop.getY());
		
		
		if (this.type == "clockWise"){
			xPos = topLeftCorner.getX();
			yPos = topLeftCorner.getY();
			currentImage = busRight;
			//xDestination = 610;
			//yDestination = 215;
			
			finalX = topRightCorner.getX()/20;
			finalY = topRightCorner.getY()/20;
			
			//xDestination = topRightCorner.getX();
			//yDestination = topRightCorner.getY();
			//currentImage = busRight;
			
			guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
		}
		
		else if (this.type == "counterClockWise"){
			xPos = botLeftCorner.getX();
			yPos = botLeftCorner.getY();
			currentImage = busRight;
			//xDestination = 575;
			//yDestination = 525;
			
			finalX = botRightCorner.getX()/20;
			finalY = botRightCorner.getY()/20;
			
			guiMoveFromCurrentPositionTo(botLeftCorner.getX()/20, botLeftCorner.getY()/20);
			
			//xDestination = botRightCorner.getX();
			//yDestination = botRightCorner.getY();
		}
		
		prevTile = 'I';
	}
	
	
	
	public Bus(String type, Character[][] grid2) {
		this.type = type;
		grid = grid2;
		
		if (this.type == "clockWise"){
			xPos = topLeftCorner.getX();
			yPos = topLeftCorner.getY();
			//xDestination = 610;
			//yDestination = 215;
			currentImage = busRight;
			finalX = topRightCorner.getX()/20;
			finalY = topRightCorner.getY()/20;
			
			//xDestination = topRightCorner.getX();
			//yDestination = topRightCorner.getY();
			
			
			guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
		}
		
		else if (this.type == "counterClockWise"){
			xPos = botLeftCorner.getX();
			yPos = botLeftCorner.getY();
			currentImage = busRight;
			//xDestination = 575;
			//yDestination = 525;
			
			finalX = botRightCorner.getX()/20;
			finalY = botRightCorner.getY()/20;
			
			guiMoveFromCurrentPositionTo(botLeftCorner.getX()/20, botLeftCorner.getY()/20);
			
			//xDestination = botRightCorner.getX();
			//yDestination = botRightCorner.getY();
		}
		
		prevTile = 'I';
	}

	@Override
	public void updatePosition(){
		super.updatePosition();
		if(reachedDest){
			System.out.println("HERE");
			reachedDest = false;
				if (xPos == topRightCorner.getX() && yPos == topRightCorner.getY()){  //going south
					currentImage = busDown;
					//xPos = 655;
					//yPos = 215;
					finalX = botRightCorner.getX()/20;
					finalY = botRightCorner.getY()/20;
					
					//guiMoveFromCurrentPositionTo(topRightCorner.getX()/20, topRightCorner.getY()/20);
				}
				
				if (xPos == botRightCorner.getX() && yPos == botRightCorner.getY()){ //going west
					currentImage = busLeft;
					//xPos = 610;
					//yPos = 565;
					finalX = botLeftCorner.getX()/20;
					finalY = botLeftCorner.getY()/20;
					
					//guiMoveFromCurrentPositionTo(botRightCorner.getX()/20, botRightCorner.getY()/20);
				}
				
				if (xPos == botLeftCorner.getX() && yPos == botLeftCorner.getY()){//going north 
					currentImage = busUp;
					//xPos = 210;
					//yPos = 507;
					finalX = topLeftCorner.getX()/20;
					finalY = topLeftCorner.getY()/20;
					
					//guiMoveFromCurrentPositionTo(botLeftCorner.getX()/20, botLeftCorner.getY()/20);
				}
				
				if (xPos == topLeftCorner.getX() && yPos == topLeftCorner.getY()){ //going east
					currentImage = busRight;
					//xPos = 210;
					//yPos = 215;
					finalX = topRightCorner.getX()/20;
					finalY = topRightCorner.getY()/20;
					//guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
			}
			guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
		}
		
		
		if (xPos == northBusStop.getX()){
				if (yPos == northBusStop.getY()){  //north bus stop locations 
					stoppedNorthRight = true;
					for (int i= 0; i<passengers.size(); i++){
						if (passengers.get(i).busStop == "north"){
							passengers.get(i).getOut();
							passengers.remove(i);
						}
					}
				}
				else if (yPos == southBusStop.getY()){ //south bus stop locations
					stoppedSouthRight = true;
					for (int i= 0; i<passengers.size(); i++){
						if (passengers.get(i).busStop == "south"){
							passengers.get(i).getOut();
							passengers.remove(i);
						}
					}
				}
			}
		
		
		if ( yPos == eastBusStop.getY()){
			if (xPos == eastBusStop.getX()){   //east bus stop location
				stoppedEastRight = true;
				for (int i= 0; i<passengers.size(); i++){
					if (passengers.get(i).busStop == "east"){
						passengers.get(i).getOut();
						passengers.remove(i);
					}
				}
			}

			else if (xPos == westBusStop.getX()){   //west bus stop location
				stoppedWestRight = true;
				for (int i= 0; i<passengers.size(); i++){
					if (passengers.get(i).busStop == "west"){
						passengers.get(i).getOut();
						passengers.remove(i);
					}
				}
			}
			
		}
	}
	
	
	
	public void busStopX(){
		timer.schedule(new TimerTask() { public void run() {
			finalX = currentDestinationX;
			//if (yPos == 216){
				stoppedNorthRight = false;
			//}
			//else if (yPos == 255){
				stoppedNorthLeft = false;
			//}
			//else if (yPos == 566){
				stoppedSouthRight = false;
			//}
			//else if (yPos == 525){
				stoppedSouthLeft = false;
			//}
			
			reachedDest = true;
			
			//guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
		}
	},
	2000);//how long to wait before running task
		
}
	public void busStopY(){
		timer.schedule(new TimerTask() { public void run() {
			yDestination = currentDestinationY;
			//if (xPos == 655){
				stoppedEastLeft = false;
			//}
			//if (xPos == 656){
				stoppedEastRight = false;
			//}
			//if (xPos ==210){
				stoppedWestRight = false;
			//}
			//if (xPos == 255){
				stoppedWestLeft = false;
			//}
		}
	},
	2000);//how long to wait before running task
		
}	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
	
	void guiMoveFromCurrentPositionTo(final int x, final int y){ // Brice - Method for traveling along the grid within the City Screen
	 	/*if(finalX == x && finalY == y) {
	 		//gui.reachedBuilding();
	 		System.out.println("HI");
	 		return;
	 	}*/
	 	//System.out.println("HI");
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
	 		//gui.reachedBuilding();
	 		System.out.println("HI");
	 		
	 		reachedDest = true;
	 		return;
	 	}
	 	
	 	if(stoppedEastRight ||
	 		stoppedNorthRight ||
	 		stoppedSouthRight ||
	 		stoppedWestRight) {
	 		moveTimer.schedule(new TimerTask() {
 				Object cookie = 1;
 				public void run() {
 					
 					System.out.println(finalX);
 					System.out.println(finalY);
 					stoppedEastRight = false;
 					stoppedWestRight = false;
 					stoppedNorthRight = false;
 					stoppedSouthRight = false;
 					guiMoveFromCurrentPositionTo(x, y);
 				}
 			},
 			2000);
	 		return;
	 	}
	 	
	 	//System.out.println("X: " + x);
	 	//System.out.println("FinalX: " + finalX);
	 	//System.out.println("Y: " + y);
	 	//System.out.println("FinalY: " + finalY);
	 	//System.out.println(grid[x][y-1]);
	 	
	 	//System.out.println(x);
	 	//System.out.println("1st condition: " + (x));
	 	//System.out.println(grid[x+1][y]);
	 	//System.out.println("2nd condition: " + (grid[x+1][y]));
	 	//Move RIGHT
	 	if(finalX - (x + 1)  >= 0 /*<= deltaX*/ && (grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) {
	 		//System.out.println("MOVING RIGHT");
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
	 		//System.out.println("MOVING LEFT");
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
	 		//System.out.println("MOVING UP");
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
	 		//System.out.println("MOVING DOWN");
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
	
	public void DoGoToLocation(Location destination) {
		//tempStill = true;
		xDestination = destination.getX();
		yDestination = destination.getY();
		//command = "GoingToLocation";
	}
}
