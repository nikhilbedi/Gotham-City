package simcity.Home.gui;

import javax.swing.*;

import simcity.Home.gui.ResidentGui;
import simcity.PersonAgent;
import simcity.Home.Home;
import simcity.Home.ResidentRole;
import Gui.Obstacle;
import Gui.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class HomeAnimationPanel extends Screen {
	
	
    static final int xPos = 200;
    static final int yPos = 150;
    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    private Image bufferImage;
    private Dimension bufferSize;
    private ResidentGui gui;

    public HomeAnimationPanel() {
    	//setSize(WINDOWX, WINDOWY);
        //setVisible(true);
        //bufferSize = this.getSize();
    	//Timer timer = new Timer(6, this );//How to make the Gui faster
    	//timer.start();
    
    		super();
           populate(); 
           //paintObstacles();
    }


    public void populate() {
    	PersonAgent agentCust = new PersonAgent("Resident");
        ResidentRole resident = new ResidentRole(agentCust);
        ResidentGui residentGui = new ResidentGui(resident);
        resident.setGui(residentGui);
        agentCust.addRole(resident);
        agentCust.startThread();
        
        addGui(residentGui);
        //resident.gotHungry();
        /*Home home = new Home("House_1");*/

    }
 
               
    @Override
    public  void paintBackground(Graphics g)
    {
    	super.paintBackground(g);
    	 Graphics g2 = (Graphics)g;
    	 Graphics text = (Graphics)g;
         Graphics table = (Graphics)g;
         Graphics cookingArea = (Graphics)g;
         Graphics platingArea = (Graphics)g;
         Graphics refridgerator = (Graphics)g;
         Graphics counterTop = (Graphics)g;
         Graphics sink = (Graphics)g;
         Graphics door = (Graphics)g;
         Graphics bed = (Graphics)g;
         Graphics mailbox = (Graphics)g;
         
         
         refridgerator.setColor(Color.lightGray);
         refridgerator.fill3DRect(380, 700, 130, 80, true);
         refridgerator.setColor(Color.GRAY);
         refridgerator.fill3DRect(380, 690, 130, 10, true);
         refridgerator.setColor(Color.black);
         refridgerator.fill3DRect(445, 690, 2, 10, true);
         text.setColor(Color.black);
         text.drawString("refridgerator", 400, 740);
         
         counterTop.setColor(Color.yellow);
         counterTop.fill3DRect(0, 700, 380, 80, true);
         
         counterTop.setColor(Color.yellow);
         counterTop.fill3DRect(0, 400, 80, 380, true);
         
         sink.setColor(Color.CYAN);
         sink.fillRect(10, 500, 60, 80);
         text.setColor(Color.black);
         text.drawString("sink", 25, 540);
         
         
         cookingArea.setColor(Color.pink);
         cookingArea.fill3DRect(160, 700, 110, 80, true);
        // cookingArea.setColor(Color.DARK_GRAY);
         //cookingArea.fillOval(190, 720, 20, 20);
         text.setColor(Color.black);
         text.drawString("cooktop", 190, 740);
         
         table.setColor(Color.BLUE);
         table.fill3DRect(260, 350, 110, 110, true);
         text.setColor(Color.white);
         text.drawString("table", 300, 400);
         
         door.setColor(Color.ORANGE);
         door.fill3DRect(350, 0, 100, 10, true);
         
         bed.setColor(Color.green);
         bed.fill3DRect(680, 180, 100, 150, false);
         bed.setColor(Color.green);
         bed.fill3DRect(685, 185, 40, 32, true);
         bed.setColor(Color.green);
         bed.fill3DRect(735, 185, 40, 32, true);
         text.setColor(Color.white);
         text.drawString("bed", 716, 260);
         
         mailbox.setColor(Color.MAGENTA);
         mailbox.fill3DRect(130, 0, 24, 30, false);
         text.setColor(Color.black);
         text.drawString("mailbox", 120, 20);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //g.fillRect(xPos, yPos, Width, Height);
    }
}