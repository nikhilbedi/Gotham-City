package simcity.restaurants.restaurant2.gui;


import simcity.restaurants.restaurant2.CustomerRole;
import simcity.restaurants.restaurant2.HostRole;

import java.awt.*;

import Gui.RoleGui;

public class HostGui extends RoleGui {

    private HostRole agent = null;
    
    private static final int speed = 2, tableStarter = 1;
    private int xPos = -20, yPos = -20;//default waiter position
    private int xDestination = -20, yDestination = -20;//default start position

    public static final int xTable = 200;
    public static final int yTable = 250;
    
    public static final int tableXBuffer = 20;
    public static final int tableYBuffer = 20;
    public static final int exitXBuffer = -20;
    public static final int exitYBuffer = -20;

    public HostGui(HostRole agent) {
        this.agent = agent;
    }

    public void updatePosition() {
        if (xPos < xDestination)
            xPos+= speed;
        else if (xPos > xDestination)
            xPos-= speed;

        if (yPos < yDestination)
            yPos+= speed;
        else if (yPos > yDestination)
            yPos-= speed;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + 20 || xDestination == xTable + 20 + 80 || xDestination == xTable + 20 + 160) 
        		& (yDestination == yTable - 20 || yDestination == yTable - 20 - 80 || yDestination == yTable - 20 - 160)) {
           agent.msgAtTable();
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, 20, 20);
    }

    public boolean isPresent() {
        return true;
    }

    public void DoBringToTable(CustomerRole customer, int tableNumber) {
        xDestination = xTable + tableXBuffer + ((tableNumber-tableStarter) * 80);
        yDestination = yTable - tableYBuffer - ((tableNumber-tableStarter) * 80);
    }

    public void DoLeaveCustomer() {
        xDestination = exitXBuffer;
        yDestination = exitYBuffer;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}
