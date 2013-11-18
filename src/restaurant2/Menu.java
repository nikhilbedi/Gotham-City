package restaurant2;
import java.util.*;

public class Menu {
	List<String> foods = new LinkedList<String> (Arrays.asList("Steak", "Chicken", "Salad", "Pizza"));
	
	public Menu() {
		//foods = Arrays.asList("Steak", "Chicken", "Salad", "Pizza");
	}
	
	public void addFood(String newFood) {
		foods.add(newFood);
	}
	
	public void removeFood(String food) {
		foods.remove(food);
	}
	
	public String getFood(int index) {
		return foods.get(index);
	}
	
	public double getPrice(String food) {
		switch(food) {
		case "Steak": return 15.99;
		case "Salad": return 5.99; 
		case "Chicken": return 10.99; 
		case "Pizza": return 8.99; 
		}
		return 0;
	}
	
	public int getNumFoods() { return foods.size();}
}
