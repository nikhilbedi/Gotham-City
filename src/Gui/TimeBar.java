package Gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class TimeBar extends JPanel{
	JProgressBar bar;
	public TimeBar(){
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
	}
}
