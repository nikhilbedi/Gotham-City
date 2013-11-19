package simcity.restaurant_evan.src.restaurant.gui;

import simcity.restaurant_evan.src.restaurant.CashierRole;
import simcity.restaurant_evan.src.restaurant.CustomerRole;
import simcity.restaurant_evan.src.restaurant.HostRole;
import simcity.restaurant_evan.src.restaurant.MarketAgent;
import simcity.restaurant_evan.src.restaurant.WaiterRole;
import simcity.restaurant_evan.src.restaurant.CookRole;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Panel in frame that contains all the restaurant information,
 * including host, cook, waiters, and customers.
 */
public class RestaurantPanel extends JPanel implements ActionListener{

    //Host, cook, waiters and customers
    private HostRole host = new HostRole("Host");
    private HostGui hostGui = new HostGui(host);
   
   // private WaiterAgent waiter = new WaiterAgent("waiter");
    private CookRole cook = new CookRole("Cook");
    private CookGui cookGui = new CookGui(cook);
   
    //private WaiterGui waiterGui = new WaiterGui(waiter);
    private WaiterGui waiterGui;
    private WaiterRole waiter;
    private CashierRole cashier;
    private MarketAgent market;
    
    //create instance of waiter and cook
    //set waiter
    //set cook

    private List<CustomerRole> customers = Collections.synchronizedList(new Vector<CustomerRole>());
    private Vector<WaiterRole> waiters = new Vector<WaiterRole>();
    private List<MarketAgent> markets = Collections.synchronizedList(new Vector<MarketAgent>());
    
    private boolean paused = false;
    
    private JPanel restLabel = new JPanel();
    private JPanel waiterLabel = new JPanel();
    private ListPanel customerPanel = new ListPanel(this, "Customers");
    private WaiterPanel waiterPanel = new WaiterPanel(this, "Waiters");
    private JPanel group = new JPanel();
    private JCheckBox stateCC = new JCheckBox();
    private JButton pause = new JButton("Pause");
    public JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   
    
    

    private RestaurantGui gui; //reference to main gui

    public RestaurantPanel(RestaurantGui gui) {
        this.gui = gui;
        cashier = new CashierRole("Cashier");
       
        market = new MarketAgent("Market");
        market.startThread();
        //cook.setMarket(markets);
        //WaiterAgent waiter = new WaiterAgent("waiter");
        //WaiterGui waiterGui = new WaiterGui(waiter);
        //waiters.add(waiter);
        //waiter.setGui(waiterGui);
        //waiter.setHost(host);
        host.setWaiter(waiters);
        cook.setCashier(cashier);
        cashier.setCook(cook);
       //TO DO update host
        //cook.setWaiter(waiter);
        //waiter.setCook(cook);
        //waiter.startThread();
        cook.startThread();
        gui.animationPanel.addGui(cookGui);
        //System.out.println("cook Gui *****************");
        //gui.animationPanel.addGui(waiterGui);
        host.startThread();
        cashier.startThread();
        
      
        int srows = 1;
        int scols = 3;
        int shgap = 20;
        int svgap = 20;
        setLayout(new GridLayout(srows, scols, shgap, svgap));
       
        group.setLayout(new GridLayout(srows, scols, shgap-10, svgap-10));
        
        group.add(restLabel);
        group.add(waiterPanel);
        group.add(customerPanel);
        pause.addActionListener(this);
        
        //group.add(pause);
        initRestLabel();
        
        add(group);
    }
public void actionPerformed(ActionEvent e) {
    	
    	if (e.getSource() == pause) {	
    		if(!paused){
    			paused = true;
    			for(WaiterRole w: waiters) {
    				w.pause();
    			}
    			cook.pause();
    			host.pause();
    			for(CustomerRole mc: customers){
    				mc.pause();
    			}
    		}
    		else {
    			paused = false;
    			
    			for(WaiterRole w: waiters) {
    				w.restart();
    			}
    			cook.restart();
    			host.restart();
    			for(CustomerRole mc: customers){
    				mc.restart();
    			}
    			//cook.restart();
    			//host.restart();
    			//paused = false;
    		}
    	}
    }

    /**
     * Sets up the restaurant label that includes the menu,
     * and host and cook information
     */
    private void initRestLabel() {
        JLabel label = new JLabel();
        restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        //restLabel.setLayout(new BorderLayout());
        restLabel.add(pause);
        label.setText(
                "<html><h3><u>		Tonight's Staff</u></h3><table><tr><td>		host:</td><td>" + host.getName() + "</td></tr></table><h3><u>		Menu</u></h3><table><tr><td>		Steak</td><td>		$15.99</td></tr><tr><td>		Chicken</td><td>		$10.99</td></tr><tr><td>		Salad</td><td>		$5.99</td></tr><tr><td>		Pizza</td><td>		$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label);//, BorderLayout.CENTER
        restLabel.add(new JLabel("        "));//, BorderLayout.EAST
        //restLabel.add(new JLabel("          "), BorderLayout.WEST);
        
    }
    public void pause() {
    	JButton button = new JButton();
    	button.setBackground(Color.white);
    	
    	Dimension paneSize = pane.getSize();
    	Dimension buttonSize = new Dimension(10, (int) (5));
    	button.setPreferredSize(buttonSize);
    	button.addActionListener(this);
        //list.add(button);
        //view.add(button);
        //restPanel.addPerson(type, name, stateCC.isSelected());//puts customer on list
        //showInfo(name);//puts hungry button on panel
       // validate();
    }
/*
    private void initWaiterLabel() {
    	JLabel label = new JLabel();
    	waiterLabel.setLayout(new BorderLayout());
    }
    */
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
        if(type.equals("Waiters")) {
        	
        	for (int i = 0; i < waiters.size(); i++) {
        		WaiterRole temp = waiters.get(i);
        		if(temp.getName() == name)
        		//	gui.updateInfoPanel(temp);
        			waiterPanel.updateWaiterPanel(temp);
        	}
        }
    }
    
    

    /**
     * Adds a customer or waiter to the appropriate list
     *
     * @param type indicates whether the person is a customer or waiter (later)
     * @param name name of person
     */
    public void addPerson(String type, String name, boolean startHungry) {

    	if (type.equals("Customers")) {
    		CustomerRole c = new CustomerRole(name);	
    		CustomerGui g = new CustomerGui(c, gui);

    		gui.animationPanel.addGui(g);// dw
    		c.setHost(host);
    		c.setCashier(cashier);
    		c.setGui(g);
    		if (startHungry){
    			c.getGui().setHungry();
//    			c.gotHungry();
    		}
    		customers.add(c);
    		c.startThread();
    	}
    }
    public void addWaiter(String name, boolean startBreak) {

    	//if (type.equals("Waiters")) {
    		WaiterRole waiter = new WaiterRole(name);	
    		WaiterGui g = new WaiterGui(waiter, gui);
    		
    		//host.setWaiter(waiter);
    		gui.animationPanel.addGui(g);// dw
    		waiter.setHost(host);
    		waiter.setGui(g);
    		waiter.setCook(cook);
    		waiter.setCashier(cashier);
    		//if (startHungry){
    		//	c.getGui().setHungry();
//    			c.gotHungry();
    		//}
    		waiters.add(waiter);
    		waiter.startThread();
    		host.startThread();
    		cook.startThread();
    		cashier.startThread();
    	//}
    }
    
}
