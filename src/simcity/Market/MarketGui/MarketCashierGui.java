package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Gui.RoleGui;
import simcity.Market.interfaces.MarketCashier;

public class MarketCashierGui extends RoleGui{
	private int xPos;
	private int yPos;
	private int xDestination;
	private int yDestination;
	private MarketCashier cashier;
	
	public MarketCashierGui(MarketCashier mc){
		cashier = mc;
		xPos = 200;
		yPos = 280;
		xDestination = 200;
		yDestination = 280;
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
	}


	public void draw(Graphics g) {
		g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
		
	}


	public boolean isPresent() {
		return true;
	}

}
