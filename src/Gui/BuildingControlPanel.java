package Gui;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

import simcity.Building;

import java.awt.Dimension;
import java.util.*;
import simcity.TheCity;

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
