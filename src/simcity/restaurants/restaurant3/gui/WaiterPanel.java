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

import simcity.restaurants.restaurant3.src.restaurant.WaiterRole;

/**
 * Subpanel of restaurantPanel.
 * This holds the scroll panes for the customers and, later, for waiters
 */
public class WaiterPanel extends JPanel implements ActionListener {

    public JScrollPane pane =
            new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JPanel view = new JPanel();
    private List<JButton> list = new ArrayList<JButton>();
    private JButton addWaiter = new JButton("Add");
    private JCheckBox stateWB;
    //private JCheckBox stateCC = new JCheckBox("Hungry?");

    private RestaurantPanel restPanel;
    private String type;
    private JTextField textName = new JTextField();
    private JPanel namePanel = new JPanel();
    
    private Object waiter;

    /**
     * Constructor for ListPanel.  Sets up all the gui
     *
     * @param rp   reference to the restaurant panel
     * @param type indicates if this is for customers or waiters
     */
    public WaiterPanel(RestaurantPanel rp, String type) {
        restPanel = rp;
        this.type = type;

        setLayout(new BoxLayout((Container) this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));

        addWaiter.addActionListener(this);
        add(addWaiter);
        
        //stateCC.addActionListener(this);
        //add(stateCC);
        
        stateWB = new JCheckBox("Break");
        stateWB.addActionListener(this);
        add(stateWB);
        
        int PANELWIDTH = 400;
        int PANELHEIGHT = 60;
        namePanel.add(new JLabel("Enter waiter name:"), BorderLayout.CENTER);
        namePanel.setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        namePanel.setMaximumSize(new Dimension(PANELWIDTH, PANELHEIGHT));
        namePanel.setMinimumSize(new Dimension(0, 0));
        
        textName.setPreferredSize(new Dimension(PANELWIDTH/2,PANELHEIGHT/2));
        textName.setMaximumSize(new Dimension(PANELWIDTH/2,PANELHEIGHT/2));
        textName.setMinimumSize(new Dimension(0,0));
        namePanel.add(textName);
        add(namePanel);
        
        //add(jtfName);

        view.setLayout(new BoxLayout((Container) view, BoxLayout.Y_AXIS));
        pane.setViewportView(view);
        add(pane);
    }

    /**
     * Method from the ActionListener interface.
     * Handles the event of the add button being pressed
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addWaiter) {
			addWaiter(textName.getText());
		}
	/*	else if (e.getSource() == stateWB) {
			// asking for break
			//if (stateWB.isSelected()) {
				System.out.println("Inside IF");
				if (waiter instanceof WaiterAgent) {
					WaiterAgent w = (WaiterAgent) waiter;
					stateWB.setEnabled(false);
					System.out.println("***** " + w.getName());
              //      w.getGui().setHungry();
                    
                }
			//}
		}
*/
		else {
			for (JButton temp : list) {
				if (e.getSource() == temp)
					//if (stateWB.isSelected()) {
						restPanel.showInfo(type, temp.getText());
				//}
			}
		}
	}
	
	
	public void updateWaiterPanel(WaiterRole waiter) {
		if (!waiter.isOnBreak && stateWB.isSelected()) {
			if (waiter.host.msgAskToGoOnBreak(waiter)) {
				waiter.isOnBreak = true;
				waiter.waiterGui.DoGoToBreakSpot();
				System.out.println("GO ON BREAK: " + waiter.getName());
			}
				
		}
		else if (waiter.isOnBreak && !stateWB.isSelected()) {
			// call back
			waiter.isOnBreak = false;
			System.out.println("BACK FROM BREAK: " + waiter.getName());
		}
	}
	
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
     * If the add button is pressed, this function creates
     * a spot for it in the scroll pane, and tells the restaurant panel
     * to add a new person.
     *
     * @param name name of new person
     */
    public void addWaiter(String name) {
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
            restPanel.addWaiter(name, stateWB.isSelected());//puts customer on list
            restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }
    
   
   }

