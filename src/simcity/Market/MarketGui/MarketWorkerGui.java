package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

import agent.Role;
import Gui.RoleGui;
import simcity.Market.Order;
import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;

public class MarketWorkerGui extends RoleGui{
	private int xPos;
	private int yPos;
	private int xDestination;
	private int yDestination;
	private MarketWorker worker;
	private MarketCustomer customer;
	private Command command;
	private enum Command {none, getting, delivering, restGetting, truck};
	private List <Order> orders;
	private boolean drawString = false;
	private Role role;
	
	
	public MarketWorkerGui(MarketWorker mw){
		worker = mw;
		xPos = 220;
		yPos = 260;
		xDestination = 220;
		yDestination = 260;
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
	        if (yPos == 40 && xPos == 450 && command == Command.getting){
	        	command = Command.none;
	        	Deliver();
	        }
	        if (xPos == 450 && yPos == 280 && command == Command.delivering){
	        	command = Command.none;
	        	worker.Brought(customer);
	        }
	        if (xPos == 130 && yPos == 40 && command == Command.restGetting){
	        	command = Command.none;
	        	LoadToTruck();
	        }
	        if (xPos == 0 && yPos == 40 && command == Command.truck){
	        	command = Command.none;
	        	worker.Sent(role);
	        }
	}


	public void draw(Graphics g) {
		g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
		if (drawString == true){
			//drawOrder(g);
		}
        
	}

	public boolean isPresent() {
		return true;
	}
	
	public void DoSend(Map<String, Integer> m, Role role){
		this.role = role;
		xDestination = 130;
		yDestination = 40;
		command = Command.restGetting;
	}

	public void LoadToTruck(){
		xDestination = 0;
		yDestination = 40;
		command = Command.truck;
	}
	public void drawOrder(Graphics g){
		int x = xPos;
		/*for (Order order : orders){
			g.drawString(order.getChoice(),x, yPos+20);
			x+=10;
		}*/
		
	}
	
	
	public void DoBring(MarketCustomer m){
		customer = m;
		xDestination = 450;
		yDestination = 40;
		command = Command.getting;
	}
	
	public void Deliver(){
		drawString = true; 
		xDestination = 450;
		yDestination = 280;
		command = Command.delivering;
	}
	
	public void DefaultPos(){
		xDestination = 220;
		yDestination = 260;
	}
}
