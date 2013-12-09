package Gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

import simcity.Building;
import simcity.bank.Bank;
import trace.AlertLog;
import trace.AlertTag;


public class BuildingInfoPanel extends JPanel{
	List<JLabel> info;
	List<JSpinner> updators;
	JSpinner spinner;
	public BuildingInfoPanel(){
		setBackground(Color.orange);
		
		setPreferredSize(new Dimension(200,600));
		setMinimumSize(new Dimension(200,600));
		setMaximumSize(new Dimension(200,600));
		
		info = new ArrayList<JLabel>();
		updators = new ArrayList<JSpinner>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		update();
	}
	
	public void addField(String s){
		JPanel c = new JPanel();
		c.setPreferredSize(new Dimension(200,40));
		c.setMaximumSize(new Dimension(200,40));
		c.setBackground(Color.orange);
		c.setVisible(true);
		JLabel l = new JLabel(s);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
		l.setLabelFor(spinner);
		c.add(l);
		c.add(spinner);
		add(c);
	}
	
	public void getUpdators(Building b){
		Vector<String> spinnerNames = b.getStockItems();
		System.err.println("This is a test name is " + b.getName());
		if(b.getName().equalsIgnoreCase("Bank")){
			AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel", "In bank check");
			add(new JButton("Robbberyy"));
		}
		if(spinnerNames == null){
			return;
		}
		for(int i=0; i < spinnerNames.size(); i++){
			addField(spinnerNames.get(i));
		}
		
	}
	
	public void update(){
		removeAll();
		for(JLabel label: info){
			label.setAlignmentX(CENTER_ALIGNMENT);
			add(label);
		}
	}
	public void update(Vector<String> strings){
		info.clear();
		for(String s: strings){
			info.add(new JLabel(s));
		}
		update();
	}

}