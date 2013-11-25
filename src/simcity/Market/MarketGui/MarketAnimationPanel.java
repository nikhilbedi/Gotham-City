package simcity.Market.MarketGui;

import javax.imageio.ImageIO;
import javax.swing.*;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketWorkerRole;
import Gui.MainScreen;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import simcity.Robot;

public class MarketAnimationPanel extends Screen {
	static final int HEIGHT = 50;
	static final int WIDTH = 50;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private Image bufferImage;
    private Dimension bufferSize;
    private List<Gui> guis = new ArrayList<Gui>();
    public PersonAgent cashier = new Robot("Cashier");
    public MarketCashierRole marketCashier = new MarketCashierRole(cashier);
    public  MarketCashierGui cashierGui = new MarketCashierGui(marketCashier);
    public PersonAgent agentWork = new Robot("Worker");
    public MarketWorkerRole marketWorker = new MarketWorkerRole(agentWork);
    public MarketWorkerGui workerGui = new MarketWorkerGui(marketWorker);
    public PersonAgent customer = new PersonAgent("Customer");
    public MarketCustomerRole marketCustomer = new MarketCustomerRole(customer);
    public MarketCustomerGui customerGui = new MarketCustomerGui(marketCustomer);
    public  PersonAgent agentCust2 = new PersonAgent("Customer2");
    public MarketCustomerRole marketCustomer2 = new MarketCustomerRole(agentCust2);
    public MarketCustomerGui customerGui2 = new MarketCustomerGui(marketCustomer2);
    public PersonAgent agentCust3 = new PersonAgent("Customer3");
    public MarketCustomerRole marketCustomer3 = new MarketCustomerRole(agentCust3);
    public MarketCustomerGui customerGui3 = new MarketCustomerGui(marketCustomer3);
    public MainScreen mainScreen = ScreenFactory.getMainScreen();
    List <Market> m = mainScreen.getMarketList();
    public Market market = m.get(0);
  //  private BufferedImage image = ImageIO.read("shop.png"); 
    public MarketAnimationPanel() {
    	super();
    	populate();
    	/*setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(20, this );
    	timer.start();*/
    }


    public void paintComponent(Graphics g) {
       
    	Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
     /*   g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );*/

        //Here is the table
        /*g2.setColor(Color.ORANGE);
        g2.fillRect(80, 150, 250, 30);//200 and 250 need to be table params

        g2.setColor(Color.GREEN);
        g2.fillRect(80, 30, 250, 10);*/

       /* g2.setColor(Color.BLACK);
        g2.fillRect(30, 50, 110, 2);*/
        
       /* for(Gui gui : guis) {
            if (gui.isPresent()) {
            //    gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
              //  gui.draw(g2);
            }
        }*/
    }
    
    
    @Override
    public void paintBackground(Graphics g){
    	
    //	g.drawImage(image, 200, 300, null);
    	super.paintBackground(g);
    	g.setColor(Color.ORANGE);
    	g.fillRect(80, 300, 600, 50);
    	g.setColor(Color.ORANGE);
    	g.fillRect(80, 250, 50, 50);
    	g.setColor(Color.ORANGE);
    	g.fillRect(630, 250, 50, 50);
    	/*g.setColor(Color.BLACK);
    	g.fillRect(80, 30, 600, 10);*/
    	
    	 Graphics2D g1 = (Graphics2D) g;
         Image img2 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/Market/MarketGui/rsz_market.jpg");
         g1.drawImage(img2, 50, 0, null);
         g1.finalize();
    	
    	
    	
    	
    	 /*Graphics2D g2 = (Graphics2D) g;
         Image img1 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/Market/MarketGui/shop.png");
         g2.drawImage(img1, 300, 45, null);
         g2.finalize();
         
         Graphics2D g3 = (Graphics2D) g;
         Image img3 = Toolkit.getDefaultToolkit().getImage("C:/Users/Mika/github/team31/src/simcity/Market/MarketGui/shop.png");
         g3.drawImage(img3, 400, 45, null);
         g3.finalize();*/
    }

    public void populate(){
    
    	List<String> foods = new ArrayList<String>();
    		marketCashier.setGui(cashierGui);
    		addGui(cashierGui);
    		cashier.addRole(marketCashier);
    		marketWorker.setGui(workerGui);
    		addGui(workerGui);
    		agentWork.addRole(marketWorker);
    		marketCustomer.setGui(customerGui);
    	//	addGui(customerGui);
    		customer.addRole(marketCustomer);
    		marketCustomer2.setGui(customerGui2);
    	  //  addGui(customerGui2);
    		agentCust2.addRole(marketCustomer2);
    		agentCust3.addRole(marketCustomer3);
    		marketCustomer3.setGui(customerGui3);
    	//	addGui(customerGui3);
    		
    		cashier.startThread();
    		agentWork.startThread();
    		//customer.startThread();
    		//agentCust2.startThread();
    		//agentCust3.startThread();
    		
    		marketCashier.setWorker(marketWorker);
    		marketCustomer.setCashier(marketCashier);
    		marketCustomer2.setCashier(marketCashier);
    		marketCustomer3.setCashier(marketCashier);
    		marketWorker.setCashier(marketCashier);
    	//	System.out.println(agentCust.roles.size());
    		
    		market.setCashier(marketCashier);
    		market.setWorker(marketWorker);
    		
    			Item beef = new Item("Beef", 10.99, 100);
    			Item chicken = new Item("Chicken", 8.99, 100);
    			Item rice = new Item("Rice", 6.99, 100);
    			Item potato = new Item("Potato", 5.99, 100);
    			Item pizza = new Item("Pizza", 5.99, 100);
    			Item salad = new Item("Salad", 3.99, 100);
    			Item steak = new Item("Steak", 10.99, 100);
    			marketCashier.getInventory().put("Beef", beef);
    			marketCashier.getInventory().put("Chicken", chicken);
    			marketCashier.getInventory().put("Rice", rice);
    			marketCashier.getInventory().put("Potato", potato);
    			marketCashier.getInventory().put("Pizza", pizza);
    			marketCashier.getInventory().put("Salad", salad);
    			marketCashier.getInventory().put("Steak", steak);
    			marketWorker.getInventory().put("Beef", beef);
    			marketWorker.getInventory().put("Chicken", chicken);
    			marketWorker.getInventory().put("Rice", rice);
    			marketWorker.getInventory().put("Potato", potato);
    			marketWorker.getInventory().put("Pizza", pizza);
    			marketWorker.getInventory().put("Salad", salad);
    			marketWorker.getInventory().put("Steak", steak);
    			
    }


}

