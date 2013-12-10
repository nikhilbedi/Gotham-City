package simcity.restaurants.restaurant3.gui;

import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant3.*;
import simcity.PersonAgent;
import simcity.Robot;
import simcity.TheCity;
import Gui.MainScreen;
import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

import java.util.*;

public class RestaurantEvanAnimationPanel extends Screen  {
	//TODO WE NEED TO CHECK THE TYPE OF EACH RESTAURANT TO MAKE SURE WE MAKE IT EQUAL TO THE CORRECT REST.
	Restaurant r3;
	List<Restaurant> restaurants = TheCity.getRestaurantList();
	/*private final int WINDOWX = 350;
	private final int WINDOWY = 250;
	private Image bufferImage;
	private Dimension bufferSize; */
	private int width, height;
	static final int tablexPos = 200;
	static final int tableyPos = 150;
	static final int WIDTH = 50;
	static final int HEIGHT = 50;


	public RestaurantEvanAnimationPanel() { 
		super();
	 	for(Restaurant r : restaurants) {
			if(r.getName().equals("Restaurant 3")){
				r3 = r;
			}
		}
		populate();
		//System.err.println(" "+temp);
		//Timer timer = new Timer(20, this );
		//timer.start();
	}


	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		Graphics g2 = (Graphics)g;

		Graphics cookingArea = (Graphics)g;
		Graphics platingArea = (Graphics)g;
		Graphics cashierArea = (Graphics)g;
		Graphics revolvingStand = (Graphics)g;
		super.paintBackground(g);


		cookingArea.setColor(Color.RED);
		cookingArea.fillRect(620, 180, 40, 80);

		cookingArea.setColor(Color.CYAN);
		cookingArea.fillRect(0, 130, 40, 80);

		platingArea.setColor(Color.PINK);
		platingArea.fillRect(0, 210, 40, 80);
		
		revolvingStand.setColor(Color.LIGHT_GRAY);
		revolvingStand.fillRect(15, 110, 20, 20);

		//Here is the table
		g2.setColor(Color.ORANGE);
		g2.fillRect(tablexPos, tableyPos, WIDTH, HEIGHT);//200 and 250 need to be table params

		g2.setColor(Color.ORANGE);
		g2.fillRect(tablexPos+85, tableyPos, WIDTH, HEIGHT);

		g2.setColor(Color.ORANGE);
		g2.fillRect(tablexPos, tableyPos+85, WIDTH, HEIGHT);

		g2.setColor(Color.ORANGE);
		g2.fillRect(tablexPos+85, tableyPos+85, WIDTH, HEIGHT);

		
		//Screen restScreen = ScreenFactory.getMeScreen("rest3");
		//List<RoleGui> guis = restScreen.guis;
		
		
		
//		for(RoleGui gui : guis) {
//			
//				Graphics order = (Graphics)g;
//				if (gui instanceof WaiterGui) {
//					WaiterGui waiterGui = (WaiterGui) gui;
//					if (waiterGui.deliveringFood)
//					{
//						order.drawString("bringing " + waiterGui.order, waiterGui.getXPos(), waiterGui.getYPos());
//					}
//				}
//				if (gui instanceof Restaurant3CustomerGui)
//				{
//					Restaurant3CustomerGui customerGui = (Restaurant3CustomerGui) gui;
//					if (customerGui.waitingForFood)
//					{
//						order.drawString("waiting for " + customerGui.order, customerGui.getXPos(), customerGui.getYPos());
//					}
//					if (customerGui.receivedFood)
//					{
//						order.drawString("eating " + customerGui.order, customerGui.getXPos(), customerGui.getYPos());
//					}
//				}
//
//				if (gui instanceof CookGui){
//					CookGui cookGui = (CookGui) gui;
//					if(cookGui.cooking) {
//						if(cookGui.tableNumber == 1) {
//							order.drawString("cooking " + cookGui.order, 0, 145);
//						}
//						if(cookGui.tableNumber == 2) {
//							order.drawString("cooking " + cookGui.order, 0, 160);
//						}
//						if(cookGui.tableNumber == 3) {
//							order.drawString("cooking " + cookGui.order, 0, 175);
//						}
//						if(cookGui.tableNumber == 4) {
//							order.drawString("cooking " + cookGui.order, 0, 195);
//						}
//					}
//					if(cookGui.plating) {
//						if(cookGui.tableNumber == 1) {
//							order.drawString("plating " + cookGui.order, 0, 225);
//						}
//						if(cookGui.tableNumber == 2) {
//							order.drawString("plating " + cookGui.order, 0, 240);
//						}
//						if(cookGui.tableNumber == 3) {
//							order.drawString("plating " + cookGui.order, 0, 255);
//						}
//						if(cookGui.tableNumber == 4) {
//							order.drawString("plating " + cookGui.order, 0, 270);
//						}
//					}
//				}
//
//				// else if(gui instanceof CustomerGui) {
//				//	CustomerGui customerGui = (CustomerGui) gui;
//				//order.drawString(customerGui.choice, customerGui.getXPos(), customerGui.getYPos());
//
//				//}
//
//			}
		}
	
	public void populate(){

		PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent waiterSharedDataPerson = new Robot("waiterSharedData");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");
		//PersonAgent custPerson = new PersonAgent("customer");

		WaiterRole waiterRole = new WaiterRole(waiterPerson);
		WaiterSharedData waiterSharedDataRole = new WaiterSharedData(waiterSharedDataPerson);
		HostRole hostRole = new HostRole(hostPerson);
		Restaurant3CookRole cookRole = new Restaurant3CookRole(cookPerson);
		Restaurant3CashierRole cashierRole = new Restaurant3CashierRole(cashierPerson);

		WaiterGui waiterGui = new WaiterGui(waiterRole,0);
		WaiterGui waiterSharedDataGui = new WaiterGui(waiterSharedDataRole, 0);
		CookGui cookGui = new CookGui(cookRole);

		waiterRole.setGui(waiterGui);
		waiterSharedDataRole.setGui(waiterSharedDataGui);
		cookRole.setGui(cookGui);

		waiterPerson.addRole(waiterRole);
		waiterSharedDataPerson.addRole(waiterSharedDataRole);
		hostPerson.addRole(hostRole);
		cookPerson.addRole(cookRole);
		cashierPerson.addRole(cashierRole);

		addGui(waiterGui);
		addGui(waiterSharedDataGui);
		addGui(cookGui);

		waiterRole.setHost(hostRole);
		waiterRole.setCook(cookRole);
		waiterRole.setCashier(cashierRole);
		hostRole.setWaiter(waiterRole);
		
		waiterSharedDataRole.setHost(hostRole);
        waiterSharedDataRole.setCook(cookRole);
        waiterSharedDataRole.setCashier(cashierRole);
        hostRole.setWaiter(waiterSharedDataRole);

		r3.setCashier(cashierRole);
		r3.setHost(hostRole);

		hostPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		waiterSharedDataPerson.startThread();
		cookPerson.startThread();
	}
}

