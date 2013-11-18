package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.WaiterRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    //A Control menu: Currently only has pause and resume
    /*private JMenuBar menuBar;
    private JMenu controlMenu;
    private JMenuItem pauseItem;
    private JMenuItem resumeItem;
    private JMenuItem checkFood;
    
    /* infoPanel holds information about the clicked customer, if there is one*/
  /*  private JPanel infoPanel;
    private JLabel infoLabel; //part of infoPanel
    private JCheckBox stateCB;//part of infoLabel
    private JPanel picturePanel; //Lab 1 assn, add a new graphic to the window*/
    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        int WINDOWX = 950;
        int WINDOWY = 650;

    	setBounds(50, 50, WINDOWX, WINDOWY);
       	setLayout(new BorderLayout(20, 20));

	Dimension animationDim = new Dimension(WINDOWX, (int) (WINDOWY * .4));
	animationPanel.setPreferredSize(animationDim);
	animationPanel.setMinimumSize(animationDim);
	animationPanel.setMaximumSize(animationDim);
	add(animationPanel, BorderLayout.LINE_END);

	//Setting up menu items
	/*menuBar = new JMenuBar();
	controlMenu = new JMenu("Menu -- Pause, Resume, Check Food Count");
	//Pause menu button
	pauseItem = new JMenuItem("Pause");
	pauseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
	pauseItem.addActionListener(this);
	controlMenu.add(pauseItem);
	menuBar.add(controlMenu);
	//Resume menu button
	resumeItem = new JMenuItem("Resume");
	resumeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
	resumeItem.addActionListener(this);
	controlMenu.add(resumeItem);
	menuBar.add(controlMenu);
	//Check Food button
	checkFood = new JMenuItem("Check Food Count");
	checkFood.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
	checkFood.addActionListener(this);
	controlMenu.add(checkFood);
	menuBar.add(controlMenu);
	setJMenuBar(menuBar);

        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * .25));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
	
	//Setting up Hungry checkbox
	stateCB = new JCheckBox();
	stateCB.setVisible(false);
        stateCB.setEnabled(true);
        stateCB.addActionListener(this);

	infoPanel.setLayout(new BorderLayout(15,15));
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to create people</i></pre></html>");
        infoPanel.add(infoLabel,BorderLayout.NORTH);
        infoPanel.add(stateCB,BorderLayout.CENTER);
	restPanel.add(infoPanel, BorderLayout.EAST);

        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .35));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
	add(restPanel, BorderLayout.NORTH);

		//Setting up host's picture
		Dimension pictureDim = new Dimension(WINDOWX, (int) (WINDOWY * .2));
		picturePanel = new JPanel();
		picturePanel.setPreferredSize(pictureDim);
		picturePanel.setMinimumSize(pictureDim);
		picturePanel.setMaximumSize(pictureDim);
		picturePanel.setBorder(BorderFactory.createTitledBorder("Your Host!"));
		//picturePanel.setLayout(new BorderLayout(20, 20));
		ImageIcon image = new ImageIcon("/home/nikhil/csci201/restaurant_nbedi/src/restaurant/gui/resources/host.jpg");
		JLabel restaurantImage = new JLabel(image);
		picturePanel.add(restaurantImage);
		restPanel.add(picturePanel, BorderLayout.SOUTH);*/
    }


    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
  /*  public void updateInfoPanel(Object person) {
        currentPerson = person;
		stateCB.setVisible(true);
        if (person instanceof CustomerRole) {
            CustomerRole customer = (CustomerRole) person;
	    stateCB.setText("Hungry?");

          //Should checkmark be there? 
            //stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
	    stateCB.setEnabled(!customer.getGui().isHungry());
            infoLabel.setText(
               "<html><pre> Customer Name: " + customer.getName() + " </pre></html>");
        }

		if(person instanceof WaiterRole) {
		    WaiterRole waiter = (WaiterRole) person;
		    if(waiter.onBreak()) {
			stateCB.setText("Back to Work");
			stateCB.setSelected(false);
			stateCB.setEnabled(true);
		    }
		    else if(waiter.askingForBreak()) {
			stateCB.setSelected(false);
			stateCB.setEnabled(false);
		    }
		    else {
			stateCB.setText("On Break");
			stateCB.setEnabled(true);
		    }
	
		    infoLabel.setText(
	               "<html><pre> Waiter Name: " + waiter.getName() + " </pre></html>");
		    
		}

        infoPanel.validate();
    }*/
    
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) {
    /*    if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerRole) {
                CustomerRole c = (CustomerRole) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof WaiterRole) {
                WaiterRole w = (WaiterRole) currentPerson;
		if(stateCB.getText().equals("Back to Work")) {
		    w.goOffBreak();
		    stateCB.setEnabled(true);
		    stateCB.setText("On Break");
		    stateCB.setSelected(false);
		}
		else {
		    w.shouldTakeBreak();
		    stateCB.setEnabled(false);
		    updateInfoPanel(w);
		}
            }
        }
		if(e.getSource() == pauseItem) {
		    restPanel.pause();
		}
		if(e.getSource() == resumeItem) {
		    restPanel.resume();
		}
		if(e.getSource() == checkFood) {
		    restPanel.printFoodCount();
		}
	*/
    }

/*    public void breakTime(boolean onBreak) {
		stateCB.setEnabled(true);
		if(onBreak) {
		    stateCB.setText("Back to Work");
		    return;
		}
    }
    */
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
  /*  public void setCustomerEnabled(CustomerRole c) {
        if (currentPerson instanceof CustomerRole) {
        	CustomerRole cust = (CustomerRole) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }*/
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
