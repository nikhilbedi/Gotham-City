package simcity.restaurants.restaurant1.gui;


import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.WaiterRole;

import Gui.RoleGui;

import java.awt.*;
import java.util.*;

public class WaiterGui extends RoleGui {

    private WaiterRole agent = null;

    private int xPos = 100, yPos = 20;//default waiter position
    private int xDestination = 100, yDestination = 20;//default start position
    private int xHome = 100;

    //Taking final out of public static final
    public static int xTable = 200;
    public static int yTable = 250;

    //Notifies when to release a permit
    public boolean release = false;
    private boolean showFood = false;
    private String foodType = "";

    //going to replace static coordinates with a map of <table number, coordinates>
    private Map<Integer, Dimension> tableCoords = new HashMap<Integer, Dimension>();


    public WaiterGui(WaiterRole agent, int home) {
        this.agent = agent;
        //Setting coordinates
		tableCoords.put(1, new Dimension(100, 150));
		tableCoords.put(2, new Dimension(300, 150));
		tableCoords.put(3, new Dimension(500, 150));
		tableCoords.put(4, new Dimension(700, 150));
		xHome += home*25;
		xPos = xHome;
		xDestination = xHome;
    }

    public void updatePosition() {
        if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

	//Redo this if statement so the agent knows when he is at any table
        if (xPos == xDestination && yPos == yDestination)  {
		    if(release) {
				agent.doneWithTask();
				release = false;
		    }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
		if(showFood) {
		    g.drawString(foodType, xPos+10, yPos-10);
		}
    }

    public boolean isPresent() {
        return true;
    }
    
    public void DoGetCustomer(int x, int y) {
		release = true;
		xDestination = x;
		yDestination = y;
    }

    public void DoSeatCustomer(CustomerRole customer, int table) {
	release = true;
	//Send customer's gui dimension coordinates
	//have the waiter walk to the table first
	Dimension whereToGo = tableCoords.get(table);
	xDestination = (int) whereToGo.getWidth() + 20;
	yDestination = (int) whereToGo.getHeight() - 20;
	//This next statement looks like a mess, let's break it down
	//We use the customer gui to send the customer which x and y coordinate to head to
	customer.getGui().DoGoToSeat((int) whereToGo.getWidth(), (int) whereToGo.getHeight());
    }
    
    public void DoGoToTable(int table) {
	release = true;
	Dimension whereToGo = tableCoords.get(table);
	xDestination = (int) whereToGo.getWidth() + 20;
	yDestination = (int) whereToGo.getHeight() - 20;
    }

    public void DoLeaveCustomer() {
        xDestination = xHome;
        yDestination = 20;
    }

    public void DoGoToCook() {
		release = true;
		xDestination = 800;
		yDestination = 75;
    }
    
    public void DoWaitingNearKitchen() {
    	xDestination = 775;
    	yDestination = 15;
    }

    public void DoGoToCashier() {
		release = true;
		xDestination = -40;
		yDestination = 300;
    }

    public void showFood(String choice) {
		foodType = choice.substring(0,2) + "?";
		showFood = true;
    }
    
    public void setFood(CustomerRole c, String choice) {
	showFood = false;
	c.getGui().eatingFood(choice);
    } 

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean onBreak() {
	return agent.onBreak();
    }
}
