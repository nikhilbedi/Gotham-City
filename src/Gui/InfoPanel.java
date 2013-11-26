package Gui;

import javax.swing.*;

import simcity.PersonAgent;

public class InfoPanel extends JPanel {
	JLabel welcome;	
	public InfoPanel(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		welcome = new JLabel ("Welcome to Arkham City");
		add(welcome);
	}
	public void updateInfoPanel(PersonAgent p){
		
	}
}
