package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;

import Gui.RoleGui;
import simcity.restaurants.restaurant5.*;

public class CashierGui extends RoleGui implements Gui {

	private CashierRole agent = null;

	

	private int DEBUGX = 600;
	private int DEBUGY = 40;

	static final int hostSize = 20;

	public CashierGui(CashierRole agent) {
		this.agent = agent;
		super.setColor(Color.CYAN);

	}

	public void updatePosition() {
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.BLACK);
		g.drawString("Cashier $: " + agent.getMoney(), DEBUGX, DEBUGY);
	}

	public boolean isPresent() {
		return true;
	}

	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	private String generateDebug(){
		return "";
		//return agent.returnAmounts();
	}


}
