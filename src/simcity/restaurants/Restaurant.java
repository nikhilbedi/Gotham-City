package simcity.restaurants;

import simcity.Building;

/**
 * This base class is necessary so we can hold all of our restaurants as similar objects, 
 * they are all very different
 * @author nikhil
 *
 */
public class Restaurant extends Building {

	public Restaurant(String type, int x, int y) {
		super(type, x, y);
		// TODO Auto-generated constructor stub
	}
	//Location
	//annnddd, that's probably it. We will all have different hosts and everything
}