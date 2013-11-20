package bank;

import java.awt.Color;

import Gui.RoleGui;

public class BankGreeterGui extends RoleGui {
	
	BankGreeterRole bankGreeter;
	
	public BankGreeterGui(BankGreeterRole bankGreeter) {
		this.bankGreeter = bankGreeter;
		myColor = Color.magenta;
		xPos = 350;
		yPos = 300;
		xDestination = 350;
		yDestination = 300;
	}	
}
