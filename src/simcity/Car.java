package simcity;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import Gui.RoleGui;

public class Car extends RoleGui{
	ImageIcon currentImage;
	ImageIcon carLeft = new ImageIcon(this.getClass().getResource("/resources/mika/carLeft.jpg"));
	ImageIcon carRight = new ImageIcon(this.getClass().getResource("/resources/mika/carRight.jpg"));
	ImageIcon carUp = new ImageIcon(this.getClass().getResource("/resources/mika/carUp.jpg"));
	ImageIcon carDown = new ImageIcon(this.getClass().getResource("/resources/mika/carDown.jpg"));
	
	
	
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
		
		if (xPos == xDestination && yPos == yDestination){ 
			
		}
	}
	
	@Override
	public void draw(Graphics g){
		g.drawImage(currentImage.getImage(), xPos, yPos, null);
	}
}
