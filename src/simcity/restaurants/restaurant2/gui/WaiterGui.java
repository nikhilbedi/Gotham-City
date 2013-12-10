package simcity.restaurants.restaurant2.gui;


import simcity.restaurants.restaurant2.Restaurant2CustomerRole;
import simcity.restaurants.restaurant2.WaiterRole;
import simcity.restaurants.restaurant2.interfaces.Cook;
import simcity.restaurants.restaurant2.interfaces.Customer;
import simcity.restaurants.restaurant2.interfaces.Waiter;

import java.awt.*;

import agent.Role;
import Gui.RoleGui;
import Gui.Screen;


public class WaiterGui extends RoleGui {
    private WaiterRole agent = null;
    
    private static final int tableStarter = 1;
    private int xBuffer = 5, prevXPos = xPos, prevYPos = yPos, speed = 1;//default waiter position
    private boolean isAtEntrance = false, doDrawString = false;
    private String displayString = "";
    
    public static final int xTable = 200;
    public static final int yTable = 250;
    
    public static final int tableXBuffer = 20;
    public static final int tableYBuffer = 20;
    public static final int exitXBuffer = -20;
    public static final int exitYBuffer = -20;
    public static int waiterDefaultX = 100;
    public static final int waiterDefaultY = 20;

    public WaiterGui(WaiterRole agent) {
        this.agent = agent;
        waiterDefaultX = 100;
        xPos = waiterDefaultX;
        xDestination = waiterDefaultX;
        yPos = 20;
    }
    
    public WaiterGui(Waiter waiter, Screen meScreen) {
		super((Role) waiter, meScreen);
		
		waiterDefaultX = 100;
        xPos = waiterDefaultX;
        xDestination = waiterDefaultX;
        yPos = 20;
	}	
    
    public void updatePosition() {
    	prevXPos = xPos;
    	prevYPos = yPos;
    	
    	super.updatePosition();

        if (xPos == xDestination && yPos == yDestination && (prevXPos != xPos || prevYPos != yPos)
        		& (xDestination == xTable + 20 || xDestination == xTable + 20 + 80 || xDestination == xTable + 20 + 160) 
        		& (yDestination == yTable - 20 || yDestination == yTable - 20 - 80 || yDestination == yTable - 20 - 160)) {
           agent.msgAtTable();
        }
        
        if(xPos == 440 && yPos == 20 && xDestination == 440 && yDestination == 20 && (prevXPos != xPos || prevYPos != yPos))
        	agent.msgAtCook();
        
        if(xPos == 20 && xDestination == 20 && (prevXPos != xPos || prevYPos != yPos))
        	agent.msgAtCustomer();
        
        if(xPos == -20 && yPos == 300 && xDestination == -20 && yDestination == 300 && (prevXPos != xPos || prevYPos != yPos))
        	agent.msgAtCashier();
        
        if(xPos == exitXBuffer && yPos == exitYBuffer && xDestination == exitXBuffer && yDestination == exitYBuffer) {
        	isAtEntrance = true;
        	System.out.println("At entrance");
        	if (xPos == xDestination && yPos == yDestination);
        		agent.msgAtEntrance();
        }
        else
        	isAtEntrance = false;
    }

    public void draw(Graphics g) {
    	super.draw(g);
        //g.setColor(Color.CYAN);
       // g.fillRect(xPos, yPos, 20, 20);
        
        g.setColor(Color.BLACK);
        if(doDrawString) {
        	g.drawString(displayString, xPos + xBuffer, yPos);
        }
    }
    
    public void setString(String str) {
    	displayString = str;
    }
    
    public void showString(boolean show) {
    	doDrawString = show;
    }
    
    public boolean isPresent() {
        return true;
    }
    
    public boolean isAtEntrance() {
        return isAtEntrance;
    }
    
    public void DoBringToTable(Customer c, int tableNumber) {
        xDestination = xTable + tableXBuffer + ((tableNumber-tableStarter) * 80);
        yDestination = yTable - tableYBuffer - ((tableNumber-tableStarter) * 80);
    }

    public void DoLeaveCustomer() {
        xDestination = exitXBuffer;
        yDestination = exitYBuffer;
    }
    
    public void DoGoToTable(int tableNumber) {
    	xDestination = xTable + tableXBuffer + ((tableNumber-tableStarter) * 80);
        yDestination = yTable - tableYBuffer - ((tableNumber-tableStarter) * 80);
    }
    
    public void DoGoToCook() {
    	xDestination = 440;
        yDestination = 20;
    }
    
    public void DoGoToCashier() {
    	xDestination = -20;
        yDestination = 300;
    }
    
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

	public void doGoToEntrance() {
		xDestination = exitXBuffer;
		yDestination = exitYBuffer;
	}

	public void DoGoToDefault() {
		xDestination = waiterDefaultX;
		yDestination = waiterDefaultY;
	}

	public void doPickUpCustomer(int waitingNumber) {
		xDestination = 40;
		yDestination = waitingNumber * 20 + 20;
	}

	public boolean isAtCustomer() {
		if(xPos == 40 && xDestination == 40)
			return true;
		return false;
	}
}
