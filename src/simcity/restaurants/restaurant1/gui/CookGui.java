package simcity.restaurants.restaurant1.gui;

import Gui.RoleGui;


import simcity.restaurants.restaurant1.Restaurant1CustomerRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.WaiterRole;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CookGui extends RoleGui {

	private CookRole agent = null;


	//Taking final out of public static final
	public static int xTable = 200;
	public static int yTable = 250;
	public List<FoodGui> foodTypes = Collections.synchronizedList(new ArrayList<FoodGui>());
	//public Map<String, Boolean> foodTypes = Collections.synchronizedMap(new LinkedHashMap<String, Boolean>());

	//Notifies when to release a permit
	public boolean release = false;
	private boolean showFood = false;

	public CookGui(CookRole agent) {
		super.setColor(Color.BLUE);
		this.agent = agent;
		xPos = 850;
		yPos = 130;//default waiter position
		xDestination = 850;
		yDestination = 130;//default waiter position 
		//Setting coordinates
		/*foodTypes.put("", false);
		foodTypes.put("", false);
		foodTypes.put("", false);
		foodTypes.put("", false);*/
	}

	public void updatePosition() {
		super.updatePosition();

		if (xPos == xDestination && yPos == yDestination)  {
			if(release) {
				//agent.doneWithTask();
				release = false;
			}
		}
	}

	public void draw(Graphics g) {
		//g.setColor(Color.RED);
		//g.fillRect(xPos, yPos, 20, 20);
		super.draw(g);
		int i = 0;
		synchronized(foodTypes) {
			for(FoodGui f : foodTypes) {
				if(f.plated == false)
					g.drawString(f.type, xPos+30, yPos-40+(i*20));
				else
					g.drawString(f.type, xPos-30, yPos-40+(i*20));
				i++;
			}
			/* for(Map.Entry<String, Boolean> entry : foodTypes.entrySet()){
				if(entry.getValue() == false)
					g.drawString(entry.getKey(), xPos+30, yPos-40+(i*20));
				else
					g.drawString(entry.getKey(), xPos-30, yPos-40+(i*20));
				i++;
			}*/
		}
	}

	public boolean isPresent() {
		return true;
	}


	public int getXPos() {
		return xPos;
	}

	public int getYPos() {
		return yPos;
	}

	public static class FoodGui {
		public String type;
		public boolean plated = false;

		public FoodGui(String t) {
			type = t;
		}
	}
}
