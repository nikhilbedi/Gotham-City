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

public class ApartmentAnimationPanel extends Screen {

	static final int xPos = 200;
	static final int yPos = 150;
	static final int WIDTH = 50;
	static final int HEIGHT = 50;
	private Image bufferImage;
	private Dimension bufferSize;
	public ResidentGui gui;
	public int size;
	public int roomNumber;
	
	public int scaleMultiplier = 3;
	
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	public int x3;
	public int y3;
	public int x4;
	public int y5;
	public int x5;
	public int x6;
	public int y6;
	
	/*
	int xStartRoomPos = 0;
	int yStartRoomPos = 0;
	
	int xFridgeStart = 445;
	int yFridgeStart = 690;
	
	int xFridgeDoorStart1 = 380;
	int yFridgeDoorStart1 = 700;
	int xFridgeDoorStart2 = 380;
	int yFridgeDoorStart2 = 690;
	*/
	
	
	

	public ApartmentAnimationPanel() {
		// setSize(WINDOWX, WINDOWY);
		// setVisible(true);
		// bufferSize = this.getSize();
		// Timer timer = new Timer(6, this );//How to make the Gui faster
		// timer.start();

		super();
		populate();
		// paintObstacles();
	}

	public void populate() {
		PersonAgent agentCust = new PersonAgent("Resident");
		ResidentRole resident = new ResidentRole(agentCust);
		ResidentGui residentGui = new ResidentGui(resident);
		resident.setGui(residentGui);
		agentCust.addRole(resident);
		
		
		
		
		//agentCust.startThread();
		//addGui(residentGui);
		
		
		
		// resident.gotHungry();
		// Home home = new Home("House_1", 10, 10, 10, 10);
	}

	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		
		int wallWidth = 10, hallwayWidth = 250;
		int roomHeight = 780/scaleMultiplier;
		int roomWidth = 800/scaleMultiplier;
		
		Graphics g2 = (Graphics) g;
		Graphics text = (Graphics) g;
		Graphics table = (Graphics) g;
		Graphics cookingArea = (Graphics) g;
		Graphics platingArea = (Graphics) g;
		Graphics refridgerator = (Graphics) g;
		Graphics counterTop = (Graphics) g;
		Graphics sink = (Graphics) g;
		Graphics door = (Graphics) g;
		Graphics bed = (Graphics) g;
		Graphics mailbox = (Graphics) g;
		Graphics couch = (Graphics) g;
		Graphics tv = (Graphics) g;
		Graphics chair = (Graphics) g;
		Graphics walls = (Graphics)g;
		
		door.setColor(Color.ORANGE);
		door.fill3DRect( 365 , 0, 50, 6 , true);
		
		walls.setColor(Color.DARK_GRAY);
		walls.fill3DRect(0, 260, 280, 2, true);
		walls.fill3DRect(0, 270, 280, 2, true);
		
		walls.fill3DRect(0, 530, 280, 2, true);
		walls.fill3DRect(0, 540, 280, 2, true);
		
		walls.fill3DRect(533, 260, 320, 2, true);
		walls.fill3DRect(533, 270, 320, 2, true);
		
		walls.fill3DRect(533, 530, 320, 2, true);
		walls.fill3DRect(533, 540, 320, 2, true);
		
		walls.fill3DRect(280, 0, 2, 800, true);
		walls.fill3DRect(290, 0, 2, 800, true);
		
		walls.fill3DRect(533, 0, 2, 800, true);
		walls.fill3DRect(523, 0, 2, 800, true);
		
		walls.fill3DRect(282, 20, 8, 2, true);
		walls.fill3DRect(282, 40, 8, 2, true);
		walls.fill3DRect(282, 292, 8, 2, true);
		walls.fill3DRect(282, 312, 8, 2, true);
		walls.fill3DRect(282, 562, 8, 2, true);
		walls.fill3DRect(282, 582, 8, 2, true);
		
		walls.fill3DRect(525, 20, 8, 2, true);
		walls.fill3DRect(525, 40, 8, 2, true);
		walls.fill3DRect(525, 292, 8, 2, true);
		walls.fill3DRect(525, 312, 8, 2, true);
		walls.fill3DRect(525, 562, 8, 2, true);
		walls.fill3DRect(525, 582, 8, 2, true);
		
		door.setColor(Color.white);
		door.fillRect( 280, 22, 15, 18);
		door.fillRect( 270 + hallwayWidth, 22, 15, 18);
		door.fillRect( 270 + hallwayWidth, 24 + roomHeight + wallWidth, 15, 18);
		door.fillRect( 270 + hallwayWidth, 24 + 2*roomHeight + 2*wallWidth, 15, 18);
		door.fillRect( 280, 24 + roomHeight + wallWidth, 15, 18);
		door.fillRect( 280, 24 + 2*roomHeight + 2*wallWidth, 15, 18);
		
		door.setColor(Color.ORANGE);
		door.fill3DRect( 283, 22, 6, 18, true);
		door.fill3DRect( 276 + hallwayWidth, 22, 6, 18, true);
		door.fill3DRect( 276 + hallwayWidth, 24 + roomHeight + wallWidth, 6, 18, true);
		door.fill3DRect( 276 + hallwayWidth, 24 + 2*roomHeight + 2*wallWidth, 6, 18, true);
		door.fill3DRect( 284, 24 + roomHeight + wallWidth, 6, 18, true);
		door.fill3DRect( 284, 24 + 2*roomHeight + 2*wallWidth, 6, 18, true);
		
		
		
		for (roomNumber = 1; roomNumber <= 6; ++roomNumber) {
			int xPadding = ((int)Math.ceil(roomNumber / 3.0)-1) * (roomWidth + hallwayWidth + wallWidth*2); 
			int yPadding = (((roomNumber % 3 == 0 ? 3: (roomNumber % 3)) - 1) * (wallWidth + roomHeight) ) ;

			refridgerator.setColor(Color.lightGray);
			refridgerator.fill3DRect(xPadding + 380 / scaleMultiplier, yPadding + 700
					/ scaleMultiplier, 130 / scaleMultiplier,
					80 / scaleMultiplier, true);
			refridgerator.setColor(Color.GRAY);
			refridgerator.fill3DRect(xPadding + 380 / scaleMultiplier, yPadding + 690
					/ scaleMultiplier, 130 / scaleMultiplier,
					10 / scaleMultiplier, true);
			refridgerator.setColor(Color.black);
			refridgerator.fill3DRect(xPadding + 445 / scaleMultiplier, yPadding + 690
					/ scaleMultiplier, 2, 10 / scaleMultiplier, true);
			text.setFont(new Font("Serif", Font.BOLD, 9));
			text.setColor(Color.black);
			text.drawString("refridgerator", xPadding + 400 / scaleMultiplier, yPadding
					+ 740 / scaleMultiplier);

			counterTop.setColor(Color.yellow);
			counterTop.fill3DRect(xPadding + 0, yPadding + 700 / scaleMultiplier,
					380 / scaleMultiplier, 80 / scaleMultiplier, true);

			counterTop.setColor(Color.yellow);
			counterTop.fill3DRect(xPadding + 0, yPadding + 400 / scaleMultiplier,
					80 / scaleMultiplier, 380 / scaleMultiplier, true);

			sink.setColor(Color.CYAN);
			sink.fillRect(xPadding + 10 / scaleMultiplier, yPadding + 500
					/ scaleMultiplier, 60 / scaleMultiplier,
					80 / scaleMultiplier);
			text.setColor(Color.black);
			text.drawString("sink", xPadding + 25 / scaleMultiplier, yPadding + 540
					/ scaleMultiplier);

			tv.setColor(Color.DARK_GRAY);
			tv.fill3DRect(xPadding + 210 / scaleMultiplier, yPadding + 168
					/ scaleMultiplier, 10 / scaleMultiplier,
					70 / scaleMultiplier, true);

			cookingArea.setColor(Color.pink);
			cookingArea.fill3DRect(xPadding + 160 / scaleMultiplier, yPadding + 700
					/ scaleMultiplier, 110 / scaleMultiplier,
					80 / scaleMultiplier, true);
			// cookingArea.setColor(Color.DARK_GRAY);
			// cookingArea.fillOval(190, 720, 20, 20);
			text.setColor(Color.black);
			text.drawString("cooktop", xPadding + 190 / scaleMultiplier, yPadding + 740
					/ scaleMultiplier);

			table.setColor(Color.BLUE);
			table.fill3DRect(xPadding + 260 / scaleMultiplier, yPadding + 350
					/ scaleMultiplier, 110 / scaleMultiplier,
					110 / scaleMultiplier, true);

			text.setColor(Color.white);
			text.drawString("table", xPadding + 300 / scaleMultiplier, yPadding + 400
					/ scaleMultiplier);

			chair.setColor(Color.gray);// bottom chair
			chair.fill3DRect(xPadding + 300 / scaleMultiplier, yPadding + 465
					/ scaleMultiplier, 30 / scaleMultiplier,
					30 / scaleMultiplier, false);
			chair.setColor(Color.lightGray);
			chair.fill3DRect(xPadding + 300 / scaleMultiplier, yPadding + 485
					/ scaleMultiplier, 30 / scaleMultiplier,
					9 / scaleMultiplier, true);
			chair.setColor(Color.gray);// right chair
			chair.fill3DRect(xPadding + 375 / scaleMultiplier, yPadding + 395
					/ scaleMultiplier, 30 / scaleMultiplier,
					30 / scaleMultiplier, false);
			chair.setColor(Color.lightGray);
			chair.fill3DRect(xPadding + 395 / scaleMultiplier, yPadding + 395
					/ scaleMultiplier, 9 / scaleMultiplier,
					30 / scaleMultiplier, true);
			chair.setColor(Color.gray);// left chair
			chair.fill3DRect(xPadding + 225 / scaleMultiplier, yPadding + 395
					/ scaleMultiplier, 30 / scaleMultiplier,
					30 / scaleMultiplier, false);
			chair.setColor(Color.lightGray);
			chair.fill3DRect(xPadding + 225 / scaleMultiplier, yPadding + 395
					/ scaleMultiplier, 9 / scaleMultiplier,
					30 / scaleMultiplier, true);
			chair.setColor(Color.gray); // top chair
			chair.fill3DRect(xPadding + 300 / scaleMultiplier, yPadding + 318
					/ scaleMultiplier, 30 / scaleMultiplier,
					30 / scaleMultiplier, false);
			chair.setColor(Color.lightGray);
			chair.fill3DRect(xPadding + 300 / scaleMultiplier, yPadding + 318
					/ scaleMultiplier, 30 / scaleMultiplier,
					9 / scaleMultiplier, true);
/*
			door.setColor(Color.ORANGE);
			door.fill3DRect(xPadding + 350 / scaleMultiplier, yPadding + 0
					/ scaleMultiplier, 100 / scaleMultiplier,
					10 / scaleMultiplier, true);
*/
			bed.setColor(Color.green);
			bed.fill3DRect(xPadding + 680 / scaleMultiplier, yPadding + 180
					/ scaleMultiplier, 100 / scaleMultiplier,
					150 / scaleMultiplier, false);
			bed.setColor(Color.green);
			bed.fill3DRect(xPadding + 685 / scaleMultiplier, yPadding + 185
					/ scaleMultiplier, 40 / scaleMultiplier,
					32 / scaleMultiplier, true);
			bed.setColor(Color.green);
			bed.fill3DRect(xPadding + 735 / scaleMultiplier, yPadding + 185
					/ scaleMultiplier, 40 / scaleMultiplier,
					32 / scaleMultiplier, true);
			text.setColor(Color.white);
			text.drawString("bed", xPadding + 716 / scaleMultiplier, yPadding + 260
					/ scaleMultiplier);

			couch.setColor(Color.green);
			couch.fill3DRect(xPadding + 40 / scaleMultiplier, yPadding + 140
					/ scaleMultiplier, 75 / scaleMultiplier,
					130 / scaleMultiplier, false);
			couch.setColor(Color.green);
			couch.fill3DRect(xPadding + 40 / scaleMultiplier, yPadding + 140
					/ scaleMultiplier, 75 / scaleMultiplier,
					20 / scaleMultiplier, true);
			couch.setColor(Color.green);
			couch.fill3DRect(xPadding + 40 / scaleMultiplier, yPadding + 250
					/ scaleMultiplier, 75 / scaleMultiplier,
					20 / scaleMultiplier, true);
			couch.setColor(Color.green);
			couch.fill3DRect(xPadding + 40 / scaleMultiplier, yPadding + 162
					/ scaleMultiplier, 32 / scaleMultiplier,
					41 / scaleMultiplier, true);
			couch.setColor(Color.green);
			couch.fill3DRect(xPadding + 40 / scaleMultiplier, yPadding + 206
					/ scaleMultiplier, 32 / scaleMultiplier,
					41 / scaleMultiplier, true);
			text.setColor(Color.white);
			// text.drawString("bed", 716, 260);

			mailbox.setColor(Color.MAGENTA);
			mailbox.fill3DRect(xPadding + 130 / scaleMultiplier, yPadding + 0,
					24 / scaleMultiplier, 30 / scaleMultiplier, false);
			text.setColor(Color.black);
			text.drawString("mailbox", xPadding + 120 / scaleMultiplier, yPadding + 20
					/ scaleMultiplier);
		}
		/*
		 * //if (gui.isPresent()) { Graphics order = (Graphics) g; if
		 * (gui.cooking == true) { order.drawString("cooking " + gui.choice,
		 * gui.getXPos(), gui.getYPos()); } //}
		 * 
		 * 
		 * else if (gui.plating) { order.drawString("plating " + gui.choice,
		 * gui.getXPos(), gui.getYPos()); } else if (gui.eating) {
		 * order.drawString("eating " + gui.choice, gui.getXPos(),
		 * gui.getYPos()); } else if (gui.clearing) {
		 * order.drawString("cleaning dishes.", gui.getXPos(), gui.getYPos()); }
		 * else if (gui.sleeping) { order.drawString("zzzzzzzzzzzz ",
		 * gui.getXPos(), gui.getYPos()); } else if (gui.checkingMail) {
		 * order.drawString("checking mail ", gui.getXPos(), gui.getYPos()); }
		 */
	}

		/*

		if (gui instanceof CookGui) {
			CookGui cookGui = (CookGui) gui;
			if (cookGui.cooking) {
				if (cookGui.tableNumber == 1) {
					order.drawString("cooking " + cookGui.order, 0, 145);
				}
				if (cookGui.tableNumber == 2) {
					order.drawString("cooking " + cookGui.order, 0, 160);
				}
				if (cookGui.tableNumber == 3) {
					order.drawString("cooking " + cookGui.order, 0, 175);
				}
				if (cookGui.tableNumber == 4) {
					order.drawString("cooking " + cookGui.order, 0, 195);
				}
			}
			if (cookGui.plating) {
				if (cookGui.tableNumber == 1) {
					order.drawString("plating " + cookGui.order, 0, 225);
				}
				if (cookGui.tableNumber == 2) {
					order.drawString("plating " + cookGui.order, 0, 240);
				}
				if (cookGui.tableNumber == 3) {
					order.drawString("plating " + cookGui.order, 0, 255);
				}
				if (cookGui.tableNumber == 4) {
					order.drawString("plating " + cookGui.order, 0, 270);
				}
			}
		}
		*/
	


	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		// g.fillRect(xPos, yPos, Width, Height);
	}

}