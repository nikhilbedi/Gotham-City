package simcity.restaurants.restaurant3.gui;


import simcity.restaurants.restaurant3.Restaurant3CustomerRole;
import Gui.RoleGui;
import Gui.Screen;
import simcity.restaurants.restaurant3.HostRole;

import java.awt.*;
public class HostGui extends RoleGui {

    private HostRole agent = null;

    //private int xPos = +40, yPos = +40;//default waiter position
    private static int Width = 20, Height = 20;
    //private int xDestination = +40, yDestination = +40;//default start position
    private int tableVariation = 85;
    private boolean returningToDoor = false;
    public static final int xTable = 200;
    public static final int yTable = 150;

    public HostGui(HostRole agent) {
        this.agent = agent;
    }
    
    public HostGui(HostRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
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

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable) & (yDestination == yTable)) {
           agent.msgAtTable();
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + tableVariation) & (yDestination == yTable)) {
           agent.msgAtTable();
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable) & (yDestination == yTable + tableVariation)) {
           agent.msgAtTable();
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable+ tableVariation ) & (yDestination == yTable + tableVariation)) {
           agent.msgAtTable();
        }
        if(xPos == -20 && yPos == -20 && returningToDoor) {
        	agent.msgAtTable();
        	returningToDoor = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, Width, Height);
    }

    public boolean isPresent() {
    	//returningToDoor = false;
        return true;
    }
/*
    public void DoBringToTable(CustomerAgent customer) {
    	
        if(customer.tableNum == 1) {
        	xDestination = xTable;
        	yDestination = yTable;
        }
        if(customer.tableNum == 2) {
        	xDestination = xTable + tableVariation;
            yDestination = yTable;
            }
        if(customer.tableNum == 3) {
        	xDestination = xTable;
            yDestination = yTable + tableVariation;
            }
        if(customer.tableNum == 4) {
        	xDestination = xTable + tableVariation;
            yDestination = yTable + tableVariation;
            }
    }
*/
    public void DoLeaveCustomer() {
        xDestination = -20;
        yDestination = -20;
        returningToDoor = true;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
