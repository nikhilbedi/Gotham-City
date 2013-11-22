package simcity.Home.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import Gui.RoleGui;
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
	private Resident resident;
	private Command command;
	boolean payingRent = false;

	private enum Command {
		none, atRefridgerator, left, atTable, atSink, atPlatingArea, atStove, atBed, atFridge, exited, atHome
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

	// @Override
	public void updatePosition() {
		super.updatePosition();
		if (xPos == 400 && yPos == -40) {
			//resident.checkMailbox();
			//payingRent = true;
			//if (person.hungerState == HungerState.Hungry
				//	&& person.eatingState == EatingState.EatAtHome && payingRent != true) {
			if(payingRent != true){
				resident.gotHungry();
				person.eatingState = EatingState.EatAtHome;

			}
		}
		if (xPos == 300 && yPos == 460 && command == Command.atTable) {
			command = Command.none;
			resident.AtTable();
		}
		if (xPos == 80 && yPos == 520 && command == Command.atSink) {
			command = Command.none;
			resident.atSink();
		}
		if (xPos == 120 && yPos == 680 && command == Command.atPlatingArea) {
			command = Command.none;
			resident.atPlatingArea();
		}
		if (xPos == 230 && yPos == 680 && command == Command.atStove) {
			command = Command.none;
			resident.atStove();
		}
		if (xPos == 700 && yPos == 214 && command == Command.atBed) {
			command = Command.none;
			resident.atBed();
		}
		if (xPos == 420 && yPos == 670 && command == Command.atFridge) {
			command = Command.none;
			resident.atFridge();
		}
		if (xPos == 400 && yPos == -40 && command == Command.exited) {
			command = Command.none;
			resident.exited();
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
		System.out.println("At Home Position");
		xDestination = 500;
		yDestination = 500;
		command = Command.atHome;

	}
}
