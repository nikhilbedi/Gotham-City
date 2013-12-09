package simcity.restaurants.restaurant5.gui;


import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import simcity.restaurants.restaurant5.interfaces.Cook;
import agent.*;
import Gui.RoleGui;
import Gui.Screen;

public class CookGui extends RoleGui implements Gui {

	private CookRole agent = null;

	private int defaultX = 202, defaultY = 76;
	
	private int cookingX = 232, cookingY = 70;
	private int DEBUGX = 600;
	private int DEBUGY = 25;

	static final int hostSize = 20;

	private enum Command {getOrder, noCommand, cookOrder, cooking};
	private Command command=Command.noCommand;

	public CookGui(CookRole agent) {
		super();
		xDestination = defaultX;
		yDestination = defaultY;
		this.agent = agent;
		setColor(Color.CYAN);
	}

	public CookGui(Cook cook, Screen meScreen) {
		super( (Role)cook, meScreen);
	}

	public void updatePosition() {
		super.updatePosition();
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.getOrder){ 
				agent.doneMoving();	
				command = Command.noCommand;
			}
			else if(command == Command.cookOrder){
				agent.doneMoving();
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
		g.drawString(agent.returnAmounts(), DEBUGX, DEBUGY);
	}

	public boolean isPresent() {
		return true;
	}


	private String generateDebug(){
		return agent.returnAmounts();
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

}
