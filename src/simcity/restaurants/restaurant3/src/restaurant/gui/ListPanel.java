package simcity.restaurants.restaurant3.src.restaurant.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import simcity.restaurants.restaurant3.src.restaurant.CustomerRole;

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
   // private JButton addWaiterB = new JButton ("Add");
    
    private JCheckBox stateCC = new JCheckBox("Hungry?");
    //private JCheckBox jchkHungry = new JCheckBox("Hungry?");

    private RestaurantPanel restPanel;
    private String type;
    private JTextField textName = new JTextField();
    private JPanel namePanel = new JPanel();
    
    private Object currentPerson2;
    
    private static int PANELWIDTH = 400;
    private static int PANELHEIGHT = 60;

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
        add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

        addPersonB.addActionListener(this);
        add(addPersonB);
        
       // addWaiterB.addActionListener(this);
        //add(addWaiterB);
        
        stateCC.addActionListener(this);
        add(stateCC);
        
        namePanel.add(new JLabel("Enter customer name:"), BorderLayout.WEST);
        namePanel.setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        namePanel.setMaximumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        namePanel.setMinimumSize(new Dimension(0, 0));
        
        textName.setPreferredSize(new Dimension(PANELWIDTH/2,PANELHEIGHT/2));
        textName.setMaximumSize(new Dimension(PANELWIDTH/2,PANELHEIGHT/2));
        textName.setMinimumSize(new Dimension(0,0));
        namePanel.add(textName);
        add(namePanel);

        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == addPersonB) {
       
        	addPerson(textName.getText());
        	}
    	//else if(e.getSource() == addWaiterB) {
    		//addWaiter(textName.getText());
    //	}
        
       else if (e.getSource() == stateCC) {
        	if (currentPerson2 instanceof CustomerRole) {
                     CustomerRole c = (CustomerRole) currentPerson2;
                     c.getGui().setHungry();
                     stateCC.setEnabled(false);
                 }
             }
            
        	
        else {
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
            Dimension buttonSize = new Dimension(paneSize.width - 20,
                    (int) (paneSize.height / 7));
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.addActionListener(this);
            list.add(button);
            view.add(button);
            restPanel.addPerson(type, name, stateCC.isSelected());//puts customer on list
            restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }
}

