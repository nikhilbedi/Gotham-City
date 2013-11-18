package simcity.restaurants.restaurant1.gui;


import simcity.restaurants.restaurant1.CustomerRole;
import Gui.RoleGui;
import simcity.restaurants.restaurant1.HostRole;

import java.awt.*;

public class HostGui extends RoleGui {

    private HostRole agent = null;

    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position

    //Taking final out of public static final
    public static int xTable = 200;
    public static int yTable = 250;

    public HostGui(HostRole agent) {
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
        		& (xDestination == xTable + 20) & (yDestination == yTable - 20)) {
	    //agent.msgAtTable();
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
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
