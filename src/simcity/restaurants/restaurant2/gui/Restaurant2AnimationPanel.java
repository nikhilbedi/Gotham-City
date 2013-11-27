package simcity.restaurants.restaurant2.gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.Robot;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant2.Restaurant2;
import simcity.restaurants.restaurant2.CashierRole;
import simcity.restaurants.restaurant2.CookRole;
import simcity.restaurants.restaurant2.HostRole;
import simcity.restaurants.restaurant2.WaiterRole;
import simcity.restaurants.restaurant2.gui.CookGui;
import simcity.restaurants.restaurant2.gui.WaiterGui;
import Gui.MainScreen;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Restaurant2AnimationPanel extends Screen {
	MainScreen mainScreen = ScreenFactory.getMainScreen();
	Restaurant r2;
	//TODO WE NEED TO CHECK THE TYPE OF EACH RESTAURANT TO MAKE SURE WE MAKE IT EQUAL TO THE CORRECT REST.
	List<Restaurant> restaurants = mainScreen.getRestaurantList();
	private final int WINDOWX1 = 0;
	private final int WINDOWY1 = 0;
	private final int WINDOWX = 450;
	private final int WINDOWY = 350;
	private final int TIMING = 20;
	private final int TABLERECTX = 200;
	private final int TABLERECTY = 250;
	private final int TABLERECTWIDTH = 50;
	private final int TABLERECTHEIGHT = 50;
	private final int KITCHENRECTX = 450;
	private final int KITCHENRECTY = 0;
	private final int KITCHENRECTWIDTH = 30;
	private final int KITCHENRECTHEIGHT = 200;
	private JButton breakButton = new JButton("Send waiter on Break");
	private Image bufferImage;
	private Dimension bufferSize;

	public Restaurant2AnimationPanel() {
		super();
		for(Restaurant r : restaurants) {
			if(r.getName().equals("Restaurant 2")){
				r2 = r;
			}
		}
		populate();
		//setSize(WINDOWX, WINDOWY);
		//setVisible(true);

		//bufferSize = this.getSize();

		//Timer timer = new Timer(TIMING, this );
		//timer.start();
	}


	public void paintBackground(Graphics g) {
		super.paintBackground(g);

		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		//g2.setColor(Color.white);
		//g2.fillRect(WINDOWX1, WINDOWY1, WINDOWX, WINDOWY );

		//Here is the table
		g2.setColor(Color.ORANGE);
		g2.fillRect(TABLERECTX, TABLERECTY, TABLERECTWIDTH, TABLERECTHEIGHT);//200 and 250 need to be table params

		g2.fillRect(TABLERECTX + 80, TABLERECTY-80, TABLERECTWIDTH, TABLERECTHEIGHT);//EXTRA TABLES
		g2.fillRect(TABLERECTX + 160, TABLERECTY-160, TABLERECTWIDTH, TABLERECTHEIGHT);

		//Kitchen Area
		g2.setColor(Color.BLACK);
		g2.fillRect(KITCHENRECTX, KITCHENRECTY, KITCHENRECTWIDTH, KITCHENRECTHEIGHT);

		g2.fillRect(KITCHENRECTX + 70, KITCHENRECTY, KITCHENRECTWIDTH, KITCHENRECTHEIGHT);
	}

	public void populate(){
		PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");
		//PersonAgent custPerson = new PersonAgent("customer");

		WaiterRole waiterRole = new WaiterRole(waiterPerson);
		HostRole hostRole = new HostRole(hostPerson);
		CookRole cookRole = new CookRole(cookPerson);
		CashierRole cashierRole = new CashierRole(cashierPerson);

		WaiterGui waiterGui = new WaiterGui(waiterRole);
		CookGui cookGui = new CookGui(cookRole);

		waiterRole.setGui(waiterGui);
		cookRole.setGui(cookGui);

		waiterPerson.addRole(waiterRole);
		hostPerson.addRole(hostRole);
		cookPerson.addRole(cookRole);
		cashierPerson.addRole(cashierRole);

		addGui(waiterGui);
		addGui(cookGui);

		waiterRole.setHost(hostRole);
		waiterRole.setCook(cookRole);
		waiterRole.setCashier(cashierRole);
		hostRole.addWaiter(waiterRole);

		hostPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		cookPerson.startThread();
		
		r2.setCashier(cashierRole);
		r2.setHost(hostRole);
		
		
	}

}

