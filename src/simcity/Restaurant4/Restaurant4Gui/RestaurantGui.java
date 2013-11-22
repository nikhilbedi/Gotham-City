/*package simcity.Restaurant4.Restaurant4Gui;

import restaurant.CustomerAgent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
*//**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 *//*
public class RestaurantGui extends JFrame implements ActionListener {
     The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     
	//JFrame animationFrame = new JFrame("Restaurant Animation");
	AnimationPanel animationPanel = new AnimationPanel();
	
     restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
         
    private RestaurantPanel restPanel = new RestaurantPanel(this);

     infoPanel holds information about the clicked customer, if there is one
    private JPanel infoPanel;
    private JPanel animation;
    private JLabel infoLabel; //part of infoPanel
    private JButton pause = new JButton("Pause/Resume");
    private final int bound = 50;
    private JButton reset = new JButton("Reset");
    *//**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     *//*
    public RestaurantGui() {
        int WINDOWX = 700;
        int WINDOWY = 700;
    	
    	setBounds(bound, bound, WINDOWX, WINDOWY);
    	
        setLayout( new BoxLayout((Container) getContentPane(), 
        		BoxLayout.Y_AXIS));

        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .3));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        add(restPanel);
        
        
        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * .1));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        add(infoPanel);
        
        
        pause.addActionListener(this);
        pause.setPreferredSize(new Dimension(130, 40));
        reset.addActionListener(this);
        reset.setPreferredSize(new Dimension(130, 40));
        
        Dimension animDim = new Dimension(450, 350);
        animation = new JPanel();
        animation.add(animationPanel);
        animationPanel.setBorder(BorderFactory.createTitledBorder("Restaurant animation"));
        animationPanel.setPreferredSize(animDim);
        add(animation);    
       
 
      //  infoPanel.setLayout(new GridLayout(1, 2));
        infoPanel.add(pause, BorderLayout.WEST);
        infoPanel.add(reset, BorderLayout.EAST);
    }
    *//**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     *//*

    
    
    
    
    *//**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     *//*
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==pause){
        	if (restPanel.paused == false ){
        		restPanel.pauseAgents();
        	}
        	else{
        		restPanel.restartAgents();
        	}
        }
       if (e.getSource()==reset){
    	   restPanel.resetInventory();
       }
        
    }
    *//**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     *//*
  
    *//**
     * Main routine to get gui started
     *//*
    public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
*/