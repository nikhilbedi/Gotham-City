package simcity.Bank;

import java.awt.Color;

import Gui.RoleGui;

public class BankTellerGui extends RoleGui {
	
	BankTellerRole bankTeller;
	
	public BankTellerGui(BankTellerRole bankTeller) {
		this.bankTeller = bankTeller;
		myColor = Color.blue;
		xPos = 550;
		yPos = 330;
		xDestination = 550;
		yDestination = 330;
	}	
}
