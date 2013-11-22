package restaurant2.gui;


import restaurant2.CustomerAgent;
import restaurant2.WaiterAgent;
import restaurant2.interfaces.Customer;

import java.awt.*;


public class WaiterGui implements Gui {
    private WaiterAgent agent = null;
    
    private static final int speed = 2, tableStarter = 1;
    private int xPos = -20, xBuffer = 5, yPos = 20, prevXPos = xPos, prevYPos = yPos;//default waiter position
    private int xDestination = 20, yDestination = 20;//default start position
    private boolean isAtEntrance = false, doDrawString = false;
    private String displayString = "";
    
    public static final int xTable = 200;
    public static final int yTable = 250;
    
    public static final int tableXBuffer = 20;
    public static final int tableYBuffer = 20;
    public static final int exitXBuffer = -20;
    public static final int exitYBuffer = -20;
    public static int waiterDefaultX;
    public static final int waiterDefaultY = 20;

    public WaiterGui(WaiterAgent agent) {
        this.agent = agent;
        waiterDefaultX = 100;
        xPos = waiterDefaultX;
        xDestination = waiterDefaultX;
        yPos = 20;
    }

    public void updatePosition() {
    	prevXPos = xPos;
    	prevYPos = yPos;
    	
        if (xPos < xDestination)
            xPos+= speed;
        else if (xPos > xDestination)
            xPos-= speed;

        if (yPos < yDestination)
            yPos+= speed;
        else if (yPos > yDestination)
            yPos-= speed;

        if (xPos == xDestination && yPos == yDestination && (prevXPos != xPos || prevYPos != yPos)
        		& (xDestination == xTable + 20 || xDestination == xTable + 20 + 80 || xDestination == xTable + 20 + 160) 
        		& (yDestination == yTable - 20 || yDestination == yTable - 20 - 80 || yDestination == yTable - 20 - 160)) {
           agent.msgAtTable();
        }
        
        if(xPos == 440 && yPos == 20 && xDestination == 440 && yDestination == 20 && (prevXPos != xPos || prevYPos != yPos))
        	agent.msgAtCook();
        
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

    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(xPos, yPos, 20, 20);
        
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
