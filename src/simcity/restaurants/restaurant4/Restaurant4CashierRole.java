package simcity.restaurants.restaurant4;
import agent.Agent;
import agent.Role;

import java.util.*;

import simcity.PersonAgent;
import simcity.Market.MarketCashierRole;
import simcity.Market.interfaces.MarketCashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cashier;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Waiter;

public class Restaurant4CashierRole extends Role implements Restaurant4Cashier {
	private String name;
	private double restaurantMoney = 1000;
	private Timer timer = new Timer();
	public List<Check> checks= new ArrayList<Check>();
	public List<Payment> payments = new ArrayList<Payment>();
	//public List<MarketAgent> markets= new ArrayList<MarketAgent>();
	
	
	public Restaurant4CashierRole(PersonAgent person){
		super(person);
	
	}
	
	public Restaurant4CashierRole(){
		super();
	
	}
	
	public String getName(){
		return name;
	}
	
	
	public void giveCheck(Restaurant4Waiter w, Restaurant4Customer c, String choice){ //waiter
		checks.add(new Check(w, c, choice));
		stateChanged();
	}
	
	public void amountDue(double a, MarketCashier c){
		payments.add(new Payment(c, a));
		stateChanged();
	}
	
	public void HereIsYourChange(double d, MarketCashier c ){
		restaurantMoney = restaurantMoney + d;
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
		double money = round(p.amountDue);
		restaurantMoney = restaurantMoney - money;
		p.cashier.hereIsMoneyRestaurant(this, money);
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
	
	private int round(double money){
	    double d = Math.abs(money);
	    int i = (int) d;
	    double result = d - (double) i;
	    if(result<0.5){
	        return money<0 ? -i : i;            
	    }else{
	        return money<0 ? -(i+1) : i+1;          
	    }
	}

	
}
