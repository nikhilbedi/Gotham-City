package simcity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import simcity.restaurants.restaurant4.Restaurant4CustomerRole.AgentEvent;
import Gui.RoleGui;

public class Bus extends RoleGui{
	Timer timer = new Timer();
	private String type;
	String destination;
	ImageIcon currentImage;
	ImageIcon busLeft = new ImageIcon(this.getClass().getResource("/resources/mika/busleft.jpg"));
	ImageIcon busRight = new ImageIcon(this.getClass().getResource("/resources/mika/busright.jpg"));
	ImageIcon busUp = new ImageIcon(this.getClass().getResource("/resources/mika/busUp.jpg"));
	ImageIcon busDown = new ImageIcon(this.getClass().getResource("/resources/mika/busDown.jpg"));
	public static List<PersonGui> passengers = new ArrayList<PersonGui>();
	int currentDestinationX;
	int currentDestinationY;
	int counterX = 0;
	int counterY = 0;
	public static boolean stoppedNorthRight = false; 
	public static boolean stoppedNorthLeft  = false;
	public static boolean stoppedSouthRight = false;
	public static boolean stoppedSouthLeft  = false;
	public static boolean stoppedEastRight = false; 
	public static boolean stoppedEastLeft  = false;
	public static boolean stoppedWestRight = false;
	public static boolean stoppedWestLeft  = false;
	
	public static Location northBusStop = new Location(400, 216, "North busstop");
	public static Location southBusStop = new Location(400, 566, "South busstop");
	public static Location eastBusStop = new Location(656, 352, "East busstop");
	public static Location westBusStop = new Location(210, 352, "West busstop");
	
	
	public Bus(String type){ //goes east
		this.type = type;
		if (this.type == "clockWise"){
			xPos = 210;
			yPos = 216;
			xDestination = 610;
			yDestination = 216;
			currentImage = busRight;
		}
		
		else if (this.type == "counterClockWise"){
			xPos = 245;
			yPos = 525;
			currentImage = busRight;
			xDestination = 575;
			yDestination = 525;
		}
	}
	
	
	
	@Override
	public void updatePosition(){
		super.updatePosition();
		if (type == "clockWise"){
			if (xPos == 610 && yPos == 216){  //going south
				currentImage = busDown;
				xPos = 656;
				yPos = 216;
				xDestination = 656;
				yDestination = 532;
			}
			
			if (xPos == 656 && yPos == 532){ //going west
				currentImage = busLeft;
				xPos = 610;
				yPos = 566;
				xDestination = 210;
				yDestination = 566;
			}
			
			if (xPos == 210 && yPos == 566){//going north 
				currentImage = busUp;
				xPos = 210;
				yPos = 508;
				xDestination = 210;
				yDestination = 216;
			}
			
			if (xPos == 210 && yPos == 216){ //going east
				xPos = 210;
				yPos = 216;
				xDestination = 610;
				yDestination = 216;
				currentImage = busRight;
			}
		}
		
		else if (type == "counterClockWise"){
			if (xPos ==575 && yPos == 525){ //going north
				currentImage = busUp;
				xPos = 615;
				yPos = 475;
				xDestination = 615;
				yDestination = 255;
			}
			if (xPos ==615 && yPos == 255){ //going west
				currentImage = busLeft;
				xPos = 580;
				yPos = 255;
				xDestination = 260;
				yDestination = 255;
			}
			
			if (xPos ==260 && yPos == 255){ //going south
				currentImage = busDown;
				xPos = 255;
				yPos = 256;
				xDestination = 255;
				yDestination = 496;
			}
			
			if (xPos ==255 && yPos == 496){ //going east
				currentImage = busRight;
				xPos = 245;
				yPos = 525;
				xDestination = 575;
				yDestination = 525;
			}		
			
			
		}
		
		if (xPos == 400){
				++counterX;
				if (xPos == 400 && yPos == 216){  //north bus stop locations 
					stoppedNorthRight = true;
					for (int i= 0; i<passengers.size(); i++){
						if (passengers.get(i).busStop == "north"){
							passengers.get(i).getOut();
							passengers.remove(i);
						}
					}
				}
				else if (xPos == 400 && yPos == 255){
					stoppedNorthLeft = true;
				}
				else if (xPos == 400 && yPos == 566){ //south bus stop locations
					stoppedSouthRight = true;
					for (int i= 0; i<passengers.size(); i++){
						if (passengers.get(i).busStop == "south"){
							passengers.get(i).getOut();
							passengers.remove(i);
						}
					}
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
		
		
		if ( yPos == 352){
			++counterY;
			if (yPos == 352 && xPos == 656){   //east bus stop location
				stoppedEastRight = true;
				for (int i= 0; i<passengers.size(); i++){
					if (passengers.get(i).busStop == "east"){
						passengers.get(i).getOut();
						passengers.remove(i);
					}
				}
			}
			else if (yPos == 351 && xPos ==615){
				stoppedEastLeft = true;
			}
			else if (yPos == 352 && xPos == 210){   //west bus stop location
				stoppedWestRight = true;
				for (int i= 0; i<passengers.size(); i++){
					if (passengers.get(i).busStop == "west"){
						passengers.get(i).getOut();
						passengers.remove(i);
					}
				}
			}
			else if (yPos == 350 && xPos == 255){
				stoppedWestLeft =true;
			}
			if (counterY == 1){
				currentDestinationY = yDestination;
				yDestination = yPos;
				busStopY();
			}
			
		}
	}
	
	
	
	public void busStopX(){
		timer.schedule(new TimerTask() { public void run() {
			xDestination = currentDestinationX;
			if (yPos == 216){
				stoppedNorthRight = false;
			}
			else if (yPos == 255){
				stoppedNorthLeft = false;
			}
			else if (yPos == 566){
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
			if (xPos == 655){
				stoppedEastLeft = false;
			}
			if (xPos == 656){
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
}
