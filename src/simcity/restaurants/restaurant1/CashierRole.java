package simcity.restaurants.restaurant1;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.tests.mock.*;
import Gui.RoleGui;
import agent.Agent;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.CashierGui;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant1.interfaces.*;
import simcity.restaurants.restaurant4.Restaurant4CashierRole.Payment;
import agent.Role;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Restaurant1 Cashier Role
 */
public class CashierRole extends Role implements Cashier {

    //Notice that we implement MyCustomers using ArrayList, but type it
    //with List semantics.
    public List<Check> checks = Collections.synchronizedList(new ArrayList<Check>());
    public CashierGui gui = null;
    public EventLog log = new EventLog();
    public List<DeliveryBill> billsToPay = Collections.synchronizedList(new ArrayList<DeliveryBill>());
    private double registerAmt = 1000.00;
    private String name;
    private Menu myMenu;
    //creating states outside the Order class
    public enum CheckState {pending, generating, done, beingPaid, paid};

    public CashierRole(PersonAgent p) {
		super(p);
		this.name = name;
		myMenu = new Menu();
    }
   

    public CashierRole() {
		// TODO Auto-generated constructor stub
    	myMenu = new Menu();
	}


	public String getName() {
	return name;
    }

    public List getCurrentChecks() {
	return checks;
    }

    // Messages

    @Override
    public void makeCheck(Waiter w, Customer c, String choice) {
    	print("Received message to make bill for Waiter");
    	log.add(new LoggedEvent("Creating bill for Waiter"));
    	checks.add(new Check(w, c, choice));
    	stateChanged();
    }
    
    //cook receives an order from the waiter and stores it in a list
    /*public void makeCheck(WaiterAgent waiter, CustomerAgent customer, String choice) {
		for(Check ch : checks) {
		    if(ch.customer = customer) {
			print("Creating bill for Waiter " + waiter.getName());
			//need to add the amount of the choice to the old check
			break;
		    }
	    }
		print("Creating bill for Waiter " + waiter.getName());
		checks.add(new Check(waiter, customer, choice));
		stateChanged();
    }*/

    public void hereIsPayment(Check ch, double cash) {
		print("Received payment from " + ch.customer);
		ch.cashPaid = cash;
		ch.state = CheckState.beingPaid;
		stateChanged();
    }
    
    public void payMarketBill(Market market, double amount) {
    	print("Received bill to pay market: " + amount);
    	log.add(new LoggedEvent("Received bill to pay market: " + amount));
    	billsToPay.add(new DeliveryBill(market, amount));
    }
    
    //TODO
 /*   public void amountDue(double a, MarketCashierRole m) {
     	print("Received bill to pay market: " + a);
    	log.add(new LoggedEvent("Received bill to pay market: " + a));
    	billsToPay.add(new DeliveryBill(m, a));
    	//stateChanged();
    }
    */
    //TODO
/*    public void HereIsYourChange(double d, marketCashierRole m) {
    	registerAmt += d;
    	//
    }*/


    /**
     * Scheduler.  Determine what action is called for, and do it.
     */
    public void amountDue(double a, MarketCashier c){  // from market Cashier 
		print("Received bill to pay market: " + a);
		registerAmt -= a;
		Restaurant r1 = (Restaurant) TheCity.getBuildingFromString("Restaurant 1");
		c.hereIsMoneyRestaurant(r1, a);
		stateChanged();
	}
	
	public void HereIsYourChange(double d, MarketCashier c ){
		registerAmt =  registerAmt+ d;
		myPerson.Do("Got change from market cashier " + d);
	
	}
    
    
    public boolean pickAndExecuteAnAction() {
    	if(theManLeavingMe != null && checks.isEmpty()) {
    		leaveWork();
    		return true;
    	}
    	
	    synchronized(billsToPay){
	    	if(!billsToPay.isEmpty()) {
	    		payBill();
	    		return true;
	    	}
	    }
	    synchronized(checks) {
			for (Check ch : checks) {
			     if(ch.state == CheckState.generating){
				 createCheck(ch);
				 return true;
			     }
			     if(ch.state == CheckState.beingPaid) {
				 giveChange(ch);
				 return true;
			     }
			}
	    }
		    
		return false;
		//we have tried all our rules and found
		//nothing to do. So return false to main loop of abstract agent
		//and wait.
    }

    // Actions
	
    private void createCheck(Check ch) {
    	print("creating check for waiter.");
		//Match menu's choice to check's choice
		ch.amount = myMenu.choices.get(ch.type);
		//Next, check if there isn't already another check with the same customer
		synchronized(checks) {
			for(Check ch2 : checks) {
			    if(ch2.customer == ch.customer && ch2 != ch) {
					ch.amount += ch2.amount;
					//concurrent error here. Removing while iterating through the list.
					checks.remove(ch2);
			    }
			}
		}
		ch.waiter.hereIsCheck(ch);
		ch.state = CheckState.done;
    }

    private void giveChange(Check ch) {
		ch.cashPaid -= ch.amount;
		print("change is " + ch.cashPaid);
		ch.customer.hereIsChange(ch.cashPaid);
		ch.state = CheckState.paid;
		if(ch.cashPaid >= 0) {
		    ch.amount = 0;
		    checks.remove(ch);
		}
		else {
		    ch.amount = -1.0 * ch.cashPaid;
		}
    }
    
    private void payBill() {
    	//For some reason, using synchronized(billsToPay) still caused concurrentMod errors. Temporary fix with try-catch
    	try {
    		for(DeliveryBill d : billsToPay) {
    			if(registerAmt >= d.amount) {
    				registerAmt -= d.amount;
    		/*		marketCashier.hereIsMoneyRestaurant(this, d.amount);*/
    				//TODO
    				/*
    				d.market.hereIsPayment(registerAmt);
    				billsToPay.remove(d);*/
    			}
    			else {
    				print("The register doesn't have enough money to pay the market bill of " + d + ". Will pay rest with rest of register amount: " + registerAmt);
    				d.market.hereIsPayment(registerAmt);
    				billsToPay.remove(d);
    			}
    		}
    	}
    	catch(ConcurrentModificationException e) {
    		payBill();
    	}
    }
    
    public class Check {
		public String type;
		public Waiter waiter;
		public Customer customer;
		//the amount of the bill
		public double amount;
		public double cashPaid;
		public CheckState state = CheckState.generating;
	
		
		public Check(Waiter w, Customer c, String t) {
		    type = t;
		    customer = c;
		    waiter = w;
		}
		public Check(WaiterRole w, Restaurant1CustomerRole c, String t) {
		    type = t;
		    customer = c;
		    waiter = w;
		}
    }
    
    public class DeliveryBill {
		public Market market;
		public double amount;
		//public CheckState state = CheckState.generating;
		
		public DeliveryBill(Market m, double amt) {
			market = m;
			amount = amt;
		}
    }
    
	public void setGui(RoleGui g) {
		super.setGui(g);
		gui = (CashierGui)g;
	}

	public CashierGui getGui() {
		return gui;
	}
}