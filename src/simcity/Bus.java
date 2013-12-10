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
	public static boolean stoppedSouthRight = false;
	public static boolean stoppedEastRight = false; 
	public static boolean stoppedWestRight = false;
	
	boolean reachedDest = false;
	
	public void setGrid(Character[][] grid) {
		System.out.println("GRID SET");
		this.grid = grid;
	}
	
	//Locations for the bus stop locations and where the person is located when waiting for the bus
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
		
		if (this.type == "clockWise"){ //In case of adding second bus other direction (Not implemented)
			xPos = topLeftCorner.getX();
			yPos = topLeftCorner.getY();
			currentImage = busRight;
			
			finalX = topRightCorner.getX()/20;
			finalY = topRightCorner.getY()/20;
			
			guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
		}
		
		prevTile = 'I';
	}
	
	
	
	public Bus(String type, Character[][] grid2) {
		this.type = type;
		grid = grid2;
		
		if (this.type == "clockWise"){ //In case of adding second bus other direction (Not implemented)
			xPos = topLeftCorner.getX();
			yPos = topLeftCorner.getY();
			currentImage = busRight;
			finalX = topRightCorner.getX()/20;
			finalY = topRightCorner.getY()/20;
			
			guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
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
					finalX = botRightCorner.getX()/20;
					finalY = botRightCorner.getY()/20;
				}
				
				if (xPos == botRightCorner.getX() && yPos == botRightCorner.getY()){ //going west
					currentImage = busLeft;
					finalX = botLeftCorner.getX()/20;
					finalY = botLeftCorner.getY()/20;
				}
				
				if (xPos == botLeftCorner.getX() && yPos == botLeftCorner.getY()){//going north 
					currentImage = busUp;
					finalX = topLeftCorner.getX()/20;
					finalY = topLeftCorner.getY()/20;
				}
				
				if (xPos == topLeftCorner.getX() && yPos == topLeftCorner.getY()){ //going east
					currentImage = busRight;
					finalX = topRightCorner.getX()/20;
					finalY = topRightCorner.getY()/20;
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
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
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
	
	public void DoGoToLocation(Location destination) {
		xDestination = destination.getX();
		yDestination = destination.getY();
	}
}
