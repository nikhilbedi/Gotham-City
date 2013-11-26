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
import simcity.restaurants.restaurant4.Restaurant4WaiterRole;
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
	MainScreen mainScreen = ScreenFactory.getMainScreen();
	
	Restaurant4 r4 = (Restaurant4) mainScreen.getRestaurantList().get(3);
	

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
//  public PersonAgent customer = new PersonAgent("Customer");
  public PersonAgent cashier = new Robot("cashier");
 // public Restaurant4CustomerRole customerRole = new Restaurant4CustomerRole(customer);
 // public Restaurant4CustomerGui customerGui = new Restaurant4CustomerGui(customerRole);
  public Restaurant4CashierRole cashierRole = new Restaurant4CashierRole(cashier);
  public TheCity cp;
  private List<Gui> guis = new ArrayList<Gui>();

    public Restaurant4AnimationPanel() {
    	super();
    	populate();
    	
    }
    
    public Restaurant4 getRestaurant(){
    	return r4;
    }
    
    
    public void setCity(TheCity c){
    	cp = c;
    }


   /* public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

    }*/

    public void paintBackground(Graphics g2){
    	super.paintBackground(g2);
    	
      /*  g2.setColor(Color.ORANGE);
        g2.fillRect(X, Y, WIDTH, HEIGHT );//200 and 250 need to be table params
*/
        Graphics2D g1 = (Graphics2D) g2;
        Image img2 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/restaurants/restaurant4/Restaurant4Gui/table.png");
        g1.drawImage(img2, X-40, Y-20, null);
        g1.finalize();
   	
        
       /* g2.setColor(Color.ORANGE);
        g2.fillRect(X+90, Y, WIDTH, HEIGHT );*/
        Graphics2D g4 = (Graphics2D) g2;
        Image img3 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/restaurants/restaurant4/Restaurant4Gui/table.png");
        g4.drawImage(img3, X+50, Y-20, null);
        g4.finalize();
        
        
        /*g2.setColor(Color.ORANGE);
        g2.fillRect(X+180, Y, WIDTH, HEIGHT );*/
        Graphics2D g5 = (Graphics2D) g2;
        Image img4 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/restaurants/restaurant4/Restaurant4Gui/table.png");
        g5.drawImage(img4, X+140, Y-20, null);
        g5.finalize();
      /*  
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+270, Y, WIDTH, HEIGHT );*/
        Graphics2D g6 = (Graphics2D) g2;
        Image img5 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/restaurants/restaurant4/Restaurant4Gui/table.png");
        g6.drawImage(img5, X+230, Y-20, null);
        g6.finalize();
        
      /*  g2.setColor(Color.GREEN);
        g2.fillRect(200, 20, 70, 30);*/
        
        Graphics2D g10 = (Graphics2D) g2;
        Image img10 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/restaurants/restaurant4/Restaurant4Gui/sm.jpg");
        g10.drawImage(img10, 220, 10, null);
        g10.finalize();
        
        
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
    //	customer.addRole(customerRole);
    	hostRole.setWaiter(waiterRole);
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
    	r4.setWaiter(waiterRole);
    	r4.setCook(cookRole);
    	/*List<Market> m = mainScreen.getMarketList();
    	r4.getCook().setMarketCashier(m.get(0).getCashier());*/
    }
    
}
