package restaurant2.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends JPanel implements ActionListener {
	
	private final int WINDOWX1 = 0;
    private final int WINDOWY1 = 0;
    private final int WINDOWX = 450;
    private final int WINDOWY = 350;
    private final int TIMING = 20;
    private final int TABLERECTX = 200;
    private final int TABLERECTY = 250;
    private final int TABLERECTWIDTH = 50;
    private final int TABLERECTHEIGHT = 50;
    private final int KITCHENRECTX = 450;
    private final int KITCHENRECTY = 0;
    private final int KITCHENRECTWIDTH = 30;
    private final int KITCHENRECTHEIGHT = 200;
    private JButton breakButton = new JButton("Send waiter on Break");
    private Image bufferImage;
    private Dimension bufferSize;

    private List<Gui> guis = new ArrayList<Gui>();

    public AnimationPanel() {
    	setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
        
    	Timer timer = new Timer(TIMING, this );
    	timer.start();
    }
    
	public void actionPerformed(ActionEvent e) {
		repaint();  //Will have paintComponent called
	}
	
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(WINDOWX1, WINDOWY1, WINDOWX, WINDOWY );

        //Here is the table
        g2.setColor(Color.ORANGE);
        g2.fillRect(TABLERECTX, TABLERECTY, TABLERECTWIDTH, TABLERECTHEIGHT);//200 and 250 need to be table params
        
        g2.fillRect(TABLERECTX + 80, TABLERECTY-80, TABLERECTWIDTH, TABLERECTHEIGHT);//EXTRA TABLES
        g2.fillRect(TABLERECTX + 160, TABLERECTY-160, TABLERECTWIDTH, TABLERECTHEIGHT);
        
        //Kitchen Area
        g2.setColor(Color.BLACK);
        g2.fillRect(KITCHENRECTX, KITCHENRECTY, KITCHENRECTWIDTH, KITCHENRECTHEIGHT);
        
        g2.fillRect(KITCHENRECTX + 70, KITCHENRECTY, KITCHENRECTWIDTH, KITCHENRECTHEIGHT);
        
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

    public void addGui(CustomerGui gui) {
        guis.add(gui);
    }

    public void addGui(HostGui gui) {
        guis.add(gui);
    }
    
    public void addGui(WaiterGui gui) {
    	guis.add(gui);
    }
    
    public void addGui(CookGui gui) {
    	guis.add(gui);
    }
}
