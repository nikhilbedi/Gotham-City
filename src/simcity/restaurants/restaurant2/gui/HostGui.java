package simcity.restaurants.restaurant2.gui;


import simcity.restaurants.restaurant2.HostRole;
import Gui.RoleGui;
import Gui.Screen;

public class HostGui extends RoleGui {
	private HostRole host = null;
	
	public HostGui(HostRole agent, Screen s) {
		super(agent, s);
		host = agent;
		xPos = -10;
		yPos = -10;
	}
}
