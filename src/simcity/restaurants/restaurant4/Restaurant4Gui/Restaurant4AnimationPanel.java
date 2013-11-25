package simcity.restaurants.restaurant4.Restaurant4Gui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketGui.MarketCashierGui;
import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.Restaurant4HostRole;
import simcity.restaurants.restaurant4.Restaurant4WaiterRole;
import Gui.Screen;
import simcity.Robot;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Restaurant4AnimationPanel extends Screen {
	static final int X = 50;
	static final int Y = 170;
	static final int HEIGHT = 50;
	static final int WIDTH = 50;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private Image bufferImage;
    private Dimension bufferSize;
  public PersonAgent host = new Robot("host");
  public Restaurant4HostRole hostRole = new Restaurant4HostRole(host);
  public PersonAgent waiter = new Robot("waiter");
  public Restaurant4WaiterRole waiterRole = new Restaurant4WaiterRole(waiter);
  public Restaurant4WaiterGui waiterGui = new Restaurant4WaiterGui(waiterRole, 1);
  public PersonAgent cook = new Robot("Cook");
  public Restaurant4CookRole cookRole = new Restaurant4CookRole(cook);
  public Restaurant4CookGui cookGui = new Restaurant4CookGui(cookRole);
  public PersonAgent customer = new PersonAgent("Customer");
  public PersonAgent cashier = new Robot("cashier");
  public Restaurant4CustomerRole customerRole = new Restaurant4CustomerRole(customer);
  public Restaurant4CustomerGui customerGui = new Restaurant4CustomerGui(customerRole);
  public Restaurant4CashierRole cashierRole = new Restaurant4CashierRole(cashier);
    private List<Gui> guis = new ArrayList<Gui>();

    public Restaurant4AnimationPanel() {
    	populate();
    	/*setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(20, this );
    	timer.start();*/
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
       /* g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );*/

        //Here is the table
/*        g2.setColor(Color.ORANGE);
        g2.fillRect(X, Y, WIDTH, HEIGHT );//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(X+90, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+180, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+270, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.GREEN);
        g2.fillRect(200, 20, 70, 30);
        
        g2.setColor(Color.GREEN);
        g2.fillRect(2, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(103, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(183, 320, 20, 25);*/
        
       /* g2.setColor(Color.BLACK);
        g2.fillRect(30, 50, 110, 2);*/
        
       /* for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }*/
    }

    public void paintObstacles(Graphics g2){
        g2.setColor(Color.ORANGE);
        g2.fillRect(X, Y, WIDTH, HEIGHT );//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(X+90, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+180, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+270, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.GREEN);
        g2.fillRect(200, 20, 70, 30);
        
        g2.setColor(Color.GREEN);
        g2.fillRect(2, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(103, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(183, 320, 20, 25);
    }
    
    
    public void populate(){
    	
    	addGui(waiterGui);
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
    	customer.addRole(customerRole);
    	hostRole.setWaiter(waiterRole);
    	waiterRole.setCashier(cashierRole);
    	waiterRole.setCook(cookRole);
    	waiterRole.setHost(hostRole);
    	cookRole.setCashier(cashierRole);
    	customerRole.setGui(customerGui);
    	customerRole.setHost(hostRole);
    	customerRole.setCashier(cashierRole);
    	
    //	cookRole.setMarketCashier();
    	customerGui.setHungry();
    }
    
}
