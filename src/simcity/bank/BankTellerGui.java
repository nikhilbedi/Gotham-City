package simcity.bank;

import java.awt.Color;

import agent.Role;

import simcity.bank.interfaces.BankTeller;

import Gui.RoleGui;
import Gui.Screen;

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

	public BankTellerGui(BankTeller teller, Screen meScreen) {
		super((Role) teller, meScreen);
	}	
}
