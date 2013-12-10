package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import simcity.CityClock;


public class CityMenuBar extends JMenuBar implements ActionListener{
	JMenu menu, submenu;
	JMenuItem xml, clear;
	JFileChooser fc;
	public CityMenuBar(){
		menu = new JMenu("File");
		fc = new JFileChooser();
		
		//this is machine dependant
		fc.setCurrentDirectory(new File("C:/Users/Hunter/github"));


		xml = new JMenuItem("Open XML");
		xml.addActionListener(this);
		clear = new JMenuItem("Clear Agents");
		clear.addActionListener(this);

		menu.add(xml);
		//menu.add(clear);

		add(menu);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == clear){
			CityClock.clearCity();
		}
		if (e.getSource() == xml) {
			int returnVal = fc.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.out.println(file.getAbsolutePath());
				System.out.println(file.getName());
				XMLHelper.createPeople(file.getName());
				//This is where a real application would open the file.
				//log.append("Opening: " + file.getName() + "." + newline);
			} else {
				//log.append("Open command cancelled by user." + newline);
			}
			//log.setCaretPosition(log.getDocument().getLength());*/

		}
	}
}


