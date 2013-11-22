package simcity.Restaurant4.Restaurant4Gui;

import javax.swing.*;

import Gui.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends Screen {
	static final int X = 50;
	static final int Y = 170;
	static final int HEIGHT = 50;
	static final int WIDTH = 50;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    public AnimationPanel() {
    	/*setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(20, this );
    	timer.start();*/
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
       /* g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );*/

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(X, Y, WIDTH, HEIGHT );//200 and 250 need to be table params

        g2.setColor(Color.ORANGE);
        g2.fillRect(X+90, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+180, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(X+270, Y, WIDTH, HEIGHT );
        
        g2.setColor(Color.GREEN);
        g2.fillRect(200, 20, 70, 30);
        
        g2.setColor(Color.GREEN);
        g2.fillRect(2, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(103, 300, 100, 20);
        
        g2.setColor(Color.YELLOW);
        g2.fillRect(183, 320, 20, 25);
        
       /* g2.setColor(Color.BLACK);
        g2.fillRect(30, 50, 110, 2);*/
        
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
    }

 /*   public void addGui(CustomerGui gui) {
        guis.add(gui);
    }

    public void addGui(WaiterGui gui) {
        guis.add(gui);
    }
    
    public void addGui(CookGui gui){
    	guis.add(gui);
    }*/
}
