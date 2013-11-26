package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.Menu;

import java.util.ArrayList;

import Gui.RoleGui;

import simcity.restaurants.restaurant5.Restaurant5CustomerRole;

public class Restaurant5CustomerGui extends RoleGui implements Gui{

	private Restaurant5CustomerRole role = null;
	private boolean isPresent = false;
	private boolean isHungry = false;

	//private HostAgent host;
	//RestaurantGui gui;
	Menu menu;

	private enum Command {noCommand, followHost, LeaveRestaurant, goToCashier, GoToWaitArea};
	private Command command=Command.noCommand;
	
	public static final int xTable = 200;
	public static final int yTable = 250;
	public static final int customerSize = 20;
	
	public static final int xCash = 400;
	public static final int yCash = 0;
	
	public static int waitX = 50;
	public static int waitY = 16;
	
	

	public Restaurant5CustomerGui(Restaurant5CustomerRole c, RestaurantGui gui){ //HostAgent m) {
		role = c;
		xPos = -40;
		yPos = -40;
		xDestination = -40;
		yDestination = -40;
		setColor(Color.GREEN);
		//maitreD = m;
		//this.gui = gui;
	}

	public void updatePosition() {
		super.updatePosition();

		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.followHost){ 
				role.doneMoving();	
				command = Command.noCommand;
			}
			else if(command == Command.goToCashier){
				role.doneMoving();
				command = Command.noCommand;
			}
			else if(command == Command.GoToWaitArea){
				role.doneMoving();
				command = Command.noCommand;
			}
			else if (command==Command.LeaveRestaurant) {
				isHungry = false;
			//	gui.setCustomerEnabled(role);
				role.doneMoving();
				command = Command.noCommand;
				}
		}
	}

	public void draw(Graphics2D g) {
		super.draw(g);
	}

	public boolean isPresent() {
		return isPresent;
	}
	public void setHungry() {
		isHungry = true;
		role.becomesHungry();
		setPresent(true);
	}
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	public void goToSeat(Dimension dest){
		xDestination = dest.width;
		yDestination = dest.height;
		command = Command.followHost;
	}
	
	public void DoFollowWaiter(){
		command = Command.noCommand;
	}

	public void DoLeaveRestaurant() {
		xDestination = -20;
		yDestination = -20;
		command = Command.LeaveRestaurant;
	}
	public void DoGoToWaitingArea(){
		xDestination = waitX;
		yDestination = waitY;
		command = Command.GoToWaitArea;
	}
	public void DoGoToCashier() {
		xDestination = xCash;
		yDestination = yCash;
		command = Command.goToCashier;
	}

}
