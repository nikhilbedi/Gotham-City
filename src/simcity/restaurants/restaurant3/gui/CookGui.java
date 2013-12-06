package simcity.restaurants.restaurant3.gui;

import Gui.RoleGui;

import java.awt.*;
import java.util.List;
import java.util.*;

import simcity.restaurants.restaurant3.Restaurant3CustomerRole;
import simcity.restaurants.restaurant3.*;

public class CookGui extends RoleGui {

    private CookRole agent = null;

    //private int xPos = +20, yPos = +200;//default cook position
    private static int Width = 20, Height = 20;
    //private int xDestination = +20, yDestination = +200;//default start position
    //private int tableVariation = 85;
    //private boolean returningToDoor = false;
    //public static final int xTable = 200;
    //public static final int yTable = 150;
    public static String order;
    public static int tableNumber;
    public static boolean cooking = false;
	public static boolean plating = false;
	
    public CookGui(CookRole agent) {
        this.agent = agent;
    }

    public void updatePosition() {
    	super.updatePosition();
    	/*
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
        */
    }

    public void draw(Graphics g) {
        super.draw(g);
    	g.setColor(Color.BLACK);
        //g.fillRect(xPos, yPos, Width, Height);
        if(cooking) {
        	if(tableNumber == 1) {
				g.drawString("cooking " + order, 0, 145);
        	}
			if(tableNumber == 2) {
				g.drawString("cooking " + order, 0, 160);
			}
			if(tableNumber == 3) {
				g.drawString("cooking " + order, 0, 175);
			}
			if(tableNumber == 4) {
				g.drawString("cooking " + order, 0, 195);
			}
		}
		if(plating) {
			if(tableNumber == 1) {
				g.drawString("plating " + order, 0, 225);
			}
			if(tableNumber == 2) {
				g.drawString("plating " + order, 0, 240);
			}
			if(tableNumber == 3) {
				g.drawString("plating " + order, 0, 255);
			}
			if(tableNumber == 4) {
				g.drawString("plating " + order, 0, 270);
			}
		}
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
    /*
    public void DoLeaveCustomer() {
        xDestination = -20;
        yDestination = -20;
        returningToDoor = true;
    }
*/
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}

