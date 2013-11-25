package simcity;

import java.awt.Color;
import java.awt.Graphics;

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
		super.setColor(Color.yellow);
		agent = c;
		
		
		/*
		xPos = agent.getLocation().getX();
		yPos = agent.getLocation().getY();
		xDestination = agent.getLocation().getX();
		yDestination = agent.getLocation().getY(); */
	}
	
	public PersonGui(){ 
		super.setColor(Color.yellow);
		
		/*
		xPos = agent.getLocation().getX();
		yPos = agent.getLocation().getY();
		xDestination = agent.getLocation().getX();
		yDestination = agent.getLocation().getY(); */
	}

	public void updatePosition() {
		super.updatePosition();
		if(xPos == xDestination && yPos == yDestination && tempStill && command.equalsIgnoreCase("GoingToLocation")) {
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
}