/*package simcity.Restaurant4.Restaurant4Gui;

import restaurant.CustomerAgent;
import restaurant.WaiterAgent;
import restaurant.WaiterAgent.WaiterState;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

*//**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 *//*
public class ListPanel extends JPanel implements ActionListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private JButton addPersonB = new JButton("Add");
    private List<JButton> list = new ArrayList<JButton>();
    private List<JCheckBox> checkBoxList  =new ArrayList<JCheckBox>();
    private RestaurantPanel restPanel;
    private String type;
    private TextField addName = new TextField(); 
    private String custName;
    private TextField addWaiter = new TextField();
    private JButton addWaiterB = new JButton("Add");
    private List<JButton> waiterButton = new ArrayList<JButton>();
    public JScrollPane pane1 =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view1 = new JPanel();
    private Dimension paneDim = new Dimension(150, 100);
    private Dimension buttonDim = new Dimension(70,30);
    private JPanel waiterPanel = new JPanel();
    private JPanel customerPanel = new JPanel();
    private static Vector<CustomerAgent> customers = new Vector<CustomerAgent>();
    private Vector<WaiterAgent> waiters = new Vector<WaiterAgent>();
    private int waiterCounter = 0;
    *//**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     *//*
    public ListPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;
        setLayout(new GridLayout(1,2));
       
        customerPanel.setLayout(new BoxLayout((Container) customerPanel, BoxLayout.Y_AXIS));
        customerPanel.add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));
        customerPanel.add(addPersonB);
        customerPanel.add(addName);
        customerPanel.add(pane);
      
        addPersonB.addActionListener(this);
        addWaiterB.addActionListener(this);
        
        pane.setViewportView(view);
        pane.setPreferredSize(paneDim);
        pane1.setPreferredSize(paneDim);
        pane1.setViewportView(view1);
        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        view1.setLayout(new BoxLayout((Container) view1, BoxLayout.Y_AXIS));
        
        waiterPanel.setLayout(new BoxLayout((Container) waiterPanel, BoxLayout.Y_AXIS));
        waiterPanel.add(new JLabel("<html><pre> <u>" + "Waiters" + "</u><br></pre></html>"));
        waiterPanel.add(addWaiterB);
        waiterPanel.add(addWaiter); 
        waiterPanel.add(pane1);
        
        
        
        add(customerPanel);
        add(waiterPanel);
        
        
    }
    
    public List<CustomerAgent> getCustomers(){
    	return customers;
    }

    *//**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     *//*
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addPersonB) {
			custName = addName.getText();
			addPerson(custName);
	        }
		
		if (e.getSource() == addWaiterB){
			if (waiterCounter<4){
			addWaiter(addWaiter.getText());
			}
		}
		
		for (int i=0; i<waiterButton.size(); i++){
			if (e.getSource() == waiterButton.get(i)){
				waiters = restPanel.getWaiters();
				if (waiters.get(i).state == WaiterState.available){
				waiters.get(i).wantToGoOnBreak();}
			}
		}
		
		for(int j=0; j<checkBoxList.size(); j++ ){
			if (e.getSource() == checkBoxList.get(j)) { 
				customers = restPanel.getCustomers();
						customers.get(j).getGui().setHungry();
						checkBoxList.get(j).setEnabled(false);
				}
		}       
		 
	}
    *//**
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     *//*
	public void addWaiter(String name){
		waiterCounter++;
		restPanel.addWaiter(name);
		JPanel panel = new JPanel();
		JButton button = new JButton(addWaiter.getText());
		button.setPreferredSize(buttonDim);
        button.setBackground(Color.white);
		panel.add(button);
		JButton buttonW = new JButton("Break");
		buttonW.setPreferredSize(buttonDim);
		panel.add(buttonW);
		waiterButton.add(buttonW);
		buttonW.addActionListener(this);
		view1.add(panel);
		validate();
	}
	
	public void updateCheckBox(CustomerAgent customer){
		
		for (int i=0; i<restPanel.getCustomers().size(); i++){
            if (restPanel.getCustomers().get(i) == customer){
            checkBoxList.get(i).setSelected(customer.getGui().isHungry());
            checkBoxList.get(i).setEnabled(!customer.getGui().isHungry());
            }
            }
		
	}
	
    public void addPerson(String name) {
        if (name != null) {
        	JPanel panel = new JPanel();
            JButton button = new JButton(name);
            button.setPreferredSize(buttonDim);
            button.setBackground(Color.white);
            JCheckBox checkbox = new JCheckBox("Hungry?");
            button.addActionListener(this);
            checkbox.addActionListener(this);
            list.add(button);
            checkBoxList.add(checkbox);
            panel.add(button);
            panel.add(checkbox);
            view.add(panel);
            restPanel.addPerson(type, name);//puts customer on list
            validate();
        }
    }
}
*/