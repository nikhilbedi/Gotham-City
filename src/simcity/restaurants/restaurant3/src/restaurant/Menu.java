package simcity.restaurants.restaurant3.src.restaurant;

import java.util.ArrayList;


public class Menu {

	public ArrayList<Food> foods;
	
	public Menu () {
		foods = new ArrayList<Food>();
		
		Food f = new Food("Salad");
		foods.add(f);
		
		f = new Food("Pizza");
		foods.add(f);
		
		f = new Food("Chicken");
		foods.add(f);
		
		f = new Food("Steak");
		foods.add(f);
		
		
	}	
}
