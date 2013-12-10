package simcity.bank;

import java.awt.Color;

import Gui.RoleGui;
import Gui.Screen;

public class BankRobberGui extends RoleGui {
	
	BankRobberRole bankRobber;
	boolean atTeller, atExit;
	int tellerNumber, prevX = 0, prevY = 0;
	
	public BankRobberGui(BankRobberRole bankRobber) {
		this.bankRobber = bankRobber;
		myColor = Color.black;
		atTeller = false;
		atExit = false;
		xPos = 20;
		yPos = 20;
		xDestination = 20;
		yDestination = 20;
	}
	public BankRobberGui(BankRobberRole bankRobber, Screen s) {
		this.bankRobber = bankRobber;
		myColor = Color.black;
		atTeller = false;
		atExit = false;
		xPos = 20;
		yPos = 20;
		xDestination = 20;
		yDestination = 20;
		homeScreen = s;
	}
	
	public void updatePosition() {
		super.updatePosition();
		
		if(xPos == 520 && xDestination == 520 &&
			yPos == 330 + tellerNumber*50 && 
			yDestination == 330 + tellerNumber*50 
			&& !atTeller) {
				atTeller = true;
				bankRobber.msgAtTeller();
		}
		
		if(xPos == -20 && xDestination == -20 &&
				yPos == 250 && yDestination == 250
				&& !atExit) {
			atExit = true;
			bankRobber.msgOutOfBank();
		}
		
		// Original message call to start the process
		if(xPos == 20 && yPos == 20 &&
			xDestination == 20 && yDestination == 20)
			{
			//xDestination = 200;
			//yDestination = 200;
			//TODO this is hacked for testing purposes
			//System.out.println("here we are");
			//bankCustomer.msgEnteredBank();
			//GetInLine
			}
		
		if(xPos != xDestination ||
			yPos != yDestination) {
			atTeller = false;
			atExit = false;
		}		
		
		prevX = xPos;
		prevY = yPos;
	}
	
	public void GoToTeller(int tellerIndex) {
		xDestination = 520;
		yDestination = 330 + tellerIndex * 50;
		
		tellerNumber = tellerIndex;
	}

	public void GetInLine(int waitingNumber) {
		xDestination = 350 - (waitingNumber) * 24;
		yDestination = 330;
	}
	
	public void LeaveBank() {
		xDestination = -20;
		yDestination = 250;
	}

	public void setX(int x) { xPos = x;}
	
	public void setY(int y) { yPos = y;}
}
