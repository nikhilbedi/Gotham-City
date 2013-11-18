package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics2D;

import simcity.Market.interfaces.MarketCustomer;
import simcity.Market.interfaces.MarketWorker;

public class MarketWorkerGui implements Gui{
	private int xPos;
	private int yPos;
	private int xDestination;
	private int yDestination;
	private MarketWorker worker;
	private MarketCustomer customer;
	private Command command;
	private enum Command {none, getting, delivering};
	
	
	public MarketWorkerGui(MarketWorker mw){
		worker = mw;
		xPos = 130;
		yPos = 110;
		xDestination = 130;
		yDestination = 110;
	}
	

	@Override
	public void updatePosition() {
		 if (xPos < xDestination)
	            xPos++;
	        else if (xPos > xDestination)
	            xPos--;

	        if (yPos < yDestination)
	            yPos++;
	        else if (yPos > yDestination)
	            yPos--;
	        if (yPos == 40 && xPos == 130 && command == Command.getting){
	        	command = Command.none;
	        	Deliver();
	        }
	        if (xPos == 290 && yPos == 130 && command == Command.delivering){
	        	command = Command.none;
	        	worker.Brought(customer);
	        }
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
		
	}

	@Override
	public boolean isPresent() {
		return true;
	}

	
	public void DoBring(MarketCustomer m){
		customer = m;
		xDestination = 130;
		yDestination = 40;
		command = Command.getting;
	}
	
	public void Deliver(){
		xDestination = 290;
		yDestination = 130;
		command = Command.delivering;
	}
	
	public void DefaultPos(){
		xDestination = 130;
		yDestination = 110;
	}
}
