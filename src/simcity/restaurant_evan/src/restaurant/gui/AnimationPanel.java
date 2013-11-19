package simcity.restaurant_evan.src.restaurant.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {

    private final int WINDOWX = 650;
    private final int WINDOWY = 650;
    static final int xPos = 200;
    static final int yPos = 150;
    static final int WIDTH = 50;
    static final int HEIGHT = 50;
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        bufferSize = this.getSize();
    	Timer timer = new Timer(6, this );//How to make the Gui faster
    	timer.start();
    }

	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        Graphics2D cookingArea = (Graphics2D)g;
        Graphics2D platingArea = (Graphics2D)g;
        Graphics2D cashierArea = (Graphics2D)g;
        
        
        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );
        
        
        cookingArea.setColor(Color.RED);
        cookingArea.fillRect(620, 180, 40, 80);
        
        cookingArea.setColor(Color.CYAN);
        cookingArea.fillRect(0, 130, 40, 80);
        
        platingArea.setColor(Color.PINK);
        platingArea.fillRect(0, 210, 40, 80);

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(xPos, yPos, WIDTH, HEIGHT);//200 and 250 need to be table params
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(xPos+85, yPos, WIDTH, HEIGHT);
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(xPos, yPos+85, WIDTH, HEIGHT);
        
        g2.setColor(Color.ORANGE);
        g2.fillRect(xPos+85, yPos+85, WIDTH, HEIGHT);

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
                Graphics2D order = (Graphics2D)g;
                if (gui instanceof WaiterGui) {
                	WaiterGui waiterGui = (WaiterGui) gui;
                	if (waiterGui.deliveringFood)
                	{
                		order.drawString("bringing " + waiterGui.order, waiterGui.getXPos(), waiterGui.getYPos());
                	}
                }
                if (gui instanceof CustomerGui)
                {
                	CustomerGui customerGui = (CustomerGui) gui;
                	if (customerGui.waitingForFood)
                	{
                		order.drawString("waiting for " + customerGui.order, customerGui.getXPos(), customerGui.getYPos());
                	}
                	if (customerGui.receivedFood)
                	{
                		order.drawString("eating " + customerGui.order, customerGui.getXPos(), customerGui.getYPos());
                	}
                }
                
                if (gui instanceof CookGui){
                	CookGui cookGui = (CookGui) gui;
                	if(cookGui.cooking) {
                		if(cookGui.tableNumber == 1) {
                			order.drawString("cooking " + cookGui.order, 0, 145);
                		}
                		if(cookGui.tableNumber == 2) {
                			order.drawString("cooking " + cookGui.order, 0, 160);
                		}
                		if(cookGui.tableNumber == 3) {
                			order.drawString("cooking " + cookGui.order, 0, 175);
                		}
                		if(cookGui.tableNumber == 4) {
                			order.drawString("cooking " + cookGui.order, 0, 195);
                		}
                	}
                	if(cookGui.plating) {
                		if(cookGui.tableNumber == 1) {
                			order.drawString("plating " + cookGui.order, 0, 225);
                		}
                		if(cookGui.tableNumber == 2) {
                			order.drawString("plating " + cookGui.order, 0, 240);
                		}
                		if(cookGui.tableNumber == 3) {
                			order.drawString("plating " + cookGui.order, 0, 255);
                		}
                		if(cookGui.tableNumber == 4) {
                			order.drawString("plating " + cookGui.order, 0, 270);
                		}
                	}
                }
             
               // else if(gui instanceof CustomerGui) {
                //	CustomerGui customerGui = (CustomerGui) gui;
                	//order.drawString(customerGui.choice, customerGui.getXPos(), customerGui.getYPos());
                	
                //}
               
            }
        }
    }

    public void addGui(CustomerGui gui) {
        guis.add(gui);
    }

    public void addGui(HostGui gui) {
        guis.add(gui);
    }
    public void addGui(WaiterGui gui) {
        guis.add(gui);
    }
    public void addGui(OrderGui gui) {
    	guis.add(gui);
    }
    public void addGui(CookGui gui) {
    	guis.add(gui);
    }
}