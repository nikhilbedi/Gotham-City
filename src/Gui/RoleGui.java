package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class RoleGui {

	///private CustomerAgent agent = null;

	private int xPos, yPos;
	private int xDestination, yDestination;
	
	
	private enum Command {noCommand, commandA, commandB, commandC};
	
	private Command command = Command.noCommand;
	
	public static final int guiSize = 20;
	
	Color myColor;

	
	public RoleGui(){
		//agent = c;
		xPos = 10;
		yPos = 10;
		xDestination = 10;
		yDestination = 10;
		command = Command.commandA;
		myColor = Color.gray;
		//this.gui = gui;
	}
	
	
	public void updatePosition(){
		if (xPos < xDestination)
            xPos+=2;
        else if (xPos > xDestination)
            xPos-=2;

        if (yPos < yDestination)
            yPos+=2;
        else if (yPos > yDestination)
            yPos-=2;
    }
    
	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillRect(xPos, yPos, guiSize, guiSize);
    }
	public void doLeaveBuilding(){
		
	}
}
