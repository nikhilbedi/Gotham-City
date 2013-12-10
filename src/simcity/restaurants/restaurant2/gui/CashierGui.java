package simcity.restaurants.restaurant2.gui;

import simcity.restaurants.restaurant2.CashierRole;
import Gui.RoleGui;
import Gui.Screen;

public class CashierGui extends RoleGui {
	private CashierRole agent = null;

    public CashierGui(CashierRole agent, Screen s) {
    	super(agent, s);
        this.agent = agent;
    }
}
