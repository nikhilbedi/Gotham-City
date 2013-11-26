package simcity.restaurants.restaurant4.Restaurant4Gui;

import simcity.restaurants.restaurant4.Restaurant4HostRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Host;

import javax.swing.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;


public class RestaurantPanel extends JPanel {

    //Host, cook, waiters and customers
   
    private JPanel restLabel = new JPanel();
   // private ListPanel customerPanel = new ListPanel(this, "Customers");
    private JPanel group = new JPanel();
  //  private RestaurantGui gui; //reference to main gui
    public boolean paused = false;
    private final int rows = 1;
    private final int columns = 2;
    private final int Gap = 10;
    private Restaurant4AnimationPanel animationPanel = new Restaurant4AnimationPanel();
    private int guiCounter = 0;
    
    public RestaurantPanel() {
    	
    	//add(animationPanel);
   //     this.gui = gui;
       
      /*  setLayout(new GridLayout(rows, columns, Gap, Gap));
        group.setLayout(new GridLayout(rows, columns, Gap, Gap));
        group.add(customerPanel);
        initRestLabel();
        add(restLabel);
        add(group);*/

    }

  
    public void pauseAgents(){
    	/*host.pause();
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
    	paused = true;*/
    	
    }
    
    public void resetInventory(){
    	//cook.resetInventory();
    }
    
    
    public void restartAgents(){
    	/*host.restart();
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
    	paused = false;*/
    	
    }
  /*  public Vector<CustomerAgent> getCustomers(){
    	return customers;
    }
    public Vector<WaiterAgent> getWaiters(){
    	return waiters;
    }
    */
    private void initRestLabel() {
/*        JLabel label = new JLabel();
        //restLabel.setLayout(new BoxLayout((Container)restLabel, BoxLayout.Y_AXIS));
        restLabel.setLayout(new BorderLayout());
        label.setText(
                "<html><h3><u>Tonight's Staff</u></h3><table><tr><td>host:</td><td>" + host.getName() + "</td></tr></table><h3><u> Menu</u></h3><table><tr><td>Steak</td><td>$15.99</td></tr><tr><td>Chicken</td><td>$10.99</td></tr><tr><td>Salad</td><td>$5.99</td></tr><tr><td>Pizza</td><td>$8.99</td></tr></table><br></html>");

        restLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        restLabel.add(label, BorderLayout.CENTER);
        restLabel.add(new JLabel("               "), BorderLayout.EAST);
        restLabel.add(new JLabel("               "), BorderLayout.WEST);*/
    }


 

  
    
    public void addWaiter(String name){
    /*	WaiterAgent w = new WaiterAgent(name);
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
    	w.setCashier(cashier);*/
    }
    
    
    
    public void addPerson(String type, String name) {

    /*	if (type.equals("Customers")) {
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
*/
    }
}
