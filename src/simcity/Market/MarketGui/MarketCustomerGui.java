package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Gui.RoleGui;
import Gui.Screen;
import simcity.Market.MarketCustomerRole;
import simcity.Market.interfaces.MarketCashier;
import simcity.Market.interfaces.MarketCustomer;

public class MarketCustomerGui extends RoleGui{

	private MarketCustomer customer;
	private Command command;
	private enum Command {buy, none, atCashier, gettingItems, left};
	
	public MarketCustomerGui(MarketCustomerRole mc){
		customer = mc;
		xPos = 200;
		yPos = 800;
		xDestination = 200;
		yDestination = 800;
		command = Command.buy;
	}
	
	public MarketCustomerGui(MarketCustomerRole role, Screen s) {
		// TODO Auto-generated constructor stub
		//super(role, s);
		customer = role;
		command  = Command.buy;
		homeScreen = s;
		xPos = 390;
		yPos = 800;
		xDestination = 390;
		yDestination = 800;
	}

	public void updatePosition() {
		super.updatePosition();
	        
	        if (xPos == 200 && yPos == 800 && command == Command.buy){
			//customer.getGroceries();
	        }
	        if (xPos == 390 && yPos == 520&& command == Command.atCashier){
	        	command = Command.none;
	        	customer.AtCashier();
	        }
	        if (xPos == 580 && yPos == 530 && command == Command.gettingItems){
	        	System.out.println("arrived");
	        	command = Command.none;
	        	customer.ArrivedToGetItem();
	        }
	        if (yPos == 800  && xDestination == 580 && command == Command.left){
	        	command = Command.none;
	        	((MarketCustomerRole) customer).msgOutOfMarket();
	        }
	}

	public void draw(Graphics g) {
		super.draw(g);
		/*g.setColor(Color.BLUE);
        g.fillRect(xPos, yPos, 20, 20);*/
		
	}

	public boolean isPresent() {
		return true;
	}

	public void DoMoveToCashier(){
		yDestination = 520;
		xDestination = 390;
		command = Command.atCashier;
	}
	
	public void DoGetItems(){
		xDestination = 580;
		yDestination = 530;
		command = Command.gettingItems;
	}
	
	public void DoLeave(){
		yDestination = 800;
		xDestination = 580;
		command = Command.left;
	}
	
	public void setDestination(int x){
		xDestination = x;
	}
}
