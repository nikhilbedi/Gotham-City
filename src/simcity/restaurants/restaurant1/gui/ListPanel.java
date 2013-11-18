package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.HostRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class ListPanel extends JPanel implements ActionListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private List<JButton> list = new ArrayList<JButton>();
    private JButton addPersonB = new JButton("Add");

    private RestaurantPanel restPanel;
    private String type;

    private JTextField nameField = new JTextField("Name", 50);
    private JCheckBox isHungry = new JCheckBox("Hungry?", true);
    // private JCheckBox onBreak = new JCheckBox("On Break", false);

    

    /**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     */
    public ListPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;
	
	//Creating Dimensions
	Dimension testDim = new Dimension(30, 20);

	// setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
	//	setLayout(new GridLayout(3, 2));
	setLayout(new BorderLayout(10, 10));
	add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

	JPanel addPeople = new JPanel();
	addPeople.setLayout(new BorderLayout(5,5));
	//adding textfield for lab2
	//nameField.setPreferredSize(testDim);
	//nameField.setMinimumSize(testDim);
	nameField.setMaximumSize(testDim);
	nameField.addActionListener(this);
	nameField.requestFocus();
	nameField.selectAll();
	addPeople.add(nameField, BorderLayout.NORTH);

	if(type == "Customers") {
	    isHungry.addActionListener(this);
	    isHungry.setVisible(true);
	    addPeople.add(isHungry, BorderLayout.EAST);
	}

	/*	if(type == "Waiters") {
	    onBreak.addActionListener(this);
	    onBreak.setVisible(true);
	    addPeople.add(onBreak, BorderLayout.EAST);
	    }*/

        addPersonB.addActionListener(this);
        addPeople.add(addPersonB, BorderLayout.WEST);

	add(addPeople, BorderLayout.NORTH);

        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    //Changing this so no popup dialog is created
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPersonB || e.getSource() == nameField) {
	    String temp = nameField.getText();
	    addPerson(temp);
        }
        else {
	    for (JButton temp:list){
                if (e.getSource() == temp)
                    restPanel.showInfo(type, temp.getText());
            }
        }
	//Make the checkboxes work
	/*	if(e.getSource() == onBreak) {
	    //Send waiter message that he wants to go on break

	    addPerson("ItWorked!");
	    }*/
    }

    /**
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     */
    public void addPerson(String name) {
        if (name != null) {
            JButton button = new JButton(name);
	    /* //Checkbox to make the customer hungry
	    JCheckBox checkbox = new JCheckBox("Hungry");
	    // list.add(checkbox);
	    view.add(checkbox);*/
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - 20,
                    (int) (paneSize.height / 7));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            view.add(button);

	    //If the customer is hungry from the start, let the RestaurantPanel know
	    if(isHungry.isSelected()) {
		restPanel.addPerson(type, name, true);
	    }
	    else {
		restPanel.addPerson(type, name, false);
	    }
	    restPanel.showInfo(type, name); //puts hungry button on panel
            validate();
        }
    }
}
