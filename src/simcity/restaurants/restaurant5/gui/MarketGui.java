package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import java.awt.*;

import Gui.RoleGui;

public class MarketGui extends RoleGui implements Gui {

    private MarketRole agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position
    
    static final int hostSize = 20;

    private int DEBUGX = 600;
	private int DEBUGY = 60;


    public MarketGui(MarketRole agent) {
        this.agent = agent;
        
    }

    public void updatePosition() {
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(xPos, yPos, hostSize, hostSize);
        g.setColor(Color.BLACK);
        int tempY = DEBUGY;
        if(agent.getName().equals("M2"))
        	tempY += 15;
        if(agent.getName().equals("M3"))
        	tempY += 30;
		g.drawString(agent.getFoodAmounts(), DEBUGX, tempY);
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
    
}
