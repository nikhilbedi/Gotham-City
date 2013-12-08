package simcity.restaurants.restaurant4.Restaurant4Gui;


import java.util.*;
import java.util.List;
import java.awt.*;

import Gui.RoleGui;
import Gui.Screen;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.interfaces.Restaurant4Cook;

public class Restaurant4CookGui extends RoleGui {
	public Restaurant4Cook agent;
	
	private List<String> current = new ArrayList<String>();
	private Command command;
	private boolean bring = false;
	private enum Command {none, frigde, pan, ready};
	boolean draw = false;
	boolean plate = false;
	private String cookingOrders;
	private String doneOrders;
	
	public Restaurant4CookGui(Restaurant4Cook c){
		agent = c;
		 xPos = 100;
		 yPos = 325;
		 xDestination = 100;
		 yDestination = 325;
		 
	}

	public Restaurant4CookGui(Restaurant4CookRole c, Screen s){
		super(c,s);
		agent = c;
		 xPos = 100;
		 yPos = 325;
		 xDestination = 100;
		 yDestination = 325;
		 
	}
	
	public void updatePosition() {
		//if(agent.getPause() == false){	
			super.updatePosition();
		
		//}
	}
	
	public void draw(Graphics g){
		super.draw(g);
	/*g.setColor(Color.RED);
	g.fillRect(xPos, yPos, 20, 20);*/
	drawOrder(g);
	
	}
	
	private void drawOrder(Graphics g){
		    	g.setColor(Color.BLACK);
		    	int xCook = 105;
		    	int xDone = 3;
		    	int x = xPos;
		    /*	if (bring ==true){
		    		for (int i =0; i<current.size(); i++){
		    		if (current.get(i)=="Steak"){
		    			g.drawString("st", x, yPos);
		    		}
		    		else if (current.get(i)=="Pizza"){
		    			g.drawString("p", x, yPos);
		    		}
		    		else if (current.get(i)=="Chicken"){
		    			g.drawString("ch", x, yPos);
		    		}
		    		else if (current.get(i)=="Salad"){
		    			g.drawString("s", x, yPos);
		    		}
		    		x = x+10;
		    	}
		    	}*/
		    	if (draw == true){
			    	if (cookingOrders=="Steak"){
						g.drawString("st", xCook, 307);
					}
					else if (cookingOrders=="Pizza"){
						g.drawString("p", xCook, 307);
					}
					else if (cookingOrders=="Chicken"){
						g.drawString("ch", xCook, 307);
					}
					else if (cookingOrders=="Salad"){
						g.drawString("s", xCook, 307);
					}
			    	xCook+=15;
		    	}
		    if (plate == true){
			    	if (doneOrders=="Steak"){
						g.drawString("st", xDone, 307);
					}
					else if (doneOrders=="Pizza"){
						g.drawString("p", xDone, 307);
					}
					else if (doneOrders=="Chicken"){
						g.drawString("ch", xDone, 307);
					}
					else if (doneOrders=="Salad"){
						g.drawString("s", xDone, 307);
					}
			    	xDone+=15;
		    	}
	}
		    
	
	
	
	public boolean isPresent() {
        return true;
    }

	/*public void BringToPan(){
		bring = true;
		xDestination  = 100;
		command = Command.pan;
	}
	
	public void GotToPan(){
		for (int i=0; i< current.size(); i++){
			cookingOrders.add(current.get(i));
		}
		current.clear();
		bring = false;
	}*/
	
	public void DoCookFood(String choice) {
		draw = true;
		cookingOrders  = choice;
		//add to list
		//current.add(choice);	
		//xDestination = 163;
		//command = Command.frigde;
		//cookingOrders.add(choice);
	}
	
	public void DoPlateOrder(String order){
		draw = false;
		plate = true;
		doneOrders = order;
		//bring = true;
		//cookingOrders.remove(order);
		//xDestination = 20;
		//command = Command.ready;
		//doneOrders.add(order);
	}

	/*public void GoHome(){
		bring = false;
		xDestination = 100;
		for(int i=0; i<current.size(); i++){
			doneOrders.add(current.get(i));
			agent.Ready(current.get(i));
		}
		current.clear();
	}*/
	
	public void DoRemoveFood(String s) {
		plate = false;
	}
	

}
