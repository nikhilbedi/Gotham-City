package simcity.bank;

import java.awt.Color;

import agent.Role;

import simcity.bank.interfaces.BankGreeter;

import Gui.RoleGui;
import Gui.Screen;

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

	public BankGreeterGui(BankGreeter greeter, Screen meScreen) {
		super((Role) greeter, meScreen);
		this.bankGreeter = bankGreeter;
		myColor = Color.magenta;
		super.setColor(myColor);
		xPos = 350;
		yPos = 300;
		xDestination = 350;
		yDestination = 300;
	}	
}
