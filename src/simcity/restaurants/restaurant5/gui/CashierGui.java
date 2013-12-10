package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;
import agent.*;
import Gui.RoleGui;
import Gui.Screen;
import simcity.restaurants.restaurant5.CashierRole;
import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.interfaces.Cashier;

public class CashierGui extends RoleGui implements Gui {

	private CashierRole agent = null;



	private int DEBUGX = 600;
	private int DEBUGY = 40;

	static final int hostSize = 20;

	public CashierGui(CashierRole agent) {
		this.agent = agent;
		super.setColor(Color.CYAN);
	}

	public CashierGui(CashierRole agent, Screen s) {
		super(agent, s);
		super.setColor(Color.CYAN);
		this.agent = agent;
	}

	public CashierGui(Cashier cashier, Screen meScreen) {
		super( (Role)cashier, meScreen);
	}

	public void updatePosition() {
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.BLACK);
		//g.drawString("Cashier $: " + agent.getMoney(), DEBUGX, DEBUGY);
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
