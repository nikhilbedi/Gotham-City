package simcity.restaurants.restaurant4.Restaurant4Gui;



import java.util.*;
import java.util.List;
import java.awt.*;

import simcity.Market.MarketCustomerRole;
import simcity.restaurants.restaurant4.Restaurant4CustomerRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Customer;
import Gui.RoleGui;
import Gui.Screen;

public class Restaurant4CustomerGui extends RoleGui{

	private Restaurant4Customer agent = null;
	private boolean isPresent = false;
	//private ListPanel listPanel;
	private boolean isHungry = false;
//	RestaurantGui gui;
	static final int defaultPos = -40;
	static final int defaultPosX = 120;
	static final int defaultPosY = 30;
	static final int rectSize = 20;
	private int xPos, yPos;
	private int xDestination, yDestination;
	private enum Command {noCommand, GoToSeat, GoToCashier, LeaveRestaurant, GoToRestaurant};
	private Command command=Command.noCommand;
	public List <Restaurant4Customer> waitingLine = new ArrayList<Restaurant4Customer>();
	private Status status;
	private enum Status{seated, notSeated };
	private int xTable;
	private int yTable;
	public boolean ordered;
	public boolean gotOrder;
	public boolean doneEating;

	
	
	public Restaurant4CustomerGui(Restaurant4CustomerRole c){ //HostAgent m) {
		agent = c;
		xPos = defaultPos;
		yPos = defaultPos;
		status = Status.notSeated;
		/*xDestination = defaultPosX;
		yDestination = defaultPosY;*/
		//maitreD = m;
		//this.gui = gui;
	}
	
	
	public Restaurant4CustomerGui(Restaurant4CustomerRole role, Screen s){
		super(role, s);
		agent = role;
		
		xPos = defaultPos;
		yPos = defaultPos;
		status = Status.notSeated;
	}
	
	public void setTable(int x, int y){
		xTable = x;
		yTable = y;
	}
	
	  
/*	public void setListPanel(ListPanel ls){
		listPanel = ls;
	}*/
	
	public void updatePosition() {
//	if(agent.getPause() == false){	
	super.updatePosition();
		if (xPos == 220 && yPos == 50 && command==Command.GoToCashier){
			command=Command.noCommand;
			agent.arrivedToCashier();
		}
		
		if (yPos == 30 && command == Command.GoToRestaurant){
			command = Command.noCommand;
			agent.AtRestaurant();
		}
		
		if (xPos == defaultPos && yPos == defaultPos && command == Command.LeaveRestaurant){
			agent.msgOutOfRestaurant4();
		}
		
		if (xPos == xDestination && yPos == yDestination) {
			if (command==Command.GoToSeat) {
				//waitingLine.remove(agent);
				agent.msgAnimationFinishedGoToSeat();
			}
			else if (command==Command.LeaveRestaurant) {
				agent.msgAnimationFinishedLeaveRestaurant();
				System.out.println("about to call gui.setCustomerEnabled(agent);");
				isHungry = false;
				//gui.setCustomerEnabled(agent);
			}
			command=Command.noCommand;
		}
		
		
	//}
	}

	public void draw(Graphics g) {
		super.draw(g);
		/*g.setColor(Color.GREEN);
		g.fillRect(xPos, yPos, rectSize, rectSize);*/
		if (ordered ==true && gotOrder==false){
		drawOrder(g);
		}
		
		if (ordered == false && gotOrder ==true && doneEating == false){
			drawOrdered(g);
		}

		}
	
	public void drawOrder(Graphics g){
		
		g.setColor(Color.BLACK);
		if (agent.getChoice()=="Steak"){
			g.drawString("st?", xTable, yTable);
		}
		else if (agent.getChoice()=="Pizza"){
			g.drawString("p?", xTable, yTable);
		}
		else if (agent.getChoice()=="Chicken"){
			g.drawString("ch?", xTable, yTable);
		}
		else if (agent.getChoice()=="Salad"){
			g.drawString("s?", xTable, yTable);
		}
	}
	
public void drawOrdered(Graphics g){
		
		g.setColor(Color.BLACK);
		if (agent.getChoice()=="Steak"){
			g.drawString("st", xTable, yTable);
		}
		else if (agent.getChoice()=="Pizza"){
			g.drawString("p", xTable, yTable);
		}
		else if (agent.getChoice()=="Chicken"){
			g.drawString("ch", xTable, yTable);
		}
		else if (agent.getChoice()=="Salad"){
			g.drawString("s", xTable, yTable);
		}
	}


	public boolean isPresent() {
		return isPresent;
	}
	
	public void setHungry() {
		isHungry = true;
		agent.gotHungry();
		
		setPresent(true);
	}
	
	public boolean isHungry() {
		return isHungry;
	}

	public void setPresent(boolean p) {
		isPresent = p;
	}

	 
	public void DoGoToSeat() {//later you will map seatnumber to table coordinates.
		xDestination = xTable;
		yDestination = yTable;
		command = Command.GoToSeat;
		status = Status.seated;
		int x  = 120;
		for (int i=0; i<waitingLine.size(); i++){
			if (waitingLine.get(i).getGui().status!=Status.seated){
				waitingLine.get(i).getGui().xDestination = x;
				x = x-40;
				}
		}	 
	}
	
	public void DoGoToRestaurant(){
		//for waiting area
		/*for (int i=0; i<listPanel.getCustomers().size(); i++){
			if (listPanel.getCustomers().get(i).customerGui.status != Status.seated)
			waitingLine.add(listPanel.getCustomers().get(i));
		}*/
		
		System.out.println(waitingLine.size());
		for (int i=0; i<waitingLine.size(); i++){
			if (waitingLine.get(i) == agent){
				if (i == 0){
					System.out.println("my place " + i);
					xDestination = 120;
				}
				else if(i==1){
					System.out.println("my place " + i);
					xDestination = 80;
				}
				else if (i==2){
					System.out.println("my place " + i);
					xDestination = 40;
				}
				else if (i==3){
					System.out.println("my place " + i);
					xDestination = 0;
				}
				
			}
		}
		yDestination = 30;
		command = Command.GoToRestaurant;
	}
	
	public void doGoToCashier(){
		xDestination =  220;
		yDestination = 50;
		
		command = Command.GoToCashier;
	}

	public void DoExitRestaurant() {
		isHungry= false;
		xDestination = defaultPos;
		yDestination = defaultPos;
		command = Command.LeaveRestaurant;
	}
}
