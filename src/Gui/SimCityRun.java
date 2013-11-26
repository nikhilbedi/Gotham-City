package Gui;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import simcity.CityClock;
import simcity.Home.gui.HomePanel;


public class SimCityRun extends JFrame implements ActionListener
{
	SimCityPanel cityPanel;
	NewPersonWindow npWindow;
	PersonSelectionPane peopleList;
	public JButton newPersonButton;
	JPanel animationPanel;
	
	public SimCityRun()
	{
		super("Team 31 Sim City");
		//This sets up the frame of the animation window
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BoxLayout((Container)this.getContentPane(), BoxLayout.Y_AXIS));
		
		animationPanel = new JPanel();
		animationPanel.setLayout(new BoxLayout(animationPanel, BoxLayout.X_AXIS));
		
		//Set information for animationpanel and personSelectionPane
		cityPanel = new SimCityPanel();
		animationPanel.add(cityPanel);
		peopleList = new PersonSelectionPane(cityPanel);
		cityPanel.setSelPane(peopleList);
		animationPanel.add(peopleList);
		
		//set information for infoPanel
		InfoPanel info = new InfoPanel();
		peopleList.setInfoPanel(info);
		
		add(animationPanel);
		add(info);
		setVisible(true);
		cityPanel.go();//starts the animation in the panel
	}
	
	public static void main(String[] args)
	{
		CityClock.startTime();
		SimCityRun runCity = new SimCityRun();
		runCity.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
