package Gui;
import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import simcity.Building;
import simcity.TheCity;
//import simcity.TheCity;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class SimCityPanel extends JPanel implements MouseListener, KeyListener
{
	Screen currentScreen;

	PersonSelectionPane selPane;
	BuildingInfoPanel buildingInfo;



	boolean start;
	boolean always = true;

	public SimCityPanel(){/*
		setPreferredSize(new Dimension(200,600));
		setMinimumSize(new Dimension(800,600));
		setMaximumSize(new Dimension(800,600));*/
		setPreferredSize(new Dimension(800, 800));
		//Building b = TheCity.getBank();
		currentScreen = ScreenFactory.getMainScreen();
		addMouseListener(this);
		addKeyListener(this);
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
				
				//selPane.refresh();
				//buildingInfo.refresh();
				revalidate();
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
			if(swap.equalsIgnoreCase("city")){
				buildingInfo.setVisible(false);
			}
			else{
				buildingInfo.setVisible(true);
				buildingInfo.setB(TheCity.getBuildingFromString(swap));
				buildingInfo.update(TheCity.getBuildingFromString(swap).getBuildingInfo());
				buildingInfo.getUpdators(TheCity.getBuildingFromString(swap));
			}
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

	public BuildingInfoPanel getBuildingInfo() {
		return buildingInfo;
	}


	public void setBuildingInfo(BuildingInfoPanel buildingInfo) {
		this.buildingInfo = buildingInfo;
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


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		System.out.println("key is "+key);
		if (key == 27){
			checkMapChange(26, 51);
		}//&&player.lChange)
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		System.out.println("key is "+key);
		if (key == 27){
			checkMapChange(26, 51);
		}//&&player.lChange)
	}
}