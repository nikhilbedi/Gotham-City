package simcity.restaurants.restaurant5;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;

public class Menu {
	Map<Integer, String> map = new HashMap<Integer, String>();
	Map<String, Double> prices = new HashMap<String, Double>();
	
	public Menu(){
		map.put(new Integer(0), "Steak");
		map.put(new Integer(1), "Chicken");
		map.put(new Integer(2), "Salad");
		map.put(new Integer(3), "Pizza");
		
		prices.put("Steak", new Double(15.99));
		prices.put("Chicken", new Double(10.99));
		prices.put("Salad", new Double(5.99));
		prices.put("Pizza", new Double(8.99));
		
	}
	public int size(){
		return map.size();
	}
	
	public String get(int i){
		return map.get(i);
	}
	public double getPrice(String s){
		return prices.get(s);
	}
	
	public int nameToInt(String s){
		for(int i=0; i<map.size(); i++){
			if(map.get(i) == s){
				return i;
			}
		}
		return -1;
	}
	
	public void remove(String s){
		//map.remove(s);
		map.remove(nameToInt(s));
	}
	
	public Vector<String> returnList(){
		Vector<String> items = new Vector<String>();
		items.add("Steak");
		items.add("Chicken");
		items.add("Salad");
		items.add("Pizza");
		return items;
	}
}