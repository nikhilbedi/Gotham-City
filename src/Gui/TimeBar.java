package Gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import simcity.CityClock;

public class TimeBar extends JPanel{
	JProgressBar bar;
	JLabel dayLabel;

	public TimeBar(){
		dayLabel = new JLabel("Sunday");
		dayLabel.setLabelFor(bar);
		bar = new JProgressBar(1, 24);
		bar.setValue(1);
		bar.setString("1:00");
		bar.setPreferredSize(new Dimension(775, 15));
		bar.setMaximumSize(new Dimension(775, 15));
		bar.setMinimumSize(new Dimension(775, 15));

		bar.setUI(new BasicProgressBarUI() {
			protected Color getSelectionBackground() { return Color.black; }
			protected Color getSelectionForeground() { return Color.white; }
		});

		bar.setBackground(Color.WHITE);
		bar.setForeground(Color.YELLOW);
		//bar.set
		bar.setStringPainted(true);
		add(dayLabel);
		add(bar);
		
	}

	public void updateTime(){
		if(bar.getValue() > 23){
			bar.setValue(1);
			bar.setString("1:00");
			bar.setForeground(Color.YELLOW);
		}
		else{
			bar.setValue(bar.getValue() + 1);
			bar.setString("" + (bar.getValue()) + ":00");
		}
		if(bar.getValue()==12){
			bar.setForeground(Color.GRAY);
		}
		int temp = CityClock.getDay();
		switch(temp){
		case 0:
			dayLabel.setText("Sunday");
			break;
		case 1:
			dayLabel.setText("Monday");
			break;
		case 2:
			dayLabel.setText("Tuesday");
			break;
		case 3:
			dayLabel.setText("Wednesday");
			break;
		case 4:
			dayLabel.setText("Thursday");
			break;
		case 5:
			dayLabel.setText("Friday");
			break;
		case 6:
			dayLabel.setText("Saturday");
			break;	

		default:
			dayLabel.setText("Quatilsday");
			break;
		}
	}
}
