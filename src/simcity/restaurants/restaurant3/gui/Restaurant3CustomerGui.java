package simcity.restaurants.restaurant3.gui;

import simcity.restaurants.restaurant3.Restaurant3CustomerRole;
import Gui.RoleGui;
import Gui.Screen;

import java.awt.*;

public class Restaurant3CustomerGui extends RoleGui{

	private Restaurant3CustomerRole agent = null;
	private boolean isPresent = false;
	private boolean isHungry = false;
	public boolean waitingForFood=false;
	public boolean receivedFood=false;
	public String order;
	//private HostAgent host;

	//private int xPos, yPos;
	private static int Width, Height;
	//private int xDestination, yDestination;
	private int tableVariation;
	private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant};
	private Command command=Command.noCommand;

	public static final int xTable = 200;
	public static final int yTable = 150;
	private static int waitingCustomers[] = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	private int posInWaitArea = -1;

	public Restaurant3CustomerGui(Restaurant3CustomerRole c){ 
		agent = c;
		for (int i = 0; i < waitingCustomers.length; ++i){
			if (waitingCustomers[i] == -1) { //empty position
				waitingCustomers[i] = 1;
				posInWaitArea = i;
				xPos = 20 * (i+1) + 5*i;
				yPos = 20;
				break;
			}
		}
		
		Width = 20;
		Height = 20;
		xDestination = xPos;
		yDestination = +20;
		tableVariation = 85;
		//maitreD = m;
		
		
	}
	public Restaurant3CustomerGui(Restaurant3CustomerRole c, Screen s){
		super(c, s);
		agent = c;
		for (int i = 0; i < waitingCustomers.length; ++i){
			if (waitingCustomers[i] == -1) { //empty position
				waitingCustomers[i] = 1;
				posInWaitArea = i;
				xPos = 20 * (i+1) + 5*i;
				yPos = 20;
				break;
			}
		}
		
		Width = 20;
		Height = 20;
		xDestination = xPos;
		yDestination = +20;
		tableVariation = 85;
	
		
	}

	public void updatePosition() {
		super.updatePosition();
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
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
				command=Command.noCommand;
			}
			else if(command==Command.GoToCashier) 
				agent.msgAnimationFinishedGoToCashier();
			else
			command=Command.noCommand;
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.GREEN);
		//g.fillRect(xPos, yPos, Width, Height);
		if (waitingForFood){
			g.drawString("waiting for " + order, getXPos(), getYPos());
		}
		if (receivedFood) {
			g.drawString("eating " + order, getXPos(), getYPos());
		}
		
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

	public void DoGoToSeat(int seatnumber) {//later you will map seatnumber to table coordinates.

		// clear the waitingCustomer array
		waitingCustomers[this.posInWaitArea] = -1;
		
		if (seatnumber == 1) {
			xDestination = xTable;
			yDestination = yTable;
		}
		if (seatnumber == 2) {
			xDestination = xTable+tableVariation;
			yDestination = yTable;
		}
		if (seatnumber == 3) {
			xDestination = xTable;
			yDestination = yTable+tableVariation;
		}
		if (seatnumber == 4) {
			xDestination = xTable+tableVariation;
			yDestination = yTable+tableVariation;
		}
		command = Command.GoToSeat;
	}

	public void DoExitRestaurant() {
		xDestination = -40;
		yDestination = -40;
		command = Command.LeaveRestaurant;
	}
	public void DoGoToCashier() {
    	xDestination = 600;
    	yDestination = 200;
    	command = Command.GoToCashier;
    }
	
	 public int getXPos() {
	        return xPos;
	 }

	 public int getYPos() {
	        return yPos;
	 }
}