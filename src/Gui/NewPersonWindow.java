package Gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import simcity.CityClock;
import simcity.PersonAgent;
import simcity.PersonGui;
import simcity.TheCity;
import simcity.Home.Home;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketGui.MarketAnimationPanel;
import simcity.interfaces.Person;

public class NewPersonWindow extends JFrame implements ActionListener {
	static final int WINDOWX = 300;
	static final int WINDOWY = 500;

	JTextField nameField;

	JSlider money;

	Vector<String> homeList;
	Vector<String> jobLocationList;
	Vector<String> jobPositionList;
	Vector<String> transportationList;
	Vector<String> homeOwnerList;

	JComboBox home;
	JComboBox jobLocation;
	JComboBox jobPosition;
	JComboBox homeOwned;
	JComboBox transportation;

	JButton finalize;

	MainScreen mainScreen;

	SimCityRun reference;

	JPanel interactionPanel;
	JPanel labelsPanel;

	public MyLabeledUnit labeledPosition;


	public NewPersonWindow(MainScreen city){
		setSize(WINDOWX, WINDOWY);


		mainScreen = city;

		setBounds(1000, 200, WINDOWX, WINDOWY);

		BoxLayout windowLayout = new BoxLayout((Container) this.getContentPane(), BoxLayout.Y_AXIS);
		setLayout(windowLayout);

		nameField = new JTextField("Enter a name");
		nameField.setMaximumSize(new Dimension(WINDOWX, 10));

		homeList = new Vector<String>(TheCity.getHomes());
		jobLocationList = new Vector<String>(TheCity.getJobLocs());
		jobPositionList = new Vector<String>(TheCity.getPos());
		transportationList = new Vector<String>(TheCity.getTransportation());
		homeOwnerList = new Vector<String>(TheCity.getProperty());

		home = new JComboBox(homeList);
		jobLocation = new JComboBox(jobLocationList);
		jobLocation.addActionListener(this);
		jobPosition = new JComboBox(jobPositionList);
		transportation = new JComboBox(transportationList);
		homeOwned = new JComboBox(homeOwnerList);


		money = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
		money.setMajorTickSpacing(100);
		money.setMinorTickSpacing(100);
		money.setPaintTicks(true);
		money.setPaintLabels(true);


		finalize = new JButton("done");

		finalize.setMinimumSize(new Dimension(200, 25));
		finalize.setMaximumSize(new Dimension(200, 25));
		finalize.setPreferredSize(new Dimension(200, 25));
		finalize.setAlignmentX(Component.CENTER_ALIGNMENT);
		finalize.addActionListener(this);

		jobPosition.setVisible(false);

		MyLabeledUnit labeledName = new MyLabeledUnit(nameField, "name");
		MyLabeledUnit labeledHome = new MyLabeledUnit(home, "Living space");
		MyLabeledUnit labeledLocation = new MyLabeledUnit(jobLocation, "Working location");
		labeledPosition = new MyLabeledUnit(jobPosition, "Position:");
		labeledPosition.label.setVisible(false);
		MyLabeledUnit labeledProperty = new MyLabeledUnit(homeOwned, "Property:");
		MyLabeledUnit labeledTransportation = new MyLabeledUnit(transportation, "Transportation:");

		add(nameField);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(money);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(labeledHome);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(labeledLocation);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(labeledPosition);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(labeledProperty);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(labeledTransportation);
		add(Box.createRigidArea(new Dimension(0,15)));
		add(finalize);



	}



	public class MyLabeledUnit extends JPanel{
		Component component;
		String s;
		JLabel label;
		public MyLabeledUnit(Component o, String s){
			component = o;
			o.setMaximumSize(new Dimension(150, 25));
			this.s = s;
			label = new JLabel(s);
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			add(label);
			add(o);
		}
		@Override
		public void setVisible(boolean b){
			component.setVisible(b);
			label.setVisible(b);
		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finalize){
			PersonGui newPersonGui = new PersonGui();
			PersonAgent newPerson = new PersonAgent(nameField.getText(), newPersonGui, mainScreen);
			newPersonGui.setAgent(newPerson);
			//newPersonGui.setColor(Color.green);
			mainScreen.addGui(newPersonGui);
			/*newPersonGui.xDestination = 500;
			newPersonGui.yDestination = 500;*/

			//setJob

			newPerson.setGui(newPersonGui);	
			newPerson.setRestaurants(mainScreen.getRestaurantList());
			newPerson.setMarkets(mainScreen.getMarketList());
			newPerson.setBank(mainScreen.getBank());

			//Give him money
			newPerson.addMoney(money.getValue()); //Evan: leaving money at 0 to test going to home ******


			//setJob
			//newPerson.setJob(jobPosition.getSelectedItem().toString(), TheCity.getBuildingFromString(jobPosition.getSelectedItem().toString()));//will this work?

			//setting Transportation
			newPerson.setPreferredTransportation(transportation.getSelectedItem().toString());


			//set home
			newPerson.setHome((Home)TheCity.getBuildingFromString("home"));

			newPerson.startThread();


			//Add person to CityClock, so they can receive messages about time
			CityClock.addPersonAgent(newPerson);

			//re-enable button
			reference.newPersonButton.setEnabled(true);


			//close the window
			dispose();
		}

		if(e.getSource() == jobLocation){
			if(jobLocation.getSelectedIndex() == 0){
				labeledPosition.setVisible(false);
			}

			if(jobLocation.getSelectedIndex()>0){
				labeledPosition.setVisible(true);
			}
		}
	}

	public void giveWindow(SimCityRun simCityRun) {
		// TODO Auto-generated method stub
		reference = simCityRun;
	}
}
