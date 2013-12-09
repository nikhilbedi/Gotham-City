package simcity;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import agent.Role;
import simcity.Market.MarketWorkerRole;
import simcity.Market.interfaces.MarketWorker;
import Gui.RoleGui;


public class Truck extends RoleGui{
	ImageIcon currentImage;
	ImageIcon truckLeft = new ImageIcon(this.getClass().getResource("/resources/mika/truckLeft.jpg"));
	ImageIcon truckRight = new ImageIcon(this.getClass().getResource("/resources/mika/truckRight.jpg"));
	ImageIcon truckUp = new ImageIcon(this.getClass().getResource("/resources/mika/truckUp.jpg"));
	ImageIcon truckDown = new ImageIcon(this.getClass().getResource("/resources/mika/truckDown.jpg"));
	
	private MarketWorker worker;
	private Role role;
	
	
	public Truck(int x, int y, int destX, int destY){
		xPos = x;
		yPos = y;
		xDestination = destX;
		yDestination = destY;
		currentImage = truckDown;
	}
	
	
	public void setWorker(MarketWorker worker2, Role role){
		worker = worker2;
		this.role = role;
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
		
		if (xPos == xDestination && yPos == yDestination){ // && and restaurant is open
			worker.Sent(role);
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
	
}

