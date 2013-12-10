package simcity.restaurants.restaurant1.gui;

import simcity.restaurants.restaurant1.CashierRole;
import Gui.RoleGui;
import Gui.Screen;

/**
 * A Cashier gui so we can get the home screen
 * @author nikhil
 *
 */
public class CashierGui extends RoleGui {

    private CashierRole agent = null;

    public CashierGui(CashierRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
    }
}
