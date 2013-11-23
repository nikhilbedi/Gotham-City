package simcity.bank;

import java.awt.Color;

import Gui.RoleGui;

public class bankCustomerGui extends RoleGui {
	
	BankCustomerRole bankCustomer;
	
	public bankCustomerGui(BankCustomerRole bankCustomer) {
		this.bankCustomer = bankCustomer;
		myColor = Color.green;
		xPos = 20;
		yPos = 20;
	}

	public void GoToTeller(int tellerIndex) {
		xDestination = 500;
		yDestination = 300 + tellerIndex * 50;
	}

	public void GetInLine(int waitingNumber) {
		xDestination = 350 - (waitingNumber) * 24;
		yDestination = 330;
	}
	
	public void LeaveBank() {
		xDestination = -20;
		yDestination = 300;
	}

	public void setX(int x) { xPos = x;}
	
	public void setY(int y) { yPos = y;}
}
