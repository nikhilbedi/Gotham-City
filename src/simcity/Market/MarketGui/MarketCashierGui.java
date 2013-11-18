package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics2D;

import simcity.Market.interfaces.MarketCashier;

public class MarketCashierGui implements Gui{
	private int xPos;
	private int yPos;
	private int xDestination;
	private int yDestination;
	private MarketCashier cashier;
	
	public MarketCashierGui(MarketCashier mc){
		cashier = mc;
		xPos = 110;
		yPos = 130;
		xDestination = 110;
		yDestination = 130;
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
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
		
	}

	@Override
	public boolean isPresent() {
		return true;
	}

}
