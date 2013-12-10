package simcity;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

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
	int finalX, finalY; //Brice - Grid stuff
	Character[][] grid;
	Character prevTile = ' ';
	Timer moveTimer = new Timer();

	public PersonAgent getAgent() {
		return agent;
	}

	public void setAgent(PersonAgent agent) {
		this.agent = agent;
	}

	public void setGrid(Character[][] grid) {
		this.grid = grid;
		prevTile = grid[xPos/20][yPos/20];
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
		if(xPos == xDestination && yPos == yDestination && tempStill && command.equalsIgnoreCase("GoingToLocation")&&finalX == xDestination/20 && finalY == yDestination/20) {
			//System.out.println("REACHED BUILDING");
			//agent.reachedBuilding();
			//reachedBuilding();
			tempStill = false;
		}
		/*	System.out.println(xPos);
		System.out.println(yPos);*/
		if (xPos == xDestination  && yPos == yDestination && tempStill && command.equalsIgnoreCase("GoingToBusStop")){
			//tempStill = false;
			if (busAtBusStop(busStop)){
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
		Location l = findNearestBusStop();
		xPos = l.getX();
		yPos = l.getY();

		System.out.println("DESTX: " + destination.getX());
		System.out.println("DESTY: " + destination.getY());

		getHomeScreen().addGui(this);
		finalX = destination.getX()/20;
		finalY = destination.getY()/20;
		xDestination = destination.getX();
		yDestination = destination.getY();
		tempStill = true;
		command = "GoingToLocation";
		guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
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

			l =  Bus.eastPickupStop;//new Location(Bus.eastBusStop.getX()-20, Bus.eastBusStop.getY());
			busStop = "east";
		}
		else if (shortestDist == westBusStop){
			l =  Bus.westPickupStop;//new Location(Bus.westBusStop.getX()+20, Bus.westBusStop.getY()+10);
			busStop = "west";
		}
		else if (shortestDist == northBusStop){
			l =  Bus.northPickupStop;//new Location(Bus.northBusStop.getX(), Bus.northBusStop.getY()+20);
			busStop = "north";
		}
		else if (shortestDist == southBusStop){
			l =  Bus.southPickupStop;//new Location(Bus.southBusStop.getX()-10, Bus.southBusStop.getY());
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
		System.out.println("west " + westBusStop);
		System.out.println("north " +northBusStop);
		System.out.println("south " + southBusStop);
		Location l = null;
		int shortestDist = findSmallestNumber(eastBusStop, westBusStop, northBusStop, southBusStop);
		if (shortestDist == eastBusStop){
			System.out.println("Closest east");
			l =  Bus.eastPickupStop;
			busStop = "east";
		}
		else if (shortestDist == westBusStop){
			System.out.println("Closest west");
			l =  Bus.westPickupStop;
			busStop = "west";
		}
		else if (shortestDist == northBusStop){
			System.out.println("Closest north");
			l =  Bus.northPickupStop;
			busStop = "north";
		}
		else if (shortestDist == southBusStop){
			System.out.println("Closest south");
			l =  Bus.southPickupStop;
			busStop = "south";
		}

		return l;
	}

	public void DoGoToLocation(Location destination) {
		if (agent.transportationState == TransportationState.Bus){
			this.destination = destination;
			System.out.println("going to bus stop");
			Location loc = findNearestBusStop();
			System.out.println("X BUS: " + loc.getX());
			System.out.println("Y BUS: " + loc.getY());
			System.out.println("X: " + xPos);
			System.out.println("Y: " + yPos);
			tempStill = true;
			finalX = loc.getX()/20;
			finalY = (loc.getY())/20;
			//xDestination = loc.getX();
			//yDestination = loc.getY();
			command = "GoingToBusStop";

			guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
		}
		else if (agent.transportationState == TransportationState.Walking){
			tempStill = true;
			this.destination = destination;
			finalX = destination.getX()/20;
			finalY = destination.getY()/20;
			//xDestination = destination.getX();
			//yDestination = destination.getY();
			command = "GoingToLocation";

			guiMoveFromCurrentPositionTo(xPos/20, yPos/20);
		}
	}

	public int getX() { return xPos;}

	public int getY() { return yPos;}

	public void reachedBuilding() {
		agent.reachedBuilding();
		tempStill = false;
	}

	public void moveTo(Location destination) {
		xDestination = destination.getX();
		yDestination = destination.getY();
	}

	void guiMoveFromCurrentPositionTo(final int x, final int y){ // Brice - Method for traveling along the grid within the City Screen
		if(finalX == x && finalY == y) {
			reachedBuilding();
			grid[x][y] = prevTile;
			return;
		}

		//Move RIGHT
		if(finalX - (x + 1)  >= 0 /*<= deltaX*/ && (grid[x+1][y] == 'S' || grid[x+1][y] == 'I')) {
			//System.out.println("MOVING RIGHT");
			try {
				char temp = grid[x+1][y];
				//grid[x+1][y] = 'P';
				//grid[x][y] = prevTile;
				prevTile = temp;
				moveTo(new Location((x+1)*20, y*20)); //Temporary timer or semaphore?
				moveTimer.schedule(new TimerTask() {
					Object cookie = 1;
					public void run() {
						guiMoveFromCurrentPositionTo(x+1, y);
					}
				},
				200);

			}
			catch(ConcurrentModificationException e) {
				System.err.println("Concurrent Modification Exception");
			}
		}

		//Move LEFT

		else if((x - 1) - finalX >= 0 && (grid[x-1][y] == 'S' || grid[x-1][y] == 'I')) {
			//System.out.println("MOVING LEFT");
			try {
				char temp = grid[x-1][y];
				//grid[x-1][y] = 'P';
				//grid[x][y] = prevTile;
				prevTile = temp;
				moveTo(new Location((x-1)*20, y*20)); //Temporary timer or semaphore?
				moveTimer.schedule(new TimerTask() {
					Object cookie = 1;
					public void run() {
						guiMoveFromCurrentPositionTo(x-1, y);
					}
				},
				200);
			}
			catch(ConcurrentModificationException e) {
				System.err.println("Concurrent Modification Exception");
			}
		}

		//Move UP
		else if((y - 1) - finalY  >= 0 && (grid[x][y-1] == 'S' || grid[x][y-1] == 'I')) {
			//System.out.println("MOVING UP");
			try {
				char temp = grid[x][y-1];
				//grid[x][y-1] = 'P';
			//	grid[x][y] = prevTile;
				prevTile = temp;
				moveTo(new Location(x*20, (y-1)*20)); //Temporary timer or semaphore?
				moveTimer.schedule(new TimerTask() {
					Object cookie = 1;
					public void run() {
						guiMoveFromCurrentPositionTo(x, y-1);
					}
				},
				200);
			}
			catch(ConcurrentModificationException e) {
				System.err.println("Concurrent Modification Exception");
			}
		}

		//Move DOWN
		else if(finalY - (y + 1) >= 0 && (grid[x][y+1] == 'S' || grid[x][y+1] == 'I')) {
			//System.out.println("MOVING DOWN");
			try {
				char temp = grid[x][y+1];
				//grid[x][y+1] = 'P';
				//grid[x][y] = prevTile;
				prevTile = temp;
				moveTo(new Location(x*20, (y+1)*20)); //Temporary timer or semaphore?
				moveTimer.schedule(new TimerTask() {
					Object cookie = 1;
					public void run() {
						guiMoveFromCurrentPositionTo(x, y+1);
					}
				},
				200);

			}
			catch(ConcurrentModificationException e) {
				System.err.println("Concurrent Modification Exception");
			}
		}

		else {
			moveTimer.schedule(new TimerTask() {
				Object cookie = 1;
				public void run() {
					guiMoveFromCurrentPositionTo(x, y);
				}
			},
			500);
		}
	}
}