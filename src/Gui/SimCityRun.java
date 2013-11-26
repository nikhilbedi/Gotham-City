package Gui;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

import  sun.audio.*;

import javax.sound.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.print.attribute.standard.Media;
import javax.swing.*;

import simcity.CityClock;
import simcity.Home.gui.HomePanel;


public class SimCityRun extends JFrame implements ActionListener
{
	SimCityPanel cityPanel;
	NewPersonWindow npWindow;
	PersonSelectionPane peopleList;
	public JButton newPersonButton;
	
	public SimCityRun()
	{
		super("Team 31 Sim City");
		//This sets up the frame of the animation window
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout((Container) this.getContentPane(), BoxLayout.X_AXIS));



		//   window.setLayout(new BoxLayout());
		
		JPanel buttonPanel = new JPanel();
		//buttonPanel.set
		//This adds the animation of the main area to the frame
		
		cityPanel = new SimCityPanel();
		add(cityPanel);
		peopleList = new PersonSelectionPane();
		
		add(peopleList);

		setVisible(true);
		cityPanel.go();//starts the animation in the panel
		
		
		/*try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("gameover.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      } */
		
		
	}
	
	public static void main(String[] args)
	{
		CityClock.startTime();
		
		//EPIC BATMAN MUSIC
		String fileName="C:/Users/Brice/CSCI201/team31/src/resources/The_Dark_Knight.wav";
		//String fileName="The_Dark_Knight.wav";
		File soundFile = new File(fileName);
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		    clip.open(audioInputStream);
			//clip.start();
			clip.loop(0);
		} catch (Exception ex) {
		   ex.printStackTrace();
		}
		
		SimCityRun runCity = new SimCityRun();
		runCity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
