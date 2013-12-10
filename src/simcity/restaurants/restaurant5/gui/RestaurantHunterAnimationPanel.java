package simcity.restaurants.restaurant5.gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.Robot;
import simcity.TheCity;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant5.CashierRole;
import simcity.restaurants.restaurant5.CookRole;
import simcity.restaurants.restaurant5.HostRole;
import simcity.restaurants.restaurant5.Restaurant5CustomerRole;
import simcity.restaurants.restaurant5.WaiterRole;

import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

public class RestaurantHunterAnimationPanel extends Screen{
	
	// = ScreenFactory.getMainScreen().getRestaurantList().get(4);
    private final int WINDOWX = 800;
    private final int WINDOWY = 360;

    private final int TABLEX = 200;
    private final int TABLEY = 250;

    private final int tableSize = 50;
    
    
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    public RestaurantHunterAnimationPanel() {
    	super();
    
    	//populate();
    }
    
    public void populate(){
    	PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");
		
		WaiterRole waiterRole = new WaiterRole(waiterPerson);
		HostRole hostRole = new HostRole(hostPerson);
		CookRole cookRole = new CookRole(cookPerson);
		CashierRole cashierRole = new CashierRole(cashierPerson);
		
		//make and set guis TODO
		WaiterGui waiterGui = new WaiterGui(waiterRole);
		CookGui cookGui = new CookGui(cookRole);
		
		waiterRole.setGui(waiterGui);
		//cookRole.setGui(cookGui);

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
		
		//r5.setCashier(cashierRole);
		//r5.setHost(hostRole);
		
		hostPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		cookPerson.startThread();

            

    }

    public void paintBackground(Graphics g) {
        super.paintBackground(g);
    	Graphics2D g2 = (Graphics2D)g;

        //Here is the table
        g2.setColor(Color.ORANGE);
        for(int i=0; i < 3; i++){
        g2.fillRect(50 + 100*i, 250, tableSize, tableSize);//200 and 250 need to be table params
        }
        
        //Cooking area
        int wall1x = 180;
        int wall1l = 75;
        int wall2y = 100;
        int wall2l = 100;
        int wall3x = wall1x + wall2l;
        int wall3l = 100;
        int wallWidth = 20;
        g2.setColor(Color.GRAY.brighter());
        g2.fillRect(wall1x, 0, wallWidth, wall1l);
        g2.fillRect(wall1x, wall2y, wall2l, wallWidth);
        g2.fillRect(wall3x, 0, wallWidth, wall3l);
        g2.setColor(Color.GRAY);
        int grillsize = 26;
        int grillx = wall3x - (grillsize+2);
        int grilly = wall2y-2 - grillsize - 2;
        g2.fillRect(grillx, grilly, grillsize, grillsize);
        g2.fillRect(grillx, grilly-30, grillsize, grillsize);
        g2.fillRect(grillx, grilly-60, grillsize, grillsize);
        
        g2.setColor(Color.YELLOW.darker());
        int counterSize = 20;
        int counterX = wall1x;
        int counterY = wall1l;
        g2.fillRect(counterX, counterY, counterSize, counterSize+5);
        
        
        //Customer waiting area
        g2.setColor(Color.BLACK);
        g2.drawLine(70, 40, wall1x, 40);

    }


}
