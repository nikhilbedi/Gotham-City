package simcity.restaurants.restaurant5.gui;


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
    private JPanel form = new JPanel();
    
    private List<JButton> list = new ArrayList<JButton>();
    
    private JTextField personField = new JTextField(15);
    private JCheckBox hungryBox = new JCheckBox();
    private JLabel hungryLabel = new JLabel("Hungry?");
    private JButton addPersonB = new JButton("Add");

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
        add(new JLabel("<html><pre> <u>" + type + "</u><br></pre></html>"));
        if(type == "Customers"){
        form.setLayout(new GridLayout(0,3));
        }
        else{
        form.setLayout(new GridLayout(0,2));
        }
        addPersonB.addActionListener(this);
    	form.add(addPersonB);
        if(type == "Customers"){
        	JPanel hungryCheck = new JPanel();
        	hungryCheck.add(hungryBox);
        	hungryCheck.add(hungryLabel);
        	form.add(hungryCheck);
        }
        form.add(personField);
        add(form);
        
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
        	addPerson(personField.getText(), hungryBox.isSelected());
        	
        	
        	
        	/*// Chapter 2.19 describes showInputDialog()
        	JCheckBox box = new JCheckBox("Hungry?");
        	String question = "Please enter a name:";
        	//Object[] dialogObjects = {box, question};
            addPerson(JOptionPane.showInputDialog(question));*/
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
    public void addPerson(String name, boolean hungry) {
        if (name != null) {
        	String temp;
            name = restPanel.addPerson(type, name, hungry);//puts customer on list
            
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
            restPanel.showInfo(type, name);//puts hungry button on panel
            validate();
        }
    }
}
