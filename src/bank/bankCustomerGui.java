package bank;

import java.awt.Color;

import Gui.RoleGui;

public class bankCustomerGui extends RoleGui {
	
	BankCustomerRole bankCustomer;
<<<<<<< HEAD
	boolean atTeller, atExit;
=======
	boolean atTeller;
>>>>>>> Cleaned up code a bit and added restaurant.
	int tellerNumber, prevX = 0, prevY = 0;
	
	public bankCustomerGui(BankCustomerRole bankCustomer) {
		this.bankCustomer = bankCustomer;
		myColor = Color.green;
		atTeller = false;
<<<<<<< HEAD
		atExit = false;
		xPos = 20;
		yPos = 20;
		xDestination = 20;
		yDestination = 20;
=======
		xPos = 20;
		yPos = 20;	
>>>>>>> Cleaned up code a bit and added restaurant.
	}
	
	public void updatePosition() {
		super.updatePosition();
		
		if(xPos == 520 && xDestination == 520 &&
<<<<<<< HEAD
			yPos == 330 + tellerNumber*50 && 
			yDestination == 330 + tellerNumber*50 
=======
			yPos == 350 + tellerNumber*50 && 
			yDestination == 350 + tellerNumber*50 
>>>>>>> Cleaned up code a bit and added restaurant.
			&& !atTeller) {
				atTeller = true;
				bankCustomer.msgAtTeller();
		}
<<<<<<< HEAD
		
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
=======
		if(xPos != xDestination ||
			yPos != yDestination)
			atTeller = false;
			
>>>>>>> Cleaned up code a bit and added restaurant.
		
		prevX = xPos;
		prevY = yPos;
	}
	
	public void GoToTeller(int tellerIndex) {
		xDestination = 520;
<<<<<<< HEAD
		yDestination = 330 + tellerIndex * 50;
=======
		yDestination = 350 + tellerIndex * 50;
>>>>>>> Cleaned up code a bit and added restaurant.
		
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
