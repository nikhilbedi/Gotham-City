package Home;

import Home.ResidentRole;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class HomeGui extends JFrame implements ActionListener {
    /* The GUI has two frames, the control frame (in variable gui) 
     * and the animation frame, (in variable animationFrame within gui)
     */
	JFrame animationFrame = new JFrame("Restaurant Animation");
	//AnimationPanel animationPanel = new AnimationPanel();

    /* restPanel holds 2 panels
     * 1) the staff listing, menu, and lists of current customers all constructed
     *    in RestaurantPanel()
     * 2) the infoPanel about the clicked Customer (created just below)
     */    
    //private RestaurantPanel restPanel = new RestaurantPanel(this);
    
    /* infoPanel holds information about the clicked customer, if there is one*/
    private JPanel infoPanel;
    private JLabel infoLabel;
    private JPanel picPanel;
    private JLabel picLabel;//part of infoPanel
    
    private JCheckBox stateCB;//part of infoLabel
    private JCheckBox stateCC;
    private JCheckBox stateWB;
    int irows = 1;
    int icols = 2;
    int ihgap = 30;
    int ivgap = 0;
    private static int boundX = 50;
    private static int boundY = 50;
    private static int WINDOWX = 700;
    private static int WINDOWY = 900;
    private Object currentPerson;/* Holds the agent that the info is about.
    		
    							Seems like a hack */

    /**
     * Constructor for RestaurantGui class.
     * Sets up all the gui components.
     */
    public HomeGui() {
       animationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       animationFrame.setBounds(100+WINDOWX, 50 , WINDOWX+100, WINDOWY+100);
       animationFrame.setVisible(true);
    	setBounds(boundX, boundY, WINDOWX, WINDOWY);

        setLayout(new BoxLayout((Container) getContentPane(), BoxLayout.Y_AXIS));
        
       // setLayout(new GridBagLayout());
        //GridBagConstraints c = new GridBagConstraints();
        //if (shouldFill) {
        //	c.fill = GridBagConstraints.HORIZONTAL;
        //}
    	//setLayout(new BoxLayout());
    	
    	Dimension animDim = new Dimension(WINDOWX, (int) (WINDOWY * .45));
    	//c.fill = GridBagConstraints.VERTICAL;
    	//c.gridx = 0;
    	//c.gridy = 0;
    	//c.anchor = GridBagConstraints.FIRST_LINE_START;
    	//c.gridheight = 4;
    	//c.gridwidth = 4;
    	
    	animationPanel.setPreferredSize(animDim);
        animationPanel.setMinimumSize(animDim);
        animationPanel.setMaximumSize(animDim);
    	add(animationPanel);
    	
        Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .35));
        //c.fill = GridBagConstraints.VERTICAL;
    	//c.gridx = 4;
    	//c.gridy = 0;
    	//c.anchor = GridBagConstraints.FIRST_LINE_END;
        /*
        restPanel.setPreferredSize(restDim);
        restPanel.setMinimumSize(restDim);
        restPanel.setMaximumSize(restDim);
        add(restPanel);
        */
        // Now, setup the info panel
        Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * .10));
        //c.fill = GridBagConstraints.VERTICAL;
    	//c.gridx = 4;
    	//c.gridy = 2;
    	//c.anchor = GridBagConstraints.LINE_END;
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(infoDim);
        infoPanel.setMinimumSize(infoDim);
        infoPanel.setMaximumSize(infoDim);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        //infoPanel.setImageIcon(String 'filename', String 'Evan');

        stateCB = new JCheckBox();
        stateCB.setVisible(false);
        stateCB.addActionListener(this);
        
        stateCC = new JCheckBox();
        stateCC.setVisible(false);
        stateCC.addActionListener(this);
        
        stateWB = new JCheckBox();
        stateWB.setVisible(false);
        stateWB.addActionListener(this);
        
        //textName = new JTextField(1);
        

        infoPanel.setLayout(new GridLayout(irows, icols, ihgap, ivgap));
        
        infoLabel = new JLabel(); 
        infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        infoPanel.add(infoLabel);
        infoPanel.add(stateCB);
        //ListPanel.add(stateCC);
        //infoPanel.add(textName);
       // addPerson(infoLabel.showInputDialog("Please enter a name:"));
        add(infoPanel);
        
        Dimension picDim = new Dimension(WINDOWX, (int) (WINDOWY * .08));
        //c.fill = GridBagConstraints.VERTICAL;
    	//c.gridx = 4;
    	//c.gridy = 3;
    	//c.anchor = GridBagConstraints.LAST_LINE_START;
        picPanel = new JPanel();
        picPanel.setPreferredSize(infoDim);
        picPanel.setMinimumSize(infoDim);
        picPanel.setMaximumSize(infoDim);
        picPanel.setBorder(BorderFactory.createTitledBorder("Evan Coutre"));
        
        
        
        //picPanel.setLayout(new GridLayout(prows, pcols, phgap, pvgap));
        
        ImageIcon image = new ImageIcon("/Users/CoutreMacBookPro/restaurant_coutre/src/images.jpeg");
        		
        picLabel = new JLabel(); 
        picLabel.setIcon(image);
        //infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
        picPanel.add(picLabel);
        //infoPanel.add(stateCB);
        add(picPanel);
        
        //textName = new JTextField();
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

        if (person instanceof ResidentRole) {
            ResidentRole customer = (ResidentRole) person;
            stateCB.setText("Hungry?");
          //Should checkmark be there? 
            stateCB.setSelected(customer.getGui().isHungry());
          //Is customer hungry? Hack. Should ask customerGui
            stateCB.setEnabled(!customer.getGui().isHungry());
          // Hack. Should ask customerGui
            //textName.setText("Enter Name: ");
            //infoLabel.add(textName);
            //infoLabel.setText(
          //     "<html><pre>     Name: " + customer.getName() + " </pre></html>");
            //ImageIcon im1 = new ImageIcon("/Desktop/pictures/echeader1.jpg");
            //infoLabel.setIcon(im1.getImage());
            //infoLabel.setIcon(new ImageIcon("/Desktop/pictures/echeader1.jpg").getImage());
        }
     
        
        infoPanel.validate();
    }
    /**
     * Action listener method that reacts to the checkbox being clicked;
     * If it's the customer's checkbox, it will make him hungry
     * For v3, it will propose a break for the waiter.
     */
    /*
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stateCB) {
            if (currentPerson instanceof CustomerAgent) {
                CustomerAgent c = (CustomerAgent) currentPerson;
                c.getGui().setHungry();
                stateCB.setEnabled(false);
            }
        }
    }
    */
    /**
     * Message sent from a customer gui to enable that customer's
     * "I'm hungry" checkbox.
     *
     * @param c reference to the customer
     */
    /*
    public void setCustomerEnabled(CustomerAgent c) {
        if (currentPerson instanceof CustomerAgent) {
            CustomerAgent cust = (CustomerAgent) currentPerson;
            if (c.equals(cust)) {
                stateCB.setEnabled(true);
                stateCB.setSelected(false);
            }
        }
    }
    */
    /**
     * Main routine to get gui started
     */
    public static void main(String[] args) {
        HomeGui gui = new HomeGui();
        gui.setTitle("csci201 Restaurant");
        gui.setVisible(true);
        gui.setResizable(false);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}