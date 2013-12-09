package simcity;

import java.awt.Color;
import java.awt.Graphics;

import simcity.PersonAgent.TransportationState;
import Gui.RoleGui;
import Gui.ScreenFactory;

/* Additions to be made:
 * removing the rect when a location is reached
 * adding a rect when an animation is needed
 */

public class PersonGui extends RoleGui {
	PersonAgent agent;
	boolean tempStill = true;
	boolean getOut = false;
	int count = 0;
	String command = "";
	Location destination;
	public String busStop = "";
	
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
			System.out.println("Arrived at the building");
			agent.reachedBuilding();
			tempStill = false;
		}
	/*	System.out.println(xPos);
		System.out.println(yPos);*/
		if (xPos == xDestination  && yPos == yDestination && tempStill && command.equalsIgnoreCase("GoingToBusStop")){
			if (busAtBusStop(busStop)){
				System.out.println("Got to bus");
				getHomeScreen().removeGui(this);
				tempStill = false;
				Location destBusStop = findDestinationBusStop();
				System.out.println(busStop);
				Bus.passengers.add(this);
				getOut = true;
			}
		}
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		//if car, blah blah blah
	}
	
	public void getOut(){
		xPos = ScreenFactory.main.bus.getXPos();
		yPos = ScreenFactory.main.bus.getYPos();
		getHomeScreen().addGui(this);
		xDestination = destination.getX();
		yDestination = destination.getY();
		tempStill = true;
		command = "GoingToLocation";
	} 
	
	public int findSmallestNumber(int a, int b, int c, int d){
		int smallest1;
		int smallest2;
		if (a<=b){
			smallest1 = a;
		}
		else {
			smallest1 = b;
		}
		if (c<=d){
			smallest2 = c;
		}
		else {
			smallest2 = d;
		}
		
		if (smallest1<=smallest2){
			return smallest1;
		}
		else {
			return smallest2;
			}
	}
	
	public boolean busAtBusStop(String s){
		boolean bus = false;
		if (s.equals("west")){
			bus = Bus.stoppedWestRight;
		}
		else if (s.equals("east")){
			bus = Bus.stoppedEastRight;
		}
		else if (s.equals("north")){
			bus =  Bus.stoppedNorthRight;
		}
		else if (s.equals("south")){
			bus = Bus.stoppedSouthRight;
		}
		return bus;
	}
	
	public Location findNearestBusStop(){
		int eastBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.eastBusStop.getX() - xPos)),2) + Math.pow(Math.abs((Bus.eastBusStop.getY() - yPos)), 2));
		int westBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.westBusStop.getX() - xPos)),2) + Math.pow(Math.abs((Bus.westBusStop.getY() - yPos)), 2));
		int northBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.northBusStop.getX() - xPos)),2) + Math.pow(Math.abs((Bus.northBusStop.getY() - yPos)), 2));
		int southBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.southBusStop.getX() - xPos)),2) + Math.pow(Math.abs((Bus.southBusStop.getY() - yPos)), 2));
		Location l = null;
		System.out.println("east " +eastBusStop);
		System.out.println("west " + westBusStop);
		System.out.println("north " +northBusStop);
		System.out.println("south " +southBusStop);
		int shortestDist = findSmallestNumber(eastBusStop, westBusStop, northBusStop, southBusStop);
		if (shortestDist == eastBusStop){
			 l =  Bus.eastBusStop;
			 busStop = "east";
		}
		else if (shortestDist == westBusStop){
			l =  Bus.westBusStop;
			busStop = "west";
		}
		else if (shortestDist == northBusStop){
			l =  Bus.northBusStop;
			busStop = "north";
		}
		else if (shortestDist == southBusStop){
			l =  Bus.southBusStop;
			busStop = "south";
		}
		System.out.println("Nearest busstop " + busStop);
		return l;
	}
	
	public Location findDestinationBusStop(){
		int eastBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.eastBusStop.getX() - destination.getX())),2) + Math.pow(Math.abs((Bus.eastBusStop.getY() - destination.getY())), 2));
		int westBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.westBusStop.getX() - destination.getX())),2) + Math.pow(Math.abs((Bus.westBusStop.getY() - destination.getY())), 2));
		int northBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.northBusStop.getX() - destination.getX())),2) + Math.pow(Math.abs((Bus.northBusStop.getY() - destination.getY())), 2));
		int southBusStop =(int) Math.sqrt(Math.pow(Math.abs((Bus.southBusStop.getX() - destination.getX())),2) + Math.pow(Math.abs((Bus.southBusStop.getY() - destination.getY())), 2));
		System.out.println("east "+ eastBusStop);
		System.out.println("west " +westBusStop);
		System.out.println("north " +northBusStop);
		System.out.println("south " + southBusStop);
		Location l = null;
		int shortestDist = findSmallestNumber(eastBusStop, westBusStop, northBusStop, southBusStop);
		if (shortestDist == eastBusStop){
			System.out.println("Closest east");
			 l =  Bus.eastBusStop;
			 busStop = "east";
		}
		else if (shortestDist == westBusStop){
			System.out.println("Closest west");
			l =  Bus.westBusStop;
			busStop = "west";
		}
		else if (shortestDist == northBusStop){
			System.out.println("Closest north");
			l =  Bus.northBusStop;
			busStop = "north";
		}
		else if (shortestDist == southBusStop){
			System.out.println("Closest south");
			l =  Bus.southBusStop;
			busStop = "south";
		}
		
		return l;
	}
	
	public void DoGoToLocation(Location destination) {
		if (agent.transportationState == TransportationState.Bus){
			this.destination = destination;
			System.out.println("going to bus stop");
			Location loc = findNearestBusStop();
			tempStill = true;
			xDestination = loc.getX();
			yDestination = loc.getY();
			System.out.println(xDestination);
			System.out.println(yDestination);
			command = "GoingToBusStop";
		}
		else if (agent.transportationState == TransportationState.Walking){
			System.out.println("Walking");
			tempStill = true;
			xDestination = destination.getX();
			yDestination = destination.getY();
			command = "GoingToLocation";
		}
	}
}