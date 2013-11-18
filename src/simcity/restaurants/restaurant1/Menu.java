package simcity.restaurants.restaurant1;

import simcity.tests.mock.*;
import agent.Agent;
import simcity.restaurants.restaurant1.WaiterRole;
import simcity.restaurants.restaurant1.CustomerRole;
import simcity.restaurants.restaurant1.Menu;
import simcity.restaurants.restaurant1.gui.HostGui;
import simcity.restaurants.restaurant1.interfaces.*;
import agent.Role;

import java.util.*;

//Does it make more sense to make a menu protected?
public class Menu {
    public Map<String, Double> choices;

    public Menu() {
    	choices = Collections.synchronizedMap(new HashMap<String, Double>());
    	//the number dictates the prices
    	choices.put("Salad", 5.0);
    	choices.put("Pizza", 10.0);
		choices.put("Steak", 15.0);
    }
}