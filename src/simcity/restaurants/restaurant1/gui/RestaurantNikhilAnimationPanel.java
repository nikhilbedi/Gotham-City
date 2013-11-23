package simcity.restaurants.restaurant1.gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.WaiterRole;
import Gui.Screen;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class RestaurantNikhilAnimationPanel extends Screen  {

	/*private final int WINDOWX = 350;
    private final int WINDOWY = 250;
    private Image bufferImage;
    private Dimension bufferSize; */
	private int xPos, yPos,width, height;

	//private List<Gui> guis = new ArrayList<Gui>();

	public RestaurantNikhilAnimationPanel() { 
		populate();
		//Timer timer = new Timer(20, this );
		//timer.start();
	}

	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		Graphics2D g2 = (Graphics2D)g;
		//Second and third table created manually
		Graphics2D g3 = (Graphics2D)g;
		Graphics2D g4 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		/* g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight() );*/

		//Agent Positions
		g2.setColor(Color.MAGENTA);
		xPos=100;
		yPos = 20;
		g2.drawString("Waiter Home", xPos, yPos);

		g2.setColor(Color.GREEN);
		xPos=20;
		yPos = 20;
		g2.drawString("Waiting", xPos, yPos);

		g2.setColor(Color.RED);
		xPos=830;
		yPos = 20;
		g2.drawString("KITCHEN", xPos, yPos);

		//Here is the table
		g2.setColor(Color.BLUE); 
		xPos = 100;
		yPos = 150;
		width = 50;
		height = 50; 
		g2.fillRect(xPos, yPos, width, height);//200 and 250 need to be table params

		g2.setColor(Color.BLUE);
		xPos = 300;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);

		g2.setColor(Color.BLUE);
		xPos = 500;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);

		xPos = 700;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);
	}



	public void populate(){
		PersonAgent custPerson = new PersonAgent("customer");
		PersonAgent waiterPerson = new PersonAgent("waiter");
		PersonAgent hostPerson = new PersonAgent("host");
		PersonAgent cookPerson = new PersonAgent("cook");
		PersonAgent cashierPerson = new PersonAgent("cashier");
		//PersonAgent custPerson = new PersonAgent("customer");
		
		CustomerRole custRole = new CustomerRole(custPerson);
		WaiterRole waiterRole = new WaiterRole(waiterPerson);
		HostRole hostRole = new HostRole(hostPerson);
		CookRole cookRole = new CookRole(cookPerson);
		CashierRole cashierRole = new CashierRole(cashierPerson);
		
		CustomerGui custGui = new CustomerGui(custRole);
		WaiterGui waiterGui = new WaiterGui(waiterRole,0);
		CookGui cookGui = new CookGui(cookRole);
		
		custRole.setGui(custGui);
		waiterRole.setGui(waiterGui);
		cookRole.setGui(cookGui);
		
/*		custRole.setPerson(custPerson);
		waiterRole.setPerson(waiterPerson);
		hostRole.setPerson(hostPerson);
		cookRole.setPerson(cookPerson);
		cashierRole.setPerson(cashierPerson);*/

		custPerson.addRole(custRole);
		waiterPerson.addRole(waiterRole);
		hostPerson.addRole(hostRole);
		cookPerson.addRole(cookRole);
		cashierPerson.addRole(cashierRole);
		
		
		addGui(custGui);
		addGui(waiterGui);
		addGui(cookGui);
		
		custRole.setHost(hostRole);
		custRole.setCashier(cashierRole);
		waiterRole.setHost(hostRole);
		waiterRole.setCook(cookRole);
		waiterRole.setCashier(cashierRole);
		hostRole.addWaiter(waiterRole);
		
		custRole.gotHungry();
		

		hostPerson.startThread();
		custPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		cookPerson.startThread();
		
		/*HostRole host = new HostRole();
		HostGui hostGui = new HostGui(host);
		PersonAgent hostp = new PersonAgent("Host");
		
		//Implementing Cooks
		CookRole cook = new CookRole();
		CookGui cookgui = new CookGui(cook);
		
		//implement cashier
		CashierRole cashier = new CashierRole();

		WaiterRole waiter = new WaiterRole();

		//Customer
		CustomerRole customer = new CustomerRole();

		PersonAgent waiterp = new PersonAgent("waiter");


		
		PersonAgent customerp = new PersonAgent("customer");
		PersonAgent cookp = new PersonAgent("cook");
		PersonAgent cashierp = new PersonAgent("cashier");

		CustomerGui cgui = new CustomerGui(customer);
		WaiterGui wgui = new WaiterGui(waiter,0);



		host.setGui(hostGui);
		host.setPerson(hostp);

		//setting cook

		cook.setPerson(cookp);
		cook.setGui(cookgui);
		cashier.setPerson(cashierp);
		waiter.setPerson(waiterp);
		customer.setPerson(customerp);

		hostp.startThread();
		customerp.startThread();
		cashierp.startThread();
		waiterp.startThread();
		cookp.startThread();

		hostp.addRole(host);
		customerp.addRole(customer);
		cashierp.addRole(cashier);
		waiterp.addRole(waiter);
		cookp.addRole(cook);

		customer.setGui(cgui);
		waiter.setGui(wgui);
		host.msgIWantFood(customer);

		addGui(cookgui);
		addGui(new HostGui(host));
		addGui(wgui);
		addGui(cgui);*/

	}
}

