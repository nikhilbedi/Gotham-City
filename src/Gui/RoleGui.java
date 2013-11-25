package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import agent.Role;

public class RoleGui {
	protected int xPos, yPos;
	protected int xDestination, yDestination;
	private static int speed = 2;
	
	private enum Command {noCommand, commandA, commandB, commandC};

	private Command command = Command.noCommand;

	public static final int guiSize = 20;
	
	protected Color myColor;
	
	protected Screen homeScreen;

	public Screen getHomeScreen() {
		System.out.println("Here is home Screen: " + homeScreen);
		return homeScreen;
	}


	public RoleGui(){
		//agent = c;
		//System.err.println("Here we are 1");    
		xPos = 10;
		yPos = 10;
		xDestination = 10;
		yDestination = 10;
		command = Command.commandA;
		myColor = Color.gray;
		//this.gui = gui;
	}


	public RoleGui(Role r, Screen meScreen) {
		// TODO Auto-generated constructor stub
	}


	public void updatePosition(){
		if (xPos < xDestination)
			xPos+=speed;
		else if (xPos > xDestination)
			xPos-=speed;

		if (yPos < yDestination)
			yPos+=speed;
		else if (yPos > yDestination)
			yPos-=speed;
	}

	public void draw(Graphics g){
		g.setColor(myColor);
		g.fillRect(xPos, yPos, guiSize, guiSize);

    }
	public void doLeaveBuilding(){
	}
	public void setColor(Color c){
		myColor = c;
	}
	public void setHomeScreen(Screen s){
		homeScreen = s;
	}
}
