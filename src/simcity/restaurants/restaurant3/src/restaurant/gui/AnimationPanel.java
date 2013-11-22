package simcity.restaurants.restaurant3.src.restaurant.gui;

import javax.swing.*;

import Gui.Screen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class AnimationPanel extends Screen{// implements ActionListener {

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

	}

	public void actionPerformed(ActionEvent e) {
	//	repaint();  //Will have paintComponent called
	}

	public void paintComponent(Graphics g) {
		Graphics g2 = (Graphics)g;

		Graphics cookingArea = (Graphics)g;
		Graphics platingArea = (Graphics)g;
		Graphics cashierArea = (Graphics)g;


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
	}

}