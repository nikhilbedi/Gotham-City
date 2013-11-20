/*package simcity.Restaurant4.Restaurant4Gui;

import restaurant.CustomerAgent;
import restaurant.HostAgent;
import restaurant.WaiterAgent;
import restaurant.CookAgent;
import restaurant.WaiterAgent.WaiterState;
import restaurant.MarketAgent;
import restaurant.CashierAgent;
import simcity.Restaurant4.Restaurant4HostRole;
import simcity.Restaurant4.interfaces.Restaurant4Host;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

*//**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 *//*
public class RestaurantPanel extends JPanel {

    //Host, cook, waiters and customers
    private Restaurant4HostRole host = new Restaurant4HostRole("Host");
    private CookAgent cook = new CookAgent("Cook");
    private Restaurant4CookGui cg;
    private CashierAgent cashier = new CashierAgent("Cashier");
    private Vector<CustomerAgent> customers = new Vector<CustomerAgent>();
    private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();
    private Vector<MarketAgent> markets = new Vector<MarketAgent>();
    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private JPanel group = new JPanel();
    private RestaurantGui gui; //reference to main gui
    public boolean paused = false;
    private final int rows = 1;
    private final int columns = 2;
    private final int Gap = 10;
    private MarketAgent market1 = new MarketAgent("market1");
    private MarketAgent market2 = new MarketAgent("market2");
    private MarketAgent market3 = new MarketAgent("market3");
    private int guiCounter = 0;
    
    public RestaurantPanel(RestaurantGui gui) {
    	
        this.gui = gui;
        host.startThread();
        cashier.startThread();
        setLayout(new GridLayout(rows, columns, Gap, Gap));
        group.setLayout(new GridLayout(rows, columns, Gap, Gap));
        group.add(customerPanel);
        initRestLabel();
        add(restLabel);
        add(group);
        cg = new Restaurant4CookGui(cook);
        gui.animationPanel.addGui(cg);
        cook.setGui(cg);
        cook.addMarket(market1);
        cook.addMarket(market2);
        cook.addMarket(market3);
        
        market1.setCook(cook);
        market2.setCook(cook);
        market3.setCook(cook);
        
        
        market1.inventory.put("Chicken",5);
        market1.inventory.put("Steak", 5);
        market1.inventory.put("Salad", 5);
        market1.inventory.put("Pizza", 5);
        
        market2.inventory.put("Chicken", 5);
        market2.inventory.put("Steak", 5);
        market2.inventory.put("Salad", 5);
        market2.inventory.put("Pizza", 5);
        
        market3.inventory.put("Chicken", 1);
        market3.inventory.put("Steak", 1);
        market3.inventory.put("Salad", 1);
        market3.inventory.put("Pizza", 1);
      
        markets.add(market1);
        markets.add(market2);
        markets.add(market3);
        
        cook.startThread();
        market1.startThread();
        market2.startThread();
        market3.startThread();
        cashier.addMarket(market1);
        cashier.addMarket(market2);
        cashier.addMarket(market3);
        
        market1.setCashier(cashier);
        market2.setCashier(cashier);
        market3.setCashier(cashier);
    }

    *//**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     *//*
    public void pauseAgents(){
    	host.pause();
    	for(int i=0; i<waiters.size(); i++){
    		waiters.get(i).pause();
    	}
    	for(int i=0; i<markets.size(); i++){
    		markets.get(i).pause();
    	}
    	cook.pause();
    	for(int i=0; i<customers.size(); i++){
    		customers.get(i).pause();
    	}
    	paused = true;
    	
    }
    
    public void resetInventory(){
    	cook.resetInventory();
    }
    
    
    public void restartAgents(){
    	host.restart();
    	for(int i=0; i<waiters.size(); i++){
    		waiters.get(i).restart();
    	}
    	for(int i=0; i<markets.size(); i++){
    		markets.get(i).restart();
    	}
    	cook.restart();
    	for(int i=0; i<customers.size(); i++){
    		customers.get(i).restart();
    	}
    	paused = false;
    	
    }
    public Vector<CustomerAgent> getCustomers(){
    	return customers;
    }
    public Vector<WaiterAgent> getWaiters(){
    	return waiters;
    }
    
    private void initRestLabel() {
        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
    }

    *//**
     * When a customer or waiter is clicked, this function calls
     * updatedInfoPanel() from the main gui so that person's information
     * will be shown
     *
     * @param type indicates whether the person is a customer or waiter
     * @param name name of person
     *//*
 

    *//**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     *//*
    
    public void addWaiter(String name){
    	WaiterAgent w = new WaiterAgent(name);
    	waiters.add(w);
    	Restaurant4WaiterGui g = new Restaurant4WaiterGui(w, guiCounter);
    	guiCounter++;
    	host.setWaiter(w);
    	gui.animationPanel.addGui(g);
    	w.setGui(g);
    	w.startThread();
    	w.setCook(cook);
    	w.setHost(host);
    	w.state = WaiterState.available;
    	w.setCashier(cashier);
    }
    
    
    
    public void addPerson(String type, String name) {

    	if (type.equals("Customers")) {
    		CustomerAgent c = new CustomerAgent(name);	
    		Restaurant4CustomerGui g = new Restaurant4CustomerGui(c, gui);
    		g.setListPanel(customerPanel);
    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setGui(g);
    		customers.add(c);
    		c.setCashier(cashier);
    		c.startThread();
    		Random rand = new Random();
    		int i = 5;
    		int j = 17;
    		double value =  i + (j - i)*rand.nextDouble();	
    		value = Math.round(value * 100);
    		value = value/100;
    		if (name.equals("honest")){
    			c.setMoney(3.00);
    		}
    		else if(name.equals("cheapest")){
    			value  = 5.99 + (8.98 - 5.99)*rand.nextDouble();
    			value = Math.round(value*100);
    			value = value/100;
    			c.setMoney(value);
    		}
    		else{
    			c.setMoney(value);
    		}
    	}
    }

}
*/