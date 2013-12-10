package simcity;

import java.awt.Graphics;

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
	
	public Car(int x, int y, int destX, int destY){
		xPos = x;
		yPos = y;
		xDestination = destX;
		yDestination = destY;
		currentImage = carDown;
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
		
		if (xPos == xDestination && yPos == yDestination && command == "moving"){ 
			command = "";
			ScreenFactory.main.removeGui(this);
			owner.arrived(xDestination, yDestination);
		}
	}
	
	public void setOwner(PersonGui p){
		owner = p;
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
}
