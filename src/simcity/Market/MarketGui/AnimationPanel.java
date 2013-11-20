package simcity.Market.MarketGui;

import javax.swing.*;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends Screen {
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
     /*   g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );*/

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(80, 150, 250, 30);//200 and 250 need to be table params

        g2.setColor(Color.GREEN);
        g2.fillRect(80, 30, 250, 10);

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

/*    public void addGui(MarketCustomerGui customerGui) {
        guis.add(customerGui);
    }

    public void addGui(MarketWorkerGui gui) {
        guis.add(gui);
    }

	public void addGui(MarketCashierGui cashierGui) {
		guis.add(cashierGui);
		
	}*/

}
