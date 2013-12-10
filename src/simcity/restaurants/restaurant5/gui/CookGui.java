package simcity.restaurants.restaurant5.gui;


import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.interfaces.Cook;
import agent.*;
import Gui.RoleGui;
import Gui.Screen;

public class CookGui extends RoleGui implements Gui {

	private CookRole role = null;

	private int defaultX = 202, defaultY = 76;
	private int standX =202, standY = 50;
	
	private int cookingX = 232, cookingY = 70;
	private int DEBUGX = 400;
	private int DEBUGY = 25;

	static final int hostSize = 20;

	private enum Command {getOrder, noCommand, cookOrder, cooking, checkStand};
	private Command command=Command.noCommand;

	public CookGui(CookRole role) {
		super();
		xDestination = defaultX;
		yDestination = defaultY;
		this.role = role;
		setColor(Color.CYAN);
	}

	public CookGui(Cook cook, Screen meScreen) {
		super( (Role)cook, meScreen);
		super.setColor(Color.CYAN);
		xPos = defaultX;
		yPos = defaultY;
		xDestination = defaultX;
		yDestination = defaultY;
		this.role = (CookRole)cook;
	}

	public void updatePosition() {
		super.updatePosition();
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.getOrder){ 
				role.doneMoving();	
				command = Command.noCommand;
			}
			else if(command == Command.checkStand){
				role.doneMoving();
				command = Command.noCommand;
			}
			else if(command == Command.cookOrder){
				role.doneMoving();
				command = Command.cooking;
			}
			
			else if(command == Command.noCommand){
				xDestination = defaultX;
				yDestination = defaultY;
			}
		}
	}

	public void draw(Graphics g) {
		super.draw(g);
		g.setColor(Color.BLACK);
		g.drawString(role.returnAmounts(), DEBUGX, DEBUGY);
	}

	public boolean isPresent() {
		return true;
	}


	private String generateDebug(){
		return role.returnAmounts();
	}
	public void DoGoToPlating(){
		System.err.println("DoGoToPlating in cookGui called");
		xDestination = defaultX;
		yDestination = defaultY;
		command = command.getOrder;
		
	}
	public void DoGoToGrill(){
		System.err.println("DoGoToPlating in cookGui called");
		xDestination = cookingX;
		yDestination = cookingY;
		command = command.cookOrder;
		
	}

	public void DoGoToStand() {
		xDestination = standX;
		yDestination = standY;
		command = command.checkStand;
		
	}

}
