package Gui;

import javax.swing.*;


public class CityMenuBar extends JMenuBar {
	JMenu menu, submenu;
	JMenuItem menuItem;
	public CityMenuBar(){
		menu = new JMenu("File");
		menuItem = new JMenuItem("Open XML");
		
		menu.add(menuItem);
		
		add(menu);
	}
}


