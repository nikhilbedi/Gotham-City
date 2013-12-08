package Gui;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SimCityPanel extends JPanel implements MouseListener
{
	Screen currentScreen;

	PersonSelectionPane selPane;
	boolean start;
	boolean always = true;

	public SimCityPanel(){
		setPreferredSize(new Dimension(800, 800));
		currentScreen = ScreenFactory.getMainScreen();
		addMouseListener(this);
		setFocusable(true);  

		/*String fileName="TheDarkKnight.mp3";
 		File soundFile = new File(fileName);
 		AudioInputStream audioInputStream = null;
 		System.out.println("Trying to play audio");
 		try {
 		    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
 		} catch (Exception ex) {
 			System.out.println("DIDNT WORK");
 		   ex.printStackTrace();
 		}*/
	}


	public void paintComponent(Graphics g){//Here is where everything in the animation panel is generated
		ScreenFactory.updateScreens();//updates location of all agents
		currentScreen.paintBackground(g);
		//currentScreen.paintObstacles(g);
		currentScreen.paintAgents(g);         
	}


	public void go(){//intializes game always happens
		while(always)
		{
			try{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e) {
				e.printStackTrace();
			}

		}
	}



	public void checkMapChange(int x, int y){

		//Have map check coords
		String swap = currentScreen.checkSwap(x,y);
		//System.out.println("swap is " + swap);
		//swap = "Restaurant";
		Screen swapScreen = ScreenFactory.getMeScreen(swap);
		if(!(swapScreen == null)){
			currentScreen = swapScreen;
			selPane.refresh();
			//currentScreen.generate();
		} 
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		//System.out.println("Coords " + e.getX() + ", " + e.getY() );
		checkMapChange(e.getX(), e.getY());
	}

	public void mousePressed(MouseEvent e){

	}

	public MainScreen getCityScreen(){
		return ScreenFactory.getMainScreen();
	}

	public PersonSelectionPane getSelPane() {
		return selPane;
	}


	public void setSelPane(PersonSelectionPane selPane) {
		this.selPane = selPane;
	}



	@Override        
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}