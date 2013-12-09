package simcity.restaurants.restaurant4.Restaurant4Gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketGui.MarketCashierGui;
import simcity.restaurants.Restaurant;
import simcity.Market.Market;
import simcity.restaurants.restaurant4.Restaurant4;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.Restaurant4HostRole;
import simcity.restaurants.restaurant4.Restaurant4SharedDataWaiterRole;
import simcity.restaurants.restaurant4.Restaurant4WaiterAgent;
import simcity.restaurants.restaurant4.Restaurant4WaiterRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;
import Gui.MainScreen;
import Gui.Screen;
import Gui.ScreenFactory;
import simcity.Robot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Restaurant4AnimationPanel extends Screen {
	//Restaurant4 r4 = (Restaurant4) mainScreen.getRestaurantList().get(3);	
	List<Restaurant> restaurants = TheCity.getRestaurantList();
	Restaurant r4;

	static final int X = 50;
	static final int Y = 170;
	static final int HEIGHT = 50;
	static final int WIDTH = 50;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private Image bufferImage;
    
    private Dimension bufferSize;
 /* public PersonAgent host = new Robot("host");
  public Restaurant4HostRole hostRole = new Restaurant4HostRole(host);
  public PersonAgent waiter = new Robot("waiter");
  public Restaurant4WaiterAgent waiterRole = new Restaurant4SharedDataWaiterRole(waiter);
  public Restaurant4WaiterGui waiterGui = new Restaurant4WaiterGui((Restaurant4Waiter) waiterRole, 1);
  public PersonAgent cook = new Robot("Cook");
  public Restaurant4CookRole cookRole = new Restaurant4CookRole(cook);
  public Restaurant4CookGui cookGui = new Restaurant4CookGui(cookRole);
//  public PersonAgent customer = new PersonAgent("Customer");
  public PersonAgent cashier = new Robot("cashier");*/
 // public Restaurant4CustomerRole customerRole = new Restaurant4CustomerRole(customer);
 // public Restaurant4CustomerGui customerGui = new Restaurant4CustomerGui(customerRole);
//  public Restaurant4CashierRole cashierRole = new Restaurant4CashierRole(cashier);
 // public TheCity cp;

    public Restaurant4AnimationPanel() {
    	super();
    	for(Restaurant r : restaurants) {
			if(r.getName().equals("Restaurant 4")){
				r4 = r;
			}
		}
    	populate();
    	
    }
    
    public Restaurant getRestaurant(){
    	return r4;
    }
    
    public void paintBackground(Graphics g2){
    	super.paintBackground(g2);
    	
    	 java.net.URL imgtemp = this.getClass().getResource("/resources/mika/table.png");
    	 ImageIcon  current= new ImageIcon(imgtemp);
         g2.drawImage(current.getImage(), X-40, Y-20, null);
        g2.finalize();
        
        java.net.URL imgtemp1 = this.getClass().getResource("/resources/mika/table.png");
        ImageIcon  current1= new ImageIcon(imgtemp1);
        g2.drawImage(current1.getImage(), X+50, Y-20, null);
       g2.finalize();
        
       java.net.URL imgtemp2 = this.getClass().getResource("/resources/mika/table.png");
       ImageIcon  current2= new ImageIcon(imgtemp2);
        g2.drawImage(current2.getImage(), X+140, Y-20, null);
        g2.finalize();

        java.net.URL imgtemp3 = this.getClass().getResource("/resources/mika/table.png");
        ImageIcon  current3= new ImageIcon(imgtemp3);
         g2.drawImage(current3.getImage(), X+230, Y-20, null);
         g2.finalize();
     
        
         java.net.URL imgtemp4 = this.getClass().getResource("/resources/mika/sm.jpg");
         ImageIcon  current4= new ImageIcon(imgtemp4);
          g2.drawImage(current4.getImage(), 220, 10, null);
          g2.finalize();
        
        
        g2.setColor(Color.GREEN);
        g2.fillRect(2, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(103, 300, 100, 20);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(107, 303, 15, 15);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(130, 303, 15, 15);
        
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(153, 303, 15, 15);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(183, 320, 20, 25);
        
        g2.setColor(Color.CYAN);
        g2.fillRect(30, 260, 40, 20);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Stand", 35, 280);
    }
    
    
    public void populate(){
    	
/*    	addGui(waiterGui);
    	addGui(cookGui);
    	//addGui(customerGui);
    	waiterRole.setGui(waiterGui);
    	cookRole.setGui(cookGui);host.startThread();
    	host.addRole(hostRole);
    	cashier.startThread();
    	cashier.addRole(cashierRole);
    	waiter.startThread();
    	waiter.addRole(waiterRole);
    	cook.startThread();
    	cook.addRole(cookRole);
    //	customer.startThread();
    //	customer.addRole(customerRole);
    	hostRole.setWaiter((Restaurant4Waiter) waiterRole);
    	waiterRole.setCashier(cashierRole);
    	waiterRole.setCook(cookRole);
    	waiterRole.setHost(hostRole);
    	cookRole.setCashier(cashierRole);
    //	customerRole.setGui(customerGui);
    //	customerRole.setHost(hostRole);
    //	customerRole.setCashier(cashierRole);
    	
    //	cookRole.setMarketCashier();
    	//customerGui.setHungry();
    	r4.setHost(hostRole);
    	r4.setCashier(cashierRole);
    	((Restaurant4) r4).setWaiter((Restaurant4Waiter) waiterRole);
    	((Restaurant4) r4).setCook(cookRole);*/
    	/*List<Market> m = mainScreen.getMarketList();
    	r4.getCook().setMarketCashier(m.get(0).getCashier());*/
    }
    
}
