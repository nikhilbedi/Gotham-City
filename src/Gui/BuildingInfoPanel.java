package Gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simcity.Building;
import simcity.Item;
import simcity.bank.Bank;
import trace.AlertLog;
import trace.AlertTag;


public class BuildingInfoPanel extends JPanel implements ChangeListener{
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
	
	public void addField(String s, int num){
		JPanel c = new JPanel();
		c.setPreferredSize(new Dimension(200,40));
		c.setMaximumSize(new Dimension(200,40));
		c.setBackground(Color.orange);
		c.setVisible(true);
		JLabel l = new JLabel(s);
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(num, 0, 100, 1));
		l.setLabelFor(spinner);
		c.add(l);
		c.add(spinner);
		add(c);
	}
	
	public void getUpdators(Building b){
		Vector<Item> spinnerItems = b.getStockItems();
		if(b.getName().equalsIgnoreCase("Bank")){
			AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel", "In bank check");
			add(new JButton("Robbberyy"));
		}
		if(spinnerItems == null){
			AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel", "Getupdatators called on invalid instance of Building");
			return;
		}
		for(int i=0; i < spinnerItems.size(); i++){
			Item temp = spinnerItems.get(i);
			addField(temp.getName(), temp.getAmount());
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		//here's where we detect a change?
	}

}