package simcity.restaurants.restaurant2.gui;

import simcity.restaurants.restaurant2.CashierRole;
import Gui.RoleGui;
import Gui.Screen;

/**
 * A Cashier gui so we can get the home screen
 * @author nikhil
 *
 */
public class Restaurant2CashierGui extends RoleGui {

    private CashierRole agent = null;

    public Restaurant2CashierGui(CashierRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
        xPos = -10;
        yPos = -10;
    }
}
