package Gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import simcity.Building;
import simcity.TheCity;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
//import simcity.TheCity;

public class BuildingControlPanel extends JPanel {
	List<Building> buildings = TheCity.getBuildings();
	public BuildingControlPanel(){
		for(Building b: buildings){
			add(new JToggleButton("open"));
		}
		add(new JToggleButton("CHAOS"));
		setPreferredSize(new Dimension(200,600));
		setMinimumSize(new Dimension(200,600));
		setMaximumSize(new Dimension(200,600));
	}
}
 