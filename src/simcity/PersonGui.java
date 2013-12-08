package simcity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.TimerTask;

import Gui.RoleGui;

/* Additions to be made:
 * removing the rect when a location is reached
 * adding a rect when an animation is needed
 */

public class PersonGui extends RoleGui {
	PersonAgent agent;
	boolean tempStill = true;
	int count = 0;
	String command = "";
	public PersonAgent getAgent() {
		return agent;
	}

	public void setAgent(PersonAgent agent) {
		this.agent = agent;
	}

	boolean reachedBuildingLocation;
	
	public PersonGui(PersonAgent c){ 
		//super();
		super.setColor(Color.yellow);
		agent = c;
		
		xPos = 200; //Possibly remove this
		xDestination = 200;
		yPos = 220;
		yDestination = 220;
		
		xPos = 160; //Possibly remove this
		xDestination = 160;
		yPos = 180;
		yDestination = 180;
		/*
		xPos = agent.getLocation().getX();
		yPos = agent.getLocation().getY();
		xDestination = agent.getLocation().getX();
		yDestination = agent.getLocation().getY(); */
	}
	
	public PersonGui(){ 
		//super();
		super.setColor(Color.yellow);
		
		xPos = 200; //Possibly remove this
		xDestination = 200;
		yPos = 220;
		yDestination = 220;
		
		xPos = 160; //Possibly remove this
		xDestination = 160;
		yPos = 180;
		yDestination = 180;
		/*
		xPos = agent.getLocation().getX();
		yPos = agent.getLocation().getY();
		xDestination = agent.getLocation().getX();
		yDestination = agent.getLocation().getY(); */
	}

	public void updatePosition() {
		super.updatePosition();
		if(xPos == xDestination && yPos == yDestination && tempStill && command.equalsIgnoreCase("GoingToLocation")&&agent.finalX == xDestination && agent.finalY == yDestination) {
			System.out.println("REACHED BUILDING");
			agent.reachedBuilding();
			tempStill = false;
		}
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		//if car, blah blah blah
	}
	
	public void DoGoToLocation(Location destination) {
		tempStill = true;
		xDestination = destination.getX();
		yDestination = destination.getY();
		command = "GoingToLocation";
	}
	
	public int getX() { return xPos;}
	
	public int getY() { return yPos;}

	public void reachedBuilding() {
		System.out.println("REACHED BUILDING");
		agent.reachedBuilding();
		tempStill = false;
	}
}