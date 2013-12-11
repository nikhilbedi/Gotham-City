package simcity;

import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import Gui.RoleGui;
import Gui.ScreenFactory;

public class Car extends RoleGui{
	ImageIcon currentImage;
	ImageIcon carLeft = new ImageIcon(this.getClass().getResource("/resources/mika/carLeft.png"));
	ImageIcon carRight = new ImageIcon(this.getClass().getResource("/resources/mika/carRight.png"));
	ImageIcon carUp = new ImageIcon(this.getClass().getResource("/resources/mika/carUp.png"));
	ImageIcon carDown = new ImageIcon(this.getClass().getResource("/resources/mika/carDown.png"));
	public String command = "";
	private PersonGui owner;
	
	int currentDestinationX;
	int currentDestinationY;

	int finalX, finalY;
	
	Character[][] grid;
	char prevTile = 'I';
	Timer moveTimer = new Timer();
	
	public Car(int x, int y, int destX, int destY){
		xPos = x;
		yPos = y;
		grid = TheCity.getGrid();
		//xDestination = destX;
		//yDestination = destY;
		finalX = destX/20;
		finalY = destY/20;
		currentImage = carDown;
		command = "moving";
		guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
	}
	@Override
	public void updatePosition(){
		if (xPos < xDestination){
			xPos+=speed;
			currentImage = carRight;
		}
		else if (xPos > xDestination){
			xPos-=speed;
			currentImage = carLeft;
		}
		if (yPos < yDestination){
			yPos+=speed;
			currentImage = carDown;
		}

		else if (yPos > yDestination){
			yPos-=speed;
			currentImage = carUp;
		}
		
		if (xPos/20 == finalX && yPos/20 == finalY && command == "moving"){ 
			command = "";
			ScreenFactory.main.removeGui(this);
			owner.arrived(finalX*20, finalY*20);
		}
	}
	
	public void setOwner(PersonGui p){
		owner = p;
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
	
	void guiMoveFromCurrentPositionTo(final int x, final int y){ // Brice - Method for traveling along the grid within the City Screen
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
	 		//reachedDest = true;
	 		grid[x][y] = prevTile;
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
