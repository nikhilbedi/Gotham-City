package simcity.restaurants.restaurant1.gui;



import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.CookRole;
import simcity.restaurants.restaurant1.WaiterRole;

import java.awt.*;
import java.util.List;
import java.util.*;

public class CookGui implements Gui {

    private CookRole agent = null;

    private int xPos = 845, yPos = 130;//default waiter position
    private int xDestination = 845, yDestination = 130;//default start position

    //Taking final out of public static final
    public static int xTable = 200;
    public static int yTable = 250;
    public List<FoodGui> foodTypes = Collections.synchronizedList(new ArrayList<FoodGui>());
    //public Map<String, Boolean> foodTypes = Collections.synchronizedMap(new LinkedHashMap<String, Boolean>());

    //Notifies when to release a permit
    public boolean release = false;
    private boolean showFood = false;

    public CookGui(CookRole agent) {
	    this.agent = agent;
		//Setting coordinates
		/*foodTypes.put("", false);
		foodTypes.put("", false);
		foodTypes.put("", false);
		foodTypes.put("", false);*/
    }

    public void updatePosition() {
        if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

	//Redo this if statement so the agent knows when he is at any table
        if (xPos == xDestination && yPos == yDestination)  {
		    if(release) {
				//agent.doneWithTask();
				release = false;
		    }
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
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
    
    public void DoGetCustomer(int x, int y) {
		release = true;
		xDestination = x;
		yDestination = y;
    }

    public void DoLeaveCustomer() {
        xDestination = 120;
        yDestination = 20;
    }

    public void DoGoToCook() {
	release = true;
	xDestination = 425;
	yDestination = 75;
    }

    public void DoGoToCashier() {
	release = true;
	xDestination = -40;
	yDestination = 300;
    }
    
    public void setFood(CustomerRole c, String choice) {
	showFood = false;
	c.getGui().eatingFood(choice);
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
