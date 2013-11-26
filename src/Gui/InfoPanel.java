package Gui;

import javax.swing.*;

import simcity.PersonAgent;

public class InfoPanel extends JPanel {
	JLabel welcome;	
	JLabel name;
	JLabel location;
	
	public InfoPanel(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		welcome = new JLabel ("Welcome to Arkham City");
		add(welcome);
	}
	public void updateInfoPanel(PersonAgent p){//update this method to display any information you want from a person.

		System.err.println(this.getComponentCount());
		this.removeAll();
		System.err.println(this.getComponentCount());
		
		remove(welcome);
		name = new JLabel(p.getName());
		location = new JLabel(p.getLocation().toString());
		add(name);
		add(location);
		System.err.println(this.getComponentCount());
		validate();
	}
}
