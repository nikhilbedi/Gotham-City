package Gui;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import simcity.Market.MarketGui.MarketPanel;

import simcity.Home.gui.HomePanel;


public class SimCityRun extends JFrame implements ActionListener
{
	SimCityPanel cityPanel;
	NewPersonWindow npWindow;
	JButton newPersonButton;
	
	public SimCityRun()
	{
		super("Team 31 Sim City");
		//This sets up the frame of the animation window
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());



		//   window.setLayout(new BoxLayout());


		//This adds the animation of the main area to the frame
		newPersonButton = new JButton("add person");
		newPersonButton.addActionListener(this);
		cityPanel = new SimCityPanel();
		add(cityPanel);
		add(newPersonButton);

		setVisible(true);
		cityPanel.go();//starts the animation in the panel
	}
	
	public static void main(String[] args)
	{
		SimCityRun runCity = new SimCityRun();

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newPersonButton ){
			System.out.println("howdy partner");
			npWindow = new NewPersonWindow(cityPanel.getCityScreen());
			npWindow.setVisible(true);
		}
	}
}