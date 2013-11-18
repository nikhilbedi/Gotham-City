package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.*;
import simcity.PersonAgent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel {

    //Host, cook, waiters and customers
    private HostRole host = new HostRole();
    private HostGui hostGui = new HostGui(host);

    //Implementing Cooks
    private CookRole cook = new CookRole();

    //implement cashier
    private CashierRole cashier = new CashierRole();
   

    //to tell where customers should be in line, use this vector
    private Vector<CustomerRole> customers = new Vector<CustomerRole>();
    private Vector<WaiterRole> waiters = new Vector<WaiterRole>();
    
    private WaiterRole waiter = new WaiterRole();

    private JPanel restLabel = new JPanel();
    private ListPanel waiterPanel = new ListPanel(this, "Waiters");
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private JPanel group = new JPanel();

    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
		host.setGui(hostGui);
		host.setPerson(new PersonAgent("Host"));
		
		
		//setting cook
		CookGui g = new CookGui(cook);
		gui.animationPanel.addGui(g);// dw
		cook.setPerson(new PersonAgent("Cook"));
		cook.setGui(g);
	
		//setting cashier
		//cashier.startThread();
		cashier.setPerson(new PersonAgent("Cashier"));
		 
		waiter.setPerson(new PersonAgent("Waiter"));
		
        gui.animationPanel.addGui(hostGui);

        setLayout(new GridLayout(1, 2, 20, 20));
        
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
		      "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.00</td></tr><tr><td>Salad</td><td>$5.00</td></tr><tr><td>Pizza</td><td>$10.00</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("        "), BorderLayout.EAST);
        restLabel.add(new JLabel("        "), BorderLayout.WEST);
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

	if (type.equals("Waiters")) {
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
     */
    public void addPerson(String type, String name, boolean checked) {

    	if (type.equals("Customers")) {
		    /*  if(name.equals(""))
			name = "c" + (customers.size()+1);*/
		    CustomerRole c = new CustomerRole();	
		    CustomerGui g = new CustomerGui(c, gui);
		    //if the hungry checkbox was previously checked, start the customer off hungry
		    if(checked)
			g.setHungry();
		    gui.animationPanel.addGui(g);// dw
		    c.setHost(host);
		    c.setGui(g);
		    c.setCashier(cashier);
		    customers.add(c);
    	}

	if(type.equals("Waiters")) {
	    /*if(name.equals(""))
	      name = "w" + (waiters.size()+1);*/
	    WaiterRole w = new WaiterRole();	
	    waiters.add(w);
	    WaiterGui g = new WaiterGui(w, waiters.size()); //last parameter is it's y-position
	    gui.animationPanel.addGui(g);// dw
	    w.setHost(host);
	    w.setCook(cook);
	    w.setGui(g);
	    w.setCashier(cashier);
	    
	    host.addWaiter(w);
	}
    }

    public void printFoodCount() {
    	cook.printFoodCount();
    }

    public void pause() {
		host.getPersonAgent().pause();
		cook.getPersonAgent().pause();
		cashier.getPersonAgent().pause();
		for(WaiterRole w : waiters) {
		    w.getPersonAgent().pause();
		}
		for(CustomerRole c : customers) {
		    c.getPersonAgent().pause();
		}
		System.out.println("Paused. Messages in motion will execute and then stop.");
    }

    public void resume() {
		host.getPersonAgent().resume();
		cook.getPersonAgent().resume();
		cashier.getPersonAgent().resume();
		for(WaiterRole w : waiters) {
		    w.getPersonAgent().resume();
		}
		for(CustomerRole c : customers) {
		    c.getPersonAgent().resume();
		}
		System.out.println("Resume.");
    }
}
