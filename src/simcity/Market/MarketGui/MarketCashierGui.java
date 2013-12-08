package simcity.Market.MarketGui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Gui.RoleGui;
import Gui.Screen;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;

public class MarketCashierGui extends RoleGui{
	
	private MarketCashier cashier;
	
	public MarketCashierGui(MarketCashier mc){
		cashier = mc;
		xPos = 390;
		yPos = 400;
		xDestination = 390;
		yDestination = 400;
	}
	public MarketCashierGui(MarketCashierRole mc, Screen s){
		super(mc, s);
		cashier = mc;
		xPos = 390;
		yPos = 400;
		xDestination = 390;
		yDestination = 400;
	}


	public void draw(Graphics g) {
		super.draw(g);
		
		
	}


	public boolean isPresent() {
		return true;
	}

}
