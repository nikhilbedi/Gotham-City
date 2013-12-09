package Gui;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;

//import helpers.XMLReader;
import  sun.audio.*;

import javax.sound.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.print.attribute.standard.Media;
import javax.swing.*;

import java.io.File;

import Gui.SoundClip;
import simcity.CityClock;
import simcity.PersonAgent;
import simcity.TheCity;
import simcity.Home.gui.HomePanel;
import simcity.restaurants.restaurant5.Restaurant5;
import trace.TracePanel;


public class SimCityRun extends JFrame implements ActionListener
{
	JPanel dataPanel;
	SimCityPanel cityPanel;
	PersonSelectionPane peopleList;
	BuildingInfoPanel buildingPanel;

	public JButton newPersonButton;

	NewPersonWindow npWindow;

	public SimCityRun()
	{
		super("Team 31 Sim City");
		//This sets up the frame of the animation window
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CityMenuBar demo = new CityMenuBar();
        setJMenuBar(demo);
        
		
		setLayout(new BoxLayout((Container)this.getContentPane(), BoxLayout.Y_AXIS));

		dataPanel = new JPanel();
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));

		//Set information for dataPane, personSelectionPane, and buildingPanel
		cityPanel = new SimCityPanel();
		buildingPanel  = new BuildingInfoPanel();
		buildingPanel.setVisible(false);
		peopleList = new PersonSelectionPane(cityPanel);

		cityPanel.setBuildingInfo(buildingPanel);
		cityPanel.setSelPane(peopleList);

		dataPanel.add(new BuildingControlPanel());
		dataPanel.add(buildingPanel);	
		dataPanel.add(cityPanel);
		dataPanel.add(peopleList);

		//set information for infoPanel
		InfoPanel info = new InfoPanel();
		peopleList.setInfoPanel(info);
		add(TheCity.bar);//Adds clock gui
		add(dataPanel);
		add(info);
		
		TracePanel tracePanel = new TracePanel();
		tracePanel.showAlertsForAllLevels();
		tracePanel.showAlertsForAllTags();
		add(tracePanel);
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
		//Sample reading an XML file
		//XMLHelper.createPeople("sampleXML.xml");

		//THE BIG BANG
		CityClock.startTime();

		//EPIC BATMAN THEME SONG
		try
		{
			final Clip clip = (Clip)AudioSystem.getLine(new Line.Info(Clip.class));

			clip.addLineListener(new LineListener()
			{
				@Override
				public void update(LineEvent event)
				{
					if (event.getType() == LineEvent.Type.STOP)
						clip.close();
				}
			});

			//clip.open(AudioSystem.getAudioInputStream(new File("The_Dark_Knight.wav"))); commenting because i listen to music on my cpu
			//clip.start();
		}
		catch (Exception exc)
		{
			exc.printStackTrace(System.out);
		}

		SimCityRun runCity = new SimCityRun();
		runCity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
