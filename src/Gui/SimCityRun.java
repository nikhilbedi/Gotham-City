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
		peopleList = new PersonSelectionPane(cityPanel);
		cityPanel.setSelPane(peopleList);
		
		add(peopleList);

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
