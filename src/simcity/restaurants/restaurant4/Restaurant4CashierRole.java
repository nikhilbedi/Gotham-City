package simcity.restaurants.restaurant4;
import Gui.RoleGui;
import agent.Agent;
import agent.Role;

import java.util.*;

import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.Restaurant;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4CashierGui;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4CashierRole extends Role implements Restaurant4Cashier {
	private String name;
	private double restaurantMoney = 10000;
	private Timer timer = new Timer();
	public List<Check> checks= new ArrayList<Check>();
	public List<Payment> payments = new ArrayList<Payment>();
	private Restaurant4CashierGui gui = null;
	//public List<MarketAgent> markets= new ArrayList<MarketAgent>();
	
	public Restaurant4CashierRole(PersonAgent person){
		super(person);
	
	}
	
	public Restaurant4CashierRole(){
		super();
	
	}
	
	public void setGui(Restaurant4CashierGui g){
		gui = g;
	}
	
	public List<Payment> getPayments(){
		return payments;
	}
	
	public String getName(){
		return name;
	}
	
	public void setGui(RoleGui g) {
		super.setGui(g);
		gui = (Restaurant4CashierGui)g;
	}

	public Restaurant4CashierGui getGui() {
		return gui;
	}
	
	
	public void giveCheck(Restaurant4Waiter w, Restaurant4Customer c, String choice){ //waiter
		checks.add(new Check(w, c, choice));
		stateChanged();
	}
	
	public void amountDue(double a, MarketCashier c){  // from market Cashier 
		myPerson.Do("Got check, about to pay");
		payments.add(new Payment(c, a));
		stateChanged();
	}
	
	public void HereIsYourChange(double d, MarketCashier c ){
		restaurantMoney =  d;
		myPerson.Do("Got change from market cashier " + d);
		for (Payment payment: payments){
			if (payment.cashier == c){
			payment.state = Payment.OrderState.gotChange;
			stateChanged();
			}
		}
	}
	
	public void hereIsPayment(Restaurant4CustomerRole customer, double m){ //customer
		for (Check check:checks){
			if (check.c == customer){
				check.state = Check.OrderState.paying;
				stateChanged();
			}
		}
	}
	

	public boolean pickAndExecuteAnAction(){
		if(theManLeavingMe != null && payments.isEmpty() && checks.isEmpty()) {
			leaveWork();
			return true;
		}
		
		for (Payment payment: payments){
			if (payment.state == Payment.OrderState.pending){
				payment.state = Payment.OrderState.paying;
				Pay(payment);
				return true;
			}
			if(payment.state == Payment.OrderState.gotChange){
				payment.state = Payment.OrderState.delete;
				Remove(payment);
				return true;
			}		
		}
		
		for (Check check: checks){
			if (check.state == Check.OrderState.pending){
				check.state = Check.OrderState.computing;
				doComputeCheck(check);
				return true;
			}
			if (check.state == Check.OrderState.computed){
				check.state = Check.OrderState.waitingWaiter;
				DoCallWaiter(check);
				return true;
			}
			if (check.state == Check.OrderState.paying){
				check.state = Check.OrderState.gettingPayment;
				doGetPayment(check);
				return true;
			}
		}
		return false;
	}
	
	private void Remove(Payment payment) {
		payments.remove(payment);
		stateChanged();
		
	}

	public void doComputeCheck(Check o){
		timer.schedule(new DoTask(o), 4000);	
	}
	
	public void DoCallWaiter(Check o){
		o.amountDue = o.prices.get(o.choice);
		o.w.HereIsCheck(o.c, o.amountDue);
		myPerson.Do("Pick up the check");
		//
	}
	
	public void Pay(Payment p){
		
		myPerson.Do("Here is money " + p.amountDue);
		Restaurant r4  = (Restaurant) TheCity.getBuildingFromString("Restaurant 4");
		p.cashier.hereIsMoneyRestaurant(r4, restaurantMoney);
	}
	
	public void doGetPayment(Check o){
		if (o.c.getMoney()>=o.c.getAmountDue()){
			myPerson.Do("Amount due "+ o.c.getAmountDue());
			o.moneyHePaid = o.c.getAmountDue();
			restaurantMoney = restaurantMoney + o.moneyHePaid;
			myPerson.Do("Got your payment " +o.moneyHePaid+ " thank you");
			double value = o.c.getMoney() - o.c.getAmountDue();	
    		value = Math.round(value * 100);
    		value = value/100;
    		o.change = value;
			o.c.HereIsYourChange(value);
			checks.remove(o);
		}
		else {
			myPerson.Do("Amount due "+ o.c.getAmountDue());
			myPerson.Do("You don't have enough money, pay the rest next time");
			double i = o.c.getMoney() - o.c.getAmountDue();
			i = Math.round(i*100);
			i = i/100;
			o.moneyHePaid = o.c.getMoney();
			restaurantMoney = restaurantMoney + o.moneyHePaid;
			myPerson.Do("You paid " + o.moneyHePaid + ", pay " + Math.abs(i) + " next time");
			o.change = i;
			o.c.HereIsYourChange(i);
			checks.remove(o);
		}
	}
	private class DoTask extends TimerTask{
		Check order;
		public DoTask(Check o){
			order = o;
		}
		public void run() {
			// TODO Auto-generated method stub
			TimerDone(order);
		}
	}
	
	
	public void TimerDone(Check o){
		o.state = Check.OrderState.computed;
		stateChanged();
	}
	

	
	public static class Check{
		public Restaurant4Customer c;
		private String choice;
		public double change;
		public double amountDue;
		private Restaurant4Waiter w;
		public OrderState state;
		public enum OrderState {pending, computing, computed, waitingWaiter, gettingPayment, paying, paid};
		private Map<String, Double> prices = new HashMap<String,Double>();
		private double moneyHePaid;
		
		public Check(Restaurant4Waiter w, Restaurant4Customer c, String s){
			this.w = w;
			this.c = c;
			this.choice = s;
			prices.put("Steak", 15.99);
			prices.put("Chicken",10.99);
			prices.put("Salad", 5.99);
			prices.put("Pizza", 8.99);
			state = OrderState.pending;
		}
	}
	public static class Payment{
		MarketCashier cashier;
		double amountDue;
		public OrderState state;
		public enum OrderState {pending, paying, paid, gotChange, delete};
		
		public Payment(MarketCashier c, double amountdue){
			cashier = c;
			amountDue = amountdue;
			state = OrderState.pending;
		}
	}
	
	

	
}
