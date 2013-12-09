package simcity;

import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import Gui.RoleGui;

public class Bus extends RoleGui{
	Timer timer = new Timer(), moveTimer = new Timer();
	private String type;
	String destination;
	ImageIcon currentImage;
	ImageIcon busLeft = new ImageIcon(this.getClass().getResource("/resources/mika/busleft.jpg"));
	ImageIcon busRight = new ImageIcon(this.getClass().getResource("/resources/mika/busright.jpg"));
	ImageIcon busUp = new ImageIcon(this.getClass().getResource("/resources/mika/busUp.jpg"));
	ImageIcon busDown = new ImageIcon(this.getClass().getResource("/resources/mika/busDown.jpg"));
	int currentDestinationX;
	int currentDestinationY;
	int counterX = 0;
	int counterY = 0;
	int finalX, finalY;
	
	Character[][] grid;
	char prevTile = 'R';
	
	Location topLeftCorner = new Location(140, 160);
	Location topRightCorner = new Location(640, 160);
	Location botLeftCorner = new Location(140, 660);
	Location botRightCorner = new Location(640, 660);
	
	public boolean stoppedNorthRight = false; 
	public boolean stoppedNorthLeft  = false;
	public boolean stoppedSouthRight = false;
	public boolean stoppedSouthLeft  = false;
	public boolean stoppedEastRight = false; 
	public boolean stoppedEastLeft  = false;
	public boolean stoppedWestRight = false;
	public boolean stoppedWestLeft  = false;
	
	boolean reachedDest = false;
	
	public void setGrid(Character[][] grid) {
		System.out.println("GRID SET");
		this.grid = grid;
	}
	
	public Bus(String type){ //goes east
		this.type = type;
		if (this.type == "clockWise"){
			xPos = topLeftCorner.getX();
			yPos = topLeftCorner.getY();
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
			reachedDest = false;
			if (type == "clockWise"){
				if (xPos == topRightCorner.getX() && yPos == topRightCorner.getY()){  //going south
					currentImage = busDown;
					//xPos = 655;
					//yPos = 215;
					finalX = botRightCorner.getX()/20;
					finalY = botRightCorner.getY()/20;
					
					guiMoveFromCurrentPositionTo(topRightCorner.getX()/20, topRightCorner.getY()/20);
				}
				
				if (xPos == botRightCorner.getX() && yPos == botRightCorner.getY()){ //going west
					currentImage = busLeft;
					//xPos = 610;
					//yPos = 565;
					finalX = botLeftCorner.getX()/20;
					finalY = botLeftCorner.getY()/20;
					
					guiMoveFromCurrentPositionTo(botRightCorner.getX()/20, botRightCorner.getY()/20);
				}
				
				if (xPos == botLeftCorner.getX() && yPos == botLeftCorner.getY()){//going north 
					currentImage = busUp;
					//xPos = 210;
					//yPos = 507;
					finalX = topLeftCorner.getX()/20;
					finalY = topLeftCorner.getY()/20;
					
					guiMoveFromCurrentPositionTo(botLeftCorner.getX()/20, botLeftCorner.getY()/20);
				}
				
				if (xPos == topLeftCorner.getX() && yPos == topLeftCorner.getY()){ //going east
					currentImage = busRight;
					//xPos = 210;
					//yPos = 215;
					finalX = topRightCorner.getX()/20;
					finalY = topRightCorner.getY()/20;
					guiMoveFromCurrentPositionTo(topLeftCorner.getX()/20, topLeftCorner.getY()/20);
				}
			}
			
			else if (type == "counterClockWise"){
				if (xPos ==botRightCorner.getX() && yPos == botRightCorner.getY()){ //going north
					currentImage = busUp;
					//xPos = 615;
					//yPos = 475;
					finalX = topRightCorner.getX();
					finalY = topRightCorner.getY();
				}
				if (xPos == topRightCorner.getX() && yPos == topRightCorner.getY()){ //going west
					currentImage = busLeft;
					//xPos = 580;
					//yPos = 255;
					finalX = topLeftCorner.getX();
					finalY = topLeftCorner.getY();
				}
				
				if (xPos == topLeftCorner.getX() && yPos == topLeftCorner.getY()){ //going south
					currentImage = busDown;
					//xPos = 255;
					//yPos = 256;
					finalX = botLeftCorner.getX();
					finalY = botLeftCorner.getY();
				}
				
				if (xPos == botLeftCorner.getX() && yPos == botLeftCorner.getY()){ //going east
					currentImage = busRight;
					//xPos = 245;
					//yPos = 525;
					finalX = botRightCorner.getX();
					finalY = botRightCorner.getY();
				}		
					System.out.println();
				
			}
		}
		/*if (xPos == 401 || xPos == 400){
				++counterX;
				if (xPos == 400 && yPos == 215){
				 stoppedNorthRight = true;
				}
				else if (xPos == 400 && yPos == 255){
					stoppedNorthLeft = true;
				}
				else if (xPos == 400 && yPos == 565){
					stoppedSouthRight = true;
				}
				else if (xPos == 401 && yPos == 525){
					stoppedSouthLeft = true;
				}
				if (counterX == 1){
					currentDestinationX = xDestination;
					xDestination = xPos;
					busStopX();
				}
			}
		
		
		if (yPos == 351 || yPos == 350){
			++counterY;
			if (yPos == 351 && xPos == 655){
				stoppedEastRight = true;
			}
			else if (yPos == 351 && xPos ==615){
				stoppedEastLeft = true;
			}
			else if (yPos == 351 && xPos == 210){
				stoppedWestRight = true;
			}
			else if (yPos == 350 && xPos == 255){
				stoppedWestLeft =true;
			}
			if (counterY == 1){
				currentDestinationY = yDestination;
				yDestination = yPos;
				busStopY();
			}
			
		}*/
	}
	
	
	
	public void busStopX(){
		timer.schedule(new TimerTask() { public void run() {
			xDestination = currentDestinationX;
			if (yPos == 215){
				stoppedNorthRight = false;
			}
			else if (yPos == 255){
				stoppedNorthLeft = false;
			}
			else if (yPos == 565){
				stoppedSouthRight = false;
			}
			else if (yPos == 525){
				stoppedSouthLeft = false;
			}
			counterX = 0;
		}
	},
	2000);//how long to wait before running task
		
}
	public void busStopY(){
		timer.schedule(new TimerTask() { public void run() {
			yDestination = currentDestinationY;
			if (yPos == 655){
				stoppedEastLeft = false;
			}
			if (yPos == 615){
				stoppedEastRight = false;
			}
			if (xPos ==210){
				stoppedWestRight = false;
			}
			if (xPos == 255){
				stoppedWestLeft = false;
			}
			counterY = 0;
		}
	},
	2000);//how long to wait before running task
		
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
	 	
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
	 		//gui.reachedBuilding();
	 		reachedDest = true;
	 		System.out.println("HI");
	 		return;
	 	}
	 	
	 	System.out.println("X: " + x);
	 	System.out.println("FinalX: " + finalX);
	 	System.out.println("Y: " + y);
	 	System.out.println("FinalY: " + finalY);
	 	System.out.println(grid[x][y-1]);
	 	
	 	//System.out.println(x);
	 	//System.out.println("1st condition: " + (x));
	 	//System.out.println(grid[x+1][y]);
	 	//System.out.println("2nd condition: " + (grid[x+1][y]));
	 	//Move RIGHT
	 	if(finalX - (x + 1)  >= 0 /*<= deltaX*/ && (grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) {
	 		System.out.println("MOVING RIGHT");
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
	 			250);
	 			
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move LEFT
	 	
	 	else if((x - 1) - finalX >= 0 && (grid[x-1][y] == 'R' || grid[x-1][y] == 'I')) {
	 		System.out.println("MOVING LEFT");
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
	 			250);
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move UP
	 	else if((y - 1) - finalY  >= 0 && (grid[x][y-1] == 'R' || grid[x][y-1] == 'I')) {
	 		System.out.println("MOVING UP");
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
	 			250);
	 		}
	 		catch(ConcurrentModificationException e) {
	 			System.err.println("Concurrent Modification Exception");
	 		}
	 	}
	 	
	 	//Move DOWN
	 	else if(finalY - (y + 1) >= 0 && (grid[x][y+1] == 'R' || grid[x][y+1] == 'I')) {
	 		System.out.println("MOVING DOWN");
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
	 			250);
	 			
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
