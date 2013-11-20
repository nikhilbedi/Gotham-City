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
	
	private final int WINDOWX = 650;
    private final int WINDOWY = 650;
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
        resident.gotHungry();
        
      
        Home home = new Home("House_1");
    }
 
               
    @Override
    public  void paintObstacles(Graphics g)
    {
    	 Graphics g2 = (Graphics)g;
    	 Graphics text = (Graphics)g;
         Graphics table = (Graphics)g;
         Graphics cookingArea = (Graphics)g;
         Graphics platingArea = (Graphics)g;
         Graphics refridgerator = (Graphics)g;
         Graphics counterTop = (Graphics)g;
         Graphics sink = (Graphics)g;
         
         g2.fillRect(0, 0, WINDOWX, WINDOWY );
         
         
         
         cookingArea.setColor(Color.RED);
         cookingArea.fillRect(620, 180, 40, 80);
         
         cookingArea.setColor(Color.CYAN);
         cookingArea.fillRect(0, 130, 40, 80);
         
         platingArea.setColor(Color.PINK);
         platingArea.fillRect(0, 210, 40, 80);
         
         refridgerator.setColor(Color.lightGray);
         refridgerator.fillRect(380, 720, 120, 100);
         text.setColor(Color.black);
         text.drawString("refridgerator", 400, 750);
         
         counterTop.setColor(Color.yellow);
         counterTop.fillRect(0, 720, 380, 100);
         
         counterTop.setColor(Color.yellow);
         counterTop.fillRect(0, 600, 100, 380);
         

         //Here is the table
         table.setColor(Color.ORANGE);
         table.fillRect(xPos, yPos, WIDTH, HEIGHT);//200 and 250 need to be table params
         
         g2.setColor(Color.ORANGE);
         g2.fillRect(xPos+85, yPos, WIDTH, HEIGHT);
         
         g2.setColor(Color.ORANGE);
         g2.fillRect(xPos, yPos+85, WIDTH, HEIGHT);
         
         g2.setColor(Color.ORANGE);
         g2.fillRect(xPos+85, yPos+85, WIDTH, HEIGHT);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        //g.fillRect(xPos, yPos, Width, Height);
    }
}