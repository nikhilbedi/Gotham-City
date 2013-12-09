package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.HostRole;
import Gui.RoleGui;
import Gui.Screen;

/**
 * A host gui so we can get the home screen
 * @author nikhil
 *
 */
public class HostGui extends RoleGui {

    private HostRole agent = null;

    public HostGui(HostRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
        xPos = -10;
        yPos = -10;
    }
}
