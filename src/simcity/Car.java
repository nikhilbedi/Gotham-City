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
	
	char direction = ' ';
	
	void guiMoveFromCurrentPositionTo(final int x, final int y){ // Brice - Method for traveling along the grid within the City Screen
	 	if(Math.abs(finalX - x) < 1 && Math.abs(finalY - y) < 1) {
	 		direction = ' ';
	 		grid[x][y] = prevTile;
	 		
	 		command = "";
			ScreenFactory.main.removeGui(this);
			owner.arrived(finalX*20, finalY*20);
	 		//reachedDest = true;
	 		return;
	 	}
	 	
	 	if(direction == 'u') {
	 		direction = ' ';
	 		moveUp(x, y);
	 		return;
	 	}
	 	else if(direction == 'd') {
	 		direction = ' ';
	 		moveDown(x, y);
	 		return;
	 	}
	 	else if(direction == 'r') {
	 		direction = ' ';
	 		moveRight(x, y);
	 		return;
	 	}
	 	else if(direction == 'l') {
	 		direction = ' ';
	 		moveLeft(x, y);
	 		return;
	 	}
	 		
	 	//Move RIGHT
	 	if(finalX - (x + 1)  >= 0 /*<= deltaX*/) {
	 		if((grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) {
	 			try {
	 				direction = ' ';
	 				moveRight(x, y);
	 				return;
	 			}
	 			catch(ConcurrentModificationException e) {
	 				System.err.println("Concurrent Modification Exception");
	 			}
	 		}
	 		else {
	 			if((grid[x][y-1] == 'R' || grid[x][y-1] == 'I')  && (y - 1) - finalY  >= 0) { //Move Up to dodge obstacle
	 				direction = 'r';
	 				moveUp(x, y);
	 				return;
	 			}
	 			else if((grid[x][y+1] == 'R' || grid[x][y+1] == 'I')  && finalY - (y + 1) >= 0) { //Move Down to dodge obstacle
	 				direction = 'r';
	 				moveDown(x, y);
	 				return;
	 			}/*
	 			else
	 				moveTimer.schedule(new TimerTask() {
	 			 		Object cookie = 1;
	 			 				public void run() {
	 			 					guiMoveFromCurrentPositionTo(x, y);
	 			 					return;
	 			 				}
	 			 			},
	 			 			500);*/
	 		}
	 	}
	 	
	 	//Move LEFT
	 	else if((x - 1) - finalX >= 0) {
	 		if((grid[x-1][y] == 'R' || grid[x-1][y] == 'I')) {
	 			try {
	 				//canMoveHorizontally = true;
	 				direction = ' ';
	 				moveLeft(x, y); //Move left if clear
	 				return;
	 			}
	 			catch(ConcurrentModificationException e) {
	 				System.err.println("Concurrent Modification Exception");
	 			}
	 		}
	 		else {
	 			if((grid[x][y-1] == 'R' || grid[x][y-1] == 'I') && (y - 1) - finalY  >= 0) { //Move Up to dodge obstacle
	 				direction = 'l';
	 				moveUp(x, y);
	 				return;
	 			}
	 			else if((grid[x][y+1] == 'R' || grid[x][y+1] == 'I') && finalY - (y + 1) >= 0) { //Move Down to dodge obstacle
	 				direction = 'l';
	 				moveDown(x, y);
	 				return;
	 			}/*
	 			else
	 				moveTimer.schedule(new TimerTask() {
	 			 		Object cookie = 1;
	 			 				public void run() {
	 			 					guiMoveFromCurrentPositionTo(x, y);
	 			 					return;
	 			 				}
	 			 			},
	 			 			500);*/
	 		}
	 	}
	 	
	 	//Move UP
	 	else if((y - 1) - finalY  >= 0) {
	 		if((grid[x][y-1] == 'R' || grid[x][y-1] == 'I')) {
	 			try {
	 				direction = ' ';
	 				moveUp(x, y);
	 				return;
	 			}
	 			catch(ConcurrentModificationException e) {
	 				System.err.println("Concurrent Modification Exception");
	 			}
	 		}
	 		else {
	 			if((grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) { //Move Right to dodge obstacle
	 				direction = 'u';
	 				moveRight(x, y);
	 				return;
	 			}
	 			else if((grid[x-1][y] == 'R' || grid[x-1][y] == 'I')) { //Move Left to dodge obstacle
	 				direction = 'u';
	 				moveLeft(x, y);
	 				return;
	 			}
	 			/*else
	 				moveTimer.schedule(new TimerTask() {
	 			 		Object cookie = 1;
	 			 				public void run() {
	 			 					guiMoveFromCurrentPositionTo(x, y);
	 			 					return;
	 			 				}
	 			 			},
	 			 			500);*/
	 		}
	 	}
	 	
	 	//Move DOWN
	 	else if(finalY - (y + 1) >= 0) {
	 		if((grid[x][y+1] == 'R' || grid[x][y+1] == 'I')) {
	 			try {
	 				direction = ' ';
	 				moveDown(x, y);
	 				return;
	 			}
	 			catch(ConcurrentModificationException e) {
	 				System.err.println("Concurrent Modification Exception");
	 			}
	 		}
	 		else {
	 			if((grid[x+1][y] == 'R' || grid[x+1][y] == 'I')) { //Move Right to dodge obstacle
	 				direction = 'd';
	 				moveRight(x, y);
	 				return;
	 			}
	 			else if((grid[x-1][y] == 'R' || grid[x-1][y] == 'I')) { //Move Left to dodge obstacle
	 				direction = 'd';
	 				moveLeft(x, y);
	 				return;
	 			}
	 			/*else
	 				moveTimer.schedule(new TimerTask() {
	 			 		Object cookie = 1;
	 			 				public void run() {
	 			 					guiMoveFromCurrentPositionTo(x, y);
	 			 					return;
	 			 				}
	 			 			},
	 			 			500);*/
	 		}
	 	}
	 	
	 		else {
	 			direction = ' ';
	 			moveTimer.schedule(new TimerTask() {
	 		Object cookie = 1;
	 				public void run() {
	 					guiMoveFromCurrentPositionTo(x, y);
	 				}
	 			},
	 			500);
	 		}
	 	//}
    }


	private void moveDown(final int x, final int y) {
		//canMoveHorizontally = true;
		char temp = grid[x][y+1];
		//grid[x][y+1] = 'B';
		//grid[x][y] = prevTile;
		prevTile = temp;
		DoGoToLocation(new Location(x*20, (y+1)*20)); //Temporary timer or semaphore?
		moveTimer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				guiMoveFromCurrentPositionTo(x, y+1);
			}
		},
		300);
	}


	private void moveUp(final int x, final int y) {
		//canMoveVertically = true;
		char temp = grid[x][y-1];
		//grid[x][y-1] = 'B';
		//grid[x][y] = prevTile;
		prevTile = temp;
		DoGoToLocation(new Location(x*20, (y-1)*20)); //Temporary timer or semaphore?
		moveTimer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				guiMoveFromCurrentPositionTo(x, y-1);
			}
		},
		300);
	}


	private void moveLeft(final int x, final int y) {
		char temp = grid[x-1][y];
		//grid[x-1][y] = 'B';
		//grid[x][y] = prevTile;
		prevTile = temp;
		DoGoToLocation(new Location((x-1)*20, y*20)); //Temporary timer or semaphore?
		moveTimer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				guiMoveFromCurrentPositionTo(x-1, y);
			}
		},
		300);
	}


	private void moveRight(final int x, final int y) {
		//canMoveHorizontally = true;
		char temp = grid[x+1][y];
		//grid[x+1][y] = 'B';
		//grid[x][y] = prevTile;
		prevTile = temp;
		DoGoToLocation(new Location((x+1)*20, y*20)); //Temporary timer or semaphore?
		moveTimer.schedule(new TimerTask() {
			Object cookie = 1;
			public void run() {
				guiMoveFromCurrentPositionTo(x+1, y);
			}
		},
		300);
	}
	
	public void DoGoToLocation(Location destination) {
		xDestination = destination.getX();
		yDestination = destination.getY();
	}
}
