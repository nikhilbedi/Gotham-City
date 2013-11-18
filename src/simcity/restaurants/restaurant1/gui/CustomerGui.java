package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.HostRole;

import java.awt.*;

public class CustomerGui implements Gui{

    private CustomerRole agent = null;
    private boolean isPresent = false;
    private boolean isHungry = false;

    //private HostAgent host;
    RestaurantGui gui;

    private int xPos, yPos;
    private int xDestination, yDestination;
    private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant};
    private Command command=Command.noCommand;

    private String myFood;

    public static final int xTable = 200;
    public static final int yTable = 250;

    public CustomerGui(CustomerRole c, RestaurantGui gui){ //HostAgent m) {
	agent = c;
	xPos = 20;
	yPos = 400;
	xDestination = 20;
	yDestination = 400;
	this.gui = gui;
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

	if (xPos == xDestination && yPos == yDestination) {
	    if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
	    else if (command==Command.LeaveRestaurant) {
			agent.msgAnimationFinishedLeaveRestaurant();
			isHungry = false;
			//gui.setCustomerEnabled(agent);
	    }
	    else if (command==Command.GoToCashier) {
			agent.madeItToCashier();
			isHungry = false;
		//	gui.setCustomerEnabled(agent);
	    }
	    command=Command.noCommand;
	}
    }

    public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, 20, 20);
		if(myFood != null){
		    g.drawString(myFood, xPos, yPos);
		}
    }

    public void eatingFood(String food) {
	myFood = food.substring(0,2);
    }

    public void doneWithFood() {
	myFood = null;
    }

    public boolean isPresent() {
	return isPresent;
    }
    public void setHungry() {
	isHungry = true;
	agent.gotHungry();
	setPresent(true);
    }
    public boolean isHungry() {
	return isHungry;
    }

    public void setPresent(boolean p) {
	isPresent = p;
    }

    public void DoGoToArea(int x, int y) {
    	//just have the customer appear at the location
    	xPos = x;
    	yPos = y;
    	xDestination = x;
    	yDestination = y;
    	//you can choose to do a command later if you want to do an animation
    }
    
    public void DoGoToSeat(int x, int y) {//later you will map seatnumber to table coordinates.
	xDestination = x;
	yDestination = y;
	command = Command.GoToSeat;
    }

    public void DoGoToCashier() {
	xDestination = -40;
	yDestination = 300;
	command = Command.GoToCashier;
    }

    public void DoExitRestaurant() {
	xDestination = -40;
	yDestination = -40;
	command = Command.LeaveRestaurant;
    }
}
