
package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel {

    //Host, cook, waiters and customers
	private HostRole host = new HostRole("Host");
	private CookRole cook = new CookRole("Cook");

	private CashierRole cashier = new CashierRole("Cashier");
	
	private MarketRole market1 = new MarketRole("M1", cook, cashier);
	private MarketRole market2 = new MarketRole("M2", cook, cashier);
	private MarketRole market3 = new MarketRole("M3", cook, cashier);
	
	
	private HostGui hostGui = new HostGui(host);
	
	
	//mostly for debugging
	private CookGui cookGui = new CookGui(cook);
	private CashierGui cashGui = new CashierGui(cashier);
	private MarketGui m1g = new MarketGui(market1);
	private MarketGui m2g = new MarketGui(market2);
	private MarketGui m3g = new MarketGui(market3);
	
	private Vector<Restaurant5CustomerRole> customers = new Vector<Restaurant5CustomerRole>();
	private Vector<WaiterRole> waiters = new Vector<WaiterRole>();
	
    private JPanel restLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private ListPanel waiterPanel = new ListPanel(this, "Waiters");
    
    private JPanel group = new JPanel();

    private RestaurantGui gui; //reference to main gui

    private boolean agentsPaused = false;
    
    private int waiterCount = 0;
    
    private int unNamedWaiterCounter = 0;
    private int unNamedCustomerCounter = 0;
    
    
    //test variables
    /*private WaiterAgent w1 = new WaiterAgent("Waiter 1", host, cook);
	private WaiterAgent w2 = new WaiterAgent("Waiter 2", host, cook);
	
	private WaiterGui waiterGui1 = new WaiterGui(w1);
	private WaiterGui waiterGui2 = new WaiterGui(w2);*/
	
	
    
    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
        
        host.setGui(hostGui);
        cook.addMarket(market1);
        cook.addMarket(market3);
        cook.addMarket(market2);
        cook.setGui(cookGui);
        cashier.addMarket(market1);
        cashier.addMarket(market2);
        cashier.addMarket(market3);
        gui.animationPanel.addGui(cookGui);
        gui.animationPanel.addGui(cashGui);
        gui.animationPanel.addGui(m1g);
        gui.animationPanel.addGui(m2g);
        gui.animationPanel.addGui(m3g);
        //TODO add markets and cashier guis
        
        host.startThread();
        cook.startThread();
        market1.startThread();
        market2.startThread();
        market3.startThread();
        cashier.startThread();
        
        setLayout(new GridLayout(1, 2, 20, 20));
        group.setLayout(new GridLayout(1, 3, 10, 10));

        group.add(customerPanel);
        group.add(waiterPanel);

        initRestLabel();
        add(restLabel);
        add(group);
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
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
                Restaurant5CustomerRole temp = customers.get(i);
                if (temp.getName() == name)
                    gui.updateInfoPanel(temp);
            }
        }
        if(type.equals("Waiters")){
        	for (int i = 0; i < waiters.size(); i++) {
                WaiterRole temp = waiters.get(i);
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
     * @param if customer, default hungry or not?
     */
    public String addPerson(String type, String name, boolean hungry){
    	if (type.equals("Customers")) {
    		if(name.equals("")){
    			name = "Customer " + unNamedCustomerCounter;
    			unNamedCustomerCounter++;
    		}
    		Restaurant5CustomerRole c = new Restaurant5CustomerRole(name);	
    		Restaurant5CustomerGui g = new Restaurant5CustomerGui(c, gui);
    		
    		c.setCashier(cashier);
    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setGui(g);
    		customers.add(c);
    		c.startThread();
    		if(hungry){
    			c.becomesHungry();
    			g.setHungry();
    		}
    	}
    	if(type.equals("Waiters")){
    		{
    			if(name.equals("")){
        			//System.out.println("here");
    				name = "Waiter " + unNamedWaiterCounter;
        			unNamedWaiterCounter++;
        			//System.out.println(name);
        		}
    			WaiterRole w = new WaiterRole(name, host, cook, cashier);
        		WaiterGui g = new WaiterGui(w, gui);
        		g.setHome(10, 60 + 20*waiterCount);
        		waiterCount++;
        		if(waiterCount > 4){
        			waiterCount = 5;
        		}
        		gui.animationPanel.addGui(g);
        		w.setGui(g);
        		waiters.add(w);
        		w.startThread();  
        		host.addWaiter(w);
        	}
    	}
    	return name;
    }
    public void pauseAgents(){
    	if(!agentsPaused)
    	{
    		for(Restaurant5CustomerRole customer: customers){
    		customer.pause();
    		}
    		for(WaiterRole waiter: waiters){
    		waiter.pause();
    		}
	    	host.pause();
	    	cook.pause();
	    	market1.pause();
	    	market2.pause();
	    	market3.pause();
	    	cashier.pause();
	    	agentsPaused = true;
    	}
    	else{
    		for(Restaurant5CustomerRole customer: customers){
        	customer.restart();
        		}
    		for(WaiterRole waiter: waiters){
        		waiter.restart();
        		}
    		host.restart();
    	    cook.restart();
    	    market1.restart();
    	    market2.restart();
    	    market3.restart();
    	    cashier.restart();
    	    agentsPaused = false;
        	
    	}
    	
    }

}
