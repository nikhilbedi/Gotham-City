package simcity;

import java.awt.Color;
import java.awt.Graphics;

import Gui.RoleGui;

public class PersonGui extends RoleGui {
	PersonAgent agent;
	
	public PersonGui(PersonAgent c){ 
		super.setColor(Color.yellow);
		agent = c;
		
		
		/*
		xPos = agent.getLocation().getX();
		yPos = agent.getLocation().getY();
		xDestination = agent.getLocation().getX();
		yDestination = agent.getLocation().getY(); */
	}

	public void updatePosition() {
		super.updatePosition();
		if(xPos == xDestination && yPos == yDestination) {
			agent.reachedBuilding();
		}
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		//if car, blah blah blah
	}
	
	public void DoGoToLocation(Location destination) {
		xDestination = destination.getX();
		yDestination = destination.getY();
	}
}