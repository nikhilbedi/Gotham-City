package simcity.restaurants.restaurant4.Restaurant4Gui;

import java.awt.Graphics;

import simcity.restaurants.restaurant4.Restaurant4CookRole;
import simcity.restaurants.restaurant4.Restaurant4HostRole;
import Gui.RoleGui;
import Gui.Screen;

public class Restaurant4HostGui extends RoleGui{
	
	public Restaurant4HostGui(Restaurant4HostRole c, Screen s){
		super(c,s);
		 xPos = 170;
		 yPos = 10;
		 xDestination = 170;
		 yDestination = 10;
		 
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
