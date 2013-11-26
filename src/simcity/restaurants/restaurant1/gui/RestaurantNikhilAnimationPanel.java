package simcity.restaurants.restaurant1.gui;

import simcity.bank.Bank;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.Restaurant1;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.PersonAgent;
import simcity.Robot;
import Gui.MainScreen;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;


public class RestaurantNikhilAnimationPanel extends Screen  {
	MainScreen mainScreen = ScreenFactory.getMainScreen();
	Restaurant r1;
	//TODO WE NEED TO CHECK THE TYPE OF EACH RESTAURANT TO MAKE SURE WE MAKE IT EQUAL TO THE CORRECT REST.
	List<Restaurant> restaurants = mainScreen.getRestaurantList();
	
	/*private final int WINDOWX = 350;
    private final int WINDOWY = 250;
    private Image bufferImage;
    private Dimension bufferSize; */
	private int xPos, yPos,width, height;

	//private List<Gui> guis = new ArrayList<Gui>();

	public RestaurantNikhilAnimationPanel() { 
		super();
	 	for(Restaurant r : restaurants) {
			if(r.getName().equals("Restaurant 1")){
				r1 = r;
			}
		}
		populate();
		//System.err.println(" "+temp);
		//Timer timer = new Timer(20, this );
		//timer.start();
	}

	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		Graphics2D g2 = (Graphics2D)g;
		//Second and third table created manually
		Graphics2D g3 = (Graphics2D)g;
		Graphics2D g4 = (Graphics2D)g;
		super.paintBackground(g);
		//Clear the screen by painting a rectangle the size of the frame
		/* g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight() );*/

		//Agent Positions
		g2.setColor(Color.MAGENTA);
		xPos=100;
		yPos = 20;
		g2.drawString("Waiter Home", xPos, yPos);

		g2.setColor(Color.GREEN);
		xPos=20;
		yPos = 20;
		g2.drawString("Waiting", xPos, yPos);

		g2.setColor(Color.RED);
		xPos=830;
		yPos = 20;
		g2.drawString("KITCHEN", xPos, yPos);

		//Here is the table
		g2.setColor(Color.BLUE); 
		xPos = 100;
		yPos = 150;
		width = 50;
		height = 50; 
		g2.fillRect(xPos, yPos, width, height);//200 and 250 need to be table params

		g2.setColor(Color.BLUE);
		xPos = 300;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);

		g2.setColor(Color.BLUE);
		xPos = 500;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);

		xPos = 700;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);
	}



	public void populate(){
		PersonAgent waiterPerson = new Robot("waiter");
		PersonAgent hostPerson = new Robot("host");
		PersonAgent cookPerson = new Robot("cook");
		PersonAgent cashierPerson = new Robot("cashier");
		//PersonAgent custPerson = new PersonAgent("customer");
		
		WaiterRole waiterRole = new WaiterRole(waiterPerson);
		HostRole hostRole = new HostRole(hostPerson);
		CookRole cookRole = new CookRole(cookPerson);
		CashierRole cashierRole = new CashierRole(cashierPerson);
		
		WaiterGui waiterGui = new WaiterGui(waiterRole,0);
		CookGui cookGui = new CookGui(cookRole);
		
		waiterRole.setGui(waiterGui);
		cookRole.setGui(cookGui);

		waiterPerson.addRole(waiterRole);
		hostPerson.addRole(hostRole);
		cookPerson.addRole(cookRole);
		cashierPerson.addRole(cashierRole);
		
		addGui(waiterGui);
		addGui(cookGui);

		waiterRole.setHost(hostRole);
		waiterRole.setCook(cookRole);
		waiterRole.setCashier(cashierRole);
		hostRole.addWaiter(waiterRole);
		
		r1.setCashier(cashierRole);
		r1.setHost(hostRole);
		
		hostPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		cookPerson.startThread();
		
		

	}
}

