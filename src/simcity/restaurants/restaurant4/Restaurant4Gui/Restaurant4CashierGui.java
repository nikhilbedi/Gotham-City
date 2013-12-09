package simcity.restaurants.restaurant4.Restaurant4Gui;

import java.awt.Graphics;

import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import simcity.restaurants.restaurant4.Restaurant4CookRole;
import Gui.RoleGui;
import Gui.Screen;

public class Restaurant4CashierGui extends RoleGui{
	public Restaurant4CashierGui(Restaurant4CashierRole c, Screen s){
		super(c,s);
		 xPos = 500;
		 yPos = 200;
		 xDestination = 500;
		 yDestination = 200;
		 
	}
	
	public void updatePosition() {
		//if(agent.getPause() == false){	
			super.updatePosition();
		
		//}
	}
	
	public void draw(Graphics g){
		super.draw(g);
	}
}
