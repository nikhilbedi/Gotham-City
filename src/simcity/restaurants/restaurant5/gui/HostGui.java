package simcity.restaurants.restaurant5.gui;



import java.util.*;
import java.awt.*;
import simcity.restaurants.restaurant5.*;
import java.awt.*;

import Gui.RoleGui;

public class HostGui extends RoleGui implements Gui {

    private HostRole agent = null;

    private int xPos = 20, yPos = 20;//default waiter position
    
    public ArrayList<Table> tables  = new ArrayList<Table>();
    public static final int firstTableX = 50;
    public static final int firstTableY = 250;
    static final int hostSize = 20;

    public HostGui(HostRole agent) {
        this.agent = agent;
        for (int i = 0; i < agent.getNumTables(); i++) {
			tables.add(new Table(i));
		}
    }

    public void updatePosition() {
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, hostSize, hostSize);
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
    
    //table class for HostGui
	private class Table {
		int xLocation;
		int yLocation;
		
		Table(int tableNumber){
			xLocation = 50 + tableNumber*100;
			yLocation = 250;
		}
		
		public int getxLocation() {
			return xLocation;
		}
		
		public int getyLocation() {
			return yLocation;
		}
		
		
		
	}
    
}
