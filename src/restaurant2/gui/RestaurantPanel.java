package restaurant2.gui;

import restaurant2.CashierRole;
import restaurant2.CookRole;
import restaurant2.CustomerRole;
import restaurant2.HostRole;
import restaurant2.Market;
import restaurant2.WaiterRole;
import restaurant2.interfaces.Cashier;
import restaurant2.interfaces.Cook;
import restaurant2.interfaces.Host;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel {
	
	private final int GRIDX = 1;
    private final int GRIDY = 2;
    private final int GRIDWIDTH = 20;
    private final int GRIDHEIGHT = 20;
    private final int MAXWAITERS = 3;
	
    //Host, cook, waiters and customers
    private HostRole host = new HostRole("Sarah");
    private CookRole cook = new CookRole("Ramsey");
    private CookGui cookGui;
    private Market market = new Market("Wal-Mart"), market2 = new Market("Costco"), market3 = new Market("Superior");
    private CashierRole cashier = new CashierRole("Cashier Bob");
    
    private Vector<CustomerRole> customers = new Vector<CustomerRole>();
    private Vector<WaiterRole> waiters = new Vector<WaiterRole>();
    
    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    //private ListPanel waiterPanel = new ListPanel(this, "Waiters");
    private JPanel group = new JPanel();

    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
        //host.setGui(hostGui);
		
        
        
        cook.addMarket(market);
        cook.addMarket(market2);
        cook.addMarket(market3);
        market.setCook(cook);
        market.setCashier(cashier);
        market2.setCook(cook);
        market2.setCashier(cashier);
        market3.setCook(cook);
        market3.setCashier(cashier);
        market.setInventory(0, 10, 0, 10);
        market2.setInventory(0,10, 0,10);
        market3.setInventory(0, 0, 0, 0);
        
        cookGui = new CookGui(cook, gui);
        gui.animationPanel.addGui(cookGui);
        cook.setGui(cookGui);
        
        host.startThread();
        cook.startThread(); 
        market.startThread();
        market2.startThread();
        market3.startThread();
        cashier.startThread();
        
        setLayout(new GridLayout(GRIDX, GRIDY, GRIDWIDTH, GRIDHEIGHT));
        group.setLayout(new BorderLayout(/*1, 2, 10, 10*/));
        
        initRestLabel();
        group.add(restLabel, BorderLayout.WEST);
        group.add(customerPanel, BorderLayout.CENTER);
        //group.add(waiterPanel, BorderLayout.EAST);
        add(group/*, BorderLayout.CENTER*/);
        
        
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);
    }

    /**
     * When a customer or waiter is clicked, this function calls
     * updatedInfoPanel() from the main gui so that person's information
     * will be shown
     *
     * @param type indicates whether the person is a customer or waiter
     * @param name name of person
     */
    public void showInfo(String type, String name) {

        if (type.equals("Customers")) {
        	
            for (int i = 0; i < customers.size(); i++) {
                CustomerRole temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
    }

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name) {

    	if (type.equals("Customers")) {
    		CustomerRole c = new CustomerRole(name);	
    		CustomerGui g = new CustomerGui(c, gui);
    		
    		switch(name) {
    			case "5": c.setCash(5); break;
    			case "10": c.setCash(10); break;
    			case "15": c.setCash(15); break;
    			case "flake": c.setCash(1);
    				c.payForMeal(false); break;
    		}
    		
    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setCashier(cashier);
    		c.setGui(g);
    		customers.add(c);
    		c.startThread();
    	}
    }
    
    public void addWaiter(String name) {
    	if(waiters.size() < MAXWAITERS) {
    		WaiterRole w = new WaiterRole(name);
    		WaiterGui g = new WaiterGui(w);
    		
    		w.setGui(g);
    		gui.animationPanel.addGui(w.getGui());
    		
    		w.setHost(host);
    		w.setCook(cook);
    		w.setCashier(cashier);
    		waiters.add(w);
    		
    		host.addWaiter(w);
    		cashier.addWaiter(w);
    		w.startThread();
    		
    		System.out.println("Added waiter " + name);
    	}
    	else {
    		System.out.println("Reached maximum number of waiters!");
    	}
    	validate();
    }
    
    public void pause() {
    	host.pause();
    	cook.pause();
    	cashier.pause();
    	market.pause();
    	market2.pause();
    	market3.pause();
    	for(CustomerRole c : customers) {
    		c.pause();
    	}
    	for(WaiterRole w : waiters) {
    		w.pause();
    	}
    }
    
    public void sendWaiterOnBreak() {
		if(waiters.size() >= 0)
			waiters.get(0).askForBreak();
	}
    
    /**
     * Determines if customer added was hungry at the time of adding
     * 
     * @param hungry determines if the person was hungry
     */
    public void hungryWasSelected(boolean hungry) {
    	if(hungry)
    		gui.clickHungry();
    }

	
     
    
}
