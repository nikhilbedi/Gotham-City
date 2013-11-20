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
	private enum Command {none, atCashier, gettingItems, left};
	
	public MarketCustomerGui(MarketCustomer mc){
		customer = mc;
		xPos = 110;
		yPos = 350;
		xDestination = 110;
		yDestination = 300;
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
	        if (xPos == 110 && yPos == 183 && command == Command.atCashier){
	        	command = Command.none;
	        	customer.AtCashier();
	        }
	        if (xPos == 290 && yPos == 183 && command == Command.gettingItems){
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
		yDestination = 183;
		command = Command.atCashier;
	}
	
	public void DoGetItems(){
		xDestination = 290;
		command = Command.gettingItems;
	}
	
	public void DoLeave(){
		yDestination = 400;
		command = Command.left;
	}
}
