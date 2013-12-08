package simcity.restaurants.restaurant5.interfaces;

import Gui.RoleGui;
import simcity.restaurants.restaurant5.*;


public interface Customer {

	public abstract String getName();
	public abstract RoleGui getGui();//needed for semaphore animation
	
	public abstract void becomesHungry();

	public abstract void msgSitAtTable(int table);

	//Messages added for v2
	public abstract void followMeToTable(Waiter  w, Menu m);

	public abstract void whatWouldYouLike(Waiter  w);
	public abstract void whatWouldYouLike(Waiter  w, Menu m);
	public abstract void hereIsYourFood(Waiter  w);
	
	//v2.1
	public abstract void giveChange(Cashier  ca, double cash);
	public abstract void giveDebt(Cashier  ca, double cash);
	public abstract void hereIsCheck(double check);
	
	public abstract void restIsFull(Host  h);	
}