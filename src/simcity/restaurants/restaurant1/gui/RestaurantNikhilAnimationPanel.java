package simcity.restaurants.restaurant1.gui;


import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.CashierRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.HostRole;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.WaiterSharedData;
import simcity.PersonAgent;
import simcity.Robot;
import simcity.TheCity;
import Gui.MainScreen;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.util.List;


/**
 * Everyone should copy my version of the animation panel to see their own correctly work in our simcity
 * @author nikhil
 *
 */
public class RestaurantNikhilAnimationPanel extends Screen  {
	Restaurant r1;
	//TODO WE NEED TO CHECK THE TYPE OF EACH RESTAURANT TO MAKE SURE WE MAKE IT EQUAL TO THE CORRECT REST.
	List<Restaurant> restaurants = TheCity.getRestaurantList();
	PersonAgent waiterPerson;
	PersonAgent waiterSharedDataPerson;
	PersonAgent hostPerson;
	PersonAgent cookPerson;
	PersonAgent cashierPerson;
	//PersonAgent custPerson = new PersonAgent("customer");
	
	WaiterRole waiterRole;
	WaiterSharedData waiterSharedDataRole;
	HostRole hostRole;
	CookRole cookRole;
	CashierRole cashierRole;
	
	WaiterGui waiterGui;
	WaiterSharedDataGui waiterSharedDataGui;
	CookGui cookGui;
	
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
		xPos=850;
		yPos = 20;
		g2.drawString("KITCHEN", xPos, yPos);
		
		g2.setColor(Color.CYAN);
		xPos=830;
		yPos = 250;
		g2.drawString("Order Stand", xPos, yPos);

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
		waiterPerson = new Robot("waiter");
		waiterSharedDataPerson = new Robot("waiterShared");
		hostPerson = new Robot("host");
		cookPerson = new Robot("cook");
		cashierPerson = new Robot("cashier");
		//PersonAgent custPerson = new PersonAgent("customer");
		
		waiterRole = new WaiterRole(waiterPerson);
		waiterSharedDataRole = new WaiterSharedData(waiterSharedDataPerson);
		hostRole = new HostRole(hostPerson);
		cookRole = new CookRole(cookPerson);
		cashierRole = new CashierRole(cashierPerson);
		
		waiterGui = new WaiterGui(waiterRole,1);
		waiterSharedDataGui = new WaiterSharedDataGui(waiterSharedDataRole, 0);
		cookGui = new CookGui(cookRole);
		
		waiterRole.setGui(waiterGui);
		waiterSharedDataRole.setGui(waiterSharedDataGui);
		cookRole.setGui(cookGui);

		waiterPerson.addRole(waiterRole);
		waiterSharedDataPerson.addRole(waiterSharedDataRole);
		hostPerson.addRole(hostRole);
		cookPerson.addRole(cookRole);
		cashierPerson.addRole(cashierRole);
		
		addGui(waiterGui);
		addGui(waiterSharedDataGui);
		addGui(cookGui);

		waiterRole.setHost(hostRole);
		waiterRole.setCook(cookRole);
		waiterRole.setCashier(cashierRole);
		hostRole.addWaiter(waiterRole);
		
		waiterSharedDataRole.setHost(hostRole);
		waiterSharedDataRole.setCook(cookRole);
		waiterSharedDataRole.setCashier(cashierRole);
		hostRole.addWaiter(waiterSharedDataRole);
		
		r1.setCashier(cashierRole);
		r1.setHost(hostRole);
		
		
		//The host doesnt think it has set it's person? I'm manually setting them
		
		/*waiterRole.setPerson(waiterPerson);
		hostRole.setPerson(hostPerson);
		cookRole.setPerson(cookPerson);
		cashierRole.setPerson(cashierPerson);;
		*/
		hostPerson.startThread();
		cashierPerson.startThread();
		waiterPerson.startThread();
		waiterSharedDataPerson.startThread();
		cookPerson.startThread();
		
		

	}
}

