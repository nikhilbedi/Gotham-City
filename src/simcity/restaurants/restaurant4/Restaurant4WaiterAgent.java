package simcity.restaurants.restaurant4;

import java.util.ConcurrentModificationException;
import java.util.TimerTask;

import simcity.PersonAgent;
import simcity.restaurants.restaurant4.Restaurant4Gui.Restaurant4WaiterGui;

import simcity.restaurants.restaurant4.Restaurant4WaiterRole.WaiterState;

import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import agent.Role;

public class Restaurant4WaiterAgent extends Role{
	
	
	
	public Restaurant4WaiterAgent(PersonAgent p){
		super(p);
		//this.person = p;
	}
	
	public void setGui(Restaurant4WaiterGui gui){

	}
	
	public void setCook(Restaurant4Cook c){

	}
	
	public void setHost(Restaurant4HostRole h){
	
	}
	
	public void setCashier(Restaurant4CashierRole c){
		
	}
	
	//messages
	
	public void atDefaultPosition(){
	
	}
	public void PleaseSeatCustomer(Restaurant4Customer c, int table){
		
	}
	
	public void msgAtTable() {//from animation
		
	}
	
	public void ReadyToOrder(Restaurant4Customer c){
		
	}
	
	public void arrivedAtTable(Restaurant4Customer c){ //from animation
	
	}
	
	public void HereIsMyChoice(Restaurant4Customer c, String choice){
	
	}
	
	public void startBreak(){
		
		
	}
	
	public void breakOver(){
	
		}

	public void youMayGoToABreak(){
		
	}
	public void arrivedToCook(Restaurant4Customer c){ //from animation
	
		}
	
	public void outOf(int table, String choice){
		
		
	}
	public void arrivedToNotifyNoFood(Restaurant4Customer c, String choice){
			
	}
	
	
	public void OrderDone(String c, int table ){ //cook
		
		}
	
	
	public void HereIsYourFood(int table){//from animation
	
	}
	
	public void DoneAndLeaving(Restaurant4Customer c){
		
	} 
	
	public void computeCheck(Restaurant4Customer c){
	
	}
	
	public void arrivedAtCashier(Restaurant4Customer c){
		
	}
	
	public void HereIsCheck(Restaurant4Customer c, double price){ //cashier
	}
	
	public void HereIsYourCheck(int table){ // from animation
		
	}
	
	
	
//	scheduler
	
	public boolean pickAndExecuteAnAction() {
	return false;
		
	}
	
	//actions

	
	
	public void SeatCustomer(MyCustomer c){   
	
	}

	
	public void TakeOrder(MyCustomer c){
		
	}
	 
	public void GoToCook(MyCustomer customer){
		
	}
	
	public void DeliverFood(MyCustomer customer){
		
	}
	
	public void doPickUpCheck(MyCustomer customer){
		
	}	
	
	public void GoToCashier(MyCustomer customer){
		
		
	}
	
	public void notifyNoFood(MyCustomer c){
		
	}
	
	public void removeCustomer(MyCustomer customer){
		
		}
	
	public void wantToGoOnBreak(){
	
	}
	
	public void goToABreak(){
		
	}
	public void doLeaveCustomer(){
		
		
	}

	public static class MyCustomer{
		public Restaurant4Customer myCustomer;
		public int table;
		public CustomerState s;
		public String choice;
		public double amountDue;
		public enum CustomerState {Waiting, Seated, ReadytoOrder,WaitingWaiter, Ordered, DeliveringFood, WaitingFood, OrderDone, Eating, ComputeCheck, WaitingCheck, DoneEating, deliveringCheck,Computed, gotCheck, outOf,  Leaving};
	
		
	public int getTable(){
		return table;
	}	
	
	public Restaurant4Customer getCustomer(){
		return myCustomer;
	}
	
	public MyCustomer(Restaurant4Customer c, int t){
		myCustomer = c;
		table = t;
		s= CustomerState.Waiting;
	}
	}

	

}
