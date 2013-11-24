package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Gui.RoleGui;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;

public class MarketCustomerGui extends RoleGui{
	private int xPos;
	private int yPos;
	private int xDestination;
	private int yDestination;
	private MarketCustomer customer;
	private Command command;
	private enum Command {buy, none, atCashier, gettingItems, left};
	
	public MarketCustomerGui(MarketCustomer mc){
		customer = mc;
		xPos = 200;
		yPos = 800;
		xDestination = 200;
		yDestination = 800;
		command = Command.buy;
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
	        
	        if (xPos == 200 && yPos == 800 && command == Command.buy){
			customer.getGroceries();
	        }
	        if (xPos == 200 && yPos == 350 && command == Command.atCashier){
	        	command = Command.none;
	        	customer.AtCashier();
	        }
	        if (xPos == 450 && yPos == 350 && command == Command.gettingItems){
	        	System.out.println("arrived");
	        	command = Command.none;
	        	customer.ArrivedToGetItem();
	        }
	}

	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);
		
	}

	public boolean isPresent() {
		return true;
	}

	public void DoMoveToCashier(){
		yDestination = 350;
		xDestination = 200;
		command = Command.atCashier;
	}
	
	public void DoGetItems(){
		System.out.println("Started to move");
		xDestination = 450;
		yDestination = 350;
		command = Command.gettingItems;
	}
	
	public void DoLeave(){
		yDestination = 800;
		xDestination = 450;
		command = Command.left;
	}
}
