/*package simcity.Market.MarketGui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simcity.PersonAgent;
import simcity.Market.Item;
import simcity.Market.Market;
import simcity.Market.MarketCashierRole;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketWorkerRole;

public class MarketPanel extends JFrame{
	//MarketAnimationPanel animationPanel = new MarketAnimationPanel();
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
	private List<String> foods = new ArrayList<String>();
	Market market = new Market();
	public MarketPanel(){
		
		marketCashier.setGui(cashierGui);
	//	animationPanel.addGui(cashierGui);
		agentCash.addRole(marketCashier);
		marketWorker.setGui(workerGui);
		//animationPanel.addGui(workerGui);
		agentWork.addRole(marketWorker);
		marketCustomer.setGui(customerGui);
		//animationPanel.addGui(customerGui);
		agentCust.addRole(marketCustomer);
		marketCustomer2.setGui(customerGui2);
		//animationPanel.addGui(customerGui2);
		agentCust2.addRole(marketCustomer2);
		setSize(450, 350);
		setTitle("Market");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	//	add(animationPanel);
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
		marketCustomer.getGroceries();
		marketCustomer2.getGroceries();
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
*/