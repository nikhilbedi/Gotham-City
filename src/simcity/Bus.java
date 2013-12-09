package simcity;

import java.awt.Graphics;
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
	int currentDestinationX;
	int currentDestinationY;
	int counterX = 0;
	int counterY = 0;
	public boolean stoppedNorthRight = false; 
	public boolean stoppedNorthLeft  = false;
	public boolean stoppedSouthRight = false;
	public boolean stoppedSouthLeft  = false;
	public boolean stoppedEastRight = false; 
	public boolean stoppedEastLeft  = false;
	public boolean stoppedWestRight = false;
	public boolean stoppedWestLeft  = false;
	public Bus(String type){ //goes east
		this.type = type;
		if (this.type == "clockWise"){
			xPos = 210;
			yPos = 215;
			xDestination = 610;
			yDestination = 215;
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
			if (xPos == 610 && yPos == 215){  //going south
				currentImage = busDown;
				xPos = 655;
				yPos = 215;
				xDestination = 655;
				yDestination = 531;
			}
			
			if (xPos == 655 && yPos == 531){ //going west
				currentImage = busLeft;
				xPos = 610;
				yPos = 565;
				xDestination = 210;
				yDestination = 565;
			}
			
			if (xPos == 210 && yPos == 565){//going north 
				currentImage = busUp;
				xPos = 210;
				yPos = 507;
				xDestination = 210;
				yDestination = 215;
			}
			
			if (xPos == 210 && yPos == 215){ //going east
				xPos = 210;
				yPos = 215;
				xDestination = 610;
				yDestination = 215;
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
				System.out.println();
			
		}
		
		if (xPos == 401 || xPos == 400){
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
			
		}
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
}
