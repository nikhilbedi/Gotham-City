package simcity.Home.gui;

import java.awt.Color;
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

public class ApartmentResidentGui extends ResidentGui {
	/*
	 * private int xPos; private int yPos; private int xDestination; private int
	 * yDestination;
	 */
	PersonAgent person;
	public String choice;
	private Resident resident;
	private Command command;
	boolean payingRent = false;
	private int roomNumber = 2;
	
	int scaleMultiplier;
	int wallWidth;
	int hallwayWidth;
	int roomHeight; 
	int roomWidth;
	
	int xPadding; 
	int yPadding; 
	
	int xRoomEntrance1 = 296;
	int xRoomEntrance2 = 500;
	int yRoomEntrance1 = 24;
	int yRoomEntrance2 = 294;
	int yRoomEntrance3 = 568;
	
	int xRoomExit1 = 258;
	int xRoomExit2 = 542;
	int yRoomExit1 = 24;
	int yRoomExit2 = 294;
	int yRoomExit3 = 568;
	

	public boolean cooking = false;
	public boolean plating = false;
	public boolean eating = false;
	public boolean clearing = false;
	public boolean sleeping = false;
	public boolean checkingMail = false;
	

	private enum Command {
		none, atRefridgerator, left, atTable, atSink, atPlatingArea, atStove, atBed, atFridge, exited, atHome, atMailbox, 
		atRoomEntrance1, atRoomEntrance2, atRoomEntrance3, atRoomEntrance4, atRoomEntrance5,atRoomEntrance6, atRoomExit
	};
	public ApartmentResidentGui() {
		super();
	}
	public ApartmentResidentGui(Resident r) {
		myColor = Color.red;
		// System.err.println("Here we are 2");
		resident = r;
		
		xPos = 400;
		yPos = -40;
		xDestination = 400;
		yDestination = -40;
		
		scaleMultiplier = 3;
		wallWidth = 10;
		hallwayWidth = 250;
		roomHeight = 780/scaleMultiplier;
		roomWidth = 800/scaleMultiplier;
		
		//roomNumber = 2;
		

		 xPadding = ((int)Math.ceil(roomNumber / 3.0)-1) * (roomWidth + hallwayWidth + wallWidth*2); 
		 yPadding = (((roomNumber % 3 == 0 ? 3: (roomNumber % 3)) - 1) * (wallWidth + roomHeight) ) ;
	}
	public ApartmentResidentGui(ResidentRole role, Screen s) {
		super(role, s);
		myColor = Color.red;
		// System.err.println("Here we are 2");
		resident = role;
		homeScreen = s;
		xPos = 400;
		yPos = -40;
		xDestination = 400;
		yDestination = -40;
		
		scaleMultiplier = 3;
		wallWidth = 10;
		hallwayWidth = 250;
		roomHeight = 780/scaleMultiplier;
		roomWidth = 800/scaleMultiplier;
		
		//roomNumber = 3;
		
		xPadding = ((int)Math.ceil(roomNumber / 3.0)-1) * (roomWidth + hallwayWidth + wallWidth*2); 
		yPadding = (((roomNumber % 3 == 0 ? 3: (roomNumber % 3)) - 1) * (wallWidth + roomHeight) ) ;
	}

	@Override
	public void updatePosition() {
		super.updatePosition();
		
		//int i;
		//for (i = roomNumber; i <= 6; ++i) {
			
		
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
		if ( xPos == xPadding +300/ scaleMultiplier &&  yPos == yPadding + 460/ scaleMultiplier && command == Command.atTable) {
			command = Command.none;
			resident.AtTable();
		}
		else if (xPos == xPadding + 80/ scaleMultiplier && yPos == yPadding + 520/ scaleMultiplier && command == Command.atSink) {
			command = Command.none;
			resident.atSink();
		}
		else if (xPos == xPadding + 120/ scaleMultiplier && yPos == yPadding + 680/ scaleMultiplier && command == Command.atPlatingArea) {
			command = Command.none;
			resident.atPlatingArea();
		}
		else if (xPos == xPadding + 230/ scaleMultiplier && yPos == yPadding + 680/ scaleMultiplier && command == Command.atStove) {
			command = Command.none;
			resident.atStove();
		}
		else if (xPos == xPadding + 700/ scaleMultiplier && yPos == yPadding + 214/ scaleMultiplier && command == Command.atBed) {
			command = Command.none;
			resident.atBed();
		}
		else if (xPos == xPadding + 420/ scaleMultiplier && yPos == yPadding + 670/ scaleMultiplier && command == Command.atFridge) {
			command = Command.none;
			resident.atFridge();
		}
		else if (xPos == 400 && yPos == -40 && command == Command.exited) {
			command = Command.none;
			resident.exited();
		}
		else if (xPos == xPadding + 138/ scaleMultiplier && yPos == yPadding + 32/ scaleMultiplier && command == Command.atMailbox) {
			command = Command.none;
			resident.atMailbox();
		}
		else if (xPos == 84 && yPos == 226 && command == Command.atHome) {
			command = Command.none;
			resident.atHome();
		}
		else if (xPos == xRoomEntrance1 && yPos == yRoomEntrance1 
				&& command == Command.atRoomEntrance1) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if ( xPos == xRoomEntrance1 && yPos == yRoomEntrance2 
				&& command == Command.atRoomEntrance2) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if ( xPos == xRoomEntrance1 && yPos == yRoomEntrance3  
				&& command == Command.atRoomEntrance3) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if (xPos == xRoomEntrance2 && yPos == yRoomEntrance1
				&& command == Command.atRoomEntrance4) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if (xPos == xRoomEntrance2 && yPos == yRoomEntrance2 
				&& command == Command.atRoomEntrance5) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if (xPos == xRoomEntrance2 && yPos == yRoomEntrance3 
				&& command == Command.atRoomEntrance6) {
			command = Command.none;
			resident.atRoomEntrance();
		}
		else if (xPos == xRoomExit1 && yPos == yRoomExit1 || xPos == xRoomExit1 && yPos == yRoomExit2 || 
				xPos == xRoomExit1 && yPos == yRoomExit3 ||xPos == xRoomExit2 && yPos == yRoomExit1 ||
				xPos == xRoomExit2 && yPos == yRoomExit2 ||xPos == xRoomExit2 && yPos == yRoomExit3
				&& command == Command.atRoomExit) {
			command = Command.none;
			resident.atRoomExit();
		}
		//}

	}

	/*
	 * public void draw(Graphics g) { g.setColor(Color.BLUE); g.fillRect(xPos,
	 * yPos, 20, 20);
	 * 
	 * }
	 */
	public void DoGoToRoomEntrance() {
		System.out.println("Going to Room Entrance");
		if(roomNumber == 1) {
			xDestination =  xRoomEntrance1;
			yDestination = yRoomEntrance1;
			command = Command.atRoomEntrance1;
		}
		else if (roomNumber == 2) {
			xDestination =  xRoomEntrance1;
			yDestination = yRoomEntrance2;
			command = Command.atRoomEntrance2;
		}
		else if (roomNumber == 3) {
			xDestination =  xRoomEntrance1;
			yDestination = yRoomEntrance3;
			command = Command.atRoomEntrance3;
		}
		else if (roomNumber == 4) {
			xDestination =  xRoomEntrance2;
			yDestination = yRoomEntrance1;
			command = Command.atRoomEntrance4;
		}
		else if (roomNumber == 5) {
			xDestination =  xRoomEntrance2;
			yDestination = yRoomEntrance2;
			command = Command.atRoomEntrance5;
		}
		else if (roomNumber == 6) {
			xDestination =  xRoomEntrance2;
			yDestination = yRoomEntrance3;
			command = Command.atRoomEntrance6;
		}
	}
	
	@Override
	public void DoGoToTable() {// 260, 350, 110, 110
		System.out.println("At Table");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 300/scaleMultiplier;
		yDestination = yPadding + 460/scaleMultiplier;
		//}
		command = Command.atTable;
	}
	@Override
	public void DoLeave() {// 350, 0, 100, 10
		System.out.println("Left restaurant");
		
		xDestination =  400;
		yDestination = -40;
		
		command = Command.left;
	}
	@Override
	public void DoClearFood() {// 10, 500, 60, 80
		System.out.println("at sink");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding +80/scaleMultiplier;
		yDestination = yPadding + 520/scaleMultiplier;
		//}
		command = Command.atSink;

	}
	@Override
	public void DoGoToPlatingArea() {
		System.out.println("at plating area");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 120/scaleMultiplier;
		yDestination = yPadding + 680/scaleMultiplier;
		//}
		command = Command.atPlatingArea;
	}
	@Override
	public void DoGoToStove() { // 160, 700, 110, 80
		System.out.println("at Stove");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 230/scaleMultiplier;
		yDestination = yPadding + 680/scaleMultiplier;
		//}
		command = Command.atStove;
	}
	@Override
	public void DoGoToBed() {
		System.out.println("at bed");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 700/scaleMultiplier;
		yDestination = yPadding + 214/scaleMultiplier;
		//}
		command = Command.atBed;

	}
	@Override
	public void DoGoToFridge() { // 380, 690, 130, 10
		System.out.println("at fridge");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 420/scaleMultiplier;
		yDestination = yPadding + 670/scaleMultiplier;
		//}
		command = Command.atFridge;

	}
	@Override
	public void DoExitHome() {
		System.out.println("exited home");
		
		xDestination = 400;
		yDestination = -40;
	
		command = Command.exited;

	}
	public void DoExitRoom() {
		System.out.println("exited room");
		System.err.println("room" + roomNumber);
		if(roomNumber == 1) {
			xDestination =  xRoomExit1;
			yDestination = yRoomExit1;
			command = Command.atRoomExit;
		}
		else if (roomNumber == 2) {
			xDestination =  xRoomExit1;
			yDestination = yRoomExit2;
			command = Command.atRoomExit;
		}
		else if (roomNumber == 3) {
			xDestination =  xRoomExit1;
			yDestination = yRoomExit3;
			command = Command.atRoomExit;
		}
		else if (roomNumber == 4) {
			xDestination =  xRoomExit2;
			yDestination = yRoomExit1;
			command = Command.atRoomExit;
		}
		else if (roomNumber == 5) {
			xDestination =  xRoomExit2;
			yDestination = yRoomExit2;
			command = Command.atRoomExit;
		}
		else if (roomNumber == 6) {
			xDestination =  xRoomExit2;
			yDestination = yRoomExit3;
			command = Command.atRoomExit;
		}
	

	}
	@Override
	public void DoReturnToHomePosition() {
		//System.out.println("At Home Position");
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		xDestination = xPadding + 84/scaleMultiplier;
		yDestination = yPadding + 224/scaleMultiplier;
		//}
		command = Command.atHome;

	}
	@Override
	public void DoGoToMailbox() {
		//for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
		System.out.println("At mailbox");
		xDestination = xPadding + 138/scaleMultiplier;
		yDestination = yPadding + 32/scaleMultiplier;
		//}
		command = Command.atMailbox;

	}

	@Override
	public int getXPos() {
		return xPos;
	}
	@Override
	public int getYPos() {
		return yPos;
	}
}
