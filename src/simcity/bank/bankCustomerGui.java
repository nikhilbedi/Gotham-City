package simcity.bank;

import java.awt.Color;

import Gui.RoleGui;
import Gui.Screen;

public class bankCustomerGui extends RoleGui {
	
	BankCustomerRole bankCustomer;
	boolean atTeller, atExit;
	int tellerNumber, prevX = 0, prevY = 0;
	
	public bankCustomerGui(BankCustomerRole bankCustomer) {
		this.bankCustomer = bankCustomer;
		myColor = Color.green;
		atTeller = false;
		atExit = false;
		xPos = 20;
		yPos = 20;
		xDestination = 20;
		yDestination = 20;
	}
	public bankCustomerGui(BankCustomerRole bankCustomer, Screen s) {
		this.bankCustomer = bankCustomer;
		myColor = Color.green;
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
				bankCustomer.msgAtTeller();
		}
		
		if(xPos == -20 && xDestination == -20 &&
				yPos == 250 && yDestination == 250
				&& !atExit) {
			atExit = true;
			bankCustomer.msgOutOfBank();
		}
		
		// Original message call to start the process
		if(xPos == 20 && yPos == 20 &&
			xDestination == 20 && yDestination == 20)
			bankCustomer.msgEnteredBank();
		
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
