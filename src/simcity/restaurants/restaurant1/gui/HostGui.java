package simcity.restaurants.restaurant1.gui;


import simcity.restaurants.restaurant1.CustomerRole;
import Gui.RoleGui;
import simcity.restaurants.restaurant1.HostRole;

import java.awt.*;

public class HostGui extends RoleGui {

    private HostRole agent = null;

   
    //Taking final out of public static final
    public static int xTable = 200;
    public static int yTable = 250;

    public HostGui(HostRole agent) {
        this.agent = agent;
        xPos = -10;
        yPos = -10;
    }

    public void updatePosition() {
    	super.updatePosition();
        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
	    //agent.msgAtTable();
        }
    }


    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(CustomerRole customer, int tableX, int tableY ) {
	xTable = tableX;
	yTable = tableY;
        xDestination = xTable + 20;
        yDestination = yTable - 20;
	//	xDestination = tableX + 20;
	//yDestination = tableY + 20;
    }

    public void DoLeaveCustomer() {
        xDestination = -20;
        yDestination = -20;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
