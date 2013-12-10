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

	private CashierRole role = null;



	private int DEBUGX = 400;
	private int DEBUGY = 40;

	static final int hostSize = 20;

	public CashierGui(CashierRole role) {
		this.role = role;
		super.setColor(Color.CYAN);
	}

	public CashierGui(CashierRole role, Screen s) {
		super(role, s);
		super.setColor(Color.CYAN);
		this.role = role;
	}

	public CashierGui(Cashier cashier, Screen meScreen) {
		super( (Role)cashier, meScreen);
		super.setColor(Color.CYAN);
		this.role = (CashierRole) cashier;
	}

	public void updatePosition() {
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.BLACK);
		g.drawString("Cashier $: " + role.getMoney(), DEBUGX, DEBUGY);
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
