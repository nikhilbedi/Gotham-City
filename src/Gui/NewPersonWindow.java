package Gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import simcity.PersonAgent;
import simcity.interfaces.Person;

public class NewPersonWindow extends JFrame implements ActionListener {
	static final int WINDOWX = 300;
	static final int WINDOWY = 500;

	JTextField nameField;

	JSlider money;

	JComboBox home;
	JComboBox jobLocation;
	JComboBox jobPosition;
	JComboBox homeOwned;
	JComboBox transportation;

	JButton finalize;

	Screen mainScreen;


	public NewPersonWindow(Screen city){
		setSize(WINDOWX, WINDOWY);


		mainScreen = city;

		setBounds(1000, 200, WINDOWX, WINDOWY);
		BoxLayout layout = new BoxLayout((Container) this.getContentPane(), BoxLayout.PAGE_AXIS);
		setLayout(layout);


		nameField = new JTextField("Name Field");
		nameField.setMaximumSize(new Dimension(WINDOWX, 10));
		String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };

		home = new JComboBox(petStrings);
		jobLocation = new JComboBox(petStrings);
		jobPosition = new JComboBox(petStrings);
		homeOwned = new JComboBox(petStrings);
		transportation = new JComboBox(petStrings);


		money = new JSlider(JSlider.HORIZONTAL, 0, 1000, 100);
		money.setMajorTickSpacing(100);
		money.setMinorTickSpacing(100);
		money.setPaintTicks(true);
		money.setPaintLabels(true);

		finalize = new JButton("done");
		finalize.addActionListener(this);

		add(nameField);
		add(money);
		add(home);
		add(jobLocation);
		add(jobPosition);
		add(homeOwned);
		add(transportation);
		add(finalize);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == finalize){
			Person newPerson = new PersonAgent(nameField.getText());
			RoleGui newPersonGui = new RoleGui();
			newPersonGui.setColor(Color.green);
			mainScreen.addGui(newPersonGui);
			newPersonGui.xDestination = 500;
			newPersonGui.yDestination = 500;

			//setGui(RoleGui g)	
			//setRestaurants(List<Restaurant> r)
			//setMarkets(List<Market> m)
			//setBank(Bank b)
			//setHome(Home h)


		}

	}
}
