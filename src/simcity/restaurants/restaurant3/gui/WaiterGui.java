package simcity.restaurants.restaurant3.gui;

import Gui.RoleGui;
import Gui.Screen;
import Gui.ScreenFactory;

import java.awt.*;
import java.util.*;

import agent.Role;
import simcity.restaurants.restaurant3.*;
import simcity.restaurants.restaurant3.interfaces.Waiter;
import trace.AlertLog;
import trace.AlertTag;

public class WaiterGui extends RoleGui {

    private Waiter agent = null;

    //public int xPos = +40, yPos = +40;//default waiter position
    private static int Width = 20, Height = 20;
    //private int xDestination = +40, yDestination = +40;//default start position
    private int tableVariation = 85;
    private int waiterVariation = 20;
    private boolean returningToDoor = false;
    public static final int xTable = 200;
    public static final int yTable = 150;
    private boolean atDestination = false;
    public boolean deliveringFood = false;
    public String order;
    private static int waitingCustomers[] = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int posInWaitArea = -1;

    public WaiterGui(Waiter agent, int home) {
    	super.setColor(Color.pink);
    	
    	for (int i = 0; i < waitingCustomers.length; ++i){
			if (waitingCustomers[i] == -1) { //empty position
				waitingCustomers[i] = 1;
				posInWaitArea = i;
				xPos = 100 + 20 * (i+1) + 5*i;
				yPos = 40;
				break;
			}
		}
		
    	//xDestination = xPos;
        this.agent = agent;
    }
    public WaiterGui(Waiter agent, Screen s) {
    	super((Role) agent, s);
    	
    	AlertLog.getInstance().logError(AlertTag.PERSON, "WaiterGUi",
				homeScreen.toString());
   
    	//super.setColor(Color.pink);
    	
    	for (int i = 0; i < waitingCustomers.length; ++i){
			if (waitingCustomers[i] == -1) { //empty position
				waitingCustomers[i] = 1;
				posInWaitArea = i;
				xPos = 100 + 20 * (i+1) + 5*i;
				yPos = 40;
				break;
			}
		}
		
    	//xDestination = xPos;
        this.agent = agent;
    }


    public void updatePosition() {
    	super.updatePosition();
    	//System.out.println("xPos:" + xPos + ", yPos:" + yPos + ", xDestination:" + xDestination + ", yDestination" + yDestination);
        if (xPos < xDestination)
            xPos++;
        else if (xPos > xDestination)
            xPos--;

        if (yPos < yDestination)
            yPos++;
        else if (yPos > yDestination)
            yPos--;

        if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable-20) & (yDestination == yTable-20)) {
        	if(!atDestination) {
        		agent.msgAtTable();
        		atDestination = true;
        	}
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + tableVariation -20) & (yDestination == yTable-20)) {
        	if(!atDestination) {
        		agent.msgAtTable();
        		atDestination = true;
        	}
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable-20) & (yDestination == yTable + tableVariation-20)) {
        	if(!atDestination) {
        		agent.msgAtTable();
        		atDestination = true;
        	}
        }
        else if (xPos == xDestination && yPos == yDestination
        		& (xDestination == xTable + tableVariation -20) & (yDestination == yTable + tableVariation-20)) {
        	if(!atDestination) {
        		agent.msgAtTable();
        		atDestination = true;
        	}
        }
        if(xPos == xDestination && yPos == yDestination & (xPos == +40) & (yPos == +40)) {
        	if(!atDestination) {
        		agent.msgAtHost();
        		//System.out.print("releasing at host sema");
        		atDestination = true;
        	}  
        }
        
        if(xPos == xDestination && yPos == yDestination & (xPos == 600) & (yPos == 200)) {
        	if(!atDestination) {
        		agent.msgAtCashier();
        		
        		atDestination = true;
        	}
        }
        
        if(xPos == xDestination && yPos == yDestination & (xPos == +40) & (yPos == 200)) {
        	if(!atDestination) {
        	agent.msgAtCook();
        	atDestination = true;
        	}
        }
        if(xPos == xDestination && yPos == yDestination & (xPos == +40) & (yPos == 100)) {
        	if(!atDestination) {
            	agent.msgAtStand();
            	atDestination = true;
            	}
        }
        if(xPos == +40 && yPos == +40 && returningToDoor) {
        	if(!atDestination) {
        		agent.msgAtHost();
        		atDestination = true;
        	}
        	returningToDoor = false;
        }
    }
   
    
    public void draw(Graphics g) {
        super.draw(g);
    	g.setColor(Color.MAGENTA);
        g.fillRect(xPos, yPos, Width, Height);
        if (deliveringFood){				
        	g.drawString("bringing " + order, getXPos(), getYPos());
        }
    }
    
    public boolean isPresent() {
    	returningToDoor = false;
        return true;
    }

    public void DoGoToTable(int tableNum) {
    	//tableNum = 1;
        if(tableNum == 1) {
        	System.out.println("table top left");
        	xDestination = xTable - waiterVariation;
        	yDestination = yTable - waiterVariation;
        	
        }
        if(tableNum == 2) {
        	System.out.println("table top right");
        	xDestination = xTable + tableVariation - waiterVariation;
            yDestination = yTable - waiterVariation ;
            }
        if(tableNum == 3) {
        	System.out.println("table bottom left");
        	xDestination = xTable - waiterVariation;
            yDestination = yTable + tableVariation - waiterVariation;
            }
        if(tableNum == 4) {
        	System.out.println("table bottom right");
        	xDestination = xTable + tableVariation - waiterVariation;
            yDestination = yTable + tableVariation - waiterVariation;
            }
        atDestination = false;
    }
    
    public void DoGoToCook() {
		xDestination = +40;
		yDestination = 200;//location of cook
		atDestination = false;
	}
    public void DoGoToStand() {
    	xDestination = +40;
		yDestination = 100;//location of revolving stand
		atDestination = false;
	}
    
    public void DoLeaveCustomer() {
    	xDestination = 100 + 20 * (posInWaitArea+1) + 5*posInWaitArea;
    	yDestination = 40;
        returningToDoor = true;
        atDestination = false;
    }
    
    public void DoGoToHost() {
    	xDestination = +40;
    	yDestination = +40;
    	atDestination = false;
    }
    public void DoGoToCashier() {
    	xDestination = 600;
    	yDestination = 200;
    	atDestination = false;
    }
    
    public void DoGoToBreakSpot() {
		xDestination = 200;
		yDestination = -20;//location of break spot
		atDestination = false;
	}

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}

