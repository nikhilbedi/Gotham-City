package Gui;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import simcity.Building;
import simcity.TheCity;
import trace.AlertLog;
import trace.AlertTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
//import simcity.TheCity;

public class BuildingControlPanel extends JPanel implements ActionListener {
	List<Building> buildings = TheCity.getBuildings();
	List<JLabel> labels;
	List<LabeledToggleButton> buttons;
	
	public BuildingControlPanel(){

		labels = new ArrayList<JLabel>();
		buttons = new ArrayList<LabeledToggleButton>();
		
		//refresh();
		for(Building b: buildings){
			if(b.getName().contains("Apartment") ||b.getName().contains("Home")){
			}
			else {
				if(b.isOpen()){
					LabeledToggleButton button = new LabeledToggleButton(b.getName(), ": OPEN");
					button.addActionListener(this);
					buttons.add(button);
					add(button);
				}
				else{
					LabeledToggleButton button = new LabeledToggleButton(b.getName(), ": CLOSED");
					button.addActionListener(this);
					buttons.add(button);
					add(button);
				}
			//else
				//add(new LabeledToggleButton(b.getName() + ": OPEN"));
			}
			
		}
		add(new LabeledToggleButton("CHAOS", "!"));
		
		//add(new JToggleButton("CHAOS"));
		
		//setAlignmentX(RIGHT_ALIGNMENT);
		//add(new JToggleButton("CHAOS"));
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.err.println("" + e.getSource());
		System.err.println("" + buttons.get(0).t);
		//if(e.getStateChange() == ItemEvent.SELECTED)
			
        	
			for(int i = 0; i < buttons.size(); i++){
				if(e.getSource() == buttons.get(i).t){
						TheCity.getBuildingFromString(buttons.get(i).building).toggleLock();
						AlertLog.getInstance().logInfo(AlertTag.GUI,
								"BCP", " buttons" + i);
						System.err.println("Value is:" + buttons.get(i).building);
				}
			}
        	
			
        }
       // else
        //{
        	
        //}
		
	//}

	

public void refresh() {
	//removeAll();
	AlertLog.getInstance().logError(AlertTag.RESIDENT_ROLE,
			"BCP", "We logging");
	
	for (LabeledToggleButton ltb : buttons) {
		//AlertLog.getInstance().logError(AlertTag.RESIDENT_ROLE,
			//	"BCP", ltb.building);
		
		Building temp = TheCity.getBuildingFromString(ltb.building);
			if(temp.isOpen()){
				ltb.oc.setText(ltb.building);
				ltb.l.setText( ": OPEN");
			}
			else{
				AlertLog.getInstance().logError(AlertTag.RESIDENT_ROLE,
						this.getName(), "We logging in");
				ltb.oc.setText(ltb.building);
				ltb.l.setText( ": CLOSED");
			}
		}
		
	}
}
class LabeledToggleButton extends JPanel{
	 JToggleButton t;
	 JLabel l;
	 public String building;
	 JLabel oc;
	
	LabeledToggleButton(String b, String s)
	{
		building = b;
		setPreferredSize(new Dimension(400,40));
		setMinimumSize(new Dimension(400,40));
		setMaximumSize(new Dimension(400,40));
		setAlignmentX(LEFT_ALIGNMENT);
		setVisible(true);
		oc = new JLabel(b);
		l = new JLabel(s);
		t = new JToggleButton("Lock");
		l.setLabelFor(t);
		oc.setLabelFor(t);
		add(oc);
		add(l);
		add(t);
		
		
		
	}
	void addActionListener(ActionListener c)
	{
		t.addActionListener(c);
	}
}