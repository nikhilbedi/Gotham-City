package simcity.bank;

import java.awt.Graphics;

import simcity.PersonAgent;
import Gui.Screen;

public class bankAnimationPanel extends Screen{
	
	public bankAnimationPanel() {
		//populate();
	}
	
	public void paintBackground(Graphics g){
		super.paintBackground(g);
		g.drawLine(545, 120, 545, 620);
		g.drawLine(150, 325, 450, 325);
		g.drawLine(150, 355, 450, 355);
	}
	
	public void populate() {
		PersonAgent person = new PersonAgent("customer"), 
				person2 = new PersonAgent("greeter"), 
				person3 = new PersonAgent("teller"), 
				person4 = new PersonAgent("customer2");
		
		BankCustomerRole bankCustomer = new BankCustomerRole(person);
		BankCustomerRole bankCustomer2 = new BankCustomerRole(person4);
		BankGreeterRole bankGreeter = new BankGreeterRole(person2);
		BankTellerRole bankTeller = new BankTellerRole(person3);
		
		BankDatabase db = new BankDatabase();
		
		bankCustomer.setGreeter(bankGreeter);
		bankCustomer2.setGreeter(bankGreeter);
		bankGreeter.addTeller(bankTeller);
		bankTeller.setGreeter(bankGreeter);
		bankTeller.setBankDatabase(db);
		
		bankCustomer.setTransactions();
		bankCustomer2.setTransactions();
		
		bankCustomerGui customerGui = new bankCustomerGui(bankCustomer);
		BankGreeterGui greeterGui = new BankGreeterGui(bankGreeter);
		bankCustomerGui customerGui2 = new bankCustomerGui(bankCustomer2);
		BankTellerGui tellerGui = new BankTellerGui(bankTeller);
		
		bankCustomer.setGui(customerGui, 50, 50);
		bankCustomer2.setGui(customerGui2, 10, 10);
		bankGreeter.setGui(greeterGui);
		bankTeller.setGui(tellerGui);
		
		person.addRole(bankCustomer);
		person.startThread();
		
		person2.addRole(bankGreeter);
		person2.startThread();
		
		person3.addRole(bankTeller);
		person3.startThread();
		
		person4.addRole(bankCustomer2);
		person4.startThread();
		
		addGui(customerGui);
		addGui(customerGui2);
		addGui(greeterGui);
		addGui(tellerGui);
		//bankCustomer.msgGoToTeller(bankTeller);
	}
}
