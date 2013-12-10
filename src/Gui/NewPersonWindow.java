package Gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;

import simcity.CityClock;
import simcity.PersonAgent;
import simcity.PersonGui;
import simcity.Home.Apartment;

import simcity.TheCity;
import simcity.Home.Home;
import simcity.Market.MarketCustomerRole;
import simcity.Market.MarketGui.MarketAnimationPanel;
import trace.*;
import simcity.interfaces.Person;

public class NewPersonWindow extends JFrame implements ActionListener {
	static final int WINDOWX = 300;
	static final int WINDOWY = 500;

	JTextField nameField;

	JSlider money;

	Vector<String> homeList;
	Vector<String> jobLocationList;
	HashMap<String, Vector<String>> buildingJobsMap;
	
	Vector<Vector<String>> jobPositionList;
	Vector<String> transportationList;
	Vector<String> homeOwnerList;

	JComboBox home;
	JComboBox jobLocation;
	JComboBox jobPosition;
	JComboBox homeOwned;
	JComboBox transportation;

	JButton finalize;

	//MainScreen mainScreen;

	JButton reference;

	JPanel interactionPanel;
	JPanel labelsPanel;
	
	PersonSelectionPane selPane;

	public MyLabeledUnit labeledPosition;
	

	public NewPersonWindow(JButton b, PersonSelectionPane pane){
		setSize(WINDOWX, WINDOWY);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	reference.setEnabled(true);
		        dispose();
		    }
		});

		reference = b;
		selPane = pane;

		setBounds(1000, 200, WINDOWX, WINDOWY);


		BoxLayout windowLayout = new BoxLayout((Container) this.getContentPane(), BoxLayout.Y_AXIS);
		setLayout(windowLayout);

		nameField = new JTextField("Person 0");
		nameField.setMaximumSize(new Dimension(WINDOWX, 10));

		homeList = new Vector<String>(TheCity.getHomes()); //will pop the home from the list if someone is living in it. 
		jobLocationList = new Vector<String>(TheCity.getJobLocs());
		jobPositionList = new Vector<Vector<String>>(TheCity.getPos());
		transportationList = new Vector<String>(TheCity.getTransportation());
		homeOwnerList = new Vector<String>(TheCity.getProperty());
		buildingJobsMap = new HashMap<String,Vector<String>>(TheCity.getJobMap());

		home = new JComboBox(homeList);
		jobLocation = new JComboBox(jobLocationList);
		jobLocation.addActionListener(this);
		jobPosition = new JComboBox();//updatethis jones TODO
		transportation = new JComboBox(transportationList);
		homeOwned = new JComboBox(homeOwnerList);


		money = new JSlider(JSlider.HORIZONTAL, 250, 1000, 1000);
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
		add(new JLabel("Money"));
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
	
	public void updateDefaultText(String s){
		nameField.setText(s);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finalize){
			MainScreen mainScreen = ScreenFactory.getMainScreen();
			PersonGui newPersonGui = new PersonGui();
			PersonAgent newPerson = new PersonAgent(nameField.getText(), newPersonGui, mainScreen);
			newPersonGui.setAgent(newPerson);
			//newPersonGui.setColor(Color.green);
			mainScreen.addGui(newPersonGui);
			
			//setJob

			newPerson.setGui(newPersonGui);	
			newPerson.setRestaurants(TheCity.getRestaurantList());
			newPerson.setMarkets(TheCity.getMarketList());
			newPerson.setBank(TheCity.getBank());
			newPerson.setGrid(TheCity.getGrid()); //Brice - setting grid for the new person to navigate City screen
			
			//Give him money
			newPerson.addMoney(money.getValue()); 

			//Just realized this is entire class is created at every call, meaning we cant set things disable when needed. - Nikhil
		/*	if(home.getSelectedItem().toString().equalsIgnoreCase("Home 1")) {
				//set home
				newPerson.setHome((Home)TheCity.getBuildingFromString("home"));
				home.setEnabled(false);
			}*/

			//setJob
			if(!jobLocation.getSelectedItem().toString().equalsIgnoreCase("no job")){ //Not sure how your code works, but this might do the trick - Nikhil
				newPerson.setJob(jobLocation.getSelectedItem().toString(), TheCity.getBuildingFromString(jobLocation.getSelectedItem().toString()));//will this work?
				//I had to change instance names and add this if statement, but yes, it will work
			}
				
			//setting Transportation

			System.out.println("Preferred Transportation: " + transportation.getSelectedItem().toString());
			//newPerson.setPreferredTransportation(transportation.getSelectedItem().toString());

			newPerson.setPreferredTransportation("Bus");



			//set home

			if(CityClock.getPeople().size() < 1)
				newPerson.setHome((Home)TheCity.getBuildingFromString("Home"));
				//newPerson.setHome((Apartment)TheCity.getBuildingFromString("Apartment 1"));

			
			
			newPerson.startThread();


			//Add person to CityClock, so they can receive messages about time
			CityClock.addPersonAgent(newPerson);

			//re-enable button
			reference.setEnabled(true);
			//System.err.println("atGui" + newPerson.getMyHome().getName());
			selPane.refresh();
			
			AlertLog.getInstance().logInfo(AlertTag.GUI, "Window", "Adding New Person: " + newPerson.getName() );
			//close the window
			dispose();
			
		}

		if(e.getSource() == jobLocation){
			if(jobLocation.getSelectedIndex() == 0){
				labeledPosition.setVisible(false);
			}

			if(jobLocation.getSelectedIndex()>0){
				labeledPosition.setVisible(true);
				JComboBox box;
				box = (JComboBox) labeledPosition.component;
				box.removeAllItems();
				
				//the -1 is because the initial value in the box is no Job and is not in the jobLocation data structure
				for(int i=0; i < jobPositionList.get(jobLocation.getSelectedIndex()).size(); i++){
					//box.addItem(buildingJobsMap.get(box.getSelectedItem().toString()).get(i));
					box.addItem(jobPositionList.get(jobLocation.getSelectedIndex()).get(i));
				}
			}
		}
	}

	

}
