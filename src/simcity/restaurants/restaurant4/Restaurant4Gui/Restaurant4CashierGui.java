package simcity.restaurants.restaurant4.Restaurant4Gui;

import simcity.restaurants.restaurant4.Restaurant4CashierRole;
import Gui.RoleGui;
import Gui.Screen;

/**
 * A Cashier gui so we can get the home screen
 * @author nikhil
 *
 */
public class Restaurant4CashierGui extends RoleGui {

    private Restaurant4CashierRole agent = null;

    public Restaurant4CashierGui(Restaurant4CashierRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
    }
}
