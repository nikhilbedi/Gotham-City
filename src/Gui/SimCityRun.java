package Gui;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
//import simcity.TheCity;
import simcity.Home.gui.HomePanel;
import simcity.restaurants.restaurant5.Restaurant5;
import trace.TracePanel;


public class SimCityRun extends JFrame implements ActionListener
{
	JPanel dataPanel;
	SimCityPanel cityPanel;
	PersonSelectionPane peopleList;
	BuildingInfoPanel buildingPanel;
	BuildingControlPanel buildingControl;
	public TracePanel tracePanel;
	CityTabbedPane tabs;


	public JButton newPersonButton;

	NewPersonWindow npWindow;

	JPanel animation;
	JPanel ui;

	public SimCityRun()
	{
		super("Team 31 Sim City");
		//This sets up the frame of the animation window
		setSize(1400, 850);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//This creates and sets the menu bar
		CityMenuBar demo = new CityMenuBar();
		setJMenuBar(demo);

		//
		BoxLayout aniLayout = new BoxLayout( animation, BoxLayout.Y_AXIS);
		//uiLayout = new (animation, BoxLayout.X_AXIS);

		setLayout(new BoxLayout((Container)this.getContentPane(), BoxLayout.X_AXIS));

		//Create animation components
		animation = new JPanel();
		animation.setPreferredSize(new Dimension(1000, 800));
		//animation.setMaximumSize(new Dimension(800, 800));
		animation.setMinimumSize(new Dimension(1000, 800));
		//animation.setLayout(aniLayout);

		animation.add(TheCity.bar);//Adds clock gui



		cityPanel = new SimCityPanel();
		animation.add(cityPanel);
		//add animation
		add(animation);

		//Create UI
		tabs = new CityTabbedPane();

		

		//Buildings tab
		buildingControl = new BuildingControlPanel();
		tabs.addPanel(buildingControl, "Buildings");

		//People tab
		JPanel infoControl = new JPanel();
		infoControl.setLayout(new BoxLayout(infoControl, BoxLayout.Y_AXIS));
		peopleList = new PersonSelectionPane(cityPanel);
		InfoPanel info = new InfoPanel();
		peopleList.setInfoPanel(info);
		infoControl.add(peopleList);
		infoControl.add(info);
		tabs.addPanel(infoControl, "People");
		
		//Info tab
		buildingPanel  = new BuildingInfoPanel();
		tabs.addPanel(buildingPanel, "Info");

		//Debug tab
		JPanel debugPanel = new JPanel();
		debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));
		tracePanel = new TracePanel();

		tracePanel.showAlertsForAllLevels();
		tracePanel.showAlertsForAllTags();
		debugPanel.add(tracePanel);
		debugPanel.add(new CityControlPanel(this));
		tabs.addPanel(debugPanel, "Debug");


		//add UI
		add(tabs);

		//setting info for the panel
		cityPanel.setBuildingInfo(buildingPanel);
		cityPanel.setSelPane(peopleList);
		cityPanel.setBCP(buildingControl);

		setVisible(true);

		cityPanel.go();//starts the animation in the panel

	}


	public static void main(String[] args)
	{
		//Sample reading an XML file

		//XMLHelper.createPeople("sampleXML.xml");
		//XMLHelper.createPeople("Restaurant5.xml");
		//XMLHelper.createPeople("Restaurant2.xml");
		//XMLHelper.createPeople("Restaurant1Complete.xml");
		XMLHelper.createPeople("completeCity.xml");

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
