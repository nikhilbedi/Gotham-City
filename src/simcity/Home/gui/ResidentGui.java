package simcity.Home.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import Gui.RoleGui;
import Gui.Screen;
import simcity.Home.Apartment;
import simcity.Home.ResidentRole;
import simcity.Home.interfaces.Resident;
import simcity.PersonAgent;
import simcity.PersonAgent.EatingState;
import simcity.PersonAgent.HungerState;

public class ResidentGui extends RoleGui {
	/*
	 * private int xPos; private int yPos; private int xDestination; private int
	 * yDestination;
	 */
	PersonAgent person;
	public String choice;
	private Resident resident;
	private Command command;
	boolean payingRent = false;

	public boolean cooking = false;
	public boolean plating = false;
	public boolean eating = false;
	public boolean clearing = false;
	public boolean sleeping = false;
	public boolean checkingMail = false;

	private enum Command {
		none, atRefridgerator, left, atTable, atSink, atPlatingArea, atStove, atBed, atFridge, exited, atHome, atMailbox
	};

	public ResidentGui(Resident r) {
		myColor = Color.red;
		// System.err.println("Here we are 2");
		resident = r;
		
		xPos = 400;
		yPos = -40;
		xDestination = 400;
		yDestination = -40;
	}
	public ResidentGui(ResidentRole role, Screen s) {
		super(role, s);
		myColor = Color.red;
		// System.err.println("Here we are 2");
		resident = role;
		homeScreen = s;
		xPos = 400;
		yPos = -40;
		xDestination = 400;
		yDestination = -40;
	}

	public ResidentGui() {
		super();
		
	}

//	public void draw(Graphics g) {
//		super.draw(g);
//		g.setColor(Color.GREEN);
//		//g.fillRect(xPos, yPos, Width, Height);
//		if (cooking){
//			g.drawString("cooking food", getXPos(), getYPos());
//		}
//		else if (plating) {
//			g.drawString("plating food", getXPos(), getYPos());
//		}
//		else if (eating) {
//			g.drawString("eating ", getXPos(), getYPos());
//		}
//		else if (clearing) {
//			g.drawString("cleaning dishes", getXPos(), getYPos());
//		}
//		else if (checkingMail) {
//			g.drawString("checking mail", getXPos(), getYPos());
//		}
//		
//	}
	
	// @Override
	public void updatePosition() {
		super.updatePosition();
		if (xPos == 400 && yPos == -40) {
			// resident.checkMailbox();
			// payingRent = true;
			// if (person.hungerState == HungerState.Hungry
			// && person.eatingState == EatingState.EatAtHome && payingRent !=
			// true) {
			//if (payingRent != true) {
				//resident.gotHungry();
				
				// person.eatingState = EatingState.EatAtHome;

			//}
		}
		if (xPos == 300 && yPos == 460 && command == Command.atTable) {
			command = Command.none;
			resident.AtTable();
		}
		else if (xPos == 80 && yPos == 520 && command == Command.atSink) {
			command = Command.none;
			resident.atSink();
		}
		else if (xPos == 120 && yPos == 680 && command == Command.atPlatingArea) {
			command = Command.none;
			resident.atPlatingArea();
		}
		else if (xPos == 230 && yPos == 680 && command == Command.atStove) {
			command = Command.none;
			resident.atStove();
		}
		else if (xPos == 700 && yPos == 214 && command == Command.atBed) {
			command = Command.none;
			resident.atBed();
		}
		else if (xPos == 420 && yPos == 670 && command == Command.atFridge) {
			command = Command.none;
			resident.atFridge();
		}
		else if (xPos == 400 && yPos == -40 && command == Command.exited) {
			command = Command.none;
			resident.exited();
		}
		else if (xPos == 138 && yPos == 32 && command == Command.atMailbox) {
			command = Command.none;
			resident.atMailbox();
		}
		else if (xPos == 84 && yPos == 226 && command == Command.atHome) {
			command = Command.none;
			resident.atHome();
		}

	}

	/*
	 * public void draw(Graphics g) { g.setColor(Color.BLUE); g.fillRect(xPos,
	 * yPos, 20, 20);
	 * 
	 * }
	 */
	
	public void DoGoToTable() {// 260, 350, 110, 110
		System.out.println("At Table");
		xDestination = 300;
		yDestination = 460;
		command = Command.atTable;
	}

	public void DoLeave() {// 350, 0, 100, 10
		System.out.println("Left restaurant");
		xDestination = 400;
		yDestination = -40;
		command = Command.left;
	}

	public void DoClearFood() {// 10, 500, 60, 80
		System.out.println("at sink");
		xDestination = 80;
		yDestination = 520;
		command = Command.atSink;

	}

	public void DoGoToPlatingArea() {
		System.out.println("at plating area");
		xDestination = 120;
		yDestination = 680;
		command = Command.atPlatingArea;
	}

	public void DoGoToStove() { // 160, 700, 110, 80
		System.out.println("at Stove");
		xDestination = 230;
		yDestination = 680;
		command = Command.atStove;
	}

	public void DoGoToBed() {
		System.out.println("at bed");
		xDestination = 700;
		yDestination = 214;
		command = Command.atBed;

	}

	public void DoGoToFridge() { // 380, 690, 130, 10
		System.out.println("at fridge");
		xDestination = 420;
		yDestination = 670;
		command = Command.atFridge;

	}

	public void DoExitHome() {
		System.out.println("exited home");
		xDestination = 400;
		yDestination = -40;
		command = Command.exited;

	}

	public void DoReturnToHomePosition() {
		//System.out.println("At Home Position");
		xDestination = 84;
		yDestination = 224;
		command = Command.atHome;

	}

	public void DoGoToMailbox() {
		System.out.println("At mailbox");
		xDestination = 138;
		yDestination = 32;
		command = Command.atMailbox;

	}


	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}
}
