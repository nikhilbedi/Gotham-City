package Gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;

import simcity.PersonAgent;

public class InfoPanel extends JPanel {
	JLabel welcome;	
	JLabel name;
	JLabel location;
	
	public InfoPanel(){
		setPreferredSize(new Dimension(1000,50));
		setMinimumSize(new Dimension(1000,50));
		setMaximumSize(new Dimension(1000,50));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		welcome = new JLabel ("Welcome to Arkham City");
		welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(welcome);
	}
	public void updateInfoPanel(PersonAgent p){//update this method to display any information you want from a person.

		this.removeAll();
		//remove(welcome);
		
		name = new JLabel("Name: " + p.getName());
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		location = new JLabel("Location: " + p.getCurrentBuilding().toString());
		location.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(name);
		add(Box.createRigidArea(new Dimension(10,10)));
		add(location);
		validate();
	}
}
