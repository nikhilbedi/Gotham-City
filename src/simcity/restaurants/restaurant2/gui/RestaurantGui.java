package simcity.restaurants.restaurant2.gui;

import simcity.restaurants.restaurant2.CustomerRole;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
	public static final int guiBoundsX = 50;
    public static final int guiBoundsY = 50;
    public static final int guiBufferY = 100;
    public static final double dimensionScale = .6;
    public static final double infoDimensionScale = .25;
    public static final double userDimensionScale = .4;
    
    public static final int animationXBuffer = 100;
    public static final int animationYBuffer = 50;
    public static final int animationWidthBuffer = 20;
    public static final int animationHeightBuffer = 20;
    
    public static final int infoX = 1;
    public static final int infoY = 2;
    public static final int infoWidth = 40;
    public static final int infoHeight = 0;
    
    public static final int imageX = 50;
    public static final int imageY = 50;
	
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
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    private JPanel infoPanel;
    private JPanel userPanel;
    private JLabel infoLabel; //part of infoPanel
    private JCheckBox stateCB;//part of infoLabel **************************
    private JLabel userLabel;
    private BufferedImage image;

    private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public RestaurantGui() {
        int WINDOWX = 550;
        int WINDOWY = 350;

        //animationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //animationFrame.setBounds(animationXBuffer+WINDOWX, animationYBuffer , 
        //		WINDOWX+animationWidthBuffer, WINDOWY+animationHeightBuffer);
        //animationFrame.setVisible(true);
    	//animationFrame.add(animationPanel); 
    	add(animationPanel);
    	
    	setBounds(guiBoundsX, guiBoundsY, WINDOWX, WINDOWY + guiBufferY + 400);

        setLayout(new BoxLayout((Container) getContentPane(), 
        		BoxLayout.Y_AXIS));

        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * dimensionScale));
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        add(restPanel);
        
        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * infoDimensionScale));
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        
        stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        infoPanel.setLayout(new GridLayout(infoX, infoY, infoWidth, infoHeight));
        
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB);
        add(infoPanel);
        
        //setup user panel
        Dimension infoDim2 = new Dimension(WINDOWX, (int) (WINDOWY * userDimensionScale));
        userPanel = new JPanel();
        userPanel.setPreferredSize(infoDim2);
        userPanel.setMinimumSize(infoDim2);
        userPanel.setMaximumSize(infoDim2);
        userPanel.setBorder(BorderFactory.createTitledBorder("User Info"));
        
        userLabel = new JLabel(); 
        userLabel.setText("Brice Roland");
        userPanel.add(userLabel);
        
        ImageIcon icon = new ImageIcon("ArtsyFace.jpg");
        Image image = icon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(imageX, imageY,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        icon = new ImageIcon(newimg);  // transform it back
        
        JLabel label = new JLabel(icon, JLabel.RIGHT); 
        userPanel.add(label); 
        
        System.out.println(System.getProperty("user.dir"));
        
        add(userPanel);
        //add(animationPanel);
        //add(animationFrame);
    }
    /**
     * updateInfoPanel() takes the given customer (or, for v3, Host) object and
     * changes the information panel to hold that person's info.
     *
     * @param person customer (or waiter) object
     */
    public void updateInfoPanel(Object person) {
        stateCB.setVisible(true);
        currentPerson = person;
        
        /*if (person instanceof CustomerRole) {
            CustomerRole customer = (CustomerRole) person;
            stateCB.setText("Hungry?");
          //Should checkmark be there? 
            stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            stateCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            infoLabel.setText(
               "<html><pre>     Name: " + customer.getName() + " </pre></html>");
        }*/
        infoPanel.validate();
    }
    
    /**
     * Activates the hungry check box if the user checked 
     * "Hungry?" before adding the agent
     */
    public void clickHungry() { stateCB.doClick();}
    
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerRole) {
                CustomerRole c = (CustomerRole) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
    }
    
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    public void setCustomerEnabled(CustomerRole c) {
        if (currentPerson instanceof CustomerRole) {
            CustomerRole cust = (CustomerRole) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }/*
    /**
     * Main routine to get gui started
     */
    /*public static void main(String[] args) {
        RestaurantGui gui = new RestaurantGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/
}
