package simcity.restaurants.restaurant2.gui;

import simcity.restaurants.restaurant2.Restaurant2CustomerRole;
import simcity.restaurants.restaurant2.HostRole;

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
	
	private final int BUTTONWIDTHBUFFER = 20;
    private final int BUTTONHEIGHTBUFFER = 7;
	
    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public JScrollPane waiterPane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private JPanel waiterView = new JPanel();
    private List<JButton> list = new ArrayList<JButton>();
    private JButton addPersonB = new JButton("Add");
    private JButton addWaiter = new JButton("Add Waiter");
    private JButton pauseButton = new JButton("Pause");
    private JButton breakButton = new JButton("Send waiter on Break");
    
    private JPanel bGroup = new JPanel(); //Add new person field with checkbox for Hungry
    private JPanel buttonGroup = new JPanel();
    private JPanel buttonGroup2 = new JPanel();
    private JPanel paneGroup = new JPanel();
    private JTextField personBName = new JTextField("Enter Name", 10);
    private JCheckBox hungryCB = new JCheckBox();

    private RestaurantPanel restPanel;
    private String type;

    /**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     */
    public ListPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;

        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><pre> <u>" + type + "/Waiters" + "</u><br></pre></html>"));
        
        bGroup.setMaximumSize(new Dimension(200,30));
        //bGroup.setPreferredSize(new Dimension(10,30));
        bGroup.setLayout(new BorderLayout());
        
        personBName.setMaximumSize(new Dimension(200,30));
        //personBName.setPreferredSize(new Dimension(10,30));
        bGroup.add(personBName, BorderLayout.WEST);
        
        hungryCB.setEnabled(true);
        hungryCB.setText("Hungry?");
        //hungryCB.addActionListener(restPanel.gui);
        bGroup.add(hungryCB, BorderLayout.EAST);
        
        add(bGroup);
        
        buttonGroup.setLayout(new BorderLayout());
        
        addPersonB.addActionListener(this);
        buttonGroup.add(addPersonB, BorderLayout.CENTER);
        
        addWaiter.addActionListener(this);
        buttonGroup.add(addWaiter, BorderLayout.EAST);
        
        buttonGroup.setMaximumSize(new Dimension(200,30));
        
        add(buttonGroup);
        
        buttonGroup2.setLayout(new BorderLayout());
        buttonGroup2.setMaximumSize(new Dimension(220,30));
        
        pauseButton.addActionListener(this);
        pauseButton.setMaximumSize(new Dimension(100, 30));
        buttonGroup2.add(pauseButton, BorderLayout.WEST);
        
        breakButton.addActionListener(this);
        breakButton.setMaximumSize(new Dimension(200, 30));
        buttonGroup2.add(breakButton, BorderLayout.EAST);
        
        add(buttonGroup2);
        
        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        waiterView.setLayout(new BoxLayout((Container) waiterView, BoxLayout.Y_AXIS));
        paneGroup.setLayout(new BorderLayout());
        
       // view.setMinimumSize(new Dimension(500,100));
        pane.setViewportView(view);
        //pane.setMinimumSize(new Dimension(500,100));
        paneGroup.add(pane, BorderLayout.WEST);
        
        //waiterView.setMinimumSize(new Dimension(50,100));
        waiterPane.setViewportView(waiterView);
        paneGroup.add(waiterPane, BorderLayout.EAST);
        
        //paneGroup.setMaximumSize(new Dimension(200,200));
        
        add(pane);
        add(waiterPane);
        
        //add(paneGroup);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPersonB) {
        	// Chapter 2.19 describes showInputDialog()
            //addPerson(JOptionPane.showInputDialog("Please enter a name:"));
            addPerson(personBName.getText());
        }
        else if(e.getSource() == addWaiter) {
        	restPanel.addWaiter(personBName.getText());
        	addWaiter(personBName.getText());
        	System.out.println("Add Waiter Button Pressed.");
        }
        else if(e.getSource() == pauseButton) {
        	restPanel.pause();
        	System.out.println("Pause Pressed.");
        }
        else if(e.getSource() == breakButton) {
        	restPanel.sendWaiterOnBreak();
        	System.out.println("Break Button Pressed.");
        }
        else {
        	// Isn't the second for loop more beautiful?
            /*for (int i = 0; i < list.size(); i++) {
                JButton temp = list.get(i);*/
        	for (JButton temp:list){
                if (e.getSource() == temp)
                    restPanel.showInfo(type, temp.getText());
            }
        }
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
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - BUTTONWIDTHBUFFER,
                    (int) (paneSize.height / BUTTONHEIGHTBUFFER));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            view.add(button);
            restPanel.addPerson(type, name);//puts customer on list
            restPanel.showInfo(type, name);//puts hungry button on panel
            restPanel.hungryWasSelected(hungryCB.isSelected());
            validate();
        }
    }
    
    public void addWaiter(String name) {
        if (name != null) {
            JButton button = new JButton(name);
            button.setBackground(Color.white);

            Dimension paneSize = pane.getSize();
            Dimension buttonSize = new Dimension(paneSize.width - BUTTONWIDTHBUFFER,
                    (int) (paneSize.height / BUTTONHEIGHTBUFFER));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            waiterView.add(button);
            restPanel.addPerson(type, name);//puts customer on list
            restPanel.showInfo(type, name);//puts hungry button on panel
            //restPanel.hungryWasSelected(hungryCB.isSelected());
            validate();
        }
    }
}
