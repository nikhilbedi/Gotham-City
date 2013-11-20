package simcity.Market.MarketGui;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketWorkerRole;
import Gui.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class MarketAnimationPanel extends Screen {
	static final int HEIGHT = 50;
	static final int WIDTH = 50;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private Image bufferImage;
    private Dimension bufferSize;
    private List<Gui> guis = new ArrayList<Gui>();

    public MarketAnimationPanel() {
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
        
        for(Gui gui : guis) {
            if (gui.isPresent()) {
            //    gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
              //  gui.draw(g2);
            }
        }
    }
    
    @Override
    public void paintObstacles(Graphics g){
    	g.setColor(Color.ORANGE);
    	g.fillRect(80, 150, 250, 30);
    	g.setColor(Color.BLACK);
    	g.fillRect(80, 30, 250, 10);
    }

    public void populate(){
    	PersonAgent agentCash = new PersonAgent("Cashier");
    	MarketCashierRole marketCashier = new MarketCashierRole(agentCash);
    	MarketCashierGui cashierGui = new MarketCashierGui(marketCashier);
    	PersonAgent agentWork = new PersonAgent("Worker");
    	MarketWorkerRole marketWorker = new MarketWorkerRole(agentWork);
    	MarketWorkerGui workerGui = new MarketWorkerGui(marketWorker);
    	PersonAgent agentCust = new PersonAgent("Customer");
    	MarketCustomerRole marketCustomer = new MarketCustomerRole(agentCust);
    	MarketCustomerGui customerGui = new MarketCustomerGui(marketCustomer);
    	PersonAgent agentCust2 = new PersonAgent("Customer2");
    	MarketCustomerRole marketCustomer2 = new MarketCustomerRole(agentCust2);
    	MarketCustomerGui customerGui2 = new MarketCustomerGui(marketCustomer2);
    	List<String> foods = new ArrayList<String>();
    	Market market = new Market();
    		marketCashier.setGui(cashierGui);
    	addGui(cashierGui);
    		agentCash.addRole(marketCashier);
    		marketWorker.setGui(workerGui);
    		addGui(workerGui);
    		agentWork.addRole(marketWorker);
    		marketCustomer.setGui(customerGui);
    		addGui(customerGui);
    		agentCust.addRole(marketCustomer);
    		marketCustomer2.setGui(customerGui2);
    	addGui(customerGui2);
    		agentCust2.addRole(marketCustomer2);
    		agentCash.startThread();
    		agentWork.startThread();
    		agentCust.startThread();
    		agentCust2.startThread();
    		marketCashier.setWorker(marketWorker);
    		marketCustomer.setCashier(marketCashier);
    		marketCustomer2.setCashier(marketCashier);
    		marketWorker.setCashier(marketCashier);
    	//	System.out.println(agentCust.roles.size());
    		foods.add("Chicken");
    		foods.add("Rice");
    		marketCustomer.getGroceries(foods);
    		marketCustomer2.getGroceries(foods);
    			Item beef = new Item("Beef", 10.99, 10);
    			Item chicken = new Item("Chicken", 8.99, 10);
    			Item rice = new Item("Rice", 6.99, 10);
    			Item potato = new Item("Potato", 5.99, 10);
    			marketCashier.getInventory().put("Beef", beef);
    			marketCashier.getInventory().put("Chicken", chicken);
    			marketCashier.getInventory().put("Rice", rice);
    			marketCashier.getInventory().put("Potato", potato);
    			marketWorker.getInventory().put("Beef", beef);
    			marketWorker.getInventory().put("Chicken", chicken);
    			marketWorker.getInventory().put("Rice", rice);
    			marketWorker.getInventory().put("Potato", potato);
    		market.setCashier(marketCashier);
    		market.addWorker(marketWorker);
    }


}
