package restaurant2.gui;


import restaurant2.CookAgent;
import restaurant2.CustomerAgent;
import restaurant2.HostAgent;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CookGui implements Gui {

    private CookAgent agent = null;
    RestaurantGui gui;
    
    List<String> strs = Collections.synchronizedList(new LinkedList<String>());
    
    private static final int speed = 2, tableStarter = 1;
    private int xPos = 490, yPos = 40;//default cook position
    private int strPos1X = 525, strPos1Y = 20;
    private int strPos2X = 525, strPos2Y = 50;
    private int strPos3X = 525, strPos3Y = 80;
    private int xDestination = 460, yDestination = 40;//default start position

    public static final int xTable = 200;
    public static final int yTable = 250;
    
    public static final int tableXBuffer = 20;
    public static final int tableYBuffer = 20;
    public static final int exitXBuffer = -20;
    public static final int exitYBuffer = -20;

    public CookGui(CookAgent agent, RestaurantGui gui) {
        this.agent = agent;
        this.gui = gui;
        
        strs.add("");
        strs.add("");
        strs.add("");
    }

    public void updatePosition() {
        xPos = 490;
        yPos = 40;
        
        //Graphics.drawString(strs.get(0), strPos1X, strPos1Y);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 20, 20);
        
        g.setColor(Color.WHITE);
        g.drawString(strs.get(0), strPos1X, strPos1Y);
        g.drawString(strs.get(1), strPos2X, strPos2Y);
        g.drawString(strs.get(2), strPos3X, strPos3Y);
    }
    
    public void setStr(int index, String newStr) {
    	strs.set(index, newStr);
    }
    
    public void setStrPos(int index, String pos) {
    	switch(index) {
    	case 0:	if(pos == "left") strPos1X = 450;
    			else strPos1X = 525; break;
    	case 1: if(pos == "left") strPos2X = 450;
				else strPos2X = 525; break;
    	case 2: if(pos == "left") strPos3X = 450;
				else strPos3X = 525;
    	}
    }
    
    public boolean isPresent() {
        return true;
    }
}
