package simcity.restaurants.restaurant5.gui;


import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;/**
 * Main GUI class.
 * Contains the main frame and subsequent panels
 */
public class RestaurantGui extends JFrame implements ActionListener {
	/* The GUI has two frames, the control frame (in variable gui) 
	 * and the animation frame, (in variable animationFrame within gui)
	 */
	//JFrame animationFrame = new JFrame("Restaurant Animation");
	RestaurantHunterAnimationPanel animationPanel = new RestaurantHunterAnimationPanel();

	/* restPanel holds 2 panels
	 * 1) the staff listing, menu, and lists of current customers all constructed
	 *    in RestaurantPanel()
	 * 2) the infoPanel about the clicked Customer (created just below)
	 */    
	private RestaurantPanel restPanel = new RestaurantPanel(this);

	/* infoPanel holds information about the clicked customer, if there is one*/
	private JPanel infoPanel;
	private JLabel infoLabel; //part of infoPanel
	private JLabel cashLabel;
	private JLabel waiterNameLabel;
	private JLabel numCustLabel;
	private JCheckBox stateCB;//part of infoLabel

	private JButton breakButton, offBreakButton;

	private Object currentPerson;/* Holds the agent that the info is about.
    								Seems like a hack */

	private JButton pause;
	private JButton resume;

	/**
	 * Constructor for RestaurantGui class.
	 * Sets up all the gui components.
	 */
	public RestaurantGui() {
		int WINDOWX = 800;
		int WINDOWY = 700;

		setBounds(50, 50, WINDOWX, WINDOWY);
		BoxLayout layout = new BoxLayout((Container) this.getContentPane(), BoxLayout.PAGE_AXIS);
		setLayout(layout);

		add(animationPanel);
		setBounds(50, 50, WINDOWX, WINDOWY);

		setLayout(new BoxLayout((Container) getContentPane(), 
				BoxLayout.Y_AXIS));

		Dimension restDim = new Dimension(WINDOWX, (int) (WINDOWY * .3));
		restPanel.setPreferredSize(restDim);
		restPanel.setMinimumSize(restDim);
		restPanel.setMaximumSize(restDim);
		add(restPanel);



		// Now, setup the info panel
		Dimension infoDim = new Dimension(WINDOWX, (int) (WINDOWY * .10));
		infoPanel = new JPanel();
		infoPanel.setPreferredSize(infoDim);
		infoPanel.setMinimumSize(infoDim);
		infoPanel.setMaximumSize(infoDim);
		infoPanel.setBorder(BorderFactory.createTitledBorder("Information"));

		stateCB = new JCheckBox();
		stateCB.setVisible(false);
		stateCB.addActionListener(this);

		breakButton = new JButton("Ask Break?");
		breakButton.setVisible(false);
		breakButton.addActionListener(this);

		offBreakButton = new JButton("Back to work");
		offBreakButton.setVisible(false);
		offBreakButton.addActionListener(this);


		//infoPanel.setLayout(new GridLayout(1, 4, 40, 0));
		infoPanel.setLayout(new FlowLayout());
		infoLabel = new JLabel();
		cashLabel = new JLabel();
		waiterNameLabel = new JLabel();
		numCustLabel = new JLabel();
		infoLabel.setText("<html><pre><i>Click Add to make customers</i></pre></html>");
		infoPanel.add(infoLabel);
		infoPanel.add(cashLabel);
		infoPanel.add(waiterNameLabel);
		infoPanel.add(numCustLabel);
		infoPanel.add(stateCB);
		infoPanel.add(breakButton);
		infoPanel.add(offBreakButton);
		add(infoPanel);

		JPanel pausePanel = new JPanel();
		Dimension pauseDim = new Dimension(WINDOWX, (int) (WINDOWY * .05));
		pausePanel.setPreferredSize(pauseDim);
		pausePanel.setMinimumSize(pauseDim);
		pausePanel.setMaximumSize(pauseDim);

		pause = new JButton("Pause");
		resume = new JButton("Resume");
		pause.addActionListener(this);
		resume.addActionListener(this);
		pausePanel.add(pause);
		pausePanel.add(resume);
		resume.setVisible(false);

		add(pausePanel);

		//custom addition
		/*java.net.URL pic = this.getClass().getResource("/profile_pic.jpeg");
        ImageIcon image = new ImageIcon(pic);
        JLabel label = new JLabel("", image, JLabel.CENTER);
        //JLabel label = new JLabel("Hello!");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        add(panel);*/


	}
	/**
	 * updateInfoPanel() takes the given customer (or, for v3, Host) object and
	 * changes the information panel to hold that person's info.
	 *
	 * @param person customer (or waiter) object
	 */
	public void updateInfoPanel(Object person) {
		currentPerson = person;

		if (person instanceof Restaurant5CustomerRole) {
			breakButton.setVisible(false);
			stateCB.setVisible(true);
			cashLabel.setVisible(true);
			waiterNameLabel.setVisible(true);
			numCustLabel.setVisible(false);
			Restaurant5CustomerRole customer = (Restaurant5CustomerRole) person;
			stateCB.setText("Hungry?");
			//Should checkmark be there? 
			stateCB.setSelected(customer.getGui().isHungry());
			//Is customer hungry? Hack. Should ask customerGui
			stateCB.setEnabled(!customer.getGui().isHungry());
			// Hack. Should ask customerGui
			infoLabel.setText(
					"<html><pre>     Name: " + customer.getName() + " </pre></html>");
			cashLabel.setText("<html><pre>     Cash: $" + customer.getMoney() + " </pre></html>");
			waiterNameLabel.setText("<html><pre>     Waiter:" + customer.getWaiterName() + " </pre></html>");

		}
		if(person instanceof WaiterRole){
			stateCB.setVisible(false);
			cashLabel.setVisible(false);
			waiterNameLabel.setVisible(false);
			numCustLabel.setVisible(true);
			WaiterRole waiter = (WaiterRole) person;
			breakButton.setVisible(waiter.isOffBreak());
			offBreakButton.setVisible(waiter.isOnBreak());
			infoLabel.setText(
					"<html><pre>     Name: " + waiter.getName() + " </pre></html>");
			numCustLabel.setText("<html><pre>     #Cust: " + waiter.getNumCustomers() + " </pre></html>");

		}
		infoPanel.validate();
	}
	/**
	 * Action listener method that reacts to the checkbox being clicked;
	 * If it's the customer's checkbox, it will make him hungry
	 * For v3, it will propose a break for the waiter.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == stateCB) {
			if (currentPerson instanceof Restaurant5CustomerRole) {
				Restaurant5CustomerRole c = (Restaurant5CustomerRole) currentPerson;
				c.getGui().setHungry();
				stateCB.setEnabled(false);
			}
		}
		if(e.getSource() == pause){
			System.out.println("Pause pressed");
			restPanel.pauseAgents();
			pause.setVisible(false);
			resume.setVisible(true);
		}
		if(e.getSource() == resume){
			System.out.println("Resume pressed");
			restPanel.pauseAgents();
			pause.setVisible(true);
			resume.setVisible(false);
		}
		if(e.getSource() == breakButton){
			if(currentPerson instanceof WaiterRole){
				WaiterRole w = (WaiterRole) currentPerson;
				w.askBreak();

			}
		}
		if(e.getSource() == offBreakButton){
			if(currentPerson instanceof WaiterRole){
				WaiterRole w = (WaiterRole) currentPerson;
				w.offBreak();
			}
		}
	}
	/**
	 * Message sent from a customer gui to enable that customer's
	 * "I'm hungry" checkbox.
	 *
	 * @param c reference to the customer
	 */
	public void setCustomerEnabled(Restaurant5CustomerRole c) {
		if (currentPerson instanceof Restaurant5CustomerRole) {
			Restaurant5CustomerRole cust = (Restaurant5CustomerRole) currentPerson;
			if (c.equals(cust)) {
				stateCB.setEnabled(true);
				stateCB.setSelected(false);
			}
		}
		if(currentPerson instanceof WaiterRole){
			//on break code
		}
	}
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
