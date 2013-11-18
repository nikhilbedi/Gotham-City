package simcity.restaurants.restaurant1.gui;

import javax.swing.*;

import Gui.Screen;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends Screen  {

    /*private final int WINDOWX = 350;
    private final int WINDOWY = 250;
    private Image bufferImage;
    private Dimension bufferSize; */
    private int xPos, yPos,width, height;

    //private List<Gui> guis = new ArrayList<Gui>();

    public AnimationPanel() { 
    	//Timer timer = new Timer(20, this );
    	//timer.start();
    }

    public void paintObstacles(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
		//Second and third table created manually
		Graphics2D g3 = (Graphics2D)g;
		Graphics2D g4 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
       /* g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight() );*/

        //Agent Positions
	        g2.setColor(Color.MAGENTA);
	        xPos=100;
	        yPos = 20;
	        g2.drawString("Waiter Home", xPos, yPos);
	        
	        g2.setColor(Color.GREEN);
	        xPos=20;
	        yPos = 20;
	        g2.drawString("Waiting", xPos, yPos);
	        
	        g2.setColor(Color.RED);
	        xPos=830;
	        yPos = 20;
	        g2.drawString("KITCHEN", xPos, yPos);
        
        //Here is the table
        g2.setColor(Color.BLUE); 
		xPos = 100;
		yPos = 150;
		width = 50;
		height = 50; 
	        g2.fillRect(xPos, yPos, width, height);//200 and 250 need to be table params
		
		g2.setColor(Color.BLUE);
		xPos = 300;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);
	
	       	g2.setColor(Color.BLUE);
		xPos = 500;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);
	
		xPos = 700;
		yPos = 150;
		g2.fillRect(xPos, yPos, width, height);


     /*  for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }*/
    }
}
