package simcity.restaurants.restaurant3.src.restaurant.gui;

import java.awt.Graphics;

import javax.swing.JLabel;

public class OrderGui implements Gui{

	//private CustomerAgent agent = null;
	//private boolean isPresent = false;
	//private boolean isHungry = false;

	//private HostAgent host;
	RestaurantGui gui;
	String type;
	
	private int xPos;// = -20;
	private int yPos;// = -20;
	private static int Width, Height;
	private int xDestination;// = -20;
	private int yDestination;// = -20;
	private int tableVariation;
	
	private boolean doneEating = false;
	private boolean doneCooking = false;
	private boolean foodOrdered = false;
	//private enum Command {noCommand, GoToSeat, LeaveRestaurant};
	//private Command command=Command.noCommand;
	JLabel orderLabel;
	public static final int xTable = 200;
	public static final int yTable = 150;

	public OrderGui(String type, RestaurantGui gui, int xPos, int yPos){ //HostAgent m) {
		//agent = c;
		this.xPos = xPos;
		this.yPos = yPos;
		//Width = 20;
		//Height = 20;
		xDestination = -20;
		yDestination = 200;
		tableVariation = 85;
		this.type = type;
		//maitreD = m;
		this.gui = gui;
	}
	
	public void doneEating() {
		doneEating = true;

	}
	public void doneCooking() {
		doneCooking = true;
	}
	public void foodOrdered() {
		foodOrdered = true;
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
/*
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) agent.msgAnimationFinishedGoToSeat();
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}*/
	}

	public void draw(Graphics g) {
		if (!doneEating){
			if(doneCooking) {
		
				//g.setColor(Color.BLACK);
				//g.fillRect(xPos, yPos, 10, 10);
				//g.drawString(type, xPos, yPos);
	
			
			}
			else if(foodOrdered) {
				//g.setColor(Color.BLACK);
				//g.fillRect(xPos, yPos, 10, 10);
				//g.drawString(type + "?", xPos, yPos);
	
			}
		}
	}
			

	 public boolean isPresent() {
	        return true;
	    }

	public void setPresent(boolean p) {
		//isPresent = p;
	}

	public void DoGoToTable(int seatnumber) {//later you will map seatnumber to table coordinates.

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
		//command = Command.GoToSeat;
	}
	
	//public void DoLeaveCustomer() {
		//xDestination = -20;
		//yDestination = -20;
	//}
	public void DoGoToCook() {
		xDestination = -20;
		yDestination = 200;
	}

	//public void DoExitRestaurant() {
		//xDestination = -40;
		//yDestination = -40;
	//}
	public int getXPos() {
		return xPos;
	}
	public int getYPos() {
		return yPos;
	
	}	
}