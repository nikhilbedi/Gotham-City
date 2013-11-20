package simcity.restaurant_evan.src.restaurant.gui;


import java.awt.Color;
import java.awt.Graphics;

import simcity.restaurant_evan.src.restaurant.CookRole;

public class CookGui implements Gui {

    private CookRole agent = null;

    private int xPos = +20, yPos = +200;//default cook position
    private static int Width = 20, Height = 20;
    private int xDestination = +20, yDestination = +200;//default start position
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
        g.setColor(Color.BLACK);
        //g.fillRect(xPos, yPos, Width, Height);
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

