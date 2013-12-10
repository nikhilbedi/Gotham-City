package simcity.restaurants.restaurant3.gui;
import java.awt.Color;

import simcity.restaurants.restaurant3.Restaurant3CashierRole;
import simcity.restaurants.restaurant3.interfaces.Cashier;
import simcity.restaurants.restaurant5.CookRole;
import simcity.restaurants.restaurant5.interfaces.Cook;
import Gui.RoleGui;
import Gui.Screen;
import agent.*;

public class CashierGui extends RoleGui {

	    private Restaurant3CashierRole agent = null;

	    public CashierGui(Cashier agent, Screen s) {
	    	super((Role)agent, s);
	        this.agent = (Restaurant3CashierRole)agent;
	    }

}
