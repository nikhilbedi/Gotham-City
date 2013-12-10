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
import simcity.TheCity;
import simcity.bank.Bank;
import trace.AlertLog;
import trace.AlertTag;


public class BuildingInfoPanel extends JPanel implements ChangeListener{
	List<JLabel> info;
	List<LabeledSpinner> updators;
	Building b;
	
	

	public BuildingInfoPanel(){
		setBackground(Color.white);
		
		setPreferredSize(new Dimension(200,600));
		setMinimumSize(new Dimension(200,600));
		setMaximumSize(new Dimension(200,600));
		
		info = new ArrayList<JLabel>();
		updators = new ArrayList<LabeledSpinner>();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		b = null;
		

		updateInfo();
	}
	
	public Building getB() {
		return b;
	}

	public void setB(Building b) {
		this.b = b;
	}

	public void setBuildingAndUpdate(Building b){
		this.b = b;
		refresh();
		
	}
	public void addField(String s, int num){
		LabeledSpinner newSpin = new LabeledSpinner(s, num);
		newSpin.addChangeListener(this);
		updators.add(newSpin);
		add(newSpin);
	}
	
	public void getUpdators(Building b){
		Vector<Item> spinnerItems = b.getStockItems();
		
		//adds bank rob button probably should go somewhere else
		if(b.getName().equalsIgnoreCase("Bank")){
			//AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel", "In bank check");
			//add(new JButton("Robbberyy"));
		}
		
		//debug
		if(spinnerItems == null){
			//AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel", "Getupdatators called on invalid instance of Building");
			return;
		}
		
		//parses building to create the spinner items list
		for(int i=0; i < spinnerItems.size(); i++){
			Item temp = spinnerItems.get(i);
			addField(temp.getName(), temp.getAmount());
		}
		
		for (LabeledSpinner updater : updators) {
			//add(updater);
		}
		
	}
	
	public void updateInfo(){
		removeAll();
		for(JLabel label: info){
			label.setAlignmentX(CENTER_ALIGNMENT);
			add(label);
		}
	}
	
	public void inputInfo(Vector<String> strings){
		info.clear();
		for(String s: strings){
			info.add(new JLabel(s));
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		System.out.println(""+ updators.get(0).spin);
		System.out.println("" + e.getSource());
		for(int i=0; i < updators.size(); i ++){
			if(e.getSource() == updators.get(i).spin ){
					JSpinner temp = updators.get(i).spin;
				   //AlertLog.getInstance().logInfo(AlertTag.GUI, "BuildingInfoPanel",
					//"Changing spinner" + i);
				  // System.out.println("Value is" + temp.getValue().hashCode());
				   b.updateItem(updators.get(i).l.getText(),temp.getValue().hashCode());
			   }
		}
	   
	}
	class LabeledSpinner extends JPanel{
		
		JLabel l;
		JSpinner spin;
		LabeledSpinner(String s, int initial)
		{
			setPreferredSize(new Dimension(200,40));
			setMaximumSize(new Dimension(200,40));
			setBackground(Color.red);
			setVisible(true);
			l = new JLabel(s);
			spin = new JSpinner(new SpinnerNumberModel(initial, 0, 100, 1));
			l.setLabelFor(spin);
			add(l);
			add(spin);
			
		}
		void addChangeListener(ChangeListener c)
		{
			spin.addChangeListener(c);
		}
	}
	public void refresh(){
		if(b == null){
			info.clear();
			info.add(new JLabel("Select a building to see info"));
			updateInfo();
			repaint();
			return;
		}
		inputInfo(b.getBuildingInfo());
		updateInfo();
		getUpdators(b);
		repaint();
	}
}