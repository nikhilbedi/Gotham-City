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
		xPos = 390;
		yPos = 400;
		xDestination = 390;
		yDestination = 400;
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
