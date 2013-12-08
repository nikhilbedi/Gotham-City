package simcity.restaurants.restaurant4.Restaurant4Gui;

import simcity.restaurants.restaurant4.Restaurant4HostRole;
import Gui.RoleGui;
import Gui.Screen;

/**
 * A host gui so we can get the home screen
 * @author nikhil
 *
 */
public class HostGui extends RoleGui {

    private Restaurant4HostRole agent = null;

    public HostGui(Restaurant4HostRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
    }
}
